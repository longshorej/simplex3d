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
import simplex3d.engine.util._
import simplex3d.engine.bounding._
import simplex3d.engine.scene._
import simplex3d.engine.graphics._
import simplex3d.engine.transformation._


abstract class Entity[T <: TransformationContext, G <: GraphicsContext] (name: String)(
  implicit transformationCtx: T, graphicsCtx: G
) extends Bounded[T, G](name) {
  
  import AccessChanges._
  
  
  private[this] final val env = graphicsContext.mkEnvironment()
  protected def environment: G#Environment = env
  protected def inheritEnvironment = true
  private[scenegraph] final val worldEnvironment = graphicsContext.mkEnvironment()
  
  private[this] final val _children = ArrayBuffer[SceneElement[T, G]]()
  private[this] final val readChildren = new ReadSeq(_children)
  protected[scenegraph] def children: ReadSeq[SceneElement[T, G]] = readChildren
  
  protected def appendChild(element: SceneElement[T, G]) {
    append(element)
    
    element.transformation.signalDataChanges()
    
    val managed = new ArrayBuffer[Spatial[T]](4)
    element.onParentChange(controllerContext, managed)
    if (controllerContext != null) controllerContext.register(managed)
  }
  
  protected def removeChild(element: SceneElement[_, _]) :Boolean = {
    val elem = element.asInstanceOf[SceneElement[T, G]]
    val removed = remove(elem)
    if (removed) { onRemove(elem) }
    removed
  }
  
  protected def removeNestedChild(element: SceneElement[T, G]) :Boolean = {
    val removed = removeNested(element)
    if (removed) { onRemove(element) }
    removed
  }
  
  private[this] def onRemove(element: SceneElement[T, G]) {
    val managed = new ArrayBuffer[Spatial[T]](4)
    element.onParentChange(null, managed)
    if (controllerContext != null) controllerContext.unregister(managed)
    
    element.updateVersion = 0
  }
  
  private[scenegraph] final override def onParentChange(
    controllerContext: ControllerContext, managed: ArrayBuffer[Spatial[T]]
  ) {
    super.onParentChange(controllerContext, managed)
    
    val size = _children.size
    var i = 0; while (i < size) { val current = _children(i)
      current.onParentChange(controllerContext, managed)
      
      i += 1
    }
  }
  
  private[this] final def append(element: SceneElement[T, G]) {
    if (element._parent != null) element._parent.removeChild(element)

    _children += element
    element._parent = this
  }

  private[this] final def remove(element: SceneElement[T, G]) :Boolean = {
    var removed = false

    val oldSize = _children.size
    _children -= element
    removed = (_children.size != oldSize)

    if (removed) element._parent = null
    removed
  }

  private[this] final def removeNested(element: SceneElement[T, G]) :Boolean = {
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
    entityUpdate(version)(false, 0)
  }
  
  private[scenegraph] final def entityUpdate
    (version: Long)
    (allowMultithreading: Boolean, minChildren: Int)
  :Boolean =
  {
    if (updateVersion == version) return false
    
    propagateWorldTransformation()
    updateVersion = version
    
    
    var updated = false
    
    if (customBoundingVolume.hasRefChanges) {
      autoBoundingVolume == null
      customBoundingVolume.clearRefChanges()
      updated = true
    }
    
    
    if (!customBoundingVolume.isDefined) {
      val atomicUpdateBounding = new java.util.concurrent.atomic.AtomicBoolean(false)
      var updateBounding = false
      
      def processChild(child: SceneElement[T, G])(allowMultithreading: Boolean, minChildren: Int) {
        val childBoundingChanged = child match {
          case entity: Entity[_, _] => entity.entityUpdate(version)(allowMultithreading, minChildren)
          case _ => child.update(version)
        }
        atomicUpdateBounding.compareAndSet(false, childBoundingChanged || atomicUpdateBounding.get)
      }
      
      if (allowMultithreading && children.size >= minChildren) {
        (0 until children.size).par.foreach(i => processChild(children(i))(false, 0))
        updateBounding = atomicUpdateBounding.get
      }
      else {
        def processChildren() {
          val size = children.size; var i = 0; while (i < size) {
            processChild(children(i))(allowMultithreading, minChildren)
            
            i += 1
          }
        }; processChildren()
        updateBounding = atomicUpdateBounding.get
      }
      
      
      if (resolveBoundingVolume == null) {
        autoBoundingVolume = new Aabb
        updateBounding = true
      }
      
      if (updateBounding || uncheckedWorldTransformation.hasDataChanges) {
        val bound = autoBoundingVolume.asInstanceOf[Aabb]
        Bounded.rebuildAabb(this)(bound.update.min, bound.update.max)
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
  
  private[scenegraph] override def updateCull(
    version: Long, enableCulling: Boolean, time: TimeStamp, view: View, renderArray: SortBuffer[AbstractMesh]
  ) {
    entityUpdateCull(
      version, true, time, view, renderArray
    )(false, 0, null, 0, 0)
  }
  
  
  private[scenegraph] def entityUpdateCull(
    version: Long, enableCulling: Boolean,
    time: TimeStamp, view: View,
    renderArray: SortBuffer[AbstractMesh]
  )(
    allowMultithreading: Boolean, minChildren: Int,
    batchArray: SortBuffer[SceneElement[T, G]], maxDepth: Int, currentDepth: Int
  ) {
    if (enableCulling) entityUpdate(version)(allowMultithreading, minChildren)
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
      else if (this.children.size >= minChildren) {
        batchChildren = true
      }
    }
    
    var hasLeafs = false
    val children = this.children
    val size = children.size; var i = 0; while (i < size) { val current = children(i)
      
      current match {
        case entity: Entity[_, _] =>
          
          def propagateEnvironment() {
            val parentEnv = worldEnvironment
            val childEnv = entity.environment
            val resultEnv = entity.worldEnvironment
    
            val parentProps = parentEnv.properties
            val childProps = childEnv.properties
            val resultProps = resultEnv.properties
            
            val size = parentProps.length; var i = 0; while (i < size) {
              
              val parentProp = parentProps(i)
              val childProp = childProps(i)
              val resultProp = resultProps(i)
              
              if (parentProp.hasDataChanges || childProp.hasDataChanges) {
                if (parentProp.isDefined) {
                  if (childProp.isDefined) {
                    childProp.get.propagate(parentProp.get, resultProp.update)
                  }
                  else {
                    resultProp.update := parentProp.get
                  }
                }
                else {
                  if (childProp.isDefined) {
                    resultProp.update := childProp.get
                  }
                  else {
                    resultProp.undefine()
                  }
                }

                childProp.clearDataChanges()
              }
              
              i += 1
            }
          }; if (entity.inheritEnvironment) propagateEnvironment()

          
          if (batchChildren) batchArray += entity
          else entity.entityUpdateCull(
            version, cullChildren, time, view, renderArray
          )(allowMultithreading, minChildren, batchArray, maxDepth, currentDepth + 1)
        
        case mesh: Mesh[_, _] =>
          hasLeafs = true
          if (batchChildren && cullChildren) batchArray += mesh
          else mesh.updateCull(version, cullChildren, time, view, renderArray)
          
        case bounded: Bounded[_, _] =>
          hasLeafs = true
          if (batchChildren && cullChildren) batchArray += bounded
          else bounded.updateCull(version, cullChildren, time, view, renderArray)
        
        case _ =>
          hasLeafs = true
          current.update(version)
      }
      
      i += 1
    }
    
    
    def postPropagation() {
      val props = worldEnvironment.properties
      val size = props.length; var i = 0; while (i < size) { val prop = props(i)
        if (prop.hasDataChanges) {
          if (hasLeafs && prop.isDefined && prop.get.hasBindingChanges) {
            worldEnvironment.signalStructuralChanges()
            prop.get.clearBindingChanges()
          }
          prop.clearDataChanges()
        }
        
        i += 1
      }
    }; postPropagation()
  }
}
