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
class Mat3x2fTest extends FunSuite {

  test("Factories") {
    testArrayFromSize(DataArray[Mat3x2f, RFloat](_))
    testArrayFromData[Mat3x2f, RFloat](DataArray[Mat3x2f, RFloat](_))
    testBufferFromSize(DataBuffer[Mat3x2f, RFloat](_))
    testBufferFromData(DataBuffer[Mat3x2f, RFloat](_))
    testViewFromData(DataView[Mat3x2f, RFloat](_, _, _))
    testReadBufferFromData(ReadDataBuffer[Mat3x2f, RFloat](_))
    testReadViewFromData(ReadDataView[Mat3x2f, RFloat](_, _, _))
    testArrayFromCollection[Mat3x2f, RFloat]((a: IndexedSeq[ReadMat3x2f]) => DataArray[Mat3x2f, RFloat](a: _*))
    testBufferFromCollection[Mat3x2f, RFloat]((a: IndexedSeq[ReadMat3x2f]) => DataBuffer[Mat3x2f, RFloat](a: _*))
  }
  
  test("Apply/Update") {
    testApplyUpdateArray[Mat3x2f, RFloat](DataArray[Mat3x2f, RFloat](_))
    testApplyUpdateBuffer(DataBuffer[Mat3x2f, RFloat](_))
    testApplyUpdateView(DataView[Mat3x2f, RFloat](_, _, _))
  }
  
  test("Sub Copy") {
    testSubCopy(DataFactory[Mat3x2f, RFloat])
  }
}
