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
import simplex3d.algorithm.intersection._
import simplex3d.engine.util._
import simplex3d.engine.scene._
import simplex3d.engine.transformation._
import simplex3d.engine.bounding._
import simplex3d.engine.graphics._



final class Mesh[T <: TransformationContext, G <: GraphicsContext] private[scenegraph] (
  name: String,
  meshParent: AbstractNode[T, G],
  final val geometry: G#Geometry, // Caution: geometry and material must never be shared among displayable meshes!
  final val material: G#Material  // Caution: geometry and material must never be shared among displayable meshes!
)(implicit transformationCtx: T, graphicsCtx: G)
extends Bounded[T, G](name) with InheritedEnvironment with AbstractMesh {
  
  import AccessChanges._
  
  
  def this(name: String)(implicit transformationContext: T, graphicsContext: G) {
    this(name, null, graphicsContext.mkGeometry(), graphicsContext.mkMaterial())
  }
  
  def worldEnvironment = parent.worldEnvironment
  
  _parent = meshParent
  override def parent = super.parent
  
  
  private[scenegraph] override def onParentChange(
    parent: AbstractNode[T, G], managed: ArrayBuffer[Spatial[T]]
  ) {
    super.onParentChange(parent, managed)
    
    // Signal changes that trigger another technique resolution to take care of new environment.
    material.signalStructuralChanges()
  }
  
  private[scenegraph] override def updateBoundingVolume(allowMultithreading: Boolean) :Boolean = {
    propagateWorldTransformation()
    
    var updateParentVolume = false
    
    if (customBoundingVolume.hasRefChanges) {
      autoBoundingVolume == null
      customBoundingVolume.clearRefChanges()
      updateParentVolume = true
    }
    
    if (!customBoundingVolume.isDefined) {
      if (autoBoundingVolume == null) {
        autoBoundingVolume = new Oabb
      }

      if (autoBoundingVolume.hasDataChanges || hasShapeChanges()) {
        autoBoundingVolume match {
          case bound: Oabb =>
            val range = if (elementRange.isDefined) elementRange.get else null
            Bounded.rebuildAabb(range, geometry)(bound.update.min, bound.update.max)
        }
        updateParentVolume = true
      }
    }
    
    if (resolveBoundingVolume.hasDataChanges || uncheckedWorldTransformation.hasDataChanges) {
      resolveBoundingVolume.clearDataChanges()
      updateParentVolume = true
    }
    
    
    clearShapeChanges()
    uncheckedWorldTransformation.clearDataChanges()
    
    updateParentVolume
  }
  
  
  final def hasShapeChanges() :Boolean = {//XXX hide this
    import AccessChanges._
    
    if (elementRange.hasDataChanges) {
      true
    }
    else {
      val indexChanges =
        if (geometry.indices.hasRefChanges) true
        else if (geometry.indices.isDefined) geometry.indices.hasDataChanges
        else false

      if (indexChanges) true
      else {
        if (geometry.vertices.hasRefChanges) true
        else if (geometry.vertices.isDefined) geometry.vertices.hasDataChanges
        else false
      }
    }
  }
  
  final def clearShapeChanges() {//XXX hide this
    import AccessChanges._
    elementRange.clearDataChanges()
    geometry.indices.clearRefChanges()
    geometry.indices.clearDataChanges()
    geometry.vertices.clearRefChanges()
    geometry.vertices.clearDataChanges()
  }
}