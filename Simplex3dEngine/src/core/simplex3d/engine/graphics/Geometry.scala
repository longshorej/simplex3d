/*
 * Simplex3dEngine - Core Module
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
package graphics

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.engine.util._


abstract class Geometry extends StructuralChangeListener {
  protected implicit val structuralChangeListener = this
  
  def attributeNames: ReadArray[String]
  def attributes: ReadArray[UncheckedAttributes]

  
  final def hasShapeChanges(elementRange: Optional[ElementRange] = null) :Boolean = {//XXX hide this
    import AccessChanges._
    
    if (elementRange != null && elementRange.hasDataChanges) {
      true
    }
    else if (elementRange != null && elementRange.isDefined) {
      val indexChanges =
        if (indices.hasRefChanges) true
        else if (indices.isDefined) indices.get.sharedState.hasDataChanges // TODO check for data changes in range
        else false
        
      if (indexChanges) true
      else {
        if (vertices.hasRefChanges) true
        else if (vertices.isDefined) vertices.get.sharedState.hasDataChanges // TODO check for data changes in range if !indices.isDefined
        else false
      }
    }
    else {
      val indexChanges =
        if (indices.hasRefChanges) true
        else if (indices.isDefined) indices.get.sharedState.hasDataChanges
        else false

      if (indexChanges) true
      else {
        if (vertices.hasRefChanges) true
        else if (vertices.isDefined) vertices.get.sharedState.hasDataChanges
        else false
      }
    }
  }

  private[this] final var vertexMode: VertexMode = Triangles()//XXX rework as Defined[EnumRef]
  final def mode = vertexMode
  final def mode_=(m: VertexMode) {
    if (vertexMode.getClass != m.getClass) this.signalStructuralChanges()
    vertexMode = m
  }
  
  final val indices = AttributeBinding[SInt, Unsigned](StructuralChangeListener.Ignore)
  final val vertices = AttributeBinding[Vec3, RFloat](this)
  final val normals = AttributeBinding[Vec3, RFloat](this)
  
  final val faceCulling = Defined[EnumRef[FaceCulling.type]](new EnumRef(FaceCulling.Disabled))
  
  
  final def copyNonattributes(geometry: Geometry) {
    mode = geometry.mode
    faceCulling.update := geometry.faceCulling.get
  }
}