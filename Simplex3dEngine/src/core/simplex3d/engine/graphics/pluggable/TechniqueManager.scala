/*
 * Simplex3dEngine - Renderer Module
 * Copyright (C) 2011-2012, Aleksey Nikiforov
 *
 * This file is part of Simplex3dEngine.
 *
 * Simplex3dEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.engine
package graphics.pluggable

import java.util.logging._
import java.util.HashMap
import java.util.HashSet
import java.util.Stack
import scala.collection._
import scala.collection.mutable.ArrayBuffer
import simplex3d.engine.util._
import simplex3d.engine.graphics._


class TechniqueManager[G <: GraphicsContext](implicit val graphicsContext: G)
extends graphics.TechniqueManager[G]
{
  import TechniqueManager.logger._
 
  val glslVersion = "120"//XXX version must come from profile
  val passManager = new PassManager[G]//XXX stub
  
  
  protected class Stage(val name: String) {
    val functions = new HashMap[String, ArrayBuffer[ShaderPrototype]]
    
    // The key is out{} block name, "" for no-output.
    val main = new HashMap[String, ArrayBuffer[ShaderPrototype]]
    
    // The key is out{} block name, "" for no-output.
    val interface = new HashMap[String, ArrayBuffer[ShaderPrototype]]
    
    
    def push(shader: ShaderPrototype) {
      //XXX Verify unique in/out block name has the same declarations,
      //XXX special treatment for gl_Position => gl_FragCoord
      
      def insert(map: HashMap[String, ArrayBuffer[ShaderPrototype]], key: String, shader: ShaderPrototype) {
        var list = map.get(key)
        if (list == null) {
          list = new ArrayBuffer[ShaderPrototype]
          map.put(key, list)
        }
        list.insert(0, shader)
      }
      
      if (shader.mainLabel.isDefined) {
        if (shader.mainOutput.isDefined) insert(main, shader.mainOutput.get.name, shader)
        else {
          val key = if (shader.outputBlock.isDefined) {
            val declarations = shader.outputBlock.get.declarations
            
            if (declarations.size == 1 && declarations.head.name == "gl_FragColor") ""
            else shader.outputBlock.get.name
          }
          else ""
          
          insert(interface, key, shader)
        }
      }
      else {
        insert(functions, shader.functionSignature.get, shader)
      }
    }
  }
  
  protected val stages = new Array[Stage](2)
  stages(0) = new Stage("Fragment")
  stages(1) = new Stage("Vertex")
  
  
  def push(shader: ShaderDeclaration) {
    shader match {
      case _: FragmentShader => stages(0).push(shader.toPrototype(glslVersion))
      case _: VertexShader => stages(1).push(shader.toPrototype(glslVersion))
    }
  }
  
  
  private[this] val dummyPredefined = new PredefinedUniforms()
  
  
  def resolveTechnique(
      meshName: String,
      geometry: G#Geometry, material: G#Material, worldEnvironment: G#Environment)
  :Technique =
  {
    
    def resolveShaderChain(
        stageId: Int, dependencyKey: String, shader: ShaderPrototype,
        resolving: HashSet[ShaderPrototype],
        acceptable: Array[HashMap[String, IndexedSeq[ShaderPrototype]]],
        rejected: HashSet[ShaderPrototype])
    :IndexedSeq[ShaderPrototype] =
    {
      if (rejected.contains(shader)) return IndexedSeq.empty[ShaderPrototype]
      
      if (resolving.contains(shader)) {
        log(Level.INFO,
          "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
          "' because of cyclic dependency."
        )
        
        rejected.add(shader)
        return IndexedSeq.empty[ShaderPrototype]
      }
      
      
      resolving.add(shader)
      val stage = stages(stageId)
      
      
      def checkAttributes() :Boolean = {
        val declarations = shader.attributeBlock
        
        var passed = true;
        var i = 0; while (passed && i < declarations.size) {
          passed = false
          val declaration = declarations(i)
          
          val attrib = graphicsContext.resolveAttributePath(declaration.name, geometry)
          passed = (attrib != null)//XXX check the type at runtime
          
          if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
            "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
            "' because the attribute '" + declaration.name + "' is not defined."
          )
          
          i += 1
        }
        
        passed
      }
      
      def checkUniform() :Boolean = {
        val declarations = shader.uniformBlock
        
        var passed = true
        var i = 0; while (passed && i < declarations.size) {
          passed = false
          val declaration = declarations(i)
          
          if (declaration.isPredefined && declaration.name == "se_pointSize") {
            passed = geometry.mode.get.isInstanceOf[PointSprites]
          }
          else {
            val resolved = graphicsContext.resolveUniformPath(
              declaration.name, dummyPredefined, material, worldEnvironment, shader.boundUniforms)
            
            passed = (resolved != null)//XXX check the type at runtime
            
            if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
              "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
              "' because the uniform '" + declaration.name + "' is not defined."
            )
          }
          
          i += 1
        }
        
        passed
      }
      
      def checkConditions() :Boolean = {
        val conditions = shader.conditions
        
        var passed = true
        var i = 0; while (passed && i < conditions.size) {
          passed = false
          val (path, condition) = conditions(i)
          
          val resolved =
            if (path == "mode") {
              geometry.mode.get
            }
            else {
              graphicsContext.resolveUniformPath(
                  path, dummyPredefined, material, worldEnvironment, shader.boundUniforms)
            }
              
          if (resolved != null) {
            passed = condition(resolved)
          }
          
          if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
            "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
            "' because the condition with path '" + path + "' was not satisfied."
          )
          
          i += 1
        }
        
        passed
      }
      
      def resolveFunctions() :Option[IndexedSeq[ShaderPrototype]] = {
        val requiredFunctions = shader.functionDependencies
        
        val chain = new ArrayBuffer[ShaderPrototype]
        var passed = true
        var i = 0; while (passed && i < requiredFunctions.size) {
          val requiredFunction = requiredFunctions(i)
          passed = false
          
          val existing = acceptable(stageId).get(requiredFunction)
          if (existing != null) {
            chain ++= existing
            passed = true
          }
          else {
            val matchingProviders = stage.functions.get(requiredFunction)
            var j = 0; while (matchingProviders != null && !passed && j < matchingProviders.size) {
              val functionProvider = matchingProviders(j)
              
              if (requiredFunction == functionProvider.functionSignature.get) {
                val subChain = resolveShaderChain(stageId, requiredFunction, functionProvider, resolving, acceptable, rejected)
                chain ++= subChain
                passed = !subChain.isEmpty
              }
              
              j += 1
            }
          }
          
          if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
            "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
            "' because the required function '" + requiredFunction + "' could not be resolved."
          )
          
          i += 1
        }
        
        if (passed) Some(chain) else None
      }
      
      def resolveMainVars() :Option[IndexedSeq[ShaderPrototype]] = {
        val inputBlocks = shader.mainInputs
        
        val chain = new ArrayBuffer[ShaderPrototype]
        var passed = true
        var i = 0; while (passed && i < inputBlocks.size) {
          val inputBlock = inputBlocks(i)
          passed = false
          
          val existing = acceptable(stageId).get(inputBlock.name)
          if (existing != null) {
            chain ++= existing
            passed = true
          }
          else {
            val matchingProviders = stage.main.get(inputBlock.name)
            var j = 0; while (matchingProviders != null && !passed && j < matchingProviders.size) {
              val outputShader = matchingProviders(j)

              val outBlock = outputShader.mainOutput
              val found = outBlock.isDefined && (outBlock.get.name == inputBlock.name)
              
              if (found) {
                val subChain = resolveShaderChain(stageId, inputBlock.name, outputShader, resolving, acceptable, rejected)
                chain ++= subChain
                passed = !subChain.isEmpty
              }
              
              j += 1
            }
          }
          
          if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
            "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
            "' because because the main input '" + inputBlock.name + "' could not be resolved."
          )
          
          i += 1
        }
        
        if (passed) Some(chain) else None
      }
      
      def resolveInputs() :Option[IndexedSeq[ShaderPrototype]] = {
        val inputBlocks = shader.inputBlocks
        val prevStageId = stageId + 1
        
        if (inputBlocks.isEmpty) return Some(IndexedSeq.empty[ShaderPrototype])
        if (prevStageId >= stages.size) None
        
        
        val prevStage = stages(prevStageId)
        
        
        val chain = new ArrayBuffer[ShaderPrototype]
        var passed = true
        var i = 0; while (passed && i < inputBlocks.size) {
          val inputBlock = inputBlocks(i)
          passed = false
          
          val existing = acceptable(prevStageId).get(inputBlock.name)
          if (existing != null) {
            chain ++= existing
            passed = true
          }
          else {
            val matchingProviders = prevStage.interface.get(inputBlock.name)
            var j = 0; while (matchingProviders != null && !passed && j < matchingProviders.size) {
              val outputShader = matchingProviders(j)
              
              val outBlock = outputShader.outputBlock
              val found = outBlock.isDefined && (outBlock.get.name == inputBlock.name)
              
              if (found) {
                val subChain = resolveShaderChain(stageId + 1, inputBlock.name, outputShader, resolving, acceptable, rejected)
                chain ++= subChain
                passed = !subChain.isEmpty
              }
              
              j += 1
            }
          }
          
          if (!passed && shader.logging.logRejected(meshName)) log(Level.INFO,
            "Shader '" + shader.logging.name + "' was rejected for mesh '" + meshName +
            "' because the input block '" + inputBlock.name + "' could not be resolved."
          )
          
          i += 1
        }
        
        if (passed) Some(chain) else None
      }
      
      // Chain order is important for dependency management, duplicate entries are allowed.
      val chain = new ArrayBuffer[ShaderPrototype]
      var passed = checkAttributes() && checkUniform() && checkConditions()
      
      if (passed) {
        passed = false
        
        val functionChain = resolveFunctions()
        if (functionChain.isDefined) {
          
          val mainVarChain = resolveMainVars()
          if (mainVarChain.isDefined) {
            
            val inputChain = resolveInputs()
            if (inputChain.isDefined) {
              chain ++= functionChain.get
              chain ++= mainVarChain.get
              chain ++= inputChain.get
              passed = true
            }
          }
        }
      }
      
      if (passed) {
        chain += shader
        acceptable(stageId).put(dependencyKey, chain)
        
        if (shader.logging.logAccepted(meshName)) log(
          Level.INFO, "Shader '" + shader.logging.name + "' was accepted for mesh '" + meshName + "'."
        )
        
        resolving.remove(shader)
        return chain
      }
      else {
        rejected.add(shader)
        resolving.remove(shader)
        return IndexedSeq.empty[ShaderPrototype]
      }
    }
  
    
    val fragmentStage = stages(0)
    val rootShaders = fragmentStage.interface.get("")
        
    var chain = IndexedSeq.empty[ShaderPrototype]
    var i = 0; while (chain.isEmpty && rootShaders != null && i < rootShaders.size) {
      val outputShader = rootShaders(i)
      
      chain = resolveShaderChain(
          0, "", outputShader,
          new HashSet[ShaderPrototype](),
          Array.fill(2)(new HashMap[String, IndexedSeq[ShaderPrototype]]),
          new HashSet[ShaderPrototype]())
          
      i += 1
    }
    
    
    //XXX
    if (!chain.isEmpty) {
      
      // Generate declaration map.
      val listDeclarationMap = new HashMap[ListNameKey, ListSizeKey]
      def processValue(value: AnyRef, name: String) {
        value match {
          
          case a: BindingList[_] =>
            val dec = new ListSizeKey(new ListNameKey("", name), a.size)
            val prev = listDeclarationMap.put(dec.nameKey, dec)
            if (prev != null && prev.size != dec.size) println("XXX log")
            
          case s: Struct =>
            var i = 0; while (i < s.listDeclarations.size) {
              val dec = s.listDeclarations(i)
              val sizeKey = dec.sizeKey()
              val prev = listDeclarationMap.put(dec.nameKey, sizeKey)
              if (prev != null && prev.size != sizeKey.size) println("XXX log")
              
              i += 1
            }
            
          case _ =>
            // ignore
        }
      }
      
      var i = 0; while (i < material.uniforms.size) {
        val prop = material.uniforms(i)
        if (prop.isDefined) processValue(prop.get, material.uniformNames(i))
        
        i += 1
      }
      
      i = 0; while (i < worldEnvironment.properties.size) {
        val prop = worldEnvironment.properties(i)
        if (prop.isDefined) processValue(prop.get.binding, worldEnvironment.propertyNames(i))
        
        i += 1
      }
      
      
      // Generate shader and technique keys. 
      val techniqueKey = new Array[(ShaderPrototype, IndexedSeq[ListSizeKey])](chain.size)
      
      i = 0; while (i < chain.size) {
        val shader = chain(i)
        val listKeys = shader.unsizedArrayKeys
        
        val resolved = new Array[ListSizeKey](listKeys.size)
        var j = 0; while (j < listKeys.size) {
          val key = listKeys(j)
          
          val listDeclaration = listDeclarationMap.get(key)
          assert(listDeclaration != null)
          resolved(j) = listDeclaration
          
          j += 1
        }
        
        techniqueKey(i) = Tuple2(shader, new ReadArray(resolved))
        
        i += 1
      }
      
      
      // Load cached technique or make a new one.
      // XXX better caching based on PropKey(geom: Seq[Boolean], mat: Seq[Boolean], env: Seq[Boolean])
      // followed by condition check, and secondary lookup based on arraySizeKey
      // Technique caching must not prevent GC.
      val readTechniqueKey = new ReadArray(techniqueKey)
      var technique = techniqueCache.get(readTechniqueKey)
      
      if (technique == null) {
        val shaders = new ArrayBuffer[Shader]
        
        var i = 0; while (i < techniqueKey.size) {
          val shaderKey = techniqueKey(i)
          
          var shader = shaderCache.get(shaderKey)
          if (shader == null) {
            val proto = chain(i)
            
            val listDeclarations = shaderKey._2
            val src = ShaderPrototype.Glsl2.shaderSrc(proto, listDeclarations)
            
            shader = new Shader(proto.shaderType, src, proto.boundUniforms)
            shaderCache.put(shaderKey, shader)
          }
          
          shaders += shader
          
          i += 1
        }
        
        shaders += genMain(Shader.Fragment, chain)
        shaders += genMain(Shader.Vertex, chain)
        
        technique = new Technique(graphicsContext, shaders.toSet)
        techniqueCache.put(readTechniqueKey, technique)
      }
      
      //println("XXX\n" + technique.shaders.map(_.src).mkString("\n\n*****************************************\n\n"))
      
      technique
    }
    else {
      null
    }
  }

  
  private[this] def genMain(shaderType: Shader.type#Value, chain: IndexedSeq[ShaderPrototype]) :Shader = {
    var body = ""
    var declarations = ""
    val mainVarBlocks = new mutable.HashSet[DeclarationBlock]
    
    var i = 0; while (i < chain.size) {
      val shaderPrototype = chain(i)
      
      if (shaderPrototype.shaderType == shaderType && shaderPrototype.mainLabel.isDefined) {
        mainVarBlocks ++= shaderPrototype.mainInputs
        mainVarBlocks ++= shaderPrototype.mainOutput
        
        declarations += ShaderPrototype.bodySignature(shaderPrototype)._2 + ";\n"//XXX cache bodySig
        body += "  " + ShaderPrototype.callSignature(shaderPrototype) + ";\n"
      }
      
      i += 1
    }
    
    
    val mainVars = for {
      declarationBlock <- mainVarBlocks
      declaration <- declarationBlock.declarations
    } yield {
      val varName = ShaderPrototype.mkRemapping(declarationBlock.name, declaration.name)._2
      (varName, "  " + declaration.glslType + " " + varName + ";")
    }
    
    val mainVarDeclarations = mainVars.toList.sortBy(_._1).map(_._2).mkString("\n")
      
    val shaderSrc =
      declarations + "\n" +
      "void main() {\n" +
        (if(mainVarDeclarations.isEmpty) "" else mainVarDeclarations + "\n\n") +
        body +
      "}\n"
    
    new Shader(shaderType, shaderSrc)
  }
  
  
  // XXX HashMap[(ShaderPrototype, Set[ListSizeKey]), Shader]
  private val shaderCache = new HashMap[(ShaderPrototype, IndexedSeq[ListSizeKey]), Shader]
  
  // XXX HashMap[Set[(ShaderPrototype, Set[ListSizeKey])], Technique]
  private val techniqueCache = new HashMap[IndexedSeq[(ShaderPrototype, IndexedSeq[ListSizeKey])], Technique]
}


object TechniqueManager {
  val logger = Logger.getLogger(this.getClass.getName)
}
