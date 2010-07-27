/*
 * Simplex3d, IntBuffer module
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

package simplex3d.buffer.intm

import java.nio._
import scala.annotation.unchecked._
import simplex3d.math.intm._
import simplex3d.buffer._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] abstract class BaseVec4i[+R <: ReadableInt](
  backing: ContiguousSeq[Int1, R]
) extends CompositeSeq[Vec4i, R](backing) {
  final def elementManifest = Vec4i.Manifest
  final def components: Int = 4

  def apply(i: Int) :ReadVec4i = {
    val j = offset + i*stride
    ConstVec4i(
      backingSeq(j),
      backingSeq(j + 1),
      backingSeq(j + 2),
      backingSeq(j + 3)
    )
  }
  def update(i: Int, v: ReadVec4i) {
    val j = offset + i*stride
    backingSeq(j) = v.x
    backingSeq(j + 1) = v.y
    backingSeq(j + 2) = v.z
    backingSeq(j + 3) = v.w
  }

  def mkReadDataArray(size: Int)
  :ReadDataArray[Vec4i, R] =
    new ArrayVec4i[R](
      backingSeq.mkReadDataArray(size*4).asInstanceOf[DataArray[Int1, R]]
    )

  def mkReadDataArray(array: R#ArrayType @uncheckedVariance)
  :ReadDataArray[Vec4i, R] =
    new ArrayVec4i[R](
      backingSeq.mkReadDataArray(array).asInstanceOf[DataArray[Int1, R]]
    )

  def mkReadDataBuffer(size: Int)
  :ReadDataBuffer[Vec4i, R] =
    new BufferVec4i[R](
      backingSeq.mkReadDataBuffer(size*4).asInstanceOf[DataBuffer[Int1, R]]
    )

  def mkReadDataBuffer(byteBuffer: ByteBuffer)
  :ReadDataBuffer[Vec4i, R] =
    new BufferVec4i[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Int1, R]]
    )

  def mkReadDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int)
  :ReadDataView[Vec4i, R] =
    new ViewVec4i[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Int1, R]],
      offset, stride
    )
}

private[buffer] final class ArrayVec4i[+R <: ReadableInt](
  override val backingSeq: DataArray[Int1, R]
) extends BaseVec4i[R](backingSeq) with DataArray[Vec4i, R] {
  protected[buffer] def mkReadOnlyInstance() = new ArrayVec4i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataArray[Int1, R]]
  )
}

private[buffer] final class BufferVec4i[+R <: ReadableInt](
  override val backingSeq: DataBuffer[Int1, R]
) extends BaseVec4i[R](backingSeq) with DataBuffer[Vec4i, R] {
  protected[buffer] def mkReadOnlyInstance() = new BufferVec4i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Int1, R]]
  )
}

private[buffer] final class ViewVec4i[+R <: ReadableInt](
  override val backingSeq: DataBuffer[Int1, R],
  override val offset: Int,
  override val stride: Int
) extends BaseVec4i[R](backingSeq) with DataView[Vec4i, R] {
  protected[buffer] def mkReadOnlyInstance() = new ViewVec4i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Int1, R]],
    offset, stride
  )
}