/*
 * Simplex3d, MathTest package
 * Copyright (C) 2010, Simplex3d Team
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

package test.math.doublem

import org.scalatest._

import simplex3d.math._
import simplex3d.math.doublem.renamed._
import simplex3d.math.floatm._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Mat3x4dTest extends FunSuite {
  val (m00, m10, m20, m30) = (1d, 2d, 3d, 4d)
  val (m01, m11, m21, m31) = (5d, 6d, 7d, 8d)
  val (m02, m12, m22, m32) = (9d, 10d, 11d, 12d)
  val (m03, m13, m23, m33) = (13d, 14d, 15d, 16d)

  val (f00, f10, f20, f30) = (1f+1e-5f, 2f+1e-5f, 3f+1e-5f, 4f+1e-5f)
  val (f01, f11, f21, f31) = (5f+1e-5f, 6f+1e-5f, 7f+1e-5f, 8f+1e-5f)
  val (f02, f12, f22, f32) = (9f+1e-5f, 10f+1e-5f, 11f+1e-5f, 12f+1e-5f)
  val (f03, f13, f23, f33) = (13f+1e-5f, 14f+1e-5f, 15f+1e-5f, 16f+1e-5f)

  val (d00, d10, d20, d30) = (1+1e-14, 2+1e-14, 3+1e-14, 4+1e-14)
  val (d01, d11, d21, d31) = (5+1e-14, 6+1e-14, 7+1e-14, 8+1e-14)
  val (d02, d12, d22, d32) = (9+1e-14, 10+1e-14, 11+1e-14, 12+1e-14)
  val (d03, d13, d23, d33) = (13+1e-14, 14+1e-14, 15+1e-14, 16+1e-14)

  val M = Mat4(
    m00, m10, m20, m30,
    m01, m11, m21, m31,
    m02, m12, m22, m32,
    m03, m13, m23, m33
  )

  test("Factories") {
    var m: AnyMat3x4 = Mat3x4(1)

    m = Mat3x4(d00)
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, 0, 0)) { (m.m00, m.m10, m.m20) }
    expect((0, d00, 0)) { (m.m01, m.m11, m.m21) }
    expect((0, 0, d00)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    )
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(
      Vec3(d00, d10, d20),
      Vec3(d01, d11, d21),
      Vec3(d02, d12, d22),
      Vec3(d03, d13, d23)
    )
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x2(
      d00, d10,
      d01, d11
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, 0)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, 0)) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x3(
      d00, d10,
      d01, d11,
      d02, d12
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, 0)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, 0)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x4(
      d00, d10,
      d01, d11,
      d02, d12,
      d03, d13
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, 0)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, 0)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, 1)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x2(
      d00, d10, d20,
      d01, d11, d21
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x3(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x2(
      d00, d10, d20, d30,
      d01, d11, d21, d31
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x3(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x4(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32,
      d03, d13, d23, d33
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(
      Vec3f(f00, f10, f20),
      Vec3f(f01, f11, f21),
      Vec3f(f02, f12, f22),
      Vec3f(f03, f13, f23)
    )
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), double(f23))) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x2f(
      f00, f10,
      f01, f11
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), 0)) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), 0)) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x3f(
      f00, f10,
      f01, f11,
      f02, f12
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), 0)) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), 0)) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat2x4f(
      f00, f10,
      f01, f11,
      f02, f12,
      f03, f13
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), 0)) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), 0)) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), 1)) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x2f(
      f00, f10, f20,
      f01, f11, f21
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x3f(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat3x4f(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22,
      f03, f13, f23
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), double(f23))) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x2f(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((0, 0, 1)) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x3f(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((0, 0, 0)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(Mat4x4f(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32,
      f03, f13, f23, f33
    ))
    expect(classOf[Mat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), double(f23))) { (m.m03, m.m13, m.m23) }


    m = ConstMat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    )
    expect(classOf[ConstMat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = ConstMat3x4(
      Vec3(d00, d10, d20),
      Vec3(d01, d11, d21),
      Vec3(d02, d12, d22),
      Vec3(d03, d13, d23)
    )
    expect(classOf[ConstMat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = ConstMat3x4(Mat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ))
    expect(classOf[ConstMat3x4]) { m.getClass }
    expect((d00, d10, d20)) { (m.m00, m.m10, m.m20) }
    expect((d01, d11, d21)) { (m.m01, m.m11, m.m21) }
    expect((d02, d12, d22)) { (m.m02, m.m12, m.m22) }
    expect((d03, d13, d23)) { (m.m03, m.m13, m.m23) }

    m = ConstMat3x4(
      Vec3f(f00, f10, f20),
      Vec3f(f01, f11, f21),
      Vec3f(f02, f12, f22),
      Vec3f(f03, f13, f23)
    )
    expect(classOf[ConstMat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), double(f23))) { (m.m03, m.m13, m.m23) }

    m = ConstMat3x4(Mat3x4f(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22,
      f03, f13, f23
    ))
    expect(classOf[ConstMat3x4]) { m.getClass }
    expect((double(f00), double(f10), double(f20))) { (m.m00, m.m10, m.m20) }
    expect((double(f01), double(f11), double(f21))) { (m.m01, m.m11, m.m21) }
    expect((double(f02), double(f12), double(f22))) { (m.m02, m.m12, m.m22) }
    expect((double(f03), double(f13), double(f23))) { (m.m03, m.m13, m.m23) }
  }

  test("Unapply") {
    Mat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ) match {
      case Mat3x4(c1, c2, c3, c4) =>
        if (
          c1 != Vec3(d00, d10, d20) ||
          c2 != Vec3(d01, d11, d21) ||
          c3 != Vec3(d02, d12, d22) ||
          c4 != Vec3(d03, d13, d23)
        ) throw new AssertionError()
    }
    ConstMat3x4(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ) match {
      case Mat3x4(c1, c2, c3, c4) =>
        if (
          c1 != Vec3(d00, d10, d20) ||
          c2 != Vec3(d01, d11, d21) ||
          c3 != Vec3(d02, d12, d22) ||
          c4 != Vec3(d03, d13, d23)
        ) throw new AssertionError()
    }
  }

  test("Const conversions") {
    val i = Mat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )

    val t: ConstMat3x4 = i
    expect(classOf[ConstMat3x4]) { t.getClass }
    assert(i == t)

    var c: ConstMat3x4 = i; var v = Mat3x4(2)
    expect(classOf[ConstMat3x4]) { c.getClass }
    v = c; assert(i == v)
    expect(classOf[Mat3x4]) { v.getClass }

    c = Mat3x4(2); v = i
    expect(classOf[Mat3x4]) { v.getClass }
    c = v; assert(i == c)
    expect(classOf[ConstMat3x4]) { c.getClass }
  }

  test("Equality methods") {
    val m = Mat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )
    val n = ConstMat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )
    assert(m == m)
    assert(m == n)
    assert(n == m)
    assert(n == n)

    assert(m.equals(n))
    assert(!m.equals(Nil))

    for (r <- 0 until 3; c <- 0 until 4) {
      val t = Mat3x4(n)
      t(c, r) = -1
      assert(t != n)
    }
  }

  test("Indexed read") {
    {
      val m = ConstMat3x4(
        1, 2, 3,
        4, 5, 6,
        7, 8, 9,
        10, 11, 12
      )

      var count = 0
      for (c <- 0 until 4; r <- 0 until 3) {
        count += 1
        expect(count) { m(c, r) }
      }

      intercept[IndexOutOfBoundsException] {
        m(4, 1)
      }
      intercept[IndexOutOfBoundsException] {
        m(-1, 1)
      }

      intercept[IndexOutOfBoundsException] {
        m(1, 3)
      }
      intercept[IndexOutOfBoundsException] {
        m(1, -1)
      }
    }

    val m = ConstMat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )

    expect(Vec3(m00, m10, m20)) { m(0) }
    expect(Vec3(m01, m11, m21)) { m(1) }
    expect(Vec3(m02, m12, m22)) { m(2) }
    expect(Vec3(m03, m13, m23)) { m(3) }

    expect(classOf[ConstVec3]) { m(0).getClass }
    expect(classOf[ConstVec3]) { m(1).getClass }
    expect(classOf[ConstVec3]) { m(2).getClass }
    expect(classOf[ConstVec3]) { m(3).getClass }

    intercept[IndexOutOfBoundsException] {
      m(4)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1)
    }
  }

  test("Indexed write") {
    var m = Mat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )

    var count = 0
    for (c <- 0 until 4; r <- 0 until 3) {
      count += 1
      m(c, r) = count + 1
      expect(count + 1) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(4, 1) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(-1, 1) = 1
    }

    intercept[IndexOutOfBoundsException] {
      m(1, 3) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(1, -1) = 1
    }

    m = Mat3x4(0)

    m(0) = Vec3(m00, m10, m20)
    m(1) = Vec3(m01, m11, m21)
    m(2) = Vec3(m02, m12, m22)
    m(3) = Vec3(m03, m13, m23)

    expect(Vec3(m00, m10, m20)) { m(0) }
    expect(Vec3(m01, m11, m21)) { m(1) }
    expect(Vec3(m02, m12, m22)) { m(2) }
    expect(Vec3(m03, m13, m23)) { m(3) }

    m = Mat3x4(0)

    m(0) = Vec2(m00, m10)
    m(1) = Vec2(m01, m11)
    m(2) = Vec2(m02, m12)
    m(3) = Vec2(m03, m13)

    expect(Vec3(m00, m10, 0)) { m(0) }
    expect(Vec3(m01, m11, 0)) { m(1) }
    expect(Vec3(m02, m12, 0)) { m(2) }
    expect(Vec3(m03, m13, 0)) { m(3) }

    intercept[IndexOutOfBoundsException] {
      m(4) = Vec3(1)
      m(4) = Vec2(1)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1) = Vec3(1)
      m(-1) = Vec2(1)
    }
  }

  test("Setters") {
    var m = Mat3x4(0)
    val i = ConstMat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )

    m = Mat3x4(0)
    m := i
    expect((m00, m10, m20)) { (m.m00, m.m10, m.m20) }
    expect((m01, m11, m21)) { (m.m01, m.m11, m.m21) }
    expect((m02, m12, m22)) { (m.m02, m.m12, m.m22) }
    expect((m03, m13, m23)) { (m.m03, m.m13, m.m23) }

    m = Mat3x4(0)
    m.set(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )
    expect((m00, m10, m20)) { (m.m00, m.m10, m.m20) }
    expect((m01, m11, m21)) { (m.m01, m.m11, m.m21) }
    expect((m02, m12, m22)) { (m.m02, m.m12, m.m22) }
    expect((m03, m13, m23)) { (m.m03, m.m13, m.m23) }
  }

  test("Const math") {
    val m = ConstMat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )
    assert(+m eq m)

    var t = Mat3x4(
      -m00, -m10, -m20,
      -m01, -m11, -m21,
      -m02, -m12, -m22,
      -m03, -m13, -m23
    )
    assert(-m == t)

    t = Mat3x4(
      2*m00, 2*m10, 2*m20,
      2*m01, 2*m11, 2*m21,
      2*m02, 2*m12, 2*m22,
      2*m03, 2*m13, 2*m23
    )
    assert(m*2 == t)

    t = Mat3x4(
      m00/2, m10/2, m20/2,
      m01/2, m11/2, m21/2,
      m02/2, m12/2, m22/2,
      m03/2, m13/2, m23/2
    )
    assert(m/2 == t)

    t = Mat3x4(
      m00+2, m10+2, m20+2,
      m01+2, m11+2, m21+2,
      m02+2, m12+2, m22+2,
      m03+2, m13+2, m23+2
    )
    assert(m + 2 == t)

    t = Mat3x4(
      m00-2, m10-2, m20-2,
      m01-2, m11-2, m21-2,
      m02-2, m12-2, m22-2,
      m03-2, m13-2, m23-2
    )
    assert(m - 2 == t)

    val n: ConstMat3x4 = m*3

    t = Mat3x4(
      4*m00, 4*m10, 4*m20,
      4*m01, 4*m11, 4*m21,
      4*m02, 4*m12, 4*m22,
      4*m03, 4*m13, 4*m23
    )
    assert(n + m == t)

    t = Mat3x4(
      2*m00, 2*m10, 2*m20,
      2*m01, 2*m11, 2*m21,
      2*m02, 2*m12, 2*m22,
      2*m03, 2*m13, 2*m23
    )
    assert(n - m == t)

    t = Mat3x4(
      3, 3, 3,
      3, 3, 3,
      3, 3, 3,
      3, 3, 3
    )
    assert(n / m == t)


    val mul42 = Mat3x2(
      90, 100, 110,
      202, 228, 254
    )
    assert(m*Mat4x2(M) == mul42)

    val mul43 = Mat3x3(
      90, 100, 110,
      202, 228, 254,
      314, 356, 398
    )
    assert(m*Mat4x3(M) == mul43)

    val mul44 = Mat3x4(
      90, 100, 110,
      202, 228, 254,
      314, 356, 398,
      426, 484, 542
    )
    assert(m*M == mul44)

    assert(m*Vec4(1, 2, 3, 4) == Vec3(90, 100, 110))
  }

  test("Mutable math") {
    val m = Mat3x4(0)
    val i = ConstMat3x4(
      m00, m10, m20,
      m01, m11, m21,
      m02, m12, m22,
      m03, m13, m23
    )

    var t = Mat3x4(
      2*m00, 2*m10, 2*m20,
      2*m01, 2*m11, 2*m21,
      2*m02, 2*m12, 2*m22,
      2*m03, 2*m13, 2*m23
    )
    m := i; m *= 2; assert(m == t)

    t = Mat3x4(
      m00/2, m10/2, m20/2,
      m01/2, m11/2, m21/2,
      m02/2, m12/2, m22/2,
      m03/2, m13/2, m23/2
    )
    m := i; m /= 2; assert(m == t)

    t = Mat3x4(
      m00+2, m10+2, m20+2,
      m01+2, m11+2, m21+2,
      m02+2, m12+2, m22+2,
      m03+2, m13+2, m23+2
    )
    m := i; m += 2; assert(m == t)

    t = Mat3x4(
      m00-2, m10-2, m20-2,
      m01-2, m11-2, m21-2,
      m02-2, m12-2, m22-2,
      m03-2, m13-2, m23-2
    )
    m := i; m -= 2; assert(m == t)

    val n: ConstMat3x4 = i*3

    t = Mat3x4(
      4*m00, 4*m10, 4*m20,
      4*m01, 4*m11, 4*m21,
      4*m02, 4*m12, 4*m22,
      4*m03, 4*m13, 4*m23
    )
    m := i; m += n; assert(m == t)

    t = Mat3x4(
      -2*m00, -2*m10, -2*m20,
      -2*m01, -2*m11, -2*m21,
      -2*m02, -2*m12, -2*m22,
      -2*m03, -2*m13, -2*m23
    )
    m := i; m -= n; assert(m == t)

    t = Mat3x4(
      90, 100, 110,
      202, 228, 254,
      314, 356, 398,
      426, 484, 542
    )
    m := i; m *= M; assert(m == t)

    t = Mat3x4(
      1, 1, 1,
      1, 1, 1,
      1, 1, 1,
      1, 1, 1
    )
    m := i; m/= m; assert(m == t)
  }

}