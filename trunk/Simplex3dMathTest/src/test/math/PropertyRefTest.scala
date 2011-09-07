/*
 * Simplex3d, MathTest package
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dMathTest.
 *
 * Simplex3dMathTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dMathTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.math

import org.scalatest._
import simplex3d.math._
import simplex3d.math.floatx._
import simplex3d.math.doublex._
import simplex3d.math.doublex.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
class PropertyRefTest extends FunSuite {

  val random = new java.util.Random(1)
  def rb = random.nextBoolean
  def ri = random.nextInt
  def rf = random.nextFloat
  def rd = random.nextDouble
  def mf = ConstMat4f(rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf, rf)
  def md = ConstMat4d(rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd, rd)


  def test[R <: ReadPropertyRef[R]](mutable: PropertyRef[R], read: R) {
    assert(!mutable.isInstanceOf[Immutable])
    assert(mutable != read)

    val clone = mutable.clone()
    assert(mutable ne clone)
    assert(mutable == clone)

    mutable := read
    assert(mutable == read)
    assert(read != clone)

    val const = mutable.toConst
    assert(
      const.isInstanceOf[Immutable] ||
      const.isInstanceOf[Boolean] ||
      const.isInstanceOf[Int] ||
      const.isInstanceOf[Float] ||
      const.isInstanceOf[Double]
    )
    assert(mutable == const)
    
    val mcopy = mutable.toMutable
    assert(mcopy.isInstanceOf[Mutable])
    assert(mutable ne mcopy)
    assert(mutable == mcopy)
    
    val rcopy = read.toMutable
    assert(rcopy.isInstanceOf[Mutable])
    assert(read ne rcopy)
    assert(read == rcopy)
    
    mutable match {
      case u: AnyVec[_] => testVec(u)
      case m: AnyMat[_] => testMat(m)
      case q: AnyQuat4[_] => // do nothing
    }
  }

  def testVec(u: AnyVec[_]) {
    u match {
      case p: PrimitiveRef[_] => assert(p.components == 1)
      case v: AnyVec2[_] => assert(v.components == 2)
      case v: AnyVec3[_] => assert(v.components == 3)
      case v: AnyVec4[_] => assert(v.components == 4)
    }
  }

  def testMat(m: AnyMat[_]) {
    m match {
      case n: AnyMat2x2[_] => assert(n.rows == 2 && n.columns == 2)
      case n: AnyMat2x3[_] => assert(n.rows == 2 && n.columns == 3)
      case n: AnyMat2x4[_] => assert(n.rows == 2 && n.columns == 4)
      case n: AnyMat3x2[_] => assert(n.rows == 3 && n.columns == 2)
      case n: AnyMat3x3[_] => assert(n.rows == 3 && n.columns == 3)
      case n: AnyMat3x4[_] => assert(n.rows == 3 && n.columns == 4)
      case n: AnyMat4x2[_] => assert(n.rows == 4 && n.columns == 2)
      case n: AnyMat4x3[_] => assert(n.rows == 4 && n.columns == 3)
      case n: AnyMat4x4[_] => assert(n.rows == 4 && n.columns == 4)
    }
  }


  test("Primitive Refs") {
    for (i <- 0 until 100) {
      val b = rb
      test[ReadBooleanRef](new BooleanRef(b), !b)

      test[ReadIntRef](new IntRef(ri), ri)

      test[ReadFloatRef](new FloatRef(rf), rf)

      test[ReadDoubleRef](new DoubleRef(rd), rd)
    }
  }

  test("Abstract Vectors") {
    for (i <- 0 until 100) {
      val b = rb
      test(Vec2b(b, rb), ConstVec2b(!b, rb))
      test(Vec3b(b, rb, rb), ConstVec3b(!b, rb, rb))
      test(Vec4b(b, rb, rb, rb), ConstVec4b(!b, rb, rb, rb))

      test(Vec2i(ri, ri), ConstVec2i(ri, ri))
      test(Vec3i(ri, ri, ri), ConstVec3i(ri, ri, ri))
      test(Vec4i(ri, ri, ri, ri), ConstVec4i(ri, ri, ri, ri))

      test(Vec2f(rf, rf), ConstVec2f(rf, rf))
      test(Vec3f(rf, rf, rf), ConstVec3f(rf, rf, rf))
      test(Vec4f(rf, rf, rf, rf), ConstVec4f(rf, rf, rf, rf))

      test(Vec2d(rd, rd), ConstVec2d(rd, rd))
      test(Vec3d(rd, rd, rd), ConstVec3d(rd, rd, rd))
      test(Vec4d(rd, rd, rd, rd), ConstVec4d(rd, rd, rd, rd))
    }
  }

  test("Abstract Quaternions") {
    for (i <- 0 until 100) {
      test(Quat4f(rf, rf, rf, rf), ConstQuat4f(rf, rf, rf, rf))
      test(Quat4d(rd, rd, rd, rd), ConstQuat4d(rd, rd, rd, rd))
    }
  }

  test("Abstract Matrices") {
    for (i <- 0 until 100) {
      test(Mat2x2f(mf), ConstMat2x2f(mf))
      test(Mat2x3f(mf), ConstMat2x3f(mf))
      test(Mat2x4f(mf), ConstMat2x4f(mf))
      test(Mat3x2f(mf), ConstMat3x2f(mf))
      test(Mat3x3f(mf), ConstMat3x3f(mf))
      test(Mat3x4f(mf), ConstMat3x4f(mf))
      test(Mat4x2f(mf), ConstMat4x2f(mf))
      test(Mat4x3f(mf), ConstMat4x3f(mf))
      test(Mat4x4f(mf), ConstMat4x4f(mf))

      test(Mat2x2d(md), ConstMat2x2d(md))
      test(Mat2x3d(md), ConstMat2x3d(md))
      test(Mat2x4d(md), ConstMat2x4d(md))
      test(Mat3x2d(md), ConstMat3x2d(md))
      test(Mat3x3d(md), ConstMat3x3d(md))
      test(Mat3x4d(md), ConstMat3x4d(md))
      test(Mat4x2d(md), ConstMat4x2d(md))
      test(Mat4x3d(md), ConstMat4x3d(md))
      test(Mat4x4d(md), ConstMat4x4d(md))
    }
  }
}