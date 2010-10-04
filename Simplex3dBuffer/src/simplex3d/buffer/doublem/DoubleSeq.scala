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
import scala.reflect.Manifest
import simplex3d.math.doublem.DoubleMath._
import simplex3d.buffer.{allocateDirectBuffer => alloc, _}
import simplex3d.buffer.Util._
import simplex3d.buffer.HalfFloatUtil.{
  doubleToHalfFloat => toHalfFloat, doubleFromHalfFloat => fromHalfFloat
}


/**
 * @author Aleksey Nikiforov (lex)
 */
private[doublem] object Shared {

  final val fromSByte = 0.00787401574803149606
  final val fromUByte = 0.00392156862745098039
  final val fromSShort = 3.05185094759971922971e-5
  final val fromUShort = 1.52590218966964217594e-5
  final val fromSInt = 4.65661287524579692411e-10
  final val fromUInt = 2.32830643708079737543e-10

  final val toSByte = 127d
  final val toUByte = 255d
  final val toSShort = 32767d
  final val toUShort = 65535d
  final val toSInt = 2147483647d
  final val toUInt = 4294967295d

  final def iround(x: Double) :Int = {
    if (x >= 0) (x + 0.5).toInt
    else (x - 0.5).toInt
  }

  final def lround(x: Double) :Int = {
    if (x >= 0) ((x + 0.5).toLong).toInt
    else ((x - 0.5).toLong).toInt
  }
}
import Shared._

private[buffer] sealed abstract class BaseDouble1[+R <: ReadableDouble](
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseSeq[Double1, Double, R](shared, backing, ro, off, str, sz) {
  final def elementManifest = componentManifest
  final def componentManifest = Manifest.Double
  final def components: Int = 1
}


// Type: SByte
private[buffer] sealed abstract class SeqDouble1SByte(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[SByte](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.SByte
  final def normalized = true

  final def mkDataArray(array: Array[Byte]) =
    new ArrayDouble1SByte(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1SByte(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1SByte(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1SByte(
  rarray: Array[Byte], warray: Array[Byte]
) extends SeqDouble1SByte(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, SByte] {
  def this() = this(emptyByte, emptyByte)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1SByte(rarray, null)

  def apply(i: Int) :Double = {
    val v = rarray(i)
    if (v < -127) -1 else v*fromSByte
  }
  def update(i: Int, v: Double) :Unit =
    warray(i) = (iround(clamp(v, -1, 1)*toSByte)).toByte
}

private[buffer] final class BufferDouble1SByte(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1SByte(shared, null, ro, 0, 1, null) with DataBuffer[Double1, SByte] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1SByte(shared, true)

  def apply(i: Int) :Double = {
    val v = buffer.get(i)
    if (v < -127) -1 else v*fromSByte
  }
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    (iround(clamp(v, -1, 1)*toSByte)).toByte
  )
}

private[buffer] final class ViewDouble1SByte(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1SByte(
  shared, new BufferDouble1SByte(shared, ro), ro, off, str, sz
) with DataView[Double1, SByte] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1SByte(shared, true, offset, stride, size)

  def apply(i: Int) :Double = {
    val v = buffer.get(offset + i*stride)
    if (v < -127) -1 else v*fromSByte
  }
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    (iround(clamp(v, -1, 1)*toSByte)).toByte
  )
}


// Type: UByte
private[buffer] sealed abstract class SeqDouble1UByte(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[UByte](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.UByte
  final def normalized = true

  final def mkDataArray(array: Array[Byte]) =
    new ArrayDouble1UByte(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1UByte(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1UByte(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1UByte(
  rarray: Array[Byte], warray: Array[Byte]
) extends SeqDouble1UByte(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, UByte] {
  def this() = this(emptyByte, emptyByte)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1UByte(rarray, null)

  def apply(i: Int) :Double = (rarray(i) & 0xFF)*fromUByte
  def update(i: Int, v: Double) :Unit =
    warray(i) = (iround(clamp(v, 0, 1)*toUByte)).toByte
}

private[buffer] final class BufferDouble1UByte(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1UByte(shared, null, ro, 0, 1, null) with DataBuffer[Double1, UByte] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1UByte(shared, true)

  def apply(i: Int) :Double = (buffer.get(i) & 0xFF)*fromUByte
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    (iround(clamp(v, 0, 1)*toUByte)).toByte
  )
}

private[buffer] final class ViewDouble1UByte(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1UByte(
  shared, new BufferDouble1UByte(shared, ro), ro, off, str, sz
) with DataView[Double1, UByte] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1UByte(shared, true, offset, stride, size)

  def apply(i: Int) :Double = (buffer.get(offset + i*stride) & 0xFF)*fromUByte
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    (iround(clamp(v, 0, 1)*toUByte)).toByte
  )
}


// Type: SShort
private[buffer] sealed abstract class SeqDouble1SShort(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[SShort](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.SShort
  final def normalized = true

  final def mkDataArray(array: Array[Short]) =
    new ArrayDouble1SShort(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1SShort(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1SShort(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1SShort(
  rarray: Array[Short], warray: Array[Short]
) extends SeqDouble1SShort(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, SShort] {
  def this() = this(emptyShort, emptyShort)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1SShort(rarray, null)

  def apply(i: Int) :Double = {
    val v = rarray(i)
    if (v < -32767) -1 else v*fromSShort
  }
  def update(i: Int, v: Double) :Unit =
    warray(i) = (iround(clamp(v, -1, 1)*toSShort)).toShort
}

private[buffer] final class BufferDouble1SShort(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1SShort(shared, null, ro, 0, 1, null) with DataBuffer[Double1, SShort] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1SShort(shared, true)

  def apply(i: Int) :Double = {
    val v = buffer.get(i)
    if (v < -32767) -1 else v*fromSShort
  }
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    (iround(clamp(v, -1, 1)*toSShort)).toShort
  )
}

private[buffer] final class ViewDouble1SShort(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1SShort(
  shared, new BufferDouble1SShort(shared, ro), ro, off, str, sz
) with DataView[Double1, SShort] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1SShort(shared, true, offset, stride, size)

  def apply(i: Int) :Double = {
    val v = buffer.get(offset + i*stride)
    if (v < -32767) -1 else v*fromSShort
  }
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    (iround(clamp(v, -1, 1)*toSShort)).toShort
  )
}


// Type: UShort
private[buffer] sealed abstract class SeqDouble1UShort(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[UShort](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.UShort
  final def normalized = true

  final def mkDataArray(array: Array[Char]) =
    new ArrayDouble1UShort(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1UShort(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1UShort(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1UShort(
  rarray: Array[Char], warray: Array[Char]
) extends SeqDouble1UShort(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, UShort] {
  def this() = this(emptyChar, emptyChar)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1UShort(rarray, null)

  def apply(i: Int) :Double = rarray(i)*fromUShort
  def update(i: Int, v: Double) =
    warray(i) = iround(clamp(v, 0, 1)*toUShort).toChar
}

private[buffer] final class BufferDouble1UShort(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1UShort(shared, null, ro, 0, 1, null) with DataBuffer[Double1, UShort] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1UShort(shared, true)

  def apply(i: Int) :Double = buffer.get(i)*fromUShort
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    iround(clamp(v, 0, 1)*toUShort).toChar
  )
}

private[buffer] final class ViewDouble1UShort(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1UShort(
  shared, new BufferDouble1UShort(shared, ro), ro, off, str, sz
) with DataView[Double1, UShort] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1UShort(shared, true, offset, stride, size)

  def apply(i: Int) :Double = buffer.get(offset + i*stride)*fromUShort
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    iround(clamp(v, 0, 1)*toUShort).toChar
  )
}


// Type: SInt
private[buffer] sealed abstract class SeqDouble1SInt(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[SInt](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.SInt
  final def normalized = true

  final def mkDataArray(array: Array[Int]) =
    new ArrayDouble1SInt(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1SInt(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1SInt(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1SInt(
  rarray: Array[Int], warray: Array[Int]
) extends SeqDouble1SInt(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, SInt] {
  def this() = this(emptyInt, emptyInt)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1SInt(rarray, null)

  def apply(i: Int) :Double = rarray(i)*fromSInt
  def update(i: Int, v: Double) :Unit =
    warray(i) = iround(clamp(v, -1, 1)*toSInt)
}

private[buffer] final class BufferDouble1SInt(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1SInt(shared, null, ro, 0, 1, null) with DataBuffer[Double1, SInt] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1SInt(shared, true)

  def apply(i: Int) :Double = buffer.get(i)*fromSInt
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    iround(clamp(v, -1, 1)*toSInt)
  )
}

private[buffer] final class ViewDouble1SInt(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1SInt(
  shared, new BufferDouble1SInt(shared, ro), ro, off, str, sz
) with DataView[Double1, SInt] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1SInt(shared, true, offset, stride, size)

  def apply(i: Int) :Double = buffer.get(offset + i*stride)*fromSInt
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    iround(clamp(v, -1, 1)*toSInt)
  )
}


// Type: UInt
private[buffer] sealed abstract class SeqDouble1UInt(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[UInt](shared, backing, ro, off, str, sz) {
  final def rawType = RawData.UInt
  final def normalized = true

  final def mkDataArray(array: Array[Int]) =
    new ArrayDouble1UInt(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1UInt(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1UInt(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1UInt(
  rarray: Array[Int], warray: Array[Int]
) extends SeqDouble1UInt(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, UInt] {
  def this() = this(emptyInt, emptyInt)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1UInt(rarray, null)

  def apply(i: Int) :Double = (rarray(i).toLong & 0xFFFFFFFFL)*fromUInt
  def update(i: Int, v: Double) :Unit = warray(i) = lround(clamp(v, 0, 1)*toUInt)
}

private[buffer] final class BufferDouble1UInt(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1UInt(shared, null, ro, 0, 1, null) with DataBuffer[Double1, UInt] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1UInt(shared, true)

  def apply(i: Int) :Double = (buffer.get(i).toLong & 0xFFFFFFFFL)*fromUInt
  def update(i: Int, v: Double) :Unit = buffer.put(
    i,
    lround(clamp(v, 0, 1)*toUInt)
  )
}

private[buffer] final class ViewDouble1UInt(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1UInt(
  shared, new BufferDouble1UInt(shared, ro), ro, off, str, sz
) with DataView[Double1, UInt] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1UInt(shared, true, offset, stride, size)

  def apply(i: Int) :Double = (buffer.get(offset + i*stride).toLong & 0xFFFFFFFFL)*fromUInt
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    lround(clamp(v, 0, 1)*toUInt)
  )
}


// Type: HalfFloat
private[buffer] sealed abstract class SeqDouble1HalfFloat(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[HalfFloat](shared, backing, ro, off, str, sz) {
  final def rawType: Int = RawData.HalfFloat
  final def normalized = false

  final def mkDataArray(array: Array[Short]) =
    new ArrayDouble1HalfFloat(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1HalfFloat(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1HalfFloat(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1HalfFloat(
  rarray: Array[Short], warray: Array[Short]
) extends SeqDouble1HalfFloat(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, HalfFloat] {
  def this() = this(emptyShort, emptyShort)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1HalfFloat(rarray, null)

  def apply(i: Int) :Double = fromHalfFloat(rarray(i))
  def update(i: Int, v: Double) :Unit = warray(i) = toHalfFloat(v)
}

private[buffer] final class BufferDouble1HalfFloat(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1HalfFloat(shared, null, ro, 0, 1, null) with DataBuffer[Double1, HalfFloat] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1HalfFloat(shared, true)

  def apply(i: Int) :Double = fromHalfFloat(buffer.get(i))
  def update(i: Int, v: Double) :Unit = buffer.put(i, toHalfFloat(v))
}

private[buffer] final class ViewDouble1HalfFloat(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1HalfFloat(
  shared, new BufferDouble1HalfFloat(shared, ro), ro, off, str, sz
) with DataView[Double1, HalfFloat] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1HalfFloat(shared, true, offset, stride, size)

  def apply(i: Int) :Double = fromHalfFloat(buffer.get(offset + i*stride))
  def update(i: Int, v: Double) :Unit = buffer.put(
    offset + i*stride,
    toHalfFloat(v)
  )
}


// Type: RawFloat
private[buffer] sealed abstract class SeqDouble1RawFloat(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[RawFloat](shared, backing, ro, off, str, sz) {
  final def rawType: Int = RawData.RawFloat
  final def normalized = false

  final def mkDataArray(array: Array[Float]) =
    new ArrayDouble1RawFloat(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1RawFloat(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1RawFloat(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1RawFloat(
  rarray: Array[Float], warray: Array[Float]
) extends SeqDouble1RawFloat(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, RawFloat] {
  def this() = this(emptyFloat, emptyFloat)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1RawFloat(rarray, null)

  def apply(i: Int) :Double = rarray(i)
  def update(i: Int, v: Double) :Unit = warray(i) = v.toFloat
}

private[buffer] final class BufferDouble1RawFloat(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1RawFloat(shared, null, ro, 0, 1, null) with DataBuffer[Double1, RawFloat] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1RawFloat(shared, true)

  def apply(i: Int) :Double = buffer.get(i)
  def update(i: Int, v: Double) :Unit = buffer.put(i, v.toFloat)
}

private[buffer] final class ViewDouble1RawFloat(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1RawFloat(
  shared, new BufferDouble1RawFloat(shared, ro), ro, off, str, sz
) with DataView[Double1, RawFloat] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1RawFloat(shared, true, offset, stride, size)

  def apply(i: Int) :Double = buffer.get(offset + i*stride)
  def update(i: Int, v: Double) :Unit = buffer.put(offset + i*stride, v.toFloat)
}


// Type: RawDouble
private[buffer] sealed abstract class SeqDouble1RawDouble(
  shared: AnyRef, backing: AnyRef, ro: Boolean,
  off: Int, str: Int, sz: java.lang.Integer
) extends BaseDouble1[RawDouble](shared, backing, ro, off, str, sz) {
  final def rawType: Int = RawData.RawDouble
  final def normalized = false

  final def mkDataArray(array: Array[Double]) =
    new ArrayDouble1RawDouble(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferDouble1RawDouble(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataView(byteBuffer: ByteBuffer, off: Int, str: Int, sz: java.lang.Integer) = {
    new ViewDouble1RawDouble(byteBuffer, byteBuffer.isReadOnly, off, str, sz)
  }
}

private[buffer] final class ArrayDouble1RawDouble(
  rarray: Array[Double], warray: Array[Double]
) extends SeqDouble1RawDouble(rarray, null, warray == null, 0, 1, null) with DataArray[Double1, RawDouble] {
  def this() = this(emptyDouble, emptyDouble)
  protected[buffer] def mkReadOnlyInstance() = new ArrayDouble1RawDouble(rarray, null)

  def apply(i: Int) :Double = rarray(i)
  def update(i: Int, v: Double) :Unit = warray(i) = v
}

private[buffer] final class BufferDouble1RawDouble(
  shared: ByteBuffer, ro: Boolean
) extends SeqDouble1RawDouble(shared, null, ro, 0, 1, null) with DataBuffer[Double1, RawDouble] {
  protected[buffer] def mkReadOnlyInstance() = new BufferDouble1RawDouble(shared, true)

  def apply(i: Int) :Double = buffer.get(i)
  def update(i: Int, v: Double) :Unit = buffer.put(i, v)
}

private[buffer] final class ViewDouble1RawDouble(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int, sz: java.lang.Integer
) extends SeqDouble1RawDouble(
  shared, new BufferDouble1RawDouble(shared, ro), ro, off, str, sz
) with DataView[Double1, RawDouble] {
  protected[buffer] def mkReadOnlyInstance() = new ViewDouble1RawDouble(shared, true, offset, stride, size)

  def apply(i: Int) :Double = buffer.get(offset + i*stride)
  def update(i: Int, v: Double) :Unit = buffer.put(offset + i*stride, v)
}
