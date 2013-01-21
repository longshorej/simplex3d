/*
 * Simplex3dData - Float Module
 * Copyright (C) 2010-2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dData.
 *
 * Simplex3dData is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dData is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.data
package float

import java.nio._
import scala.annotation.unchecked._
import simplex3d.math.floatx._
import simplex3d.data.extension._
import RawEnum._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[data] abstract class BaseVec3f[+R <: TangibleFloat](
  prim: ReadContiguous[RFloat, R], off: Int, str: Int
) extends CompositeSeq[Vec3f, R, TangibleFloat](prim, off, str) {
  final def formatTag = Vec3f.Tag
  final def accessorTag = Vec3f.Tag
  final def components: Int = 3

  final def mkReadDataArray[P <: TangibleFloat](prim: ReadDataArray[Vec3f#Component, P])
  :ReadDataArray[Vec3f, P] = {
    (prim.rawEnum match {
      case UByte => new ArrayVec3fUByte(prim.asInstanceOf[ArrayRFloatUByte])
      case RFloat => new ArrayVec3fRFloat(prim.asInstanceOf[ArrayRFloatRFloat])
      case _ => new ArrayVec3f(prim)
    }).asInstanceOf[ReadDataArray[Vec3f, P]]
  }
  final def mkReadDataBuffer[P <: TangibleFloat](prim: ReadDataBuffer[Vec3f#Component, P])
  :ReadDataBuffer[Vec3f, P] = {
    (prim.rawEnum match {
      case UByte => new BufferVec3fUByte(prim.asInstanceOf[BufferRFloatUByte])
      case RFloat => new BufferVec3fRFloat(prim.asInstanceOf[BufferRFloatRFloat])
      case _ => new BufferVec3f(prim)
    }).asInstanceOf[ReadDataBuffer[Vec3f, P]]
  }
  protected final def mkReadDataViewInstance[P <: TangibleFloat](
    prim: ReadDataBuffer[Vec3f#Component, P], off: Int, str: Int
  ) :ReadDataView[Vec3f, P] = {
    (prim.rawEnum match {
      case UByte => new ViewVec3fUByte(prim.asInstanceOf[BufferRFloatUByte], off, str)
      case RFloat => new ViewVec3fRFloat(prim.asInstanceOf[BufferRFloatRFloat], off, str)
      case _ => new ViewVec3f(prim, off, str)
    }).asInstanceOf[ReadDataView[Vec3f, P]]
  }

  final override def mkSerializableInstance() = new CompositeRFloat(components)
}

private[data] final class ArrayVec3f[+R <: TangibleFloat](
  prim: ReadDataArray[RFloat, R]
) extends BaseVec3f[R](prim, 0, 3) with DataArray[Vec3f, R] {
  type Read = ReadDataArray[Vec3f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec3f = {
    val j = i*3
    ConstVec3f(
      primitives(j),
      primitives(j + 1),
      primitives(j + 2)
    )
  }
  def update(i: Int, v: ReadVec3f) {
    val j = i*3
    primitives(j) = v.x
    primitives(j + 1) = v.y
    primitives(j + 2) = v.z
  }
}

private[data] final class BufferVec3f[+R <: TangibleFloat](
  prim: ReadDataBuffer[RFloat, R]
) extends BaseVec3f[R](prim, 0, 3) with DataBuffer[Vec3f, R] {
  type Read = ReadDataBuffer[Vec3f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec3f = {
    val j = i*3
    ConstVec3f(
      primitives(j),
      primitives(j + 1),
      primitives(j + 2)
    )
  }
  def update(i: Int, v: ReadVec3f) {
    val j = i*3
    primitives(j) = v.x
    primitives(j + 1) = v.y
    primitives(j + 2) = v.z
  }
}

private[data] final class ViewVec3f[+R <: TangibleFloat](
  prim: ReadDataBuffer[RFloat, R], off: Int, str: Int
) extends BaseVec3f[R](prim, off, str) with DataView[Vec3f, R] {
  type Read = ReadDataView[Vec3f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec3f = {
    val j = offset + i*stride
    ConstVec3f(
      primitives(j),
      primitives(j + 1),
      primitives(j + 2)
    )
  }
  def update(i: Int, v: ReadVec3f) {
    val j = offset + i*stride
    primitives(j) = v.x
    primitives(j + 1) = v.y
    primitives(j + 2) = v.z
  }
}
