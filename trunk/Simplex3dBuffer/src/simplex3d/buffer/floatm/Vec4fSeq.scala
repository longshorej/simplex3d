/*
 * Simplex3d, FloatBuffer module
 * Copyright (C) 2010, Simplex3d Team
 *
 * This file is part of Simplex3dBuffer.
 *
 * Simplex3dBuffer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dBuffer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.buffer.floatm

import java.nio._
import scala.annotation.unchecked._
import simplex3d.math.floatm._
import simplex3d.buffer._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] abstract class BaseVec4f[+R <: DefinedFloat](
  primitive: Contiguous[RFloat, R], off: Int, str: Int
) extends CompositeSeq[Vec4f, R](primitive, off, str) {
  final def elemManifest = Vec4f.Manifest
  final def readManifest = Vec4f.ReadManifest
  final def components: Int = 4

  def mkDataArray(array: R#Array @uncheckedVariance)
  :DataArray[Vec4f, R] =
    new ArrayVec4f[R](
      backing.mkDataArray(array).asInstanceOf[DataArray[RFloat, R]]
    )

  def mkReadDataBuffer(byteBuffer: ByteBuffer)
  :ReadDataBuffer[Vec4f, R] =
    new BufferVec4f[R](
      backing.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[RFloat, R]]
    )

  protected def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int)
  :ReadDataView[Vec4f, R] =
    new ViewVec4f[R](
      backing.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[RFloat, R]],
      off, str
    )

  override def mkSerializableInstance() = new SerializableFloatData(components, rawType)
}

private[buffer] final class ArrayVec4f[+R <: DefinedFloat](
  backing: DataArray[RFloat, R]
) extends BaseVec4f[R](backing, 0, 4) with DataArray[Vec4f, R] {
  protected[buffer] def mkReadOnlyInstance() = new ArrayVec4f(
    backing.asReadOnly().asInstanceOf[DataArray[RFloat, R]]
  )

  def apply(i: Int) :ConstVec4f = {
    val j = i*4
    ConstVec4f(
      backing(j),
      backing(j + 1),
      backing(j + 2),
      backing(j + 3)
    )
  }
  def update(i: Int, v: ReadVec4f) {
    val j = i*4
    backing(j) = v.x
    backing(j + 1) = v.y
    backing(j + 2) = v.z
    backing(j + 3) = v.w
  }
}

private[buffer] final class BufferVec4f[+R <: DefinedFloat](
  backing: DataBuffer[RFloat, R]
) extends BaseVec4f[R](backing, 0, 4) with DataBuffer[Vec4f, R] {
  protected[buffer] def mkReadOnlyInstance() = new BufferVec4f(
    backing.asReadOnly().asInstanceOf[DataBuffer[RFloat, R]]
  )

  def apply(i: Int) :ConstVec4f = {
    val j = i*4
    ConstVec4f(
      backing(j),
      backing(j + 1),
      backing(j + 2),
      backing(j + 3)
    )
  }
  def update(i: Int, v: ReadVec4f) {
    val j = i*4
    backing(j) = v.x
    backing(j + 1) = v.y
    backing(j + 2) = v.z
    backing(j + 3) = v.w
  }
}

private[buffer] final class ViewVec4f[+R <: DefinedFloat](
  backing: DataBuffer[RFloat, R], off: Int, str: Int
) extends BaseVec4f[R](backing, off, str) with DataView[Vec4f, R] {
  protected[buffer] def mkReadOnlyInstance() = new ViewVec4f(
    backing.asReadOnly().asInstanceOf[DataBuffer[RFloat, R]],
    offset, stride
  )

  def apply(i: Int) :ConstVec4f = {
    val j = offset + i*stride
    ConstVec4f(
      backing(j),
      backing(j + 1),
      backing(j + 2),
      backing(j + 3)
    )
  }
  def update(i: Int, v: ReadVec4f) {
    val j = offset + i*stride
    backing(j) = v.x
    backing(j + 1) = v.y
    backing(j + 2) = v.z
    backing(j + 3) = v.w
  }
}
