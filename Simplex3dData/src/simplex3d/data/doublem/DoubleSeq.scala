/*
 * Simplex3d, DoubleData module
 * Copyright (C) 2010, Simplex3d Team
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
package doublem

import java.nio._
import scala.reflect._
import scala.annotation.unchecked._
import simplex3d.data.MetaManifest
import simplex3d.data.Util._
import simplex3d.data.conversion.Double._


/**
 * @author Aleksey Nikiforov (lex)
 */
private[buffer] abstract class BaseRDouble[+R <: DefinedDouble](
  shared: AnyRef, primitive: AnyRef, ro: Boolean,
  off: Int, str: Int
)
extends BaseSeq[RDouble, Double, Double, R](shared, primitive, ro, off, str)
with CompositionFactory[RDouble, DefinedDouble]
{
  final def elemManifest = MetaManifest.RDouble
  final def readManifest = Manifest.Double
  final def components: Int = 1

  final def mkReadDataArray[P <: DefinedDouble](primitive: ReadDataArray[RDouble, P])
  :ReadDataArray[RDouble, P] = primitive
  final def mkReadDataBuffer[P <: DefinedDouble](primitive: ReadDataBuffer[RDouble, P])
  :ReadDataBuffer[RDouble, P] = primitive
  protected final def mkReadDataViewInstance[P <: DefinedDouble](
    primitive: ReadDataBuffer[RDouble, P], off: Int, str: Int
  ) :ReadDataView[RDouble, P] = {
    (primitive.rawType match {
      case RawType.RFloat =>
        new impl.ViewRDoubleRFloat(primitive.asInstanceOf[ReadDataBuffer[RDouble, RFloat]], off, str)
      case _ =>
        new ViewRDouble(primitive, off, str)
    }).asInstanceOf[ReadDataView[RDouble, P]]
  }

  protected final def mkReadDataViewInstance(
    byteBuffer: ByteBuffer, off: Int, str: Int
  ) :ReadDataView[RDouble, R] = {
    (rawType match {
      case RawType.RFloat =>
        val primitive = backing.mkReadDataBuffer(byteBuffer).asInstanceOf[ReadDataBuffer[RDouble, RFloat]]
        new impl.ViewRDoubleRFloat(primitive, off, str)
      case _ =>
        new ViewRDouble(backing.mkReadDataBuffer(byteBuffer), off, str)
    }).asInstanceOf[ReadDataView[RDouble, R]]
  }

  final override def mkSerializableInstance() = new PrimitiveRDouble(rawType)
}

private[buffer] final class ViewRDouble[+R <: DefinedDouble](
  primitive: ReadDataBuffer[RDouble, R], off: Int, str: Int
) extends BaseRDouble[R](primitive, primitive, primitive.readOnly, off, str) with DataView[RDouble, R] {
  final def normalized = backing.normalized
  final def rawType = backing.rawType
  def mkReadOnlyInstance() = new ViewRDouble(backing.asReadOnly(), offset, stride)

  def apply(i: Int) :Double = backing(offset + i*stride)
  def update(i: Int, v: Double) :Unit = backing(offset + i*stride) = v

  final def mkDataArray(array: R#Array @uncheckedVariance) :DataArray[RDouble, R] =
    backing.mkDataArray(array)
  final def mkReadDataBuffer(byteBuffer: ByteBuffer) :ReadDataBuffer[RDouble, R] =
    backing.mkReadDataBuffer(byteBuffer)
}


// Type: SByte
private[buffer] final class ArrayRDoubleSByte(
  rarray: Array[Byte], warray: Array[Byte]
)
extends BaseRDouble[SByte](rarray, null, warray == null, 0, 1) with DataArray[RDouble, SByte]
with PrimitiveFactory[RDouble, SByte]
{
  def this() = this(emptyByte, emptyByte)
  def mkReadOnlyInstance() = new ArrayRDoubleSByte(rarray, null)
  def rawType = RawType.SByte
  def normalized = true

  def mkDataArray(array: Array[Byte]) =
    new ArrayRDoubleSByte(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSByte(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSByte(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toSByte(v) }
}

private[buffer] final class BufferRDoubleSByte(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[SByte](shared, null, ro, 0, 1) with DataBuffer[RDouble, SByte] {
  def mkReadOnlyInstance() = new BufferRDoubleSByte(shared, true)
  def rawType = RawType.SByte
  def normalized = true

  def mkDataArray(array: Array[Byte]) =
    new ArrayRDoubleSByte(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSByte(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSByte(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toSByte(v)) }
}


// Type: UByte
private[buffer] final class ArrayRDoubleUByte(
  rarray: Array[Byte], warray: Array[Byte]
)
extends BaseRDouble[UByte](rarray, null, warray == null, 0, 1) with DataArray[RDouble, UByte]
with PrimitiveFactory[RDouble, UByte]
{
  def this() = this(emptyByte, emptyByte)
  def mkReadOnlyInstance() = new ArrayRDoubleUByte(rarray, null)
  def rawType = RawType.UByte
  def normalized = true

  def mkDataArray(array: Array[Byte]) =
    new ArrayRDoubleUByte(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUByte(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUByte(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toUByte(v) }
}

private[buffer] final class BufferRDoubleUByte(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[UByte](shared, null, ro, 0, 1) with DataBuffer[RDouble, UByte] {
  def mkReadOnlyInstance() = new BufferRDoubleUByte(shared, true)
  def rawType = RawType.UByte
  def normalized = true

  def mkDataArray(array: Array[Byte]) =
    new ArrayRDoubleUByte(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUByte(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUByte(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toUByte(v)) }
}


// Type: SShort
private[buffer] final class ArrayRDoubleSShort(
  rarray: Array[Short], warray: Array[Short]
)
extends BaseRDouble[SShort](rarray, null, warray == null, 0, 1) with DataArray[RDouble, SShort]
with PrimitiveFactory[RDouble, SShort]
{
  def this() = this(emptyShort, emptyShort)
  def mkReadOnlyInstance() = new ArrayRDoubleSShort(rarray, null)
  def rawType = RawType.SShort
  def normalized = true

  def mkDataArray(array: Array[Short]) =
    new ArrayRDoubleSShort(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSShort(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSShort(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toSShort(v) }
}

private[buffer] final class BufferRDoubleSShort(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[SShort](shared, null, ro, 0, 1) with DataBuffer[RDouble, SShort] {
  def mkReadOnlyInstance() = new BufferRDoubleSShort(shared, true)
  def rawType = RawType.SShort
  def normalized = true

  def mkDataArray(array: Array[Short]) =
    new ArrayRDoubleSShort(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSShort(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSShort(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toSShort(v)) }
}


// Type: UShort
private[buffer] final class ArrayRDoubleUShort(
  rarray: Array[Char], warray: Array[Char]
)
extends BaseRDouble[UShort](rarray, null, warray == null, 0, 1) with DataArray[RDouble, UShort]
with PrimitiveFactory[RDouble, UShort]
{
  def this() = this(emptyChar, emptyChar)
  def mkReadOnlyInstance() = new ArrayRDoubleUShort(rarray, null)
  def rawType = RawType.UShort
  def normalized = true

  def mkDataArray(array: Array[Char]) =
    new ArrayRDoubleUShort(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUShort(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUShort(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toUShort(v) }
}

private[buffer] final class BufferRDoubleUShort(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[UShort](shared, null, ro, 0, 1) with DataBuffer[RDouble, UShort] {
  def mkReadOnlyInstance() = new BufferRDoubleUShort(shared, true)
  def rawType = RawType.UShort
  def normalized = true

  def mkDataArray(array: Array[Char]) =
    new ArrayRDoubleUShort(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUShort(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUShort(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toUShort(v)) }
}


// Type: SInt
private[buffer] final class ArrayRDoubleSInt(
  rarray: Array[Int], warray: Array[Int]
)
extends BaseRDouble[SInt](rarray, null, warray == null, 0, 1) with DataArray[RDouble, SInt]
with PrimitiveFactory[RDouble, SInt]
{
  def this() = this(emptyInt, emptyInt)
  def mkReadOnlyInstance() = new ArrayRDoubleSInt(rarray, null)
  def rawType = RawType.SInt
  def normalized = true

  def mkDataArray(array: Array[Int]) =
    new ArrayRDoubleSInt(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSInt(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSInt(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toSInt(v) }
}

private[buffer] final class BufferRDoubleSInt(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[SInt](shared, null, ro, 0, 1) with DataBuffer[RDouble, SInt] {
  def mkReadOnlyInstance() = new BufferRDoubleSInt(shared, true)
  def rawType = RawType.SInt
  def normalized = true

  def mkDataArray(array: Array[Int]) =
    new ArrayRDoubleSInt(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleSInt(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromSInt(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toSInt(v)) }
}


// Type: UInt
private[buffer] final class ArrayRDoubleUInt(
  rarray: Array[Int], warray: Array[Int]
)
extends BaseRDouble[UInt](rarray, null, warray == null, 0, 1) with DataArray[RDouble, UInt]
with PrimitiveFactory[RDouble, UInt]
{
  def this() = this(emptyInt, emptyInt)
  def mkReadOnlyInstance() = new ArrayRDoubleUInt(rarray, null)
  def rawType = RawType.UInt
  def normalized = true

  def mkDataArray(array: Array[Int]) =
    new ArrayRDoubleUInt(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUInt(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUInt(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toUInt(v) }
}

private[buffer] final class BufferRDoubleUInt(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[UInt](shared, null, ro, 0, 1) with DataBuffer[RDouble, UInt] {
  def mkReadOnlyInstance() = new BufferRDoubleUInt(shared, true)
  def rawType = RawType.UInt
  def normalized = true

  def mkDataArray(array: Array[Int]) =
    new ArrayRDoubleUInt(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleUInt(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromUInt(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toUInt(v)) }
}


// Type: HFloat
private[buffer] final class ArrayRDoubleHFloat(
  rarray: Array[Short], warray: Array[Short]
)
extends BaseRDouble[HFloat](rarray, null, warray == null, 0, 1) with DataArray[RDouble, HFloat]
with PrimitiveFactory[RDouble, HFloat]
{
  def this() = this(emptyShort, emptyShort)
  def mkReadOnlyInstance() = new ArrayRDoubleHFloat(rarray, null)
  def rawType: Int = RawType.HFloat
  def normalized = false

  def mkDataArray(array: Array[Short]) =
    new ArrayRDoubleHFloat(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleHFloat(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromHFloat(rarray(i))
  def update(i: Int, v: Double) { warray(i) = toHFloat(v) }
}

private[buffer] final class BufferRDoubleHFloat(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[HFloat](shared, null, ro, 0, 1) with DataBuffer[RDouble, HFloat] {
  def mkReadOnlyInstance() = new BufferRDoubleHFloat(shared, true)
  def rawType: Int = RawType.HFloat
  def normalized = false

  def mkDataArray(array: Array[Short]) =
    new ArrayRDoubleHFloat(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleHFloat(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = fromHFloat(buff.get(i))
  def update(i: Int, v: Double) { buff.put(i, toHFloat(v)) }
}


// Type: RFloat
private[buffer] final class ArrayRDoubleRFloat(
  rarray: Array[Float], warray: Array[Float]
)
extends BaseRDouble[RFloat](rarray, null, warray == null, 0, 1) with DataArray[RDouble, RFloat]
with PrimitiveFactory[RDouble, RFloat]
{
  def this() = this(emptyFloat, emptyFloat)
  def mkReadOnlyInstance() = new ArrayRDoubleRFloat(rarray, null)
  def rawType: Int = RawType.RFloat
  def normalized = false

  def mkDataArray(array: Array[Float]) =
    new ArrayRDoubleRFloat(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleRFloat(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = rarray(i)
  def update(i: Int, v: Double) { warray(i) = v.toFloat }
}

private[buffer] final class BufferRDoubleRFloat(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[RFloat](shared, null, ro, 0, 1) with DataBuffer[RDouble, RFloat] {
  def mkReadOnlyInstance() = new BufferRDoubleRFloat(shared, true)
  def rawType: Int = RawType.RFloat
  def normalized = false

  def mkDataArray(array: Array[Float]) =
    new ArrayRDoubleRFloat(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleRFloat(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = buff.get(i)
  def update(i: Int, v: Double) { buff.put(i, v.toFloat) }
}


// Type: RDouble
private[buffer] final class ArrayRDoubleRDouble(
  rarray: Array[Double], warray: Array[Double]
)
extends BaseRDouble[RDouble](rarray, null, warray == null, 0, 1) with DataArray[RDouble, RDouble]
with PrimitiveFactory[RDouble, RDouble]
{
  def this() = this(emptyDouble, emptyDouble)
  def mkReadOnlyInstance() = new ArrayRDoubleRDouble(rarray, null)
  def rawType: Int = RawType.RDouble
  def normalized = false

  def mkDataArray(array: Array[Double]) =
    new ArrayRDoubleRDouble(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleRDouble(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = rarray(i)
  def update(i: Int, v: Double) { warray(i) = v }
}

private[buffer] final class BufferRDoubleRDouble(
  shared: ByteBuffer, ro: Boolean
) extends BaseRDouble[RDouble](shared, null, ro, 0, 1) with DataBuffer[RDouble, RDouble] {
  def mkReadOnlyInstance() = new BufferRDoubleRDouble(shared, true)
  def rawType: Int = RawType.RDouble
  def normalized = false

  def mkDataArray(array: Array[Double]) =
    new ArrayRDoubleRDouble(array, array)
  def mkReadDataBuffer(byteBuffer: ByteBuffer) = {
    new BufferRDoubleRDouble(byteBuffer, byteBuffer.isReadOnly)
  }

  def apply(i: Int) :Double = buff.get(i)
  def update(i: Int, v: Double) { buff.put(i, v) }
}