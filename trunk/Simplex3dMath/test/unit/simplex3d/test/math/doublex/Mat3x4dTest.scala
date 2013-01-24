/*
 * Simplex3dMath - Test Package
 * Copyright (C) 2010-2011, Aleksey Nikiforov
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

package simplex3d.test.math.doublex

import org.scalatest._

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.floatx._
import simplex3d.math.doublex.functions._
import MatConstants._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Mat3x4dTest extends FunSuite {
  
  test("Clone") {
    var t: ReadMat3x4 = Mat3x4(5)
    assert(t.clone ne t)
    assert(t.clone == t)

    t = ConstMat3x4(5)
    assert(t.clone eq t)
  }

  test("Factories") {
    var m: ReadMat3x4 = Mat3x4(1)

    m = Mat3x4(d00)
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, 0, 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((0, d00, 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, d00, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    )
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(
      Vec4(d00, d01, d02, d03),
      Vec4(d10, d11, d12, d13),
      Vec4(d20, d21, d22, d23)
    )
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x2(
      d00, d01,
      d10, d11
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x2(
      d00, d01,
      d10, d11,
      d20, d21
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x2(
      d00, d01,
      d10, d11,
      d20, d21,
      d30, d31
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x3(
      d00, d01, d02,
      d10, d11, d12
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x3(
      d00, d01, d02,
      d10, d11, d12,
      d20, d21, d22
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x3(
      d00, d01, d02,
      d10, d11, d12,
      d20, d21, d22,
      d30, d31, d32
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23,
      d30, d31, d32, d33
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(
      Vec4f(f00, f01, f02, f03),
      Vec4f(f10, f11, f12, f13),
      Vec4f(f20, f21, f22, f23)
    )
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), toDouble(f23))) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x2f(
      f00, f01,
      f10, f11
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x2f(
      f00, f01,
      f10, f11,
      f20, f21
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x2f(
      f00, f01,
      f10, f11,
      f20, f21,
      f30, f31
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), 0, 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), 0, 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x3f(
      f00, f01, f02,
      f10, f11, f12
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x3f(
      f00, f01, f02,
      f10, f11, f12,
      f20, f21, f22
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x3f(
      f00, f01, f02,
      f10, f11, f12,
      f20, f21, f22,
      f30, f31, f32
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), 0)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), 0)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat2x4f(
      f00, f01, f02, f03,
      f10, f11, f12, f13
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((0, 0, 1, 0)) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat3x4f(
      f00, f01, f02, f03,
      f10, f11, f12, f13,
      f20, f21, f22, f23
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), toDouble(f23))) { (m.m20, m.m21, m.m22, m.m23) }

    m = Mat3x4(Mat4x4f(
      f00, f01, f02, f03,
      f10, f11, f12, f13,
      f20, f21, f22, f23,
      f30, f31, f32, f33
    ))
    expectResult(classOf[Mat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), toDouble(f23))) { (m.m20, m.m21, m.m22, m.m23) }


    m = ConstMat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    )
    expectResult(classOf[ConstMat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = ConstMat3x4(
      Vec4(d00, d01, d02, d03),
      Vec4(d10, d11, d12, d13),
      Vec4(d20, d21, d22, d23)
    )
    expectResult(classOf[ConstMat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = ConstMat3x4(Mat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    ))
    expectResult(classOf[ConstMat3x4]) { m.getClass }
    expectResult((d00, d01, d02, d03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((d10, d11, d12, d13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((d20, d21, d22, d23)) { (m.m20, m.m21, m.m22, m.m23) }

    m = ConstMat3x4(
      Vec4f(f00, f01, f02, f03),
      Vec4f(f10, f11, f12, f13),
      Vec4f(f20, f21, f22, f23)
    )
    expectResult(classOf[ConstMat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), toDouble(f23))) { (m.m20, m.m21, m.m22, m.m23) }

    m = ConstMat3x4(Mat3x4f(
      f00, f01, f02, f03,
      f10, f11, f12, f13,
      f20, f21, f22, f23
    ))
    expectResult(classOf[ConstMat3x4]) { m.getClass }
    expectResult((toDouble(f00), toDouble(f01), toDouble(f02), toDouble(f03))) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((toDouble(f10), toDouble(f11), toDouble(f12), toDouble(f13))) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((toDouble(f20), toDouble(f21), toDouble(f22), toDouble(f23))) { (m.m20, m.m21, m.m22, m.m23) }
  }

  test("Unapply") {
    Mat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    ) match {
      case Mat3x4(c1, c2, c3) =>
        if (
          c1 != Vec4(d00, d01, d02, d03) ||
          c2 != Vec4(d10, d11, d12, d13) ||
          c3 != Vec4(d20, d21, d22, d23)
        ) throw new AssertionError()
    }
    ConstMat3x4(
      d00, d01, d02, d03,
      d10, d11, d12, d13,
      d20, d21, d22, d23
    ) match {
      case Mat3x4(c1, c2, c3) =>
        if (
          c1 != Vec4(d00, d01, d02, d03) ||
          c2 != Vec4(d10, d11, d12, d13) ||
          c3 != Vec4(d20, d21, d22, d23)
        ) throw new AssertionError()
    }
  }

  test("Const conversions") {
    val i = Mat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )

    val t: ConstMat3x4 = i
    expectResult(classOf[ConstMat3x4]) { t.getClass }
    assert(i == t)

    var c = ConstMat3x4(2); val v = i
    expectResult(classOf[Mat3x4]) { v.getClass }
    c = v; assert(i == c)
    expectResult(classOf[ConstMat3x4]) { c.getClass }
  }

  test("Equality methods") {
    val m = Mat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )
    val n = ConstMat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )
    assert(m == m)
    assert(m == n)
    assert(n == m)
    assert(n == n)

    assert(m.equals(n))
    assert(!m.equals(Nil))

    for (r <- 0 until 4; c <- 0 until 3) {
      val t = Mat3x4(n)
      t(c, r) = -1
      assert(t != n)
    }

    assert(m == Mat3x4f(M))
    for (r <- 0 until 4; c <- 0 until 3) {
      val t = Mat3x4f(M)
      t(c, r) = -1
      assert(m != t)
    }
  }

  test("Indexed read") {
    val m = ConstMat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )

    var count = 0
    for (c <- 0 until 3; r <- 0 until 4) {
      count += 1
      expectResult(count) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(3, 1)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1, 1)
    }

    intercept[IndexOutOfBoundsException] {
      m(1, 4)
    }
    intercept[IndexOutOfBoundsException] {
      m(1, -1)
    }

    expectResult(Vec4(m00, m01, m02, m03)) { m(0) }
    expectResult(Vec4(m10, m11, m12, m13)) { m(1) }
    expectResult(Vec4(m20, m21, m22, m23)) { m(2) }

    expectResult(classOf[ConstVec4]) { m(0).getClass }
    expectResult(classOf[ConstVec4]) { m(1).getClass }
    expectResult(classOf[ConstVec4]) { m(2).getClass }

    intercept[IndexOutOfBoundsException] {
      m(3)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1)
    }
  }

  test("Indexed write") {
    var m = Mat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )

    var count = 0
    for (c <- 0 until 3; r <- 0 until 4) {
      count += 1
      m(c, r) = count + 1
      expectResult(count + 1) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(3, 1) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(-1, 1) = 1
    }

    intercept[IndexOutOfBoundsException] {
      m(1, 4) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(1, -1) = 1
    }

    m = Mat3x4(0)

    m(0) = Vec4(m00, m01, m02, m03)
    m(1) = Vec4(m10, m11, m12, m13)
    m(2) = Vec4(m20, m21, m22, m23)

    expectResult(Vec4(m00, m01, m02, m03)) { m(0) }
    expectResult(Vec4(m10, m11, m12, m13)) { m(1) }
    expectResult(Vec4(m20, m21, m22, m23)) { m(2) }

    m = Mat3x4(0)

    m(0) = Vec3(m00, m01, m02)
    m(1) = Vec3(m10, m11, m12)
    m(2) = Vec3(m20, m21, m22)

    expectResult(Vec4(m00, m01, m02, 0)) { m(0) }
    expectResult(Vec4(m10, m11, m12, 0)) { m(1) }
    expectResult(Vec4(m20, m21, m22, 0)) { m(2) }

    m = Mat3x4(0)

    m(0) = Vec2(m00, m01)
    m(1) = Vec2(m10, m11)
    m(2) = Vec2(m20, m21)

    expectResult(Vec4(m00, m01, 0, 0)) { m(0) }
    expectResult(Vec4(m10, m11, 0, 0)) { m(1) }
    expectResult(Vec4(m20, m21, 0, 0)) { m(2) }

    intercept[IndexOutOfBoundsException] {
      m(3) = Vec4(1)
      m(3) = Vec3(1)
      m(3) = Vec2(1)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1) = Vec4(1)
      m(-1) = Vec3(1)
      m(-1) = Vec2(1)
    }
  }

  test("Setters") {
    var m = Mat3x4(0)
    val i = ConstMat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )

    m = Mat3x4(0)
    m := i
    expectResult((m00, m01, m02, m03)) { (m.m00, m.m01, m.m02, m.m03) }
    expectResult((m10, m11, m12, m13)) { (m.m10, m.m11, m.m12, m.m13) }
    expectResult((m20, m21, m22, m23)) { (m.m20, m.m21, m.m22, m.m23) }
  }

  test("Const math") {
    val m = ConstMat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )
    assert(+m eq m)

    var t = Mat3x4(
      -m00, -m01, -m02, -m03,
      -m10, -m11, -m12, -m13,
      -m20, -m21, -m22, -m23
    )
    assert(-m == t)

    t = Mat3x4(
      2*m00, 2*m01, 2*m02, 2*m03,
      2*m10, 2*m11, 2*m12, 2*m13,
      2*m20, 2*m21, 2*m22, 2*m23
    )
    assert(m*2 == t)

    t = Mat3x4(
      m00/2, m01/2, m02/2, m03/2,
      m10/2, m11/2, m12/2, m13/2,
      m20/2, m21/2, m22/2, m23/2
    )
    assert(m/2 == t)

    t = Mat3x4(
      m00+2, m01+2, m02+2, m03+2,
      m10+2, m11+2, m12+2, m13+2,
      m20+2, m21+2, m22+2, m23+2
    )
    assert(m + 2 == t)

    t = Mat3x4(
      m00-2, m01-2, m02-2, m03-2,
      m10-2, m11-2, m12-2, m13-2,
      m20-2, m21-2, m22-2, m23-2
    )
    assert(m - 2 == t)

    val n: ConstMat3x4 = m*3

    t = Mat3x4(
      4*m00, 4*m01, 4*m02, 4*m03,
      4*m10, 4*m11, 4*m12, 4*m13,
      4*m20, 4*m21, 4*m22, 4*m23
    )
    assert(n + m == t)

    t = Mat3x4(
      2*m00, 2*m01, 2*m02, 2*m03,
      2*m10, 2*m11, 2*m12, 2*m13,
      2*m20, 2*m21, 2*m22, 2*m23
    )
    assert(n - m == t)

    t = Mat3x4(
      3, 3, 3, 3,
      3, 3, 3, 3,
      3, 3, 3, 3
    )
    assert(n / m == t)


    val mul23 = Mat2x4(
      38, 44, 50, 56,
      98, 116, 134, 152
    )
    assert(m*Mat2x3(M) == mul23)

    val mul33 = Mat3x4(
      38, 44, 50, 56,
      98, 116, 134, 152,
      158, 188, 218, 248
    )
    assert(m*Mat3x3(M) == mul33)

    val mul43 = Mat4x4(
      38, 44, 50, 56,
      98, 116, 134, 152,
      158, 188, 218, 248,
      218, 260, 302, 344
    )
    assert(m*Mat4x3(M) == mul43)

    assert(m*Vec3(1, 2, 3) == Vec4(38, 44, 50, 56))
  }

  test("Mutable math") {
    val m = Mat3x4(0)
    val i = ConstMat3x4(
      m00, m01, m02, m03,
      m10, m11, m12, m13,
      m20, m21, m22, m23
    )

    var t = Mat3x4(
      2*m00, 2*m01, 2*m02, 2*m03,
      2*m10, 2*m11, 2*m12, 2*m13,
      2*m20, 2*m21, 2*m22, 2*m23
    )
    m := i; m *= 2; assert(m == t)

    t = Mat3x4(
      m00/2, m01/2, m02/2, m03/2,
      m10/2, m11/2, m12/2, m13/2,
      m20/2, m21/2, m22/2, m23/2
    )
    m := i; m /= 2; assert(m == t)

    t = Mat3x4(
      m00+2, m01+2, m02+2, m03+2,
      m10+2, m11+2, m12+2, m13+2,
      m20+2, m21+2, m22+2, m23+2
    )
    m := i; m += 2; assert(m == t)

    t = Mat3x4(
      m00-2, m01-2, m02-2, m03-2,
      m10-2, m11-2, m12-2, m13-2,
      m20-2, m21-2, m22-2, m23-2
    )
    m := i; m -= 2; assert(m == t)

    val n: ConstMat3x4 = i*3

    t = Mat3x4(
      4*m00, 4*m01, 4*m02, 4*m03,
      4*m10, 4*m11, 4*m12, 4*m13,
      4*m20, 4*m21, 4*m22, 4*m23
    )
    m := i; m += n; assert(m == t)

    t = Mat3x4(
      -2*m00, -2*m01, -2*m02, -2*m03,
      -2*m10, -2*m11, -2*m12, -2*m13,
      -2*m20, -2*m21, -2*m22, -2*m23
    )
    m := i; m -= n; assert(m == t)

    t = Mat3x4(
      38, 44, 50, 56,
      98, 116, 134, 152,
      158, 188, 218, 248
    )
    m := i; m *= Mat3(M); assert(m == t)

    t = Mat3x4(
      1, 1, 1, 1,
      1, 1, 1, 1,
      1, 1, 1, 1
    )
    m := i; m/= m; assert(m == t)
  }

}
