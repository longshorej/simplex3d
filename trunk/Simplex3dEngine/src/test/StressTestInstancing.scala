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

package test

import scala.collection.mutable.ArrayBuffer
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.algorithm.noise._
import simplex3d.algorithm.mesh._
import simplex3d.engine._
import simplex3d.engine.renderer._
import simplex3d.engine.bounding._
import simplex3d.engine.input._
import simplex3d.engine.input.handler._
import simplex3d.engine.graphics._
import simplex3d.engine.scenegraph._
import simplex3d.engine.impl._
import simplex3d.engine.default._


object StressTestInstancing extends BasicApp {
  val objectCount = 3000
  val title = "Stress Test (Instancing): " + objectCount + " objects."
  
  override lazy val settings = new Settings(
    fullscreen = false,
    verticalSync = false,
    capabilitiesLog = true,
    performanceLog = true,
    resolution = Some(Vec2i(800, 600))
  )
  
  
  override lazy val sceneGraphSettings = new SceneGraphSettings(
    multithreadedControllers = true,
    multithreadedParsing = true
  )
  
  
  val seed = 2
  val nx = new ClassicalGradientNoise(0)
  val ny = new ClassicalGradientNoise(seed + 1)
  val nz = new ClassicalGradientNoise(seed + 2)
  val offsetScale = 0.05
  def curve(index: Int, uptime: Double): Vec3 = {
    val a = index*offsetScale + uptime
    val b = 28.25896 //uptime*curveSpeed
    Vec3(nx(a, b), ny(a, b), nz(a, b))*100
  }
  
  
  val objectTexture = Texture2d(Vec2i(128), DataBuffer[Vec3, UByte](128*128)); {
    val img = objectTexture.write
    val borderWidth = 20
    
    var y = 0; while (y < objectTexture.dimensions.y) {
      var x = 0; while (x < objectTexture.dimensions.x) {
        val i = x + y*objectTexture.dimensions.x
        
        if (
          x < borderWidth || x >= objectTexture.dimensions.x - borderWidth ||
          y < borderWidth || y >= objectTexture.dimensions.y - borderWidth
        ) {
          img(i) = Vec3(0.1, 0.1, 1)
        }
        else {
          img(i) = Vec3(0, 0.8, 0.8)
        }
        
        x += 1
      }
      y += 1
    }
  }
  
  
  def init() {
    world.camera.transformation.mutable.translation := Vec3(0, 0, 200)
    
    val camControls = new FirstPersonHandler(world.camera.transformation)
    addInputListener(camControls)
    addInputListener(new MouseGrabber(true)(KeyCode.Num_Enter, KeyCode.K_Enter)(camControls)())
    
    
    val (indexBuffer, vertexBuffer, normalBuffer, texCoordBuffer) = Shapes.makeBox()
    val indices = Attributes(indexBuffer)
    val vertices = Attributes(vertexBuffer)
    val normals = Attributes(normalBuffer)
    val texCoords = Attributes(texCoordBuffer)
    
    
    val scale = 1.7
    val node = new InstancingNode("Instancing Node")
    node.instanceBoundingVolume.defineAs(new Oabb(Vec3(-0.5)*scale, Vec3(0.5)*scale))
    node.customBoundingVolume.defineAs(new Oabb(Vec3(Double.MinValue), Vec3(Double.MaxValue)))
    
    node.geometry.faceCulling.mutable := FaceCulling.Back
    
    node.geometry.indices.defineAs(indices)
    node.geometry.vertices.defineAs(vertices)
    node.geometry.normals.defineAs(normals)
    node.geometry.texCoords.defineAs(texCoords)
    
    node.material.texture.mutable := objectTexture
    
    world.attach(node)

    
    val random = new java.util.Random(3)
    def randomRotation() = normalize(Quat4(random.nextDouble, random.nextDouble, random.nextDouble, random.nextDouble))
    def smallRandomRotation() = normalize(Quat4(1, random.nextDouble, random.nextDouble, random.nextDouble))
    
    for (i <- 0 until objectCount) {
      
      val instance = node.appendInstance("Instance" + i)
      
      instance.transformation.mutable.scale := scale
      instance.transformation.mutable.rotation := randomRotation()
      instance.transformation.mutable.translation := curve(i, 0)
    }
  }
    
  def update(time: TimeStamp) {
    
  }
}
