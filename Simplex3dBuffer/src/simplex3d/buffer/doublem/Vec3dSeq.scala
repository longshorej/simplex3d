/*
 * Simplex3d, DoubleBuffer module
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

package simplex3d.buffer.doublem

import java.nio._
import scala.annotation.unchecked._
import simplex3d.math.doublem._
import simplex3d.buffer._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] abstract class BaseVec3d[+R <: ReadableDouble](
  backing: ContiguousSeq[Double1, R]
) extends CompositeSeq[Vec3d, R](backing) {
  final def elementManifest = Vec3d.Manifest
  final def components: Int = 3

  def apply(i: Int) :ReadVec3d = {
    val j = offset + i*stride
    ConstVec3d(
      backingSeq(j),
      backingSeq(j + 1),
      backingSeq(j + 2)
    )
  }
  def update(i: Int, v: ReadVec3d) {
    val j = offset + i*stride
    backingSeq(j) = v.x
    backingSeq(j + 1) = v.y
    backingSeq(j + 2) = v.z
  }

  def mkReadDataArray(size: Int)
  :ReadDataArray[Vec3d, R] =
    new ArrayVec3d[R](
      backingSeq.mkReadDataArray(size*3).asInstanceOf[DataArray[Double1, R]]
    )

  def mkReadDataArray(array: R#ArrayType @uncheckedVariance)
  :ReadDataArray[Vec3d, R] =
    new ArrayVec3d[R](
      backingSeq.mkReadDataArray(array).asInstanceOf[DataArray[Double1, R]]
    )

  def mkReadDataBuffer(size: Int)
  :ReadDataBuffer[Vec3d, R] =
    new BufferVec3d[R](
      backingSeq.mkReadDataBuffer(size*3).asInstanceOf[DataBuffer[Double1, R]]
    )

  def mkReadDataBuffer(byteBuffer: ByteBuffer)
  :ReadDataBuffer[Vec3d, R] =
    new BufferVec3d[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Double1, R]]
    )

  def mkReadDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int)
  :ReadDataView[Vec3d, R] =
    new ViewVec3d[R](
      backingSeq.mkReadDataBuffer(byteBuffer).asInstanceOf[DataBuffer[Double1, R]],
      offset, stride
    )
}

private[buffer] final class ArrayVec3d[+R <: ReadableDouble](
  override val backingSeq: DataArray[Double1, R]
) extends BaseVec3d[R](backingSeq) with DataArray[Vec3d, R] {
  protected[buffer] def mkReadOnlyInstance() = new ArrayVec3d(
    backingSeq.asReadOnlySeq().asInstanceOf[DataArray[Double1, R]]
  )
}

private[buffer] final class BufferVec3d[+R <: ReadableDouble](
  override val backingSeq: DataBuffer[Double1, R]
) extends BaseVec3d[R](backingSeq) with DataBuffer[Vec3d, R] {
  protected[buffer] def mkReadOnlyInstance() = new BufferVec3d(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Double1, R]]
  )
}

private[buffer] final class ViewVec3d[+R <: ReadableDouble](
  override val backingSeq: DataBuffer[Double1, R],
  override val offset: Int,
  override val stride: Int
) extends BaseVec3d[R](backingSeq) with DataView[Vec3d, R] {
  protected[buffer] def mkReadOnlyInstance() = new ViewVec3d(
    backingSeq.asReadOnlySeq().asInstanceOf[DataBuffer[Double1, R]],
    offset, stride
  )
}