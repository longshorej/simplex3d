/*
 * Simplex3d, FloatBuffer module
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

package simplex3d.buffer.floatm

import java.nio._
import simplex3d.buffer._
import simplex3d.buffer.FloatUtil.{
  floatToHalfFloat => toHalfFloat, floatFromHalfFloat => fromHalfFloat
}
import simplex3d.math._
import simplex3d.math.floatm.FloatMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[floatm] object Shared {
  // Use double multiplication for float sequences to prevent errors
  // Use division for double sequences to prevent errors
  final val fromNSByte = 0.00787401574803149606
  final val fromNUByte = 0.00392156862745098039
  final val fromNSShort = 3.05185094759971922971e-5
  final val fromNUShort = 1.52590218966964217594e-5
  final val fromNSInt = 4.65661287524579692411e-10
  final val fromNUInt = 2.32830643708079737543e-10

  final val toNSByte = 127f
  final val toNUByte = 255f
  final val toNSShort = 32767f
  final val toNUShort = 65535f
  final val toNSInt = 2147483647f
  final val toNUInt = 4294967295f

  final def iround(x: Float) :Int = {
    if (x >= 0) int(x + 0.5f)
    else int(x - 0.5f)
  }
}
import Shared._

private[buffer] sealed abstract class BaseFloat1[+D <: ReadableFloat](
  buff: D#BufferType
) extends BaseSeq[Float1, Float, D](buff) {
  final def elementManifest = componentManifest
  final def componentManifest = scala.reflect.ClassManifest.Float
  final def components: Int = 1
}


// Type: SByte
private[buffer] sealed abstract class SeqFloat1SByte(
  buff: ByteBuffer
) extends BaseFloat1[SByte](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1SByte(new Array[Byte](size))
  final def mkDataArray(array: Array[Byte]) =
    new ArrayFloat1SByte(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1SByte(allocateByteBuffer(size))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1SByte(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1SByte(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1SByte(
  override val array: Array[Byte]
) extends SeqFloat1SByte(ByteBuffer.wrap(array)) with DataArray[Float1, SByte] {
  def this() = this(new Array[Byte](0))
  def backingSeq = this

  def bindingType = RawType.SByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = array(i)
  def update(i: Int, v: Float) = array(i) = byte(iround(v))
}

private[buffer] final class BufferFloat1SByte(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1SByte(
  sharedBuffer.duplicate()
) with DataBuffer[Float1, SByte] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(i)
  def update(i: Int, v: Float) = buffer.put(i, byte(iround(v)))
}

private[buffer] final class ViewFloat1SByte(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1SByte(
  sharedBuffer.duplicate()
) with DataView[Float1, SByte] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1SByte(sharedBuffer)

  def bindingType = RawType.SByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(offset + i*stride)
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, byte(iround(v)))
}


// Type: UByte
private[buffer] sealed abstract class SeqFloat1UByte(
  buff: ByteBuffer
) extends BaseFloat1[UByte](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1UByte(new Array[Byte](size))
  final def mkDataArray(array: Array[Byte]) =
    new ArrayFloat1UByte(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1UByte(allocateByteBuffer(size))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1UByte(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1UByte(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1UByte(
  override val array: Array[Byte]
) extends SeqFloat1UByte(ByteBuffer.wrap(array)) with DataArray[Float1, UByte] {
  def this() = this(new Array[Byte](0))
  def backingSeq = this

  def bindingType = RawType.UByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = array(i) & 0xFF
  def update(i: Int, v: Float) = array(i) = byte(iround(v))
}

private[buffer] final class BufferFloat1UByte(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1UByte(
  sharedBuffer.duplicate()
) with DataBuffer[Float1, UByte] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(i) & 0xFF
  def update(i: Int, v: Float) = buffer.put(i, byte(iround(v)))
}

private[buffer] final class ViewFloat1UByte(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1UByte(
  sharedBuffer.duplicate()
) with DataView[Float1, UByte] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1UByte(sharedBuffer)

  def bindingType = RawType.UByte
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(offset + i*stride) & 0xFF
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, byte(iround(v)))
}


// Type: NSByte
private[buffer] sealed abstract class SeqFloat1NSByte(
  buff: ByteBuffer
) extends BaseFloat1[NSByte](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NSByte(new Array[Byte](size))
  final def mkDataArray(array: Array[Byte]) =
    new ArrayFloat1NSByte(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NSByte(allocateByteBuffer(size))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NSByte(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NSByte(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NSByte(
  override val array: Array[Byte]
) extends SeqFloat1NSByte(
  ByteBuffer.wrap(array)
) with DataArray[Float1, NSByte] {
  def this() = this(new Array[Byte](0))
  def backingSeq = this

  def bindingType = RawType.SByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = array(i)
    if (v < -127) -1 else float(v*fromNSByte)
  }
  def update(i: Int, v: Float) =
    array(i) = byte(iround(clamp(v, -1, 1)*toNSByte))
}

private[buffer] final class BufferFloat1NSByte(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NSByte(
  sharedBuffer.duplicate()
) with DataBuffer[Float1, NSByte] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(i)
    if (v < -127) -1 else float(v*fromNSByte)
  }
  def update(i: Int, v: Float) = buffer.put(
    i,
    byte(iround(clamp(v, -1, 1)*toNSByte))
  )
}

private[buffer] final class ViewFloat1NSByte(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NSByte(
  sharedBuffer.duplicate()
) with DataView[Float1, NSByte] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NSByte(sharedBuffer)

  def bindingType = RawType.SByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(offset + i*stride)
    if (v < -127) -1 else float(v*fromNSByte)
  }
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    byte(iround(clamp(v, -1, 1)*toNSByte))
  )
}


// Type: NUByte
private[buffer] sealed abstract class SeqFloat1NUByte(
  buff: ByteBuffer
) extends BaseFloat1[NUByte](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NUByte(new Array[Byte](size))
  final def mkDataArray(array: Array[Byte]) =
    new ArrayFloat1NUByte(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NUByte(allocateByteBuffer(size))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NUByte(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NUByte(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NUByte(
  override val array: Array[Byte]
) extends SeqFloat1NUByte(
  ByteBuffer.wrap(array)
) with DataArray[Float1, NUByte] {
  def this() = this(new Array[Byte](0))
  def backingSeq = this

  def bindingType = RawType.UByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = float((array(i) & 0xFF)*fromNUByte)
  def update(i: Int, v: Float) = array(i) = byte(iround(clamp(v, 0, 1)*toNUByte))
}

private[buffer] final class BufferFloat1NUByte(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NUByte(
  sharedBuffer.duplicate()
) with DataBuffer[Float1, NUByte] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = float((buffer.get(i) & 0xFF)*fromNUByte)
  def update(i: Int, v: Float) = buffer.put(
    i,
    byte(iround(clamp(v, 0, 1)*toNUByte))
  )
}

private[buffer] final class ViewFloat1NUByte(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NUByte(
  sharedBuffer.duplicate()
) with DataView[Float1, NUByte] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NUByte(sharedBuffer)

  def bindingType = RawType.UByte
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(
    (buffer.get(offset + i*stride) & 0xFF)*fromNUByte
  )
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    byte(iround(clamp(v, 0, 1)*toNUByte))
  )
}


// Type: SShort
private[buffer] sealed abstract class SeqFloat1SShort(
  buff: ShortBuffer
) extends BaseFloat1[SShort](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1SShort(new Array[Short](size))
  final def mkDataArray(array: Array[Short]) =
    new ArrayFloat1SShort(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1SShort(allocateByteBuffer(size*2))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1SShort(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1SShort(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1SShort(
  override val array: Array[Short]
) extends SeqFloat1SShort(
  ShortBuffer.wrap(array)
) with DataArray[Float1, SShort] {
  def this() = this(new Array[Short](0))
  def backingSeq = this

  def bindingType = RawType.SShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = array(i)
  def update(i: Int, v: Float) = array(i) = short(iround(v))
}

private[buffer] final class BufferFloat1SShort(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1SShort(
  sharedBuffer.asShortBuffer()
) with DataBuffer[Float1, SShort] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(i)
  def update(i: Int, v: Float) = buffer.put(i, short(iround(v)))
}

private[buffer] final class ViewFloat1SShort(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1SShort(
  sharedBuffer.asShortBuffer()
) with DataView[Float1, SShort] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1SShort(sharedBuffer)

  def bindingType = RawType.SShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(offset + i*stride)
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, short(iround(v)))
}


// Type: UShort
private[buffer] sealed abstract class SeqFloat1UShort(
  buff: CharBuffer
) extends BaseFloat1[UShort](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1UShort(new Array[Char](size))
  final def mkDataArray(array: Array[Char]) =
    new ArrayFloat1UShort(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1UShort(allocateByteBuffer(size*2))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1UShort(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1UShort(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1UShort(
  override val array: Array[Char]
) extends SeqFloat1UShort(
  CharBuffer.wrap(array)
) with DataArray[Float1, UShort] {
  def this() = this(new Array[Char](0))
  def backingSeq = this

  def bindingType = RawType.UShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = array(i)
  def update(i: Int, v: Float) = array(i) = iround(v).asInstanceOf[Char]
}

private[buffer] final class BufferFloat1UShort(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1UShort(
  sharedBuffer.asCharBuffer()
) with DataBuffer[Float1, UShort] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(i)
  def update(i: Int, v: Float) = buffer.put(i, iround(v).asInstanceOf[Char])
}

private[buffer] final class ViewFloat1UShort(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1UShort(
  sharedBuffer.asCharBuffer()
) with DataView[Float1, UShort] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1UShort(sharedBuffer)

  def bindingType = RawType.UShort
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(offset + i*stride)
  def update(i: Int, v: Float) = {
    buffer.put(offset + i*stride, iround(v).asInstanceOf[Char])
  }
}


// Type: NSShort
private[buffer] sealed abstract class SeqFloat1NSShort(
  buff: ShortBuffer
) extends BaseFloat1[NSShort](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NSShort(new Array[Short](size))
  final def mkDataArray(array: Array[Short]) =
    new ArrayFloat1NSShort(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NSShort(allocateByteBuffer(size*2))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NSShort(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NSShort(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NSShort(
  override val array: Array[Short]
) extends SeqFloat1NSShort(
  ShortBuffer.wrap(array)
) with DataArray[Float1, NSShort] {
  def this() = this(new Array[Short](0))
  def backingSeq = this

  def bindingType = RawType.SShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = array(i)
    if (v < -32767) -1 else float(v*fromNSShort)
  }
  def update(i: Int, v: Float) =
    array(i) = short(iround(clamp(v, -1, 1)*toNSShort))
}

private[buffer] final class BufferFloat1NSShort(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NSShort(
  sharedBuffer.asShortBuffer()
) with DataBuffer[Float1, NSShort] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(i)
    if (v < -32767) -1 else float(v*fromNSShort)
  }
  def update(i: Int, v: Float) = buffer.put(
    i,
    short(iround(clamp(v, -1, 1)*toNSShort))
  )
}

private[buffer] final class ViewFloat1NSShort(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NSShort(
  sharedBuffer.asShortBuffer()
) with DataView[Float1, NSShort] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NSShort(sharedBuffer)

  def bindingType = RawType.SShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(offset + i*stride)
    if (v < -32767) -1 else float(v*fromNSShort)
  }
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    short(iround(clamp(v, -1, 1)*toNSShort))
  )
}


// Type: NUShort
private[buffer] sealed abstract class SeqFloat1NUShort(
  buff: CharBuffer
) extends BaseFloat1[NUShort](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NUShort(new Array[Char](size))
  final def mkDataArray(array: Array[Char]) =
    new ArrayFloat1NUShort(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NUShort(allocateByteBuffer(size*2))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NUShort(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NUShort(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NUShort(
  override val array: Array[Char]
) extends SeqFloat1NUShort(
  CharBuffer.wrap(array)
) with DataArray[Float1, NUShort] {
  def this() = this(new Array[Char](0))
  def backingSeq = this

  def bindingType = RawType.UShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(array(i)*fromNUShort)
  def update(i: Int, v: Float) {
    array(i) = iround(clamp(v, 0, 1)*toNUShort).asInstanceOf[Char]
  }
}

private[buffer] final class BufferFloat1NUShort(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NUShort(
  sharedBuffer.asCharBuffer()
) with DataBuffer[Float1, NUShort] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(buffer.get(i)*fromNUShort)
  def update(i: Int, v: Float) = buffer.put(
    i,
    iround(clamp(v, 0, 1)*toNUShort).asInstanceOf[Char]
  )
}

private[buffer] final class ViewFloat1NUShort(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NUShort(
  sharedBuffer.asCharBuffer()
) with DataView[Float1, NUShort] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NUShort(sharedBuffer)

  def bindingType = RawType.UShort
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(buffer.get(offset + i*stride)*fromNUShort)
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    iround(clamp(v, 0, 1)*toNUShort).asInstanceOf[Char]
  )
}


// Type: SInt
private[buffer] sealed abstract class SeqFloat1SInt(
  buff: IntBuffer
) extends BaseFloat1[SInt](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1SInt(new Array[Int](size))
  final def mkDataArray(array: Array[Int]) =
    new ArrayFloat1SInt(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1SInt(allocateByteBuffer(size*4))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1SInt(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1SInt(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1SInt(
  override val array: Array[Int]
) extends SeqFloat1SInt(IntBuffer.wrap(array)) with DataArray[Float1, SInt] {
  def this() = this(new Array[Int](0))
  def backingSeq = this

  def bindingType = RawType.SInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = array(i)
  def update(i: Int, v: Float) = array(i) = int(iround(v))
}

private[buffer] final class BufferFloat1SInt(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1SInt(sharedBuffer.asIntBuffer()) with DataBuffer[Float1, SInt]{
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(i)
  def update(i: Int, v: Float) = buffer.put(i, int(iround(v)))
}

private[buffer] final class ViewFloat1SInt(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1SInt(sharedBuffer.asIntBuffer()) with DataView[Float1, SInt] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1SInt(sharedBuffer)

  def bindingType = RawType.SInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = buffer.get(offset + i*stride)
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, int(iround(v)))
}


// Type: UInt
private[buffer] sealed abstract class SeqFloat1UInt(
  buff: IntBuffer
) extends BaseFloat1[UInt](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1UInt(new Array[Int](size))
  final def mkDataArray(array: Array[Int]) =
    new ArrayFloat1UInt(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1UInt(allocateByteBuffer(size*4))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1UInt(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1UInt(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1UInt(
  override val array: Array[Int]
) extends SeqFloat1UInt(IntBuffer.wrap(array)) with DataArray[Float1, UInt] {
  def this() = this(new Array[Int](0))
  def backingSeq = this

  def bindingType = RawType.UInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = long(array(i)) & 0xFFFFFFFFL
  def update(i: Int, v: Float) = array(i) = int(long(iround(v)) & 0xFFFFFFFFL)
}

private[buffer] final class BufferFloat1UInt(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1UInt(sharedBuffer.asIntBuffer()) with DataBuffer[Float1, UInt]{
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = long(buffer.get(i)) & 0xFFFFFFFFL
  def update(i: Int, v: Float) = buffer.put(
    i,
    int(long(iround(v)) & 0xFFFFFFFFL)
  )
}

private[buffer] final class ViewFloat1UInt(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1UInt(sharedBuffer.asIntBuffer()) with DataView[Float1, UInt] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1UInt(sharedBuffer)

  def bindingType = RawType.UInt
  def normalized: Boolean = false

  def apply(i: Int) :Float = long(buffer.get(offset + i*stride)) & 0xFFFFFFFFL
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    int(long(iround(v)) & 0xFFFFFFFFL)
  )
}


// Type: NSInt
private[buffer] sealed abstract class SeqFloat1NSInt(
  buff: IntBuffer
) extends BaseFloat1[NSInt](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NSInt(new Array[Int](size))
  final def mkDataArray(array: Array[Int]) =
    new ArrayFloat1NSInt(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NSInt(allocateByteBuffer(size*4))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NSInt(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NSInt(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NSInt(
  override val array: Array[Int]
) extends SeqFloat1NSInt(
  IntBuffer.wrap(array)
) with DataArray[Float1, NSInt] {
  def this() = this(new Array[Int](0))
  def backingSeq = this

  def bindingType = RawType.SInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = array(i)
    if (v < -2147483647) -1 else float(v*fromNSInt)
  }
  def update(i: Int, v: Float) = array(i) = int(iround(clamp(v, -1, 1)*toNSInt))
}

private[buffer] final class BufferFloat1NSInt(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NSInt(
  sharedBuffer.asIntBuffer()
) with DataBuffer[Float1, NSInt] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.SInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(i)
    if (v < -2147483647) -1 else float(v*fromNSInt)
  }
  def update(i: Int, v: Float) = buffer.put(
    i,
    int(iround(clamp(v, -1, 1)*toNSInt))
  )
}

private[buffer] final class ViewFloat1NSInt(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NSInt(
  sharedBuffer.asIntBuffer()
) with DataView[Float1, NSInt] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NSInt(sharedBuffer)

  def bindingType = RawType.SInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = {
    val v = buffer.get(offset + i*stride)
    if (v < -2147483647) -1 else float(v*fromNSInt)
  }
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    int(iround(clamp(v, -1, 1)*toNSInt))
  )
}


// Type: NUInt
private[buffer] sealed abstract class SeqFloat1NUInt(
  buff: IntBuffer
) extends BaseFloat1[NUInt](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1NUInt(new Array[Int](size))
  final def mkDataArray(array: Array[Int]) =
    new ArrayFloat1NUInt(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1NUInt(allocateByteBuffer(size*4))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1NUInt(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1NUInt(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1NUInt(
  override val array: Array[Int]
) extends SeqFloat1NUInt(
  IntBuffer.wrap(array)
) with DataArray[Float1, NUInt] {
  def this() = this(new Array[Int](0))
  def backingSeq = this

  def bindingType = RawType.UInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = float((long(array(i)) & 0xFFFFFFFFL)*fromNUInt)
  def update(i: Int, v: Float) = array(i) = int(iround(clamp(v, 0, 1)*toNUInt))
}

private[buffer] final class BufferFloat1NUInt(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1NUInt(
  sharedBuffer.asIntBuffer()
) with DataBuffer[Float1, NUInt] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def bindingType = RawType.UInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(
    (long(buffer.get(i)) & 0xFFFFFFFFL)*fromNUInt
  )
  def update(i: Int, v: Float) = buffer.put(
    i,
    int(iround(clamp(v, 0, 1)*toNUInt))
  )
}

private[buffer] final class ViewFloat1NUInt(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1NUInt(
  sharedBuffer.asIntBuffer()
) with DataView[Float1, NUInt] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1NUInt(sharedBuffer)

  def bindingType = RawType.UInt
  def normalized: Boolean = true

  def apply(i: Int) :Float = float(
    (long(buffer.get(offset + i*stride)) & 0xFFFFFFFFL)*fromNUInt
  )
  def update(i: Int, v: Float) = buffer.put(
    offset + i*stride,
    int(iround(clamp(v, 0, 1)*toNUInt))
  )
}


// Type: HalfFloat
private[buffer] sealed abstract class SeqFloat1HalfFloat(
  buff: ShortBuffer
) extends BaseFloat1[HalfFloat](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()

  final def mkDataArray(size: Int) =
    new ArrayFloat1HalfFloat(new Array[Short](size))
  final def mkDataArray(array: Array[Short]) =
    new ArrayFloat1HalfFloat(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1HalfFloat(allocateByteBuffer(size*2))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1HalfFloat(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1HalfFloat(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1HalfFloat(
  override val array: Array[Short]
) extends SeqFloat1HalfFloat(
  ShortBuffer.wrap(array)
) with DataArray[Float1, HalfFloat] {
  def this() = this(new Array[Short](0))
  def backingSeq = this

  def normalized: Boolean = false
  def bindingType: Int = RawType.HalfFloat

  def apply(i: Int) :Float = fromHalfFloat(array(i))
  def update(i: Int, v: Float) = array(i) = toHalfFloat(v)
}

private[buffer] final class BufferFloat1HalfFloat(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1HalfFloat(
  sharedBuffer.asShortBuffer()
) with DataBuffer[Float1, HalfFloat] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def normalized: Boolean = false
  def bindingType: Int = RawType.HalfFloat

  def apply(i: Int) :Float = fromHalfFloat(buffer.get(i))
  def update(i: Int, v: Float) = buffer.put(i, toHalfFloat(v))
}

private[buffer] final class ViewFloat1HalfFloat(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1HalfFloat(
  sharedBuffer.asShortBuffer()
) with DataView[Float1, HalfFloat] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1HalfFloat(sharedBuffer)

  def normalized: Boolean = false
  def bindingType: Int = RawType.HalfFloat

  def apply(i: Int) :Float = fromHalfFloat(buffer.get(offset + i*stride))
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, toHalfFloat(v))
}


// Type: RawFloat
private[buffer] sealed abstract class SeqFloat1RawFloat(
  buff: FloatBuffer
) extends BaseFloat1[RawFloat](buff) {
  final def asReadOnlyBuffer() = buffer.asReadOnlyBuffer()
  final def asBuffer() = buffer.duplicate()
  
  final def mkDataArray(size: Int) =
    new ArrayFloat1RawFloat(new Array[Float](size))
  final def mkDataArray(array: Array[Float]) =
    new ArrayFloat1RawFloat(array)
  final def mkDataBuffer(size: Int) =
    new BufferFloat1RawFloat(allocateByteBuffer(size*4))
  final def mkDataBuffer(byteBuffer: ByteBuffer) =
    new BufferFloat1RawFloat(byteBuffer)
  final def mkDataView(byteBuffer: ByteBuffer, offset: Int, stride: Int) =
    new ViewFloat1RawFloat(byteBuffer, offset, stride)
}

private[buffer] final class ArrayFloat1RawFloat(
  override val array: Array[Float]
) extends SeqFloat1RawFloat(
  FloatBuffer.wrap(array)
) with DataArray[Float1, RawFloat] {
  def this() = this(new Array[Float](0))
  def backingSeq = this

  def normalized: Boolean = false
  def bindingType: Int = RawType.RawFloat

  def apply(i: Int) :Float = array(i)
  def update(i: Int, v: Float) = array(i) = v
}

private[buffer] final class BufferFloat1RawFloat(
  private[buffer] override val sharedBuffer: ByteBuffer
) extends SeqFloat1RawFloat(
  sharedBuffer.asFloatBuffer()
) with DataBuffer[Float1, RawFloat] {
  def this() = this(allocateByteBuffer(0))
  def backingSeq = this

  def normalized: Boolean = false
  def bindingType: Int = RawType.RawFloat

  def apply(i: Int) :Float = buffer.get(i)
  def update(i: Int, v: Float) = buffer.put(i, v)
}

private[buffer] final class ViewFloat1RawFloat(
  private[buffer] override val sharedBuffer: ByteBuffer,
  val offset: Int,
  override val stride: Int
) extends SeqFloat1RawFloat(
  sharedBuffer.asFloatBuffer()
) with DataView[Float1, RawFloat] {
  def this() = this(allocateByteBuffer(0), 0, 1)
  val backingSeq = new BufferFloat1RawFloat(sharedBuffer)

  def normalized: Boolean = false
  def bindingType: Int = RawType.RawFloat

  def apply(i: Int) :Float = buffer.get(offset + i*stride)
  def update(i: Int, v: Float) = buffer.put(offset + i*stride, v)
}
