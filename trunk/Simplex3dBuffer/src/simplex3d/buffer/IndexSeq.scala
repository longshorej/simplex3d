/*
 * Simplex3d, BaseBuffer module
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

package simplex3d.buffer

import java.nio._
import scala.annotation.unchecked._


/**
 * @author Aleksey Nikiforov (lex)
 */
trait ReadIndexSeq[+R <: RawData]
extends ReadContiguousSeq[Int1, R] {
  protected def mkReadOnlyInstance() :ReadIndexSeq[R]
  def asReadOnlySeq() :ReadIndexSeq[R]

  def mkReadIndexArray(size: Int) :ReadIndexArray[R] =
    mkReadDataArray(size).asInstanceOf[ReadIndexArray[R]]
  def mkReadIndexArray(array: R#ArrayType @uncheckedVariance) :ReadIndexArray[R] =
    mkReadDataArray(array).asInstanceOf[ReadIndexArray[R]]
  def mkReadIndexBuffer(size: Int) :ReadIndexBuffer[R] =
    mkReadDataBuffer(size).asInstanceOf[ReadIndexBuffer[R]]
  def mkReadIndexBuffer(byteBuffer: ByteBuffer) :ReadIndexBuffer[R] =
    mkReadDataBuffer(byteBuffer).asInstanceOf[ReadIndexBuffer[R]]

  def mkIndexArray(size: Int) :IndexArray[R] =
    mkDataArray(size).asInstanceOf[IndexArray[R]]
  def mkIndexArray(array: R#ArrayType @uncheckedVariance) :IndexArray[R] =
    mkDataArray(array).asInstanceOf[IndexArray[R]]
  def mkIndexBuffer(size: Int) :IndexBuffer[R] =
    mkDataBuffer(size).asInstanceOf[IndexBuffer[R]]
  def mkIndexBuffer(byteBuffer: ByteBuffer) :IndexBuffer[R] =
    mkDataBuffer(byteBuffer).asInstanceOf[IndexBuffer[R]]

  def copyAsIndexArray() :IndexArray[R] =
    super.copyAsDataArray().asInstanceOf[IndexArray[R]]
  def copyAsIndexBuffer() :IndexBuffer[R] =
    super.copyAsDataBuffer().asInstanceOf[IndexBuffer[R]]
}

trait IndexSeq[+R <: RawData]
extends ContiguousSeq[Int1, R] with ReadIndexSeq[R]


trait ReadIndexArray[+R <: RawData]
extends ReadIndexSeq[R] with ReadDataArray[Int1, R] {
  protected def mkReadOnlyInstance() :ReadIndexArray[R]
  override def asReadOnlySeq() :ReadIndexArray[R] = {
    asReadOnlySeqImpl().asInstanceOf[ReadIndexArray[R]]
  }
}

trait IndexArray[+R <: RawData]
extends IndexSeq[R] with DataArray[Int1, R] with ReadIndexArray[R]


trait ReadIndexBuffer[+R <: RawData]
extends ReadIndexSeq[R] with ReadDataBuffer[Int1, R] {
  protected def mkReadOnlyInstance() :ReadIndexBuffer[R]
  override def asReadOnlySeq() :ReadIndexBuffer[R] = {
    asReadOnlySeqImpl().asInstanceOf[ReadIndexBuffer[R]]
  }
}

trait IndexBuffer[+R <: RawData]
extends IndexSeq[R] with DataBuffer[Int1, R] with ReadIndexBuffer[R]


object ReadIndexArray {
  def apply[R <: ReadableIndex](da: ReadDataArray[_, R])(
    implicit ref: FactoryRef[Int1, R]
  ) :ReadIndexArray[R] = {
    val res = ref.factory.mkReadDataArray(da.sharedArray)
    if (da.isReadOnly) res.asReadOnlySeq() else res
  }
}

object IndexArray {
  def apply[R <: ReadableIndex](array: R#ArrayType)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexArray[R] = {
    ref.factory.mkDataArray(array)
  }

  def apply[R <: ReadableIndex](size: Int)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexArray[R] = {
    ref.factory.mkDataArray(size)
  }

  def apply[R <: ReadableIndex](vals: Int*)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexArray[R] = {
    val data = ref.factory.mkDataArray(vals.size)
    data.put(vals)
    data
  }

  def apply[R <: ReadableIndex](da: DataArray[_, R])(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexArray[R] = {
    if (da.isReadOnly) throw new ClassCastException(
      "The DataArray must not be read-only."
    )
    ref.factory.mkDataArray(da.array)
  }
}

object ReadIndexBuffer {
  def apply[R <: ReadableIndex](buffer: ByteBuffer)(
    implicit ref: FactoryRef[Int1, R]
  ) :ReadIndexBuffer[R] = {
    ref.factory.mkReadDataBuffer(buffer)
  }

  def apply[R <: ReadableIndex](db: ReadDataBuffer[_, _])(
    implicit ref: FactoryRef[Int1, R]
  ) :ReadIndexBuffer[R] = {
    val res = ref.factory.mkReadDataBuffer(db.sharedBuffer)
    if (db.isReadOnly) res.asReadOnlySeq() else res
  }
}

object IndexBuffer {
  def apply[R <: ReadableIndex](buffer: ByteBuffer)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexBuffer[R] = {
    ref.factory.mkDataBuffer(buffer)
  }

  def apply[R <: ReadableIndex](size: Int)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexBuffer[R] = {
    ref.factory.mkDataBuffer(size)
  }

  def apply[R <: ReadableIndex](vals: Int*)(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexBuffer[R] = {
    val data = ref.factory.mkDataBuffer(vals.size)
    data.put(vals)
    data
  }

  def apply[R <: ReadableIndex](db: DataBuffer[_, _])(
    implicit ref: FactoryRef[Int1, R]
  ) :IndexBuffer[R] = {
    if (db.isReadOnly) throw new ClassCastException(
      "The DataBuffer must not be read-only."
    )
    ref.factory.mkDataBuffer(db.sharedBuffer)
  }
}