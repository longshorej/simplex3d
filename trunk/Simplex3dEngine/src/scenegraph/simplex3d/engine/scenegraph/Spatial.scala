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

import scala.collection.mutable.ArrayBuffer
import simplex3d.math.double._
import simplex3d.engine.util._
import simplex3d.engine.scene._
import simplex3d.engine.transformation._


abstract class Spatial[T <: TransformationContext] private[scenegraph] (final val name: String)(
  implicit transformationContext: T
) extends scene.Spatial { self =>
  
  protected type Graphics <: graphics.GraphicsContext
  
  import AccessChanges._
  
  
  private[scenegraph] final var _parent: AbstractNode[T, Graphics] = _
  protected def parent: AbstractNode[T, Graphics] = _parent
  
  private[scenegraph] final var controllerContext: ControllerContext = null
  private[scenegraph] final var controllers: ArrayBuffer[Updater] = null
  
  final val transformation: T#Transformation = {
    transformationContext.mkTransformation(this.isInstanceOf[AbstractCamera])
  }
  private[scenegraph] final val uncheckedWorldTransformation: T#Transformation =
    transformationContext.mkTransformation()
  
  protected def worldMatrix = uncheckedWorldTransformation.matrix

  
  
  final def worldTransformation: T#Transformation#Read = {
    def update(node: AbstractNode[T, _]) :Boolean = {
      val parentUpdated = if (node.parent != null) update(node.parent) else false
      
      if (parentUpdated || node.transformation.hasDataChanges) {
        node.propagateWorldTransformation()
        true
      }
      else false
    }
    
    if (parent != null) update(parent)
    propagateWorldTransformation()
    
    uncheckedWorldTransformation.asInstanceOf[T#Transformation#Read]
  }
  
  private[scenegraph] final def propagateWorldTransformation() {
    val parentTransformation = if (parent == null) null else parent.uncheckedWorldTransformation
    
    transformation.propagateChanges(
      parentTransformation.asInstanceOf[transformation.Read],
      uncheckedWorldTransformation.asInstanceOf[transformation.Mutable]
    )
    
    transformation.clearDataChanges()
  }
  
  private[scenegraph] final def updateWorldTransformation() :Boolean = {
    propagateWorldTransformation()
    val changed = uncheckedWorldTransformation.hasDataChanges
    uncheckedWorldTransformation.clearDataChanges()
    changed
  }
  
  
  private[scenegraph] def onSpatialParentChange(
    parent: AbstractNode[T, _], managed: ArrayBuffer[Spatial[T]]
  ) {
    transformation.signalDataChanges()
    
    if (parent != null) controllerContext = parent.controllerContext
    else controllerContext = null
    
    if (controllers != null) managed += this
  }
  
  /** Controllers are executed for all objects attached to a scene-graph when it is updated.
   */
  def controller(function: TimeStamp => Unit) :Updater = {
    val updater = new Updater(function)
    addController(updater)
    updater
  }
  
  def addController(controller: Updater) {
    if (controllers == null) {
      controllers = new ArrayBuffer[Updater](4)
      if (controllerContext != null) controllerContext.register(List(this))
    }
    controllers += controller
  }
  
  def removeController(controller: Updater) {
    if (controllers != null) {
      controllers -= controller
      
      if (controllers.isEmpty) {
        controllers = null
        if (controllerContext != null) controllerContext.unregister(List(this))
      }
    }
  }
  
  
  private[scenegraph] final def runUpdaters(updaters: ArrayBuffer[Updater], time: TimeStamp) {
    val size = updaters.size
    var i = 0; while (i < size) {
      updaters(i).apply(time)
      
      i += 1
    }
  }
}