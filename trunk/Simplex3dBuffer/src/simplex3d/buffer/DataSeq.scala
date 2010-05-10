/*
 * Simplex3d, BaseBuffer module
 * Copyright (C) 2010 Simplex3d Team
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
import scala.annotation._
import scala.annotation.unchecked._
import simplex3d.math._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] abstract class BaseSeq[
  T <: MetaType, @specialized(Int, Float, Double) E, +D <: RawType
] (val buffer: D#BufferType) {

  final def componentBytes: Int = Binding.byteLength(componentBinding)
  final val byteSize = buffer.capacity*componentBytes
  final val byteOffset = offset*componentBytes
  final val byteStride = stride*componentBytes
  def bindingBuffer: Buffer

  final val size: Int = (buffer.capacity - offset + stride)/(components +stride)
  protected[buffer] final val step = components + stride

  def apply(i: Int) :E
  def update(i: Int, v: E)

  def makeArray(size: Int) :DataArray[T, D]
  def makeArray(array: D#ArrayType @uncheckedVariance) :DataArray[T, D]
  def makeBuffer(size: Int) :DataBuffer[T, D]
  def makeBuffer(byteBuffer: ByteBuffer) :DataBuffer[T, D]
  def makeView(byteBuffer: ByteBuffer, offset: Int, stride: Int) :DataView[T, D]

  def components: Int
  def componentBinding: Int
  def normalized: Boolean

  def offset: Int
  def stride: Int

  def backingSeq: ContiguousSeq[T#Component, D]

  protected def translatePut(
    destOffset: Int,
    src: ContiguousSeq[T#Component, _],
    srcOffset: Int,
    srcStep: Int,
    srcLim: Int
  )

  
  def put(
    index: Int,
    src: ContiguousSeq[T#Component, _],
    srcOffset: Int, srcStride: Int, count: Int
  ) {
    def grp(binding: Int) = {
      (binding: @switch) match {
        case Binding.SByte => 0
        case Binding.UByte => 0
        case Binding.SShort => 1
        case Binding.UShort => 2
        case Binding.SInt => 3
        case Binding.UInt => 3
        case Binding.RawFloat => 4
        case Binding.RawDouble => 5
        case _ => throw new AssertionError("Binding not found.")
      }
    }
    val group = grp(componentBinding)

    if (
      stride == 0 &&
      srcStride == 0 &&
      (
        componentBinding == src.componentBinding ||
        (!normalized && !src.normalized && group == grp(src.componentBinding))
      )
    ) {
      val srcLim = srcOffset + count*components
      if (srcLim < src.buffer.capacity) throw new BufferUnderflowException()

      buffer.position(index*step + offset)
      src.buffer.position(srcOffset)
      src.buffer.limit(srcLim)

      try {
        (group: @switch) match {
          case 0 =>
            buffer.asInstanceOf[ByteBuffer].put(
              src.buffer.asInstanceOf[ByteBuffer]
            )
          case 1 =>
            buffer.asInstanceOf[ShortBuffer].put(
              src.buffer.asInstanceOf[ShortBuffer]
            )
          case 2 =>
            buffer.asInstanceOf[CharBuffer].put(
              src.buffer.asInstanceOf[CharBuffer]
            )
          case 3 =>
            buffer.asInstanceOf[IntBuffer].put(
              src.buffer.asInstanceOf[IntBuffer]
            )
          case 4 =>
            buffer.asInstanceOf[FloatBuffer].put(
              src.buffer.asInstanceOf[FloatBuffer]
            )
          case 5 =>
            buffer.asInstanceOf[DoubleBuffer].put(
              src.buffer.asInstanceOf[DoubleBuffer]
            )
        }
      }
      finally {
        // Always restore the limit, since Buffer.get(index) depends on it.
        src.buffer.limit(src.buffer.capacity)
      }
    }
    else {
      val destOffset = index*step + offset
      val srcStep = srcStride + components
      val srcLim = srcOffset + count*srcStep

      if (index + count > size) throw new BufferOverflowException()
      if (srcLim > src.buffer.capacity) throw new BufferUnderflowException()

      translatePut(
        destOffset,
        src,
        srcOffset,
        srcStep,
        srcLim
      )
    }
  }

  def put(
    index: Int,
    src: ContiguousSeq[T#Component, _],
    srcOffset: Int, count: Int
  ) {
    put(
      index,
      src,
      srcOffset,
      0,
      count
    )
  }

  def put(
    index: Int,
    src: ContiguousSeq[T#Component, _]
  ) {
    put(
      index,
      src,
      0,
      0,
      src.size/components
    )
  }

  def put(
    src: ContiguousSeq[T#Component, _]
  ) {
    put(
      0,
      src,
      0,
      0,
      src.size/components
    )
  }

  def put(
    index: Int,
    src: DataSeq[T, _],
    first: Int, count: Int
  ) {
    put(
      index,
      src.backingSeq,
      src.offset + first*src.step,
      src.stride,
      count
    )
  }

  def put(
    index: Int,
    src: DataSeq[T, _]
  ) {
    put(
      index,
      src.backingSeq,
      src.offset,
      src.stride,
      src.size
    )
  }

  def put(
    src: DataSeq[T, _]
  ) {
    put(
      0,
      src.backingSeq,
      src.offset,
      src.stride,
      src.size
    )
  }

  def asArray() :DataArray[T, D] = {
    val copy = makeArray(size)
    copy.put(
      0,
      backingSeq,
      this.offset,
      this.stride,
      size
    )
    copy
  }
  def asBuffer() :DataBuffer[T, D] = {
    val copy = makeBuffer(size)
    copy.put(
      0,
      backingSeq,
      this.offset,
      this.stride,
      size
    )
    copy
  }
  def asView(byteBuffer: ByteBuffer, offset: Int, stride: Int) :DataView[T, D]={
    val copy = makeView(byteBuffer, offset, stride)
    copy.put(
      0,
      backingSeq,
      this.offset,
      this.stride,
      size
    )
    copy
  }
}

trait DataSeq[T <: MetaType, +D <: RawType] extends BaseSeq[T, T#Element, D]

trait ContiguousSeq[T <: MetaType, +D <: RawType] extends DataSeq[T, D] {
  final val offset = 0
  final val stride = 0
}

trait DataArray[T <: MetaType, +D <: RawType]
extends DataSeq[T, D] with ContiguousSeq[T, D] {
  final def bindingBuffer = buffer

  def array: D#ArrayType = backingSeq.array
  def backingSeq: DataArray[T#Component, D]
}

trait DataBuffer[T <: MetaType, +D <: RawType]
extends DataView[T, D] with ContiguousSeq[T, D]

trait DataView[T <: MetaType, +D <: RawType] extends DataSeq[T, D] {
  final def bindingBuffer = byteBuffer
  
  def byteBuffer: ByteBuffer = backingSeq.byteBuffer
  def backingSeq: DataBuffer[T#Component, D]

  if (!buffer.isDirect)
    throw new IllegalArgumentException(
      "The buffer must be direct."
    )
  if (byteBuffer.order != ByteOrder.nativeOrder)
    throw new IllegalArgumentException(
      "The buffer must have native byte order."
    )
}


object DataArray {
  def apply[T <: MetaType, D <: ReadType](array: D#ArrayType)(
    implicit ref: DataSeqFactoryRef[T, D]
  ) :DataArray[T, D] = {
    ref.factory.makeArray(array)
  }

  def apply[T <: MetaType, D <: ReadType](size: Int)(
    implicit ref: DataSeqFactoryRef[T, D]
  ) :DataArray[T, D] = {
    ref.factory.makeArray(size)
  }
}

object DataBuffer {
  def apply[T <: MetaType, D <: ReadType](buffer: ByteBuffer)(
    implicit ref: DataSeqFactoryRef[T, D]
  ) :DataBuffer[T, D] = {
    ref.factory.makeBuffer(buffer)
  }

  def apply[T <: MetaType, D <: ReadType](size: Int)(
    implicit ref: DataSeqFactoryRef[T, D]
  ) :DataBuffer[T, D] = {
    ref.factory.makeBuffer(size)
  }
}

object DataView {
  def apply[T <: MetaType, D <: ReadType](
    buffer: ByteBuffer, offset: Int, stride: Int
  )(implicit ref: DataSeqFactoryRef[T, D]) :DataView[T, D] = {
    ref.factory.makeView(buffer, offset, stride)
  }
}

final class DataSeqFactoryRef[T <: MetaType, D <: RawType](
  val factory: DataSeq[T, D]
)

// Extend this, add implicit tuples to your package object to enable constructor
abstract class GenericSeq[T <: Composite, +D <: RawType](
  val backingSeq: ContiguousSeq[T#Component, D]
) extends BaseSeq[T, T#Element, D](backingSeq.buffer) {
  
  final def componentBinding = backingSeq.componentBinding
  final def normalized: Boolean = backingSeq.normalized
}
