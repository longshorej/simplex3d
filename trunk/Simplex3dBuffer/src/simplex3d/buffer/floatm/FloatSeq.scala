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

package simplex3d.buffer
package floatm

import java.nio._
import scala.reflect._
import simplex3d.buffer.MetaManifest
import simplex3d.buffer.Util._
import simplex3d.buffer.conversion.Float._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] sealed abstract class BaseRFloat[+R <: DefinedFloat](
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseSeq[RFloat, Float, Float, R](shared, primitive, ro, off, str) {
  final def elemManifest = MetaManifest.RFloat
  final def readManifest = Manifest.Float
  final def components: Int = 1
  
  override def mkSerializableInstance() = new SerializableFloatData(components, rawType)
}


// Type: SByte
private[buffer] sealed abstract class SeqRFloatSByte(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[SByte](shared, primitive, ro, off, str) {
  final def rawType = RawType.SByte
  final def normalized = true

  final def mkDataArray(array: Array[Byte]) =
    new ArrayRFloatSByte(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatSByte(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatSByte(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatSByte(
  rarray: Array[Byte], warray: Array[Byte]
) extends SeqRFloatSByte(rarray, null, warray == null, 0, 1) with DataArray[RFloat, SByte] {
  def this() = this(emptyByte, emptyByte)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatSByte(rarray, null)

  def apply(i: Int) :Float = fromSByte(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toSByte(v) }
}

private[buffer] final class BufferRFloatSByte(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatSByte(shared, null, ro, 0, 1) with DataBuffer[RFloat, SByte] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatSByte(shared, true)

  def apply(i: Int) :Float = fromSByte(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toSByte(v)) }
}

private[buffer] final class ViewRFloatSByte(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatSByte(
  shared, new BufferRFloatSByte(shared, ro), ro, off, str
) with DataView[RFloat, SByte] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatSByte(shared, true, offset, stride)

  def apply(i: Int) :Float = fromSByte(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toSByte(v)) }
}


// Type: UByte
private[buffer] sealed abstract class SeqRFloatUByte(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[UByte](shared, primitive, ro, off, str) {
  final def rawType = RawType.UByte
  final def normalized = true

  final def mkDataArray(array: Array[Byte]) =
    new ArrayRFloatUByte(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatUByte(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatUByte(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatUByte(
  rarray: Array[Byte], warray: Array[Byte]
) extends SeqRFloatUByte(rarray, null, warray == null, 0, 1) with DataArray[RFloat, UByte] {
  def this() = this(emptyByte, emptyByte)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatUByte(rarray, null)

  def apply(i: Int) :Float = fromUByte(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toUByte(v) }
}

private[buffer] final class BufferRFloatUByte(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatUByte(shared, null, ro, 0, 1) with DataBuffer[RFloat, UByte] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatUByte(shared, true)

  def apply(i: Int) :Float = fromUByte(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toUByte(v)) }
}

private[buffer] final class ViewRFloatUByte(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatUByte(
  shared, new BufferRFloatUByte(shared, ro), ro, off, str
) with DataView[RFloat, UByte] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatUByte(shared, true, offset, stride)

  def apply(i: Int) :Float = fromUByte(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toUByte(v)) }
}


// Type: SShort
private[buffer] sealed abstract class SeqRFloatSShort(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[SShort](shared, primitive, ro, off, str) {
  final def rawType = RawType.SShort
  final def normalized = true

  final def mkDataArray(array: Array[Short]) =
    new ArrayRFloatSShort(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatSShort(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatSShort(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatSShort(
  rarray: Array[Short], warray: Array[Short]
) extends SeqRFloatSShort(rarray, null, warray == null, 0, 1) with DataArray[RFloat, SShort] {
  def this() = this(emptyShort, emptyShort)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatSShort(rarray, null)

  def apply(i: Int) :Float = fromSShort(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toSShort(v) }
}

private[buffer] final class BufferRFloatSShort(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatSShort(shared, null, ro, 0, 1) with DataBuffer[RFloat, SShort] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatSShort(shared, true)

  def apply(i: Int) :Float = fromSShort(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toSShort(v)) }
}

private[buffer] final class ViewRFloatSShort(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatSShort(
  shared, new BufferRFloatSShort(shared, ro), ro, off, str
) with DataView[RFloat, SShort] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatSShort(shared, true, offset, stride)

  def apply(i: Int) :Float = fromSShort(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toSShort(v)) }
}


// Type: UShort
private[buffer] sealed abstract class SeqRFloatUShort(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[UShort](shared, primitive, ro, off, str) {
  final def rawType = RawType.UShort
  final def normalized = true

  final def mkDataArray(array: Array[Char]) =
    new ArrayRFloatUShort(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatUShort(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatUShort(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatUShort(
  rarray: Array[Char], warray: Array[Char]
) extends SeqRFloatUShort(rarray, null, warray == null, 0, 1) with DataArray[RFloat, UShort] {
  def this() = this(emptyChar, emptyChar)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatUShort(rarray, null)

  def apply(i: Int) :Float = fromUShort(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toUShort(v) }
}

private[buffer] final class BufferRFloatUShort(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatUShort(shared, null, ro, 0, 1) with DataBuffer[RFloat, UShort] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatUShort(shared, true)

  def apply(i: Int) :Float = fromUShort(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toUShort(v)) }
}

private[buffer] final class ViewRFloatUShort(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatUShort(
  shared, new BufferRFloatUShort(shared, ro), ro, off, str
) with DataView[RFloat, UShort] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatUShort(shared, true, offset, stride)

  def apply(i: Int) :Float = fromUShort(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toUShort(v)) }
}


// Type: SInt
private[buffer] sealed abstract class SeqRFloatSInt(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[SInt](shared, primitive, ro, off, str) {
  final def rawType = RawType.SInt
  final def normalized = true

  final def mkDataArray(array: Array[Int]) =
    new ArrayRFloatSInt(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatSInt(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatSInt(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatSInt(
  rarray: Array[Int], warray: Array[Int]
) extends SeqRFloatSInt(rarray, null, warray == null, 0, 1) with DataArray[RFloat, SInt] {
  def this() = this(emptyInt, emptyInt)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatSInt(rarray, null)

  def apply(i: Int) :Float = fromSInt(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toSInt(v) }
}

private[buffer] final class BufferRFloatSInt(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatSInt(shared, null, ro, 0, 1) with DataBuffer[RFloat, SInt] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatSInt(shared, true)

  def apply(i: Int) :Float = fromSInt(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toSInt(v)) }
}

private[buffer] final class ViewRFloatSInt(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatSInt(
  shared, new BufferRFloatSInt(shared, ro), ro, off, str
) with DataView[RFloat, SInt] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatSInt(shared, true, offset, stride)

  def apply(i: Int) :Float = fromSInt(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toSInt(v)) }
}


// Type: UInt
private[buffer] sealed abstract class SeqRFloatUInt(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[UInt](shared, primitive, ro, off, str) {
  final def rawType = RawType.UInt
  final def normalized = true

  final def mkDataArray(array: Array[Int]) =
    new ArrayRFloatUInt(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatUInt(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatUInt(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatUInt(
  rarray: Array[Int], warray: Array[Int]
) extends SeqRFloatUInt(rarray, null, warray == null, 0, 1) with DataArray[RFloat, UInt] {
  def this() = this(emptyInt, emptyInt)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatUInt(rarray, null)

  def apply(i: Int) :Float = fromUInt(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toUInt(v) }
}

private[buffer] final class BufferRFloatUInt(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatUInt(shared, null, ro, 0, 1) with DataBuffer[RFloat, UInt] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatUInt(shared, true)

  def apply(i: Int) :Float = fromUInt(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toUInt(v)) }
}

private[buffer] final class ViewRFloatUInt(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatUInt(
  shared, new BufferRFloatUInt(shared, ro), ro, off, str
) with DataView[RFloat, UInt] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatUInt(shared, true, offset, stride)

  def apply(i: Int) :Float = fromUInt(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toUInt(v)) }
}


// Type: HFloat
private[buffer] sealed abstract class SeqRFloatHFloat(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[HFloat](shared, primitive, ro, off, str) {
  final def rawType: Int = RawType.HFloat
  final def normalized = false

  final def mkDataArray(array: Array[Short]) =
    new ArrayRFloatHFloat(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatHFloat(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatHFloat(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatHFloat(
  rarray: Array[Short], warray: Array[Short]
) extends SeqRFloatHFloat(rarray, null, warray == null, 0, 1) with DataArray[RFloat, HFloat] {
  def this() = this(emptyShort, emptyShort)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatHFloat(rarray, null)

  def apply(i: Int) :Float = fromHFloat(rarray(i))
  def update(i: Int, v: Float) { warray(i) = toHFloat(v) }
}

private[buffer] final class BufferRFloatHFloat(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatHFloat(shared, null, ro, 0, 1) with DataBuffer[RFloat, HFloat] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatHFloat(shared, true)

  def apply(i: Int) :Float = fromHFloat(buff.get(i))
  def update(i: Int, v: Float) { buff.put(i, toHFloat(v)) }
}

private[buffer] final class ViewRFloatHFloat(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatHFloat(
  shared, new BufferRFloatHFloat(shared, ro), ro, off, str
) with DataView[RFloat, HFloat] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatHFloat(shared, true, offset, stride)

  def apply(i: Int) :Float = fromHFloat(buff.get(offset + i*stride))
  def update(i: Int, v: Float) { buff.put(offset + i*stride, toHFloat(v)) }
}


// Type: RFloat
private[buffer] sealed abstract class SeqRFloatRFloat(
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
) extends BaseRFloat[RFloat](shared, primitive, ro, off, str) {
  final def rawType: Int = RawType.RFloat
  final def normalized = false

  final def mkDataArray(array: Array[Float]) =
    new ArrayRFloatRFloat(array, array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRFloatRFloat(byteBuffer, byteBuffer.isReadOnly)
  }
  protected final def mkReadDataViewInstance(byteBuffer: ByteBuffer, off: Int, str: Int) = {
    new ViewRFloatRFloat(byteBuffer, byteBuffer.isReadOnly, off, str)
  }
}

private[buffer] final class ArrayRFloatRFloat(
  rarray: Array[Float], warray: Array[Float]
) extends SeqRFloatRFloat(rarray, null, warray == null, 0, 1) with DataArray[RFloat, RFloat] {
  def this() = this(emptyFloat, emptyFloat)
  protected[buffer] def mkReadOnlyInstance() = new ArrayRFloatRFloat(rarray, null)

  def apply(i: Int) :Float = rarray(i)
  def update(i: Int, v: Float) { warray(i) = v }
}

private[buffer] final class BufferRFloatRFloat(
  shared: ByteBuffer, ro: Boolean
) extends SeqRFloatRFloat(shared, null, ro, 0, 1) with DataBuffer[RFloat, RFloat] {
  protected[buffer] def mkReadOnlyInstance() = new BufferRFloatRFloat(shared, true)

  def apply(i: Int) :Float = buff.get(i)
  def update(i: Int, v: Float) { buff.put(i, v) }
}

private[buffer] final class ViewRFloatRFloat(
  shared: ByteBuffer, ro: Boolean, off: Int, str: Int
) extends SeqRFloatRFloat(
  shared, new BufferRFloatRFloat(shared, ro), ro, off, str
) with DataView[RFloat, RFloat] {
  protected[buffer] def mkReadOnlyInstance() = new ViewRFloatRFloat(shared, true, offset, stride)

  def apply(i: Int) :Float = buff.get(offset + i*stride)
  def update(i: Int, v: Float) { buff.put(offset + i*stride, v) }
}
