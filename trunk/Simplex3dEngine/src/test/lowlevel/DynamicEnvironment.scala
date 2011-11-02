/*
 * Simplex3dEngine - Test Package
 * Copyright (C) 2011, Aleksey Nikiforov
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

package test.lowlevel

import scala.collection.mutable.ArrayBuffer
import simplex3d.math.types._
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.algorithm.noise._
import simplex3d.algorithm.mesh._
import simplex3d.engine._
import simplex3d.engine.graphics._
import simplex3d.engine.bounding._
import simplex3d.engine.input._
import simplex3d.engine.input.handler._
import simplex3d.engine.scenegraph._
import simplex3d.engine.impl._


/** This test checks environment propagation under different conditions
 * by using color, intensity, and contrast as environmental properties.
 * 
 * This test is meant for debugging rather than a demonstration.
 * 
 * Colors:
 * Colors are used to test propagation by alternating red, green, and blue.
 * Since odd nodes are empty, this pattern gets inverted for visible meshes to red, blue, and green.
 * The last cube is red because its' closest two parents do not defined the color, so the last color gets inherited.
 * 
 * Intensity:
 * Intensity is set at the level of the second and fourth cubes. So when intensity property on the second cube
 * is undefined every 2 seconds, the second and third cubes reset to the default
 * while forth and fifth cubes hold the intensity value.
 * 
 * Contrast:
 * The contrast alternates between an array of one and two factors every 4 seconds.
 * This servers as a test for dynamic binding resolution, which allows the same environmental property to
 * resolve to different structs depending on the circumstances.
 * Contrast inherits from UpdatableEnvironmentalEffect which can alter its' binding uniquely for each mesh
 * depending on the camera matrices. When the camera is moved further from the meshes,
 * they gradually loose color and become white.
 * 
 * The printout indicates what Meshes have their techniques re-evaluated.
 * Notice that techniques do not change every frame, but only when the properties are altered in a way that requires
 * a different shader. The bindings are resolved only in response to technique changes.
 * These optimizations avoid expensive operations and results in exception performance
 * without sacrificing the flexibility. 
 */
object DynamicEnvironment extends app.App with impl.lwjgl.App with scala.App {
  val title = "Dynamic Environment"

  lazy val settings = new Settings(
    fullscreen = false,
    verticalSync = true,
    resolution = Some(Vec2i(800, 600))
  )

  addInputListener(new InputListener {
    override val keyboardListener = new KeyboardListener {
      override def keyTyped(input: Input, e: KeyEvent) {
        if (KeyCode.K_Escape == e.keyCode) dispose()
      }
    }
  })
  
  
  type TestT = simplex3d.engine.transformation.ComponentTransformation3dContext
  type TestG = testenv.GraphicsContext
  
  implicit final val TransformationContext = new TestT
  implicit final val GraphicsContext = new TestG
  
  
  val scene = new SceneGraph("World", new SceneGraphSettings, new Camera("World Camera"), new testenv.TechniqueManager)

  
  var nodes: Array[Node[TestT, TestG]] = _
  
  def init() {
    scene.camera.transformation.mutable.translation := Vec3(-25, 25, 100)
    
    val camControls = new FirstPersonHandler(scene.camera.transformation)
    addInputListener(camControls)
    addInputListener(new MouseGrabber(true)(KeyCode.Num_Enter, KeyCode.K_Enter)(camControls)())
    
    
    val (indices, vertices, _, _) = Shapes.makeBox()
    val lineIndices = DataBuffer[SInt, UByte](indices.size/3 * 6)
    MeshConversion.linesFromTriangles(indices, vertices, lineIndices)
    
    var node = new Node("Root")
    
    nodes = (for (i <- 0 until 9) yield {
      val newnode = new Node("Node Level " + i)
      node.appendChild(newnode)
      node = newnode
      
      if (i % 2 == 0) {
        val mesh = new Mesh("Cube Level " + i)
        
        mesh.geometry.mode = Lines(3)
        
        mesh.geometry.indices.defineAs(Attributes(lineIndices))
        mesh.geometry.vertices.defineAs(Attributes(vertices))
        
        val scale = 50 - i/2*10
        mesh.transformation.mutable.translation := Vec3(-0.5*scale, 0.5*scale, 0.5*scale)*0.9999
        mesh.transformation.mutable.scale := scale
        
        node.appendChild(mesh)
      }
      
      node
    }).toArray

    
    nodes(6).environment.intencity.mutable.value := 0.75
    for (i <- 0 until 7) { nodes(i).environment.nodeColor.mutable.color := Vec3(1, 0, 0) }
    nodes(0).environment.contrast.mutable.factor := 0.1
    
    scene.attach(nodes(0))
  } 
  
  def update(time: TimeStamp) {
    if (time.total.toInt %2 == 0) nodes(1).environment.intencity.mutable.value := 0.75
    else nodes(1).environment.intencity.undefine()
    
    if (time.total.toInt %4 == 0) nodes(0).environment.contrast.mutable.secondary := true
    else nodes(0).environment.contrast.mutable.secondary := false
  }
  
  
  // App methods.
  import SceneAccess._
  
  override def preUpdate(time: TimeStamp) {
    scene.update(time)
  }
  
  override def render(time: TimeStamp) {
    scene.render(renderManager, time)
  }
  
  override def manage() {
    scene.manage(renderManager.renderContext, frameTimer, 0.01)
  }
  
  override def reshape(position: inVec2i, contrastensions: inVec2i) {
    val aspect = contrastensions.x.toDouble/contrastensions.y
    scene.camera.projection := perspectiveProj(radians(60), aspect, 10, 500)
  }
  
  override def main(args: Array[String]) = {
    super.main(args)
    launch()
  }
}

package testenv {
  
  sealed abstract class ReadIntencity extends Readable[Intencity] {
    def value: ReadDoubleRef
  }
  
  final class Intencity extends ReadIntencity with EnvironmentalEffect[Intencity] {
    type Read = ReadIntencity
    protected def mkMutable() = new Intencity
    
    val value = new DoubleRef(1)
  
    def :=(r: Readable[Intencity]) {
      val t = r.asInstanceOf[Intencity]
      value := t.value
    }
    
    def propagate(parentVal: ReadIntencity, result: Intencity) {
      result.value := value
    }
    
    def hasStructuralChanges = false
    
    def resolveBinding() = value
  }
  
  
  sealed abstract class ReadNodeColor extends Readable[NodeColor] {
    def color: ReadVec3
  }
  
  final class NodeColor extends ReadNodeColor with EnvironmentalEffect[NodeColor] {
    type Read = ReadNodeColor
    protected def mkMutable() = new NodeColor
    
    val color = Vec3(1)
  
    def :=(r: Readable[NodeColor]) {
      val t = r.asInstanceOf[NodeColor]
      color := t.color
    }
    
    def propagate(parentVal: ReadNodeColor, result: NodeColor) {
      if (parentVal.color == Vec3.UnitX) result.color := Vec3.UnitY
      else if (parentVal.color == Vec3.UnitY) result.color := Vec3.UnitZ
      else if (parentVal.color == Vec3.UnitZ) result.color := Vec3.UnitX
      else result.color := Vec3.One
    }
    
    def hasStructuralChanges = false
    
    def resolveBinding() = color
  }
  
  
  sealed abstract class ReadContrast extends Readable[Contrast] {
    def factor: ReadDoubleRef
    def secondary: ReadBooleanRef
  }
  
  final class Contrast extends ReadContrast with UpdatableEnvironmentalEffect[Contrast] {
    type Read = ReadContrast
    protected def mkMutable() = new Contrast
    
    val factor = new DoubleRef(0)
    val secondary = new BooleanRef(false)
  
    def :=(r: Readable[Contrast]) {
      val t = r.asInstanceOf[Contrast]
      
      factor := t.factor
      secondary := t.secondary
    }
    
    def propagate(parentVal: ReadContrast, result: Contrast) {
      if (result.secondary != secondary) result.binding = null
      
      result.factor := factor
      result.secondary := secondary
    }
    
    def hasStructuralChanges = {
      (binding == null) || (secondary ^ binding.factors.size == 2)
    }
    
    private var binding: ContrastBinding = _
    
    def resolveBinding() = {
      println("Resolving contrast binding.")
      
      binding = 
        if (secondary) new ContrastBinding(2)
        else new ContrastBinding(1)
      
      var i = 0; while (i < binding.factors.size) {
        binding.factors(i) := factor
        i += 1
      }
      
      binding
    }
    
    def updateBinding(predefinedUniforms: ReadPredefinedUniforms) {
      binding.factors(0) := length(predefinedUniforms.se_modelViewMatrix(3))*0.004
    }
  }
  
  final class ContrastBinding(factorCount: Int) extends ReflectStruct[ContrastBinding] {
    type Read = ContrastBinding
    protected def mkMutable() = new ContrastBinding(factorCount)
    
    val factors = new BindingArray(new DoubleRef(0), factorCount)
    
    reflect(classOf[ContrastBinding])
  }
  
  
  class Environment extends ReflectEnvironment {
    val intencity = OptionalProperty[Intencity](new Intencity, this)
    val nodeColor = OptionalProperty[NodeColor](new NodeColor, this)
    val contrast = OptionalProperty[Contrast](new Contrast, this)
    
    reflect(classOf[Environment])
  }
  
  
  final class GraphicsContext extends graphics.GraphicsContext {
  
    type Geometry = renderer.Geometry
    type Material = renderer.Material
    type Environment = testenv.Environment
    
    val mkGeometry = () => new Geometry
    val mkMaterial = () => new Material
    val mkEnvironment = () => new Environment
    
    initNamespace()
  }
  
  
  final class TechniqueManager[G <: GraphicsContext](implicit val graphicsContext: G) extends graphics.TechniqueManager[G] {
    val passManager = new renderer.PassManager[G]
    
    val vertexShader = new Shader(Shader.VertexShader, """
      uniform mat4 se_modelViewProjectionMatrix;
      attribute vec3 vertices;
      
      void main() {
        gl_Position = se_modelViewProjectionMatrix*vec4(vertices, 1.0);
      }
      """
    )
    
    val fragmentShader = new Shader(Shader.FragmentShader, """
        uniform vec3 nodeColor;
        
        float resolveIntensity();
        float resolveContrastFactor();
        
        void main() {
          vec3 color = nodeColor * resolveIntensity() + vec3(resolveContrastFactor());
          gl_FragColor = vec4(color, 1);
        }
      """
    )
      
    val withoutIntencity = new Shader(Shader.FragmentShader, """
        float resolveIntensity() {
          return 0.25;
        }
      """
    )
    
    val withIntencity = new Shader(Shader.FragmentShader, """
        uniform float intencity;
        
        float resolveIntensity() {
          return intencity;
        }
      """
    )
    
    val contrast1 = new Shader(Shader.FragmentShader, """
        struct Contrast {
          float factors[1];
        };
        
        uniform Contrast contrast;
        
        float resolveContrastFactor() {
          return contrast.factors[0];
        }
      """
    )
    
    val contrast2 = new Shader(Shader.FragmentShader, """
        struct Contrast {
          float factors[2];
        };
        
        uniform Contrast contrast;
        
        float resolveContrastFactor() {
          return contrast.factors[0] + contrast.factors[1];
        }
      """
    )
  
    
    private val cache = new java.util.HashMap[(Shader, Shader), Technique]
    def getTechnique(intencityShader: Shader, contrastShader: Shader) :Technique = {
      val key = (intencityShader, contrastShader)
      var technique = cache.get(key)
      if (technique == null) {
        technique = new Technique(
          graphicsContext,
          List[Shader](vertexShader, fragmentShader, intencityShader, contrastShader)
        )
        cache.put(key, technique)
      }
      technique
    }
    
    def resolveTechnique(
      meshName: String,
      geometry: G#Geometry, material: G#Material, worldEnvironment: G#Environment
    )
    :Technique = {
      
      assert(worldEnvironment.nodeColor.isDefined)
      assert(worldEnvironment.contrast.isDefined)
      
      
      print("Resolving technique for mesh '" + meshName + "': ")
      
      val intencityShader =
        if (worldEnvironment.intencity.isDefined) {
          print("with intencity, ")
          withIntencity
        }
        else {
          print("without intencity, ")
          withoutIntencity
        }
      
      val contrastShader =
        if (worldEnvironment.contrast.defined.secondary) {
          println("with contrast2.")
          contrast2
        }
        else {
          println("with contrast1.")
          contrast1
        }
      
      getTechnique(intencityShader, contrastShader)
    }
  }
}
