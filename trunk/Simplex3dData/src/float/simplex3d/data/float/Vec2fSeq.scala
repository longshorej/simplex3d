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
private[data] abstract class BaseVec2f[+R <: TangibleFloat](
  prim: ReadContiguous[RFloat, R], off: Int, str: Int
) extends CompositeSeq[Vec2f, R, TangibleFloat](prim, off, str) {
  final def formatTag = Vec2f.Tag
  final def accessorTag = Vec2f.Tag
  final def components: Int = 2

  final def mkReadDataArray[P <: TangibleFloat](prim: ReadDataArray[Vec2f#Component, P])
  :ReadDataArray[Vec2f, P] = {
    (prim.rawEnum match {
      case RFloat => new ArrayVec2fRFloat(prim.asInstanceOf[ArrayRFloatRFloat])
      case _ => new ArrayVec2f(prim)
    }).asInstanceOf[ReadDataArray[Vec2f, P]]
  }
  final def mkReadDataBuffer[P <: TangibleFloat](prim: ReadDataBuffer[Vec2f#Component, P])
  :ReadDataBuffer[Vec2f, P] = {
    (prim.rawEnum match {
      case RFloat => new BufferVec2fRFloat(prim.asInstanceOf[BufferRFloatRFloat])
      case _ => new BufferVec2f(prim)
    }).asInstanceOf[ReadDataBuffer[Vec2f, P]]
  }
  protected final def mkReadDataViewInstance[P <: TangibleFloat](
    prim: ReadDataBuffer[Vec2f#Component, P], off: Int, str: Int
  ) :ReadDataView[Vec2f, P] = {
    (prim.rawEnum match {
      case RFloat => new ViewVec2fRFloat(prim.asInstanceOf[BufferRFloatRFloat], off, str)
      case _ => new ViewVec2f(prim, off, str)
    }).asInstanceOf[ReadDataView[Vec2f, P]]
  }

  final override def mkSerializableInstance() = new CompositeRFloat(components)
}

private[data] final class ArrayVec2f[+R <: TangibleFloat](
  prim: ReadDataArray[RFloat, R]
) extends BaseVec2f[R](prim, 0, 2) with DataArray[Vec2f, R] {
  type Read = ReadDataArray[Vec2f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec2f = {
    val j = i*2
    ConstVec2f(
      primitives(j),
      primitives(j + 1)
    )
  }
  def update(i: Int, v: ReadVec2f) {
    val j = i*2
    primitives(j) = v.x
    primitives(j + 1) = v.y
  }
}

private[data] final class BufferVec2f[+R <: TangibleFloat](
  prim: ReadDataBuffer[RFloat, R]
) extends BaseVec2f[R](prim, 0, 2) with DataBuffer[Vec2f, R] {
  type Read = ReadDataBuffer[Vec2f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec2f = {
    val j = i*2
    ConstVec2f(
      primitives(j),
      primitives(j + 1)
    )
  }
  def update(i: Int, v: ReadVec2f) {
    val j = i*2
    primitives(j) = v.x
    primitives(j + 1) = v.y
  }
}

private[data] final class ViewVec2f[+R <: TangibleFloat](
  prim: ReadDataBuffer[RFloat, R], off: Int, str: Int
) extends BaseVec2f[R](prim, off, str) with DataView[Vec2f, R] {
  type Read = ReadDataView[Vec2f, R @uncheckedVariance]

  def apply(i: Int) :ConstVec2f = {
    val j = offset + i*stride
    ConstVec2f(
      primitives(j),
      primitives(j + 1)
    )
  }
  def update(i: Int, v: ReadVec2f) {
    val j = offset + i*stride
    primitives(j) = v.x
    primitives(j + 1) = v.y
  }
}
