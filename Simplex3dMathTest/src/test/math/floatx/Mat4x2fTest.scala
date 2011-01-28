/*
 * Simplex3d, MathTest package
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

package test.math.floatx

import org.scalatest._

import simplex3d.math._
import simplex3d.math.float._
import simplex3d.math.doublex._
import simplex3d.math.floatx.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Mat4x2fTest extends FunSuite {
  val (m00, m10, m20, m30) = (1f, 2f, 3f, 4f)
  val (m01, m11, m21, m31) = (5f, 6f, 7f, 8f)
  val (m02, m12, m22, m32) = (9f, 10f, 11f, 12f)
  val (m03, m13, m23, m33) = (13f, 14f, 15f, 16f)

  val (f00, f10, f20, f30) = (1f+1e-5f, 2f+1e-5f, 3f+1e-5f, 4f+1e-5f)
  val (f01, f11, f21, f31) = (5f+1e-5f, 6f+1e-5f, 7f+1e-5f, 8f+1e-5f)
  val (f02, f12, f22, f32) = (9f+1e-5f, 10f+1e-5f, 11f+1e-5f, 12f+1e-5f)
  val (f03, f13, f23, f33) = (13f+1e-5f, 14f+1e-5f, 15f+1e-5f, 16f+1e-5f)

  val (d00, d10, d20, d30) = (1+1e-5, 2+1e-5, 3+1e-5, 4+1e-5)
  val (d01, d11, d21, d31) = (5+1e-5, 6+1e-5, 7+1e-5, 8+1e-5)
  val (d02, d12, d22, d32) = (9+1e-5, 10+1e-5, 11+1e-5, 12+1e-5)
  val (d03, d13, d23, d33) = (13+1e-5, 14+1e-5, 15+1e-5, 16+1e-5)

  val M = Mat4(
    m00, m10, m20, m30,
    m01, m11, m21, m31,
    m02, m12, m22, m32,
    m03, m13, m23, m33
  )

  test("Clone") {
    var t: ReadMat4x2 = Mat4x2(5)
    assert(t.clone() ne t)
    assert(t.clone() == t)

    t = ConstMat4x2(5)
    assert(t.clone() eq t)
  }

  test("Factories") {
    var m: ReadMat4x2 = Mat4x2(1)

    m = Mat4x2(f00)
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, 0, 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((0, f00, 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    )
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(
      Vec4(f00, f10, f20, f30),
      Vec4(f01, f11, f21, f31)
    )
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x2(
      f00, f10,
      f01, f11
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x4(
      f00, f10,
      f01, f11,
      f02, f12,
      f03, f13
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x2(
      f00, f10, f20,
      f01, f11, f21
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x3(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x4(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22,
      f03, f13, f23
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x3(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x4(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32,
      f03, f13, f23, f33
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(
      Vec4d(d00, d10, d20, d30),
      Vec4d(d01, d11, d21, d31)
    )
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x2d(
      d00, d10,
      d01, d11
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x3d(
      d00, d10,
      d01, d11,
      d02, d12
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat2x4d(
      d00, d10,
      d01, d11,
      d02, d12,
      d03, d13
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), 0, 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), 0, 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x2d(
      d00, d10, d20,
      d01, d11, d21
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x3d(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat3x4d(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), 0)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), 0)) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x2d(
      d00, d10, d20, d30,
      d01, d11, d21, d31
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x3d(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }

    m = Mat4x2(Mat4x4d(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32,
      d03, d13, d23, d33
    ))
    expect(classOf[Mat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }


    m = ConstMat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    )
    expect(classOf[ConstMat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = ConstMat4x2(
      Vec4(f00, f10, f20, f30),
      Vec4(f01, f11, f21, f31)
    )
    expect(classOf[ConstMat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = ConstMat4x2(Mat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ))
    expect(classOf[ConstMat4x2]) { m.getClass }
    expect((f00, f10, f20, f30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((f01, f11, f21, f31)) { (m.m01, m.m11, m.m21, m.m31) }

    m = ConstMat4x2(
      Vec4d(d00, d10, d20, d30),
      Vec4d(d01, d11, d21, d31)
    )
    expect(classOf[ConstMat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }

    m = ConstMat4x2(Mat4x2d(
      d00, d10, d20, d30,
      d01, d11, d21, d31
    ))
    expect(classOf[ConstMat4x2]) { m.getClass }
    expect((Float(d00), Float(d10), Float(d20), Float(d30))) { (m.m00, m.m10, m.m20, m.m30) }
    expect((Float(d01), Float(d11), Float(d21), Float(d31))) { (m.m01, m.m11, m.m21, m.m31) }
  }

  test("Unapply") {
    Mat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ) match {
      case Mat4x2(c1, c2) =>
        if (
          c1 != Vec4(f00, f10, f20, f30) ||
          c2 != Vec4(f01, f11, f21, f31)
        ) throw new AssertionError()
    }
    ConstMat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ) match {
      case Mat4x2(c1, c2) =>
        if (
          c1 != Vec4(f00, f10, f20, f30) ||
          c2 != Vec4(f01, f11, f21, f31)
        ) throw new AssertionError()
    }
  }

  test("Const conversions") {
    val i = Mat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )

    val t: ConstMat4x2 = i
    expect(classOf[ConstMat4x2]) { t.getClass }
    assert(i == t)

    var c: ConstMat4x2 = i; var v = Mat4x2(2)
    expect(classOf[ConstMat4x2]) { c.getClass }
    v = c; assert(i == v)
    expect(classOf[Mat4x2]) { v.getClass }

    c = Mat4x2(2); v = i
    expect(classOf[Mat4x2]) { v.getClass }
    c = v; assert(i == c)
    expect(classOf[ConstMat4x2]) { c.getClass }
  }

  test("Equality methods") {
    val m = Mat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )
    val n = ConstMat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )
    assert(m == m)
    assert(m == n)
    assert(n == m)
    assert(n == n)

    assert(m.equals(n))
    assert(!m.equals(Nil))

    for (r <- 0 until 4; c <- 0 until 2) {
      val t = Mat4x2(n)
      t(c, r) = -1
      assert(t != n)
    }

    assert(m == Mat4x2d(M))
    for (r <- 0 until 4; c <- 0 until 2) {
      val t = Mat4x2d(M)
      t(c, r) = -1
      assert(m != t)
    }
  }

  test("Indexed read") {
    val m = ConstMat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )

    var count = 0
    for (c <- 0 until 2; r <- 0 until 4) {
      count += 1
      expect(count) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(2, 1)
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

    expect(Vec4(m00, m10, m20, m30)) { m(0) }
    expect(Vec4(m01, m11, m21, m31)) { m(1) }

    expect(classOf[ConstVec4]) { m(0).getClass }
    expect(classOf[ConstVec4]) { m(1).getClass }

    intercept[IndexOutOfBoundsException] {
      m(2)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1)
    }
  }

  test("Indexed write") {
    var m = Mat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )

    var count = 0
    for (c <- 0 until 2; r <- 0 until 4) {
      count += 1
      m(c, r) = count + 1
      expect(count + 1) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(2, 1) = 1
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

    m = Mat4x2(0)

    m(0) = Vec4(m00, m10, m20, m30)
    m(1) = Vec4(m01, m11, m21, m31)

    expect(Vec4(m00, m10, m20, m30)) { m(0) }
    expect(Vec4(m01, m11, m21, m31)) { m(1) }

    m = Mat4x2(0)

    m(0) = Vec3(m00, m10, m20)
    m(1) = Vec3(m01, m11, m21)

    expect(Vec4(m00, m10, m20, 0)) { m(0) }
    expect(Vec4(m01, m11, m21, 0)) { m(1) }

    m = Mat4x2(0)

    m(0) = Vec2(m00, m10)
    m(1) = Vec2(m01, m11)

    expect(Vec4(m00, m10, 0, 0)) { m(0) }
    expect(Vec4(m01, m11, 0, 0)) { m(1) }

    intercept[IndexOutOfBoundsException] {
      m(2) = Vec4(1)
      m(2) = Vec3(1)
      m(2) = Vec2(1)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1) = Vec4(1)
      m(-1) = Vec3(1)
      m(-1) = Vec2(1)
    }
  }

  test("Setters") {
    var m = Mat4x2(0)
    val i = ConstMat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )

    m = Mat4x2(0)
    m := i
    expect((m00, m10, m20, m30)) { (m.m00, m.m10, m.m20, m.m30) }
    expect((m01, m11, m21, m31)) { (m.m01, m.m11, m.m21, m.m31) }
  }

  test("Const math") {
    val m = ConstMat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )
    assert(+m eq m)

    var t = Mat4x2(
      -m00, -m10, -m20, -m30,
      -m01, -m11, -m21, -m31
    )
    assert(-m == t)

    t = Mat4x2(
      2*m00, 2*m10, 2*m20, 2*m30,
      2*m01, 2*m11, 2*m21, 2*m31
    )
    assert(m*2 == t)

    t = Mat4x2(
      m00/2, m10/2, m20/2, m30/2,
      m01/2, m11/2, m21/2, m31/2
    )
    assert(m/2 == t)

    t = Mat4x2(
      m00+2, m10+2, m20+2, m30+2,
      m01+2, m11+2, m21+2, m31+2
    )
    assert(m + 2 == t)

    t = Mat4x2(
      m00-2, m10-2, m20-2, m30-2,
      m01-2, m11-2, m21-2, m31-2
    )
    assert(m - 2 == t)

    val n: ConstMat4x2 = m*3

    t = Mat4x2(
      4*m00, 4*m10, 4*m20, 4*m30,
      4*m01, 4*m11, 4*m21, 4*m31
    )
    assert(n + m == t)

    t = Mat4x2(
      2*m00, 2*m10, 2*m20, 2*m30,
      2*m01, 2*m11, 2*m21, 2*m31
    )
    assert(n - m == t)

    t = Mat4x2(
      3, 3, 3, 3,
      3, 3, 3, 3
    )
    assert(n / m == t)


    val mul22 = Mat4x2(
      11, 14, 17, 20,
      35, 46, 57, 68
    )
    assert(m*Mat2x2(M) == mul22)

    val mul23 = Mat4x3(
      11, 14, 17, 20,
      35, 46, 57, 68,
      59, 78, 97, 116
    )
    assert(m*Mat2x3(M) == mul23)

    val mul24 = Mat4x4(
      11, 14, 17, 20,
      35, 46, 57, 68,
      59, 78, 97, 116,
      83, 110, 137, 164
    )
    assert(m*Mat2x4(M) == mul24)

    assert(m*Vec2(1, 2) == Vec4(11, 14, 17, 20))
  }

  test("Mutable math") {
    val m = Mat4x2(0)
    val i = ConstMat4x2(
      m00, m10, m20, m30,
      m01, m11, m21, m31
    )

    var t = Mat4x2(
      2*m00, 2*m10, 2*m20, 2*m30,
      2*m01, 2*m11, 2*m21, 2*m31
    )
    m := i; m *= 2; assert(m == t)

    t = Mat4x2(
      m00/2, m10/2, m20/2, m30/2,
      m01/2, m11/2, m21/2, m31/2
    )
    m := i; m /= 2; assert(m == t)

    t = Mat4x2(
      m00+2, m10+2, m20+2, m30+2,
      m01+2, m11+2, m21+2, m31+2
    )
    m := i; m += 2; assert(m == t)

    t = Mat4x2(
      m00-2, m10-2, m20-2, m30-2,
      m01-2, m11-2, m21-2, m31-2
    )
    m := i; m -= 2; assert(m == t)

    val n: ConstMat4x2 = i*3

    t = Mat4x2(
      4*m00, 4*m10, 4*m20, 4*m30,
      4*m01, 4*m11, 4*m21, 4*m31
    )
    m := i; m += n; assert(m == t)

    t = Mat4x2(
      -2*m00, -2*m10, -2*m20, -2*m30,
      -2*m01, -2*m11, -2*m21, -2*m31
    )
    m := i; m -= n; assert(m == t)

    t = Mat4x2(
      11, 14, 17, 20,
      35, 46, 57, 68
    )
    m := i; m *= Mat2(M); assert(m == t)

    t = Mat4x2(
      1, 1, 1, 1,
      1, 1, 1, 1
    )
    m := i; m/= m; assert(m == t)
  }

}
