/*
 * Simplex3d, BufferTest package
 * Copyright (C) 2010, Simplex3d Team
 *
 * This file is part of Simplex3dBufferTest.
 *
 * Simplex3dBufferTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dBufferTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.buffer
package intm

import org.scalatest._
import simplex3d.math.intm._
import simplex3d.buffer._
import simplex3d.buffer.intm._

import Descriptors._
import FactoryTest._
import TestUtil._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Int1Test extends FunSuite {

  test("Factories") {
    testIndexArrayUsingDataSize(IndexArray(_, _))
    testIndexBufferUsingDataSize(IndexBuffer(_, _))
    
    testIndexArrayFromSize(IndexArray[UByte](_))
    testIndexArrayFromData[UByte](IndexArray[UByte](_))
    testIndexBufferFromSize(IndexBuffer[UByte](_))
    testIndexBufferFromData(IndexBuffer[UByte](_))
    testReadIndexBufferFromData(ReadIndexBuffer[UByte](_))
    testIndexArrayFromCollection(IndexArray[UByte](_))
    testIndexBufferFromCollection(IndexBuffer[UByte](_))
    
    testIndexArrayFromSize(IndexArray[UShort](_))
    testIndexArrayFromData[UShort](IndexArray[UShort](_))
    testIndexBufferFromSize(IndexBuffer[UShort](_))
    testIndexBufferFromData(IndexBuffer[UShort](_))
    testReadIndexBufferFromData(ReadIndexBuffer[UShort](_))
    testIndexArrayFromCollection(IndexArray[UShort](_))
    testIndexBufferFromCollection(IndexBuffer[UShort](_))
    
    testIndexArrayFromSize(IndexArray[UInt](_))
    testIndexArrayFromData[UInt](IndexArray[UInt](_))
    testIndexBufferFromSize(IndexBuffer[UInt](_))
    testIndexBufferFromData(IndexBuffer[UInt](_))
    testReadIndexBufferFromData(ReadIndexBuffer[UInt](_))
    testIndexArrayFromCollection(IndexArray[UInt](_))
    testIndexBufferFromCollection(IndexBuffer[UInt](_))
    

    testArrayFromSize(DataArray[Int1, SByte](_))
    testArrayFromData[Int1, SByte](DataArray[Int1, SByte](_))
    testBufferFromSize(DataBuffer[Int1, SByte](_))
    testBufferFromData(DataBuffer[Int1, SByte](_))
    testViewFromData(DataView[Int1, SByte](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, SByte](_))
    testReadViewFromData(ReadDataView[Int1, SByte](_, _, _))
    testArrayFromCollection[Int1, SByte]((a: IndexedSeq[Int]) => DataArray[Int1, SByte](a: _*))
    testArrayFromCollection[Int1, SByte]((a: IndexedSeq[Int]) => DataArray[Int1, SByte](a))
    testBufferFromCollection[Int1, SByte]((a: IndexedSeq[Int]) => DataBuffer[Int1, SByte](a: _*))
    testBufferFromCollection[Int1, SByte]((a: IndexedSeq[Int]) => DataBuffer[Int1, SByte](a))
    
    testArrayFromSize(DataArray[Int1, UByte](_))
    testArrayFromData[Int1, UByte](DataArray[Int1, UByte](_))
    testBufferFromSize(DataBuffer[Int1, UByte](_))
    testBufferFromData(DataBuffer[Int1, UByte](_))
    testViewFromData(DataView[Int1, UByte](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, UByte](_))
    testReadViewFromData(ReadDataView[Int1, UByte](_, _, _))
    testArrayFromCollection[Int1, UByte]((a: IndexedSeq[Int]) => DataArray[Int1, UByte](a: _*))
    testArrayFromCollection[Int1, UByte]((a: IndexedSeq[Int]) => DataArray[Int1, UByte](a))
    testBufferFromCollection[Int1, UByte]((a: IndexedSeq[Int]) => DataBuffer[Int1, UByte](a: _*))
    testBufferFromCollection[Int1, UByte]((a: IndexedSeq[Int]) => DataBuffer[Int1, UByte](a))
    
    testArrayFromSize(DataArray[Int1, SShort](_))
    testArrayFromData[Int1, SShort](DataArray[Int1, SShort](_))
    testBufferFromSize(DataBuffer[Int1, SShort](_))
    testBufferFromData(DataBuffer[Int1, SShort](_))
    testViewFromData(DataView[Int1, SShort](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, SShort](_))
    testReadViewFromData(ReadDataView[Int1, SShort](_, _, _))
    testArrayFromCollection[Int1, SShort]((a: IndexedSeq[Int]) => DataArray[Int1, SShort](a: _*))
    testArrayFromCollection[Int1, SShort]((a: IndexedSeq[Int]) => DataArray[Int1, SShort](a))
    testBufferFromCollection[Int1, SShort]((a: IndexedSeq[Int]) => DataBuffer[Int1, SShort](a: _*))
    testBufferFromCollection[Int1, SShort]((a: IndexedSeq[Int]) => DataBuffer[Int1, SShort](a))
    
    testArrayFromSize(DataArray[Int1, UShort](_))
    testArrayFromData[Int1, UShort](DataArray[Int1, UShort](_))
    testBufferFromSize(DataBuffer[Int1, UShort](_))
    testBufferFromData(DataBuffer[Int1, UShort](_))
    testViewFromData(DataView[Int1, UShort](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, UShort](_))
    testReadViewFromData(ReadDataView[Int1, UShort](_, _, _))
    testArrayFromCollection[Int1, UShort]((a: IndexedSeq[Int]) => DataArray[Int1, UShort](a: _*))
    testArrayFromCollection[Int1, UShort]((a: IndexedSeq[Int]) => DataArray[Int1, UShort](a))
    testBufferFromCollection[Int1, UShort]((a: IndexedSeq[Int]) => DataBuffer[Int1, UShort](a: _*))
    testBufferFromCollection[Int1, UShort]((a: IndexedSeq[Int]) => DataBuffer[Int1, UShort](a))
    
    testArrayFromSize(DataArray[Int1, SInt](_))
    testArrayFromData[Int1, SInt](DataArray[Int1, SInt](_))
    testBufferFromSize(DataBuffer[Int1, SInt](_))
    testBufferFromData(DataBuffer[Int1, SInt](_))
    testViewFromData(DataView[Int1, SInt](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, SInt](_))
    testReadViewFromData(ReadDataView[Int1, SInt](_, _, _))
    testArrayFromCollection[Int1, SInt]((a: IndexedSeq[Int]) => DataArray[Int1, SInt](a: _*))
    testArrayFromCollection[Int1, SInt]((a: IndexedSeq[Int]) => DataArray[Int1, SInt](a))
    testBufferFromCollection[Int1, SInt]((a: IndexedSeq[Int]) => DataBuffer[Int1, SInt](a: _*))
    testBufferFromCollection[Int1, SInt]((a: IndexedSeq[Int]) => DataBuffer[Int1, SInt](a))
    
    testArrayFromSize(DataArray[Int1, UInt](_))
    testArrayFromData[Int1, UInt](DataArray[Int1, UInt](_))
    testBufferFromSize(DataBuffer[Int1, UInt](_))
    testBufferFromData(DataBuffer[Int1, UInt](_))
    testViewFromData(DataView[Int1, UInt](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Int1, UInt](_))
    testReadViewFromData(ReadDataView[Int1, UInt](_, _, _))
    testArrayFromCollection[Int1, UInt]((a: IndexedSeq[Int]) => DataArray[Int1, UInt](a: _*))
    testArrayFromCollection[Int1, UInt]((a: IndexedSeq[Int]) => DataArray[Int1, UInt](a))
    testBufferFromCollection[Int1, UInt]((a: IndexedSeq[Int]) => DataBuffer[Int1, UInt](a: _*))
    testBufferFromCollection[Int1, UInt]((a: IndexedSeq[Int]) => DataBuffer[Int1, UInt](a))
  }

  private val size = 10

  test("Apply/Update") {
    testSByte(DataArray[Int1, SByte](size))
    testSByte(DataBuffer[Int1, SByte](size))
    testSByte(DataView[Int1, SByte](genBuffer(size, Descriptors.Int1SByte)._1, 0, 2))
    testSByte(DataView[Int1, SByte](genBuffer(size, Descriptors.Int1SByte)._1, 1, 2))

    testUByte(DataArray[Int1, UByte](size))
    testUByte(DataBuffer[Int1, UByte](size))
    testUByte(DataView[Int1, UByte](genBuffer(size, Descriptors.Int1UByte)._1, 0, 2))
    testUByte(DataView[Int1, UByte](genBuffer(size, Descriptors.Int1UByte)._1, 1, 2))
    
    testSShort(DataArray[Int1, SShort](size))
    testSShort(DataBuffer[Int1, SShort](size))
    testSShort(DataView[Int1, SShort](genBuffer(size, Descriptors.Int1SShort)._1, 0, 2))
    testSShort(DataView[Int1, SShort](genBuffer(size, Descriptors.Int1SShort)._1, 1, 2))

    testUShort(DataArray[Int1, UShort](size))
    testUShort(DataBuffer[Int1, UShort](size))
    testUShort(DataView[Int1, UShort](genBuffer(size, Descriptors.Int1UShort)._1, 0, 2))
    testUShort(DataView[Int1, UShort](genBuffer(size, Descriptors.Int1UShort)._1, 1, 2))

    testSInt(DataArray[Int1, SInt](size))
    testSInt(DataBuffer[Int1, SInt](size))
    testSInt(DataView[Int1, SInt](genBuffer(size, Descriptors.Int1SInt)._1, 0, 2))
    testSInt(DataView[Int1, SInt](genBuffer(size, Descriptors.Int1SInt)._1, 1, 2))

    testUInt(DataArray[Int1, UInt](size))
    testUInt(DataBuffer[Int1, UInt](size))
    testUInt(DataView[Int1, UInt](genBuffer(size, Descriptors.Int1UInt)._1, 0, 2))
    testUInt(DataView[Int1, UInt](genBuffer(size, Descriptors.Int1UInt)._1, 1, 2))
  }

  private def testSByte(seq: DataSeq[Int1, SByte]) {
    testIndex(seq)

    testApplyUpdate(seq, -129, 127, 127)
    testApplyUpdate(seq, -128, -128, -128)
    testApplyUpdate(seq, -1, -1, -1)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, 127, 127, 127)
    testApplyUpdate(seq, 128, -128, -128)
  }

  private def testUByte(seq: DataSeq[Int1, UByte]) {
    testIndex(seq)

    testApplyUpdate(seq, -1, 255, -1)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, 127, 127, 127)
    testApplyUpdate(seq, 128, 128, -128)
    testApplyUpdate(seq, 255, 255, -1)
    testApplyUpdate(seq, 256, 0, 0)
    testApplyUpdate(seq, 257, 1, 1)
  }
  
  private def testSShort(seq: DataSeq[Int1, SShort]) {
    testIndex(seq)

    testApplyUpdate(seq, -32769, 32767, 32767)
    testApplyUpdate(seq, -32768, -32768, -32768)
    testApplyUpdate(seq, -1, -1, -1)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, 32767, 32767, 32767)
    testApplyUpdate(seq, 32768, -32768, -32768)
  }

  private def testUShort(seq: DataSeq[Int1, UShort]) {
    testIndex(seq)

    testApplyUpdate(seq, -1, 65535, 65535)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, 32767, 32767, 32767)
    testApplyUpdate(seq, 32768, 32768, 32768)
    testApplyUpdate(seq, 65535, 65535, 65535)
    testApplyUpdate(seq, 65536, 0, 0)
    testApplyUpdate(seq, 65537, 1, 1)
  }
  
  private def testSInt(seq: DataSeq[Int1, SInt]) {
    testIndex(seq)

    testApplyUpdate(seq, Int.MinValue, Int.MinValue, Int.MinValue)
    testApplyUpdate(seq, -1, -1, -1)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, Int.MaxValue, Int.MaxValue, Int.MaxValue)
  }

  private def testUInt(seq: DataSeq[Int1, UInt]) {
    testIndex(seq)

    testApplyUpdate(seq, Int.MinValue, Int.MinValue, Int.MinValue)
    testApplyUpdate(seq, -1, -1, -1)
    testApplyUpdate(seq, 0, 0, 0)
    testApplyUpdate(seq, 1, 1, 1)
    testApplyUpdate(seq, Int.MaxValue, Int.MaxValue, Int.MaxValue)
  }
}
