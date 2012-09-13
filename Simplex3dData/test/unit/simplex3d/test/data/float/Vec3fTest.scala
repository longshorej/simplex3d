/*
 * Simplex3dData - Test Package
 * Copyright (C) 2010-2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dDataTest.
 *
 * Simplex3dDataTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dDataTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.test.data
package float

import org.scalatest._
import simplex3d.math.floatx._
import simplex3d.data._
import simplex3d.data.float._

import Descriptors._
import FactoryTestUtil._
import ApplyUpdateTestUtil._
import SubCopyTestUtil._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Vec3fTest extends FunSuite {

  test("Factories") {
    testArrayFromSize(DataArray[Vec3f, SByte](_))
    testArrayFromData[Vec3f, SByte](DataArray[Vec3f, SByte](_))
    testBufferFromSize(DataBuffer[Vec3f, SByte](_))
    testBufferFromData(DataBuffer[Vec3f, SByte](_))
    testViewFromData(DataView[Vec3f, SByte](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, SByte](_))
    testReadViewFromData(ReadDataView[Vec3f, SByte](_, _, _))
    testArrayFromCollection[Vec3f, SByte]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, SByte](a: _*))
    testBufferFromCollection[Vec3f, SByte]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, SByte](a: _*))

    testArrayFromSize(DataArray[Vec3f, UByte](_))
    testArrayFromData[Vec3f, UByte](DataArray[Vec3f, UByte](_))
    testBufferFromSize(DataBuffer[Vec3f, UByte](_))
    testBufferFromData(DataBuffer[Vec3f, UByte](_))
    testViewFromData(DataView[Vec3f, UByte](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, UByte](_))
    testReadViewFromData(ReadDataView[Vec3f, UByte](_, _, _))
    testArrayFromCollection[Vec3f, UByte]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, UByte](a: _*))
    testBufferFromCollection[Vec3f, UByte]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, UByte](a: _*))

    testArrayFromSize(DataArray[Vec3f, SShort](_))
    testArrayFromData[Vec3f, SShort](DataArray[Vec3f, SShort](_))
    testBufferFromSize(DataBuffer[Vec3f, SShort](_))
    testBufferFromData(DataBuffer[Vec3f, SShort](_))
    testViewFromData(DataView[Vec3f, SShort](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, SShort](_))
    testReadViewFromData(ReadDataView[Vec3f, SShort](_, _, _))
    testArrayFromCollection[Vec3f, SShort]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, SShort](a: _*))
    testBufferFromCollection[Vec3f, SShort]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, SShort](a: _*))

    testArrayFromSize(DataArray[Vec3f, UShort](_))
    testArrayFromData[Vec3f, UShort](DataArray[Vec3f, UShort](_))
    testBufferFromSize(DataBuffer[Vec3f, UShort](_))
    testBufferFromData(DataBuffer[Vec3f, UShort](_))
    testViewFromData(DataView[Vec3f, UShort](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, UShort](_))
    testReadViewFromData(ReadDataView[Vec3f, UShort](_, _, _))
    testArrayFromCollection[Vec3f, UShort]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, UShort](a: _*))
    testBufferFromCollection[Vec3f, UShort]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, UShort](a: _*))

    testArrayFromSize(DataArray[Vec3f, SInt](_))
    testArrayFromData[Vec3f, SInt](DataArray[Vec3f, SInt](_))
    testBufferFromSize(DataBuffer[Vec3f, SInt](_))
    testBufferFromData(DataBuffer[Vec3f, SInt](_))
    testViewFromData(DataView[Vec3f, SInt](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, SInt](_))
    testReadViewFromData(ReadDataView[Vec3f, SInt](_, _, _))
    testArrayFromCollection[Vec3f, SInt]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, SInt](a: _*))
    testBufferFromCollection[Vec3f, SInt]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, SInt](a: _*))

    testArrayFromSize(DataArray[Vec3f, UInt](_))
    testArrayFromData[Vec3f, UInt](DataArray[Vec3f, UInt](_))
    testBufferFromSize(DataBuffer[Vec3f, UInt](_))
    testBufferFromData(DataBuffer[Vec3f, UInt](_))
    testViewFromData(DataView[Vec3f, UInt](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, UInt](_))
    testReadViewFromData(ReadDataView[Vec3f, UInt](_, _, _))
    testArrayFromCollection[Vec3f, UInt]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, UInt](a: _*))
    testBufferFromCollection[Vec3f, UInt]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, UInt](a: _*))
    
    testArrayFromSize(DataArray[Vec3f, HFloat](_))
    testArrayFromData[Vec3f, HFloat](DataArray[Vec3f, HFloat](_))
    testBufferFromSize(DataBuffer[Vec3f, HFloat](_))
    testBufferFromData(DataBuffer[Vec3f, HFloat](_))
    testViewFromData(DataView[Vec3f, HFloat](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, HFloat](_))
    testReadViewFromData(ReadDataView[Vec3f, HFloat](_, _, _))
    testArrayFromCollection[Vec3f, HFloat]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, HFloat](a: _*))
    testBufferFromCollection[Vec3f, HFloat]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, HFloat](a: _*))
    
    testArrayFromSize(DataArray[Vec3f, RFloat](_))
    testArrayFromData[Vec3f, RFloat](DataArray[Vec3f, RFloat](_))
    testBufferFromSize(DataBuffer[Vec3f, RFloat](_))
    testBufferFromData(DataBuffer[Vec3f, RFloat](_))
    testViewFromData(DataView[Vec3f, RFloat](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Vec3f, RFloat](_))
    testReadViewFromData(ReadDataView[Vec3f, RFloat](_, _, _))
    testArrayFromCollection[Vec3f, RFloat]((a: IndexedSeq[ReadVec3f]) => DataArray[Vec3f, RFloat](a: _*))
    testBufferFromCollection[Vec3f, RFloat]((a: IndexedSeq[ReadVec3f]) => DataBuffer[Vec3f, RFloat](a: _*))
  }

  test("Apply/Update") {
    testApplyUpdateArray[Vec3f, SByte](DataArray[Vec3f, SByte](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, SByte](_))
    testApplyUpdateView(DataView[Vec3f, SByte](_, _, _))

    testApplyUpdateArray[Vec3f, UByte](DataArray[Vec3f, UByte](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, UByte](_))
    testApplyUpdateView(DataView[Vec3f, UByte](_, _, _))

    testApplyUpdateArray[Vec3f, SShort](DataArray[Vec3f, SShort](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, SShort](_))
    testApplyUpdateView(DataView[Vec3f, SShort](_, _, _))

    testApplyUpdateArray[Vec3f, UShort](DataArray[Vec3f, UShort](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, UShort](_))
    testApplyUpdateView(DataView[Vec3f, UShort](_, _, _))

    testApplyUpdateArray[Vec3f, SInt](DataArray[Vec3f, SInt](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, SInt](_))
    testApplyUpdateView(DataView[Vec3f, SInt](_, _, _))

    testApplyUpdateArray[Vec3f, UInt](DataArray[Vec3f, UInt](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, UInt](_))
    testApplyUpdateView(DataView[Vec3f, UInt](_, _, _))

    testApplyUpdateArray[Vec3f, HFloat](DataArray[Vec3f, HFloat](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, HFloat](_))
    testApplyUpdateView(DataView[Vec3f, HFloat](_, _, _))

    testApplyUpdateArray[Vec3f, RFloat](DataArray[Vec3f, RFloat](_))
    testApplyUpdateBuffer(DataBuffer[Vec3f, RFloat](_))
    testApplyUpdateView(DataView[Vec3f, RFloat](_, _, _))
  }
  
  test("Sub Copy") {
    testSubCopy(DataFactory[Vec3f, UByte])
    testSubCopy(DataFactory[Vec3f, SByte])
    testSubCopy(DataFactory[Vec3f, UShort])
    testSubCopy(DataFactory[Vec3f, SShort])
    testSubCopy(DataFactory[Vec3f, UInt])
    testSubCopy(DataFactory[Vec3f, SInt])
    testSubCopy(DataFactory[Vec3f, HFloat])
    testSubCopy(DataFactory[Vec3f, RFloat])
  }
}
