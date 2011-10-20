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
import simplex3d.algorithm.intersection.{ Frustum, Collision }
import simplex3d.engine.bounding._
import simplex3d.engine.graphics._
import simplex3d.engine.transformation._


abstract class Entity[T <: TransformationContext, G <: GraphicsContext](
  implicit transformationContext: T, final val graphicsContext: G
) extends Bounded[T] {
  
  import SubtextAccess._
  
  
  private[this] final val env = graphicsContext.mkEnvironment()
  protected def environment: G#Environment = env
  protected def inheritEnvironment = true
  private[scenegraph] final val worldEnvironment = graphicsContext.mkEnvironment()
  
  private[this] final val _children = ArrayBuffer[SceneElement[T]]()
  private[this] final val readChildren = new ReadSeq(_children)
  protected[scenegraph] def children: ReadSeq[SceneElement[T]] = readChildren

  
  protected def appendChild(element: SceneElement[T]) {
    append(element)
    
    val managed = new ArrayBuffer[Spatial[T]](4)
    element.manageControllerContext(this.controllerContext, managed)
    this.controllerContext.register(managed)
  }
  
  protected def removeChild(element: SceneElement[T]) :Boolean = {
    val removed = remove(element)
    if (removed) { onRemove(element) }
    removed
  }
  
  protected def removeNestedChild(element: SceneElement[T]) :Boolean = {
    val removed = removeNested(element)
    if (removed) { onRemove(element) }
    removed
  }
  
  private[this] def onRemove(element: SceneElement[T]) {
    val managed = new ArrayBuffer[Spatial[T]](4)
    element.manageControllerContext(null, managed)
    this.controllerContext.unregister(managed)
    
    element.updateVersion = 0
  }
  
  private[scenegraph] final override def manageControllerContext(
    controllerContext: ControllerContext, managed: ArrayBuffer[Spatial[T]]
  ) {
    super.manageControllerContext(controllerContext, managed)
    
    val size = _children.size
    var i = 0; while (i < size) { val current = _children(i)
      
      current.manageControllerContext(controllerContext, managed)
      
      i += 1
    }
  }
  
  private[this] final def append(element: SceneElement[T]) {
    if (element._parent != null) element._parent.removeChild(element)

    _children += element
    element._parent = this
  }

  private[this] final def remove(element: SceneElement[T]) :Boolean = {
    var removed = false

    val oldSize = _children.size
    _children -= element
    removed = (_children.size != oldSize)

    if (removed) element._parent = null
    removed
  }

  private[this] final def removeNested(element: SceneElement[T]) :Boolean = {
    var removed = removeChild(element)

    val size = _children.size
    var i = 0; while (!removed && i < size) { val current = _children(i)
      current match {
        case entity: Entity[_, _] => removed = entity.removeNestedChild(element)
        case _ => // do nothing.
      }
    
      i += 1
    }

    removed
  }
  
    
  private[scenegraph] override def update(version: Long) :Boolean = {
    if (updateVersion != version) {
      propagateWorldTransformation()
      updateVersion = version
    }
    
    var updated = false
    
    if (customBoundingVolume.hasRefChanges) {
      autoBoundingVolume == null
      customBoundingVolume.clearRefChanges()
      updated = true
    }
    
    
    if (!customBoundingVolume.isDefined) {
      var updateBounding = false
      
      val size = children.size
      var i = 0; while (i < size) { val child = children(i)
        
        val childBoundingChanged = child match {
          case bounded: Bounded[_] => bounded.update(version)
          case _ => child.update(version)
        }
        updateBounding = childBoundingChanged || updateBounding
        
        i += 1
      }
      
      if (resolveBoundingVolume == null) {
        autoBoundingVolume = new Aabb
        updateBounding = true
      }
      
      if (updateBounding || uncheckedWorldTransformation.hasDataChanges) {
        val bound = autoBoundingVolume.asInstanceOf[Aabb]
        Bounded.rebuildAabb(this)(bound.mutable.min, bound.mutable.max)
        updated = true
      }
    }
    
    
    if (resolveBoundingVolume.hasDataChanges) {
      resolveBoundingVolume.clearDataChanges()
      updated = true
    }
    
    uncheckedWorldTransformation.clearDataChanges()
    updated
  }
  
  private[scenegraph] override def cull(
    version: Long, time: TimeStamp, view: View, renderArray: ArrayBuffer[SceneElement[T]]
  ) {
    entityCull(
      true, version, time, view, renderArray
    )(false, 0, null, 0, 0)
  }
  
  
  private[scenegraph] def entityCull(
    enableCulling: Boolean, version: Long,
    time: TimeStamp, view: View,
    renderArray: ArrayBuffer[SceneElement[T]]
  )(
    allowMultithreading: Boolean, minChildren: Int,
    batchArray: ArrayBuffer[SceneElement[T]], maxDepth: Int, currentDepth: Int
  ) {
    
    if (enableCulling) update(version)
    else updateWorldTransformation(version)
    
    
    val frustumTest = 
      if (!enableCulling) Collision.Inside
      else BoundingVolume.intersect(view.frustum, resolveBoundingVolume, uncheckedWorldTransformation)
    
    val cullChildren = 
      if (frustumTest == Collision.Outside) return
      else if (frustumTest == Collision.Inside) false
      else true
    
    if (animators != null && shouldRunAnimators) {
      runUpdaters(animators, time)
      shouldRunAnimators = false
    }
    
    
    var  batchChildren = false
    if (allowMultithreading && batchArray != null) {
      if (currentDepth >= maxDepth) {
        batchChildren = true
      }
      else if (minChildren <= this.children.size) {
        batchChildren = true
      }
    }
    
    val children = this.children
    val size = children.size
    var i = 0; while (i < size) { val current = children(i)
      
      current match {
        case entity: Entity[_, _] =>
          
          def propagateEnvironment() {
            val resultEnv = entity.worldEnvironment
    
            val parentProps = this.worldEnvironment.properties
            val childProps = entity.environment.properties
            val resultProps = resultEnv.properties
            
            val size = parentProps.length
            var i = 0; while (i < size) {
              
              val parentProp = parentProps(i)
              val childProp = childProps(i)
              val resultProp = resultProps(i)
              
              if (parentProp.hasDataChanges || childProp.hasDataChanges) {
                def propagateEnvironmentalProperty() {
                  
                  if (parentProp.isDefined) {
                    if (childProp.isDefined) {
                      val structChanges = childProp.defined.propagate(parentProp.defined, resultProp.mutable)
                      if (structChanges) resultEnv.signalStructuralChanges()
                    }
                    else {
                      resultProp.mutable := parentProp.defined
                    }
                  }
                  else {
                    if (childProp.isDefined) {
                      resultProp.mutable := childProp.defined
                    }
                    else {
                      resultProp.undefine()
                    }
                  }
                  
                }; propagateEnvironmentalProperty()
                childProp.clearDataChanges()
              }
              
              i += 1
            }
          }; if (entity.inheritEnvironment) propagateEnvironment()

          if (batchChildren) batchArray += entity
          else entity.entityCull(
            cullChildren, version, time, view, renderArray
          )(allowMultithreading, minChildren, batchArray, maxDepth, currentDepth + 1)
        
        case mesh: Mesh[_, _] =>
          if (batchChildren) batchArray += mesh
          else mesh.cull(version, time, view, renderArray)
          
        case bounded: Bounded[_] =>
          if (batchChildren) batchArray += bounded
          else bounded.cull(version, time, view, renderArray)
        
        case _ =>
          // do nothing.
      }
      
      i += 1
    }
  }
}
