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

import org.scalatest._

import java.nio._
import scala.reflect._
import simplex3d.buffer._
import simplex3d.buffer.RawType._
import simplex3d.buffer.intm._
import simplex3d.buffer.floatm._
import simplex3d.buffer.doublem._
import simplex3d.math.intm._
import simplex3d.math.floatm._
import simplex3d.math.doublem._

import TestUtil._


/**
 * @author Aleksey Nikiforov (lex)
 */
object ApplyUpdateTest extends FunSuite {

  private val randomSrc = new java.util.Random(0)
  private def ni = randomSrc.nextInt
  private def nf = randomSrc.nextFloat
  private def nd = randomSrc.nextDouble

  def intFromBits(bits: String) :Int = {
    var x = 0L
    var i = 0; while (i < bits.length) {
      if (bits(i) != ' ') {
        x <<= 1
        if (bits(i) == '1') x |= 1
      }

      i += 1
    }

    x.toInt
  }
  def intToBits(x: Int) :String = {
    var s = ""
    var check = 0

    var i = 31; while (i >= 0) {
      if (i != 31 && (i + 1) % 8 == 0) s += " "

      val digit = (x >>> i) & 1
      s += digit
      if (i > 15) check |= digit

      i -= 1
    }

    if (check != 0) s else s.substring(18)
  }
  def floatFromBits(bits: String) = java.lang.Float.intBitsToFloat(intFromBits(bits))
  def floatToBits(x: Float) = intToBits(java.lang.Float.floatToRawIntBits(x))

  def testIndex[E <: MetaElement](seq: DataSeq[E, RawData]) {
    assert(seq.size > 0)

    intercept[Exception] { seq(-1) }
    seq(0)
    seq(seq.size - 1)
    intercept[Exception] { seq(seq.size) }

    val ro = seq.asReadOnlySeq().asInstanceOf[DataSeq[E, RawData]]
    intercept[Exception] { ro(0) = seq(0) }
    intercept[Exception] { ro(seq.size - 1) = seq(0) }
  }

  def testApplyUpdate(seq: DataSeq[Int1, _], value: Int, expected: Int, store: AnyVal) {
    assert(seq.size > 0)
    val i = randomSrc.nextInt(seq.size)

    seq(i) = value
    assert(seq(i) == expected)
    verify(seq.asBuffer(), seq.offset + i*seq.stride, store)
  }

  def testApplyUpdate(seq: DataSeq[Float1, _], value: Float, expected: Float, store: AnyVal) {
    assert(seq.size > 0)
    val i = randomSrc.nextInt(seq.size)

    seq(i) = value

    if (FloatMath.isnan(expected)) assert(FloatMath.isnan(seq(i)))
    else assert(seq(i) == expected)

    verify(seq.asBuffer(), seq.offset + i*seq.stride, store)
  }

  def testApplyUpdate(seq: DataSeq[Double1, _], value: Double, expected: Double, store: AnyVal) {
    assert(seq.size > 0)
    val i = randomSrc.nextInt(seq.size)

    seq(i) = value

    if (DoubleMath.isnan(expected)) assert(DoubleMath.isnan(seq(i)))
    else assert(seq(i) == expected)

    verify(seq.asBuffer(), seq.offset + i*seq.stride, store)
  }

  private def verify(buff: Buffer, index: Int, value: AnyVal) {
    buff match {
      case b: ByteBuffer => assert(b.get(index) == value)
      case b: ShortBuffer => assert(b.get(index) == value)
      case b: CharBuffer => assert(b.get(index) == value)
      case b: IntBuffer => assert(b.get(index) == value)
      case b: FloatBuffer =>
        val stored = b.get(index)
        if (FloatMath.isnan(stored)) assert(FloatMath.isnan(value.asInstanceOf[Float]))
        else assert(b.get(index) == value)
      case b: DoubleBuffer =>
        val stored = b.get(index)
        if (DoubleMath.isnan(stored)) assert(DoubleMath.isnan(value.asInstanceOf[Double]))
        else assert(b.get(index) == value)
    }
  }

  private def cmp(x: Any, y: Any) {
    (x, y) match {
      case (a: Int, b: Int) => assert(a == b)
      case (a: Float, b: Float) =>
        if (FloatMath.isnan(a)) assert(FloatMath.isnan(b))
        else assert(a == b)
      case (a: Double, b: Double) =>
        if (DoubleMath.isnan(a)) assert(DoubleMath.isnan(b))
        else assert(a == b)
    }
  }

  private def verifyValue(seq: inData[_ <: MetaElement], i: Int) {
    def get(i: Int, j: Int) :Any = seq.backingSeq(seq.offset + seq.stride*i + j)

    seq(i) match {
      case Vec2i(x, y) =>       cmp(x, get(i, 0)); cmp(y, get(i, 1))
      case Vec3i(x, y, z) =>    cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2))
      case Vec4i(x, y, z, w) => cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2)); cmp(w, get(i, 3))
      case Vec2f(x, y) =>       cmp(x, get(i, 0)); cmp(y, get(i, 1))
      case Vec3f(x, y, z) =>    cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2))
      case Vec4f(x, y, z, w) => cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2)); cmp(w, get(i, 3))
      case Vec2d(x, y) =>       cmp(x, get(i, 0)); cmp(y, get(i, 1))
      case Vec3d(x, y, z) =>    cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2))
      case Vec4d(x, y, z, w) => cmp(x, get(i, 0)); cmp(y, get(i, 1)); cmp(z, get(i, 2)); cmp(w, get(i, 3))
    }
  }

  private def updateValue[E <: MetaElement](seq: outData[E], i: Int, bcopy: outData[E#Component]) {
    def iput(i: Int, j: Int, u: Int) {
      val s = bcopy.asInstanceOf[Data[Int1]]
      s(seq.offset + seq.stride*i + j) = u
    }
    def fput(i: Int, j: Int, u: Float) {
      val s = bcopy.asInstanceOf[Data[Float1]]
      s(seq.offset + seq.stride*i + j) = u
    }
    def dput(i: Int, j: Int, u: Double) {
      val s = bcopy.asInstanceOf[Data[Double1]]
      s(seq.offset + seq.stride*i + j) = u
    }

    val e = seq.elementManifest match {
      case Vec2i.ReadManifest =>
        val u = Vec2i(ni, ni)
        iput(i, 0, u.x); iput(i, 1, u.y)
        u
      case Vec3i.ReadManifest =>
        val u = Vec3i(ni, ni, ni)
        iput(i, 0, u.x); iput(i, 1, u.y); iput(i, 2, u.z)
        u
      case Vec4i.ReadManifest =>
        val u = Vec4i(ni, ni, ni, ni)
        iput(i, 0, u.x); iput(i, 1, u.y); iput(i, 2, u.z); iput(i, 3, u.w)
        u
      case Vec2f.ReadManifest =>
        val u = Vec2f(nf, nf)
        fput(i, 0, u.x); fput(i, 1, u.y)
        u
      case Vec3f.ReadManifest =>
        val u = Vec3f(nf, nf, nf)
        fput(i, 0, u.x); fput(i, 1, u.y); fput(i, 2, u.z)
        u
      case Vec4f.ReadManifest =>
        val u = Vec4f(nf, nf, nf, nf)
        fput(i, 0, u.x); fput(i, 1, u.y); fput(i, 2, u.z); fput(i, 3, u.w)
        u
      case Vec2d.ReadManifest =>
        val u = Vec2d(nd, nd)
        dput(i, 0, u.x); dput(i, 1, u.y)
        u
      case Vec3d.ReadManifest =>
        val u = Vec3d(nd, nd, nd)
        dput(i, 0, u.x); dput(i, 1, u.y); dput(i, 2, u.z)
        u
      case Vec4d.ReadManifest =>
        val u = Vec4d(nd, nd, nd, nd)
        dput(i, 0, u.x); dput(i, 1, u.y); dput(i, 2, u.z); dput(i, 3, u.w)
        u
    }

    seq(i) = e.asInstanceOf[E#Read]
  }

  private def testApplyUpdate[E <: MetaElement](seq: outData[E]) {
    testIndex(seq)

    val bcopy = seq.backingSeq.copyAsDataArray()

    var i = 0; while (i < seq.size) {
      verifyValue(seq, i)
      i += 1
    }

    i = 0; while (i < seq.size) {
      updateValue(seq, i, bcopy)
      i += 1
    }

    i = 0; while (i < bcopy.size) {
      cmp(bcopy(i), seq.backingSeq(i))
      i += 1
    }
  }

  def testApplyUpdateArray[E <: MetaElement, R <: RawData](
    factory: (R#ArrayType) => DataArray[E, R]
  )(implicit descriptor: Descriptor[E, R]) {
    testApplyUpdate(factory(genRandomArray(10, descriptor)))
  }

  def testApplyUpdateBuffer[E <: MetaElement, R <: RawData](
    factory: (ByteBuffer) => DataBuffer[E, R]
  )(implicit descriptor: Descriptor[E, R]) {
    val size = 10*descriptor.components*RawType.byteLength(descriptor.rawType)
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1))
  }

  def testApplyUpdateView[E <: MetaElement, R <: RawData](
    factory: (ByteBuffer, Int, Int) => DataView[E, R]
  )(implicit descriptor: Descriptor[E, R]) {
    val size = 10*descriptor.components*RawType.byteLength(descriptor.rawType)
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 0, 1))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 0, 2))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 0, 3))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 0, 4))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 0, 5))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 1, 2))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 1, 3))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 1, 4))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 1, 5))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 2, 3))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 2, 4))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 2, 5))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 3, 4))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 3, 5))
    testApplyUpdate(factory(genRandomBuffer(size, descriptor)._1, 4, 5))
  }
}
