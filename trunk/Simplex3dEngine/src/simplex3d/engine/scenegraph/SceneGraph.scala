/*
 * Simplex3dEngine - SceneGraph Module
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

package simplex3d.engine
package scenegraph

import scala.collection.mutable._
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.algorithm.intersection.{ Frustum, Collision }
import simplex3d.engine.scene._
import simplex3d.engine.transformation._
import simplex3d.engine.bounding._
import simplex3d.engine.graphics._


class SceneGraph[T <: TransformationContext, G <: GraphicsContext](
  name: Symbol,
  val camera: Camera[T],
  final val techniqueManager: TechniqueManager[G]
)(implicit transformationContext: T)
extends Scene(name) {
  
  import SubtextAccess._
  
  
  private[this] var version: Long = 0
  private[this] val controllerContext = new ControllerContext
  
  protected val _root: Node[T, G] = new Node()(transformationContext, techniqueManager.graphicsContext)
  protected def root = _root
  root.controllerContext = controllerContext
  root.customBoundingVolume.defineAs(new Aabb(Vec3(Double.MinValue), Vec3(Double.MaxValue)))
  root.appendChild(camera)
  
  
  protected def attach(elem: SceneElement[T]) {
    root.appendChild(elem)
  }
  protected def detach(elem: SceneElement[T]) :Boolean = {
    root.removeNestedChild(elem)
  }
  
  
  def preload(context: RenderContext, frameTimer: FrameTimer, timeSlice: Double) :Double = {
    1.0
  }
  
  // TODO update controllers via PassManager
  def updateControllers(time: TimeStamp) {
    version += 1
    controllerContext.update(time)
  }
  
  
  private val batchArray = new ArrayBuffer[SceneElement[T]](100)
  private val synchronizedResult = new ArrayBuffer[AbstractMesh](100) with SynchronizedBuffer[AbstractMesh] //XXX try with something more lightweight
  
  def buildRenderArray(pass: Pass, time: TimeStamp, result: InplaceSortBuffer[AbstractMesh]) {
    val camera = this.camera // TODO camera should come from the pass.
    camera.sync()
    
    val frustum = Frustum(camera.viewProjection)
    val view = new View(Vec2i(-1), camera, frustum) //XXX proper dimensions
    
    val allowMultithreading = true // XXX must come from settings
    if (allowMultithreading) batchArray.clear()
    
    root.entityCull(
      true, version, time, view, result.asInstanceOf[InplaceSortBuffer[SceneElement[T]]] //XXX rework casting
    )(allowMultithreading, 50, batchArray, 3, 0) // XXX these args must come from settings
    
    if (allowMultithreading) {
      synchronizedResult.clear()
      
      (0 until batchArray.size).par.foreach { i =>
        batchArray(i) match {
          case b: Bounded[_] => b.cull(version, time, view, synchronizedResult.asInstanceOf[ArrayBuffer[SceneElement[T]]])
          case _ => // do nothing.
        }
      }
      result ++= synchronizedResult
    }
    
    // Resolve techniques.
    val size = result.size
    var i = 0; while (i < size) { val mesh = result(i)
      
      if (mesh.hasStructuralChanges) {
        val technique = techniqueManager.resolveTechnique(mesh.geometry, mesh.material, mesh.worldEnvironment)
        mesh.technique.defineAs(technique)
        
        mesh.geometry.clearStructuralChanges()
        mesh.material.clearStructuralChanges()
      }
      
      i += 1
    }
  }
  
  def manage(context: RenderContext, frameTimer: FrameTimer, timeSlice: Double) {
    
  }
  
  def cleanup(context: RenderContext) {
    
  }
}
