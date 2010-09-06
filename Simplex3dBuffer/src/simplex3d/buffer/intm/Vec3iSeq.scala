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
private[buffer] abstract class BaseVec3i[+R <: ReadableInt](
  backing: ContiguousSeq[Int1, R], off: Int, str: Int
) extends CompositeSeq[Vec3i, R](backing, off, str) {
  final def elementManifest = Vec3i.Manifest
  final def components: Int = 3

  def apply(i: Int) :ReadVec3i = {
    val j = offset + i*stride
    ConstVec3i(
      backingSeq(j),
      backingSeq(j + 1),
      backingSeq(j + 2)
    )
  }
  def update(i: Int, v: ReadVec3i) {
    val j = offset + i*stride
    backingSeq(j) = v.x
    backingSeq(j + 1) = v.y
    backingSeq(j + 2) = v.z
  }

  def mkDataArray(size: Int)
  :DataArray[Vec3i, R] =
    new ArrayVec3i[R](
      backingSeq.mkDataArray(size*3).asInstanceOf[DataArray[Int1, R]]
    )

  def mkDataArray(array: R#ArrayType @uncheckedVariance)
  :DataArray[Vec3i, R] =
    new ArrayVec3i[R](
      backingSeq.mkDataArray(array).asInstanceOf[DataArray[Int1, R]]
    )

  def mkDataBuffer(size: Int)
  :DataBuffer[Vec3i, R] =
    new BufferVec3i[R](
      backingSeq.mkDataBuffer(size*3).asInstanceOf[DataBuffer[Int1, R]]
    )

  def mkReadDataBuffer(byteBuffer: ByteBuffer)
  :ReadDataBuffer[Vec3i, R] =
    new BufferVec3i[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Int1, R]]
    )

  def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int)
  :ReadDataView[Vec3i, R] =
    new ViewVec3i[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Int1, R]],
      off, str
    )
}

private[buffer] final class ArrayVec3i[+R <: ReadableInt](
  backingSeq: DataArray[Int1, R]
) extends BaseVec3i[R](backingSeq, 0, 3) with DataArray[Vec3i, R] {
  protected[buffer] def mkReadOnlyInstance() = new ArrayVec3i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataArray[Int1, R]]
  )
}

private[buffer] final class BufferVec3i[+R <: ReadableInt](
  backingSeq: DataBuffer[Int1, R]
) extends BaseVec3i[R](backingSeq, 0, 3) with DataBuffer[Vec3i, R] {
  protected[buffer] def mkReadOnlyInstance() = new BufferVec3i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Int1, R]]
  )
}

private[buffer] final class ViewVec3i[+R <: ReadableInt](
  backingSeq: DataBuffer[Int1, R],
  off: Int,
  str: Int
) extends BaseVec3i[R](backingSeq, off, str) with DataView[Vec3i, R] {
  protected[buffer] def mkReadOnlyInstance() = new ViewVec3i(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Int1, R]],
    off, str
  )
}
