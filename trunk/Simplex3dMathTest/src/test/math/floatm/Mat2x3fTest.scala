/*
 * Simplex3d, MathTest package
 * Copyright (C) 2010 Simplex3d Team
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

package test.math.floatm

import org.scalatest._

import simplex3d.math.BaseMath._
import simplex3d.math.floatm.renamed._
import simplex3d.math.doublem._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Mat2x3fTest extends FunSuite {
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

  test("Factories") {
    var m: AnyMat2x3 = Mat2x3(1)

    m = Mat2x3(f00)
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, 0)) { (m.m00, m.m10) }
    expect((0, f00)) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    )
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(
      Vec2(f00, f10),
      Vec2(f01, f11),
      Vec2(f02, f12)
    )
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x2(
      f00, f10,
      f01, f11
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x4(
      f00, f10,
      f01, f11,
      f02, f12,
      f03, f13
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x2(
      f00, f10, f20,
      f01, f11, f21
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x3(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x4(
      f00, f10, f20,
      f01, f11, f21,
      f02, f12, f22,
      f03, f13, f23
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x2(
      f00, f10, f20, f30,
      f01, f11, f21, f31
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x3(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x4(
      f00, f10, f20, f30,
      f01, f11, f21, f31,
      f02, f12, f22, f32,
      f03, f13, f23, f33
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = Mat2x3(
      Vec2d(d00, d10),
      Vec2d(d01, d11),
      Vec2d(d02, d12)
    )
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x2d(
      d00, d10,
      d01, d11
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x3d(
      d00, d10,
      d01, d11,
      d02, d12
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat2x4d(
      d00, d10,
      d01, d11,
      d02, d12,
      d03, d13
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x2d(
      d00, d10, d20,
      d01, d11, d21
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x3d(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat3x4d(
      d00, d10, d20,
      d01, d11, d21,
      d02, d12, d22,
      d03, d13, d23
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x2d(
      d00, d10, d20, d30,
      d01, d11, d21, d31
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((0, 0)) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x3d(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = Mat2x3(Mat4x4d(
      d00, d10, d20, d30,
      d01, d11, d21, d31,
      d02, d12, d22, d32,
      d03, d13, d23, d33
    ))
    expect(classOf[Mat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }


    m = ConstMat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    )
    expect(classOf[ConstMat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = ConstMat2x3(
      Vec2(f00, f10),
      Vec2(f01, f11),
      Vec2(f02, f12)
    )
    expect(classOf[ConstMat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = ConstMat2x3(Mat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    ))
    expect(classOf[ConstMat2x3]) { m.getClass }
    expect((f00, f10)) { (m.m00, m.m10) }
    expect((f01, f11)) { (m.m01, m.m11) }
    expect((f02, f12)) { (m.m02, m.m12) }

    m = ConstMat2x3(
      Vec2d(d00, d10),
      Vec2d(d01, d11),
      Vec2d(d02, d12)
    )
    expect(classOf[ConstMat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }

    m = ConstMat2x3(Mat2x3d(
      d00, d10,
      d01, d11,
      d02, d12
    ))
    expect(classOf[ConstMat2x3]) { m.getClass }
    expect((float(d00), float(d10))) { (m.m00, m.m10) }
    expect((float(d01), float(d11))) { (m.m01, m.m11) }
    expect((float(d02), float(d12))) { (m.m02, m.m12) }
  }

  test("Unapply") {
    Mat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    ) match {
      case Mat2x3(c1, c2, c3) =>
        if (
          c1 != Vec2(f00, f10) ||
          c2 != Vec2(f01, f11) ||
          c3 != Vec2(f02, f12)
        ) throw new AssertionError()
    }
    ConstMat2x3(
      f00, f10,
      f01, f11,
      f02, f12
    ) match {
      case Mat2x3(c1, c2, c3) =>
        if (
          c1 != Vec2(f00, f10) ||
          c2 != Vec2(f01, f11) ||
          c3 != Vec2(f02, f12)
        ) throw new AssertionError()
    }
  }

  test("Const conversions") {
    val i = Mat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )

    val t: ConstMat2x3 = i
    expect(classOf[ConstMat2x3]) { t.getClass }
    assert(i == t)

    var c: ConstMat2x3 = i; var v = Mat2x3(2)
    expect(classOf[ConstMat2x3]) { c.getClass }
    v = c; assert(i == v)
    expect(classOf[Mat2x3]) { v.getClass }

    c = Mat2x3(2); v = i
    expect(classOf[Mat2x3]) { v.getClass }
    c = v; assert(i == c)
    expect(classOf[ConstMat2x3]) { c.getClass }
  }

  test("Equality methods") {
    val m = Mat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )
    val n = ConstMat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )
    assert(m == m)
    assert(m == n)
    assert(n == m)
    assert(n == n)

    assert(m.equals(n))
    assert(!m.equals(Nil))

    for (r <- 0 until 2; c <- 0 until 3) {
      val t = Mat2x3(n)
      t(c, r) = -1
      assert(t != n)
    }
  }

  test("Indexed read") {
    {
      val m = ConstMat2x3(
        1, 2,
        3, 4,
        5, 6
      )

      var count = 0
      for (c <- 0 until 3; r <- 0 until 2) {
        count += 1
        expect(count) { m(c, r) }
      }

      intercept[IndexOutOfBoundsException] {
        m(3, 1)
      }
      intercept[IndexOutOfBoundsException] {
        m(-1, 1)
      }

      intercept[IndexOutOfBoundsException] {
        m(1, 2)
      }
      intercept[IndexOutOfBoundsException] {
        m(1, -1)
      }
    }

    val m = ConstMat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )

    expect(Vec2(m00, m10)) { m(0) }
    expect(Vec2(m01, m11)) { m(1) }
    expect(Vec2(m02, m12)) { m(2) }

    expect(classOf[ConstVec2]) { m(0).getClass }
    expect(classOf[ConstVec2]) { m(1).getClass }
    expect(classOf[ConstVec2]) { m(2).getClass }

    intercept[IndexOutOfBoundsException] {
      m(3)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1)
    }
  }

  test("Indexed write") {
    var m = Mat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )

    var count = 0
    for (c <- 0 until 3; r <- 0 until 2) {
      count += 1
      m(c, r) = count + 1
      expect(count + 1) { m(c, r) }
    }

    intercept[IndexOutOfBoundsException] {
      m(3, 1) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(-1, 1) = 1
    }

    intercept[IndexOutOfBoundsException] {
      m(1, 2) = 1
    }
    intercept[IndexOutOfBoundsException] {
      m(1, -1) = 1
    }

    m = Mat2x3(0)

    m(0) = Vec2(m00, m10)
    m(1) = Vec2(m01, m11)
    m(2) = Vec2(m02, m12)

    expect(Vec2(m00, m10)) { m(0) }
    expect(Vec2(m01, m11)) { m(1) }
    expect(Vec2(m02, m12)) { m(2) }

    intercept[IndexOutOfBoundsException] {
      m(3) = Vec2(1)
    }
    intercept[IndexOutOfBoundsException] {
      m(-1) = Vec2(1)
    }
  }

  test("Setters") {
    var m = Mat2x3(0)
    val i = ConstMat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )

    m = Mat2x3(0)
    m := i
    expect((m00, m10)) { (m.m00, m.m10) }
    expect((m01, m11)) { (m.m01, m.m11) }
    expect((m02, m12)) { (m.m02, m.m12) }

    m = Mat2x3(0)
    m.set(
      m00, m10,
      m01, m11,
      m02, m12
    )
    expect((m00, m10)) { (m.m00, m.m10) }
    expect((m01, m11)) { (m.m01, m.m11) }
    expect((m02, m12)) { (m.m02, m.m12) }
  }

  test("Const math") {
    val m = ConstMat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )
    assert(+m eq m)

    var t = Mat2x3(
      -m00, -m10,
      -m01, -m11,
      -m02, -m12
    )
    assert(-m == t)

    t = Mat2x3(
      2*m00, 2*m10,
      2*m01, 2*m11,
      2*m02, 2*m12
    )
    assert(m*2 == t)

    t = Mat2x3(
      m00/2, m10/2,
      m01/2, m11/2,
      m02/2, m12/2
    )
    assert(m/2 == t)

    t = Mat2x3(
      m00+2, m10+2,
      m01+2, m11+2,
      m02+2, m12+2
    )
    assert(m + 2 == t)

    t = Mat2x3(
      m00-2, m10-2,
      m01-2, m11-2,
      m02-2, m12-2
    )
    assert(m - 2 == t)

    val n: ConstMat2x3 = m*3

    t = Mat2x3(
      4*m00, 4*m10,
      4*m01, 4*m11,
      4*m02, 4*m12
    )
    assert(n + m == t)

    t = Mat2x3(
      2*m00, 2*m10,
      2*m01, 2*m11,
      2*m02, 2*m12
    )
    assert(n - m == t)

    t = Mat2x3(
      3, 3,
      3, 3,
      3, 3
    )
    assert(n / m == t)

    
    val mul32 = Mat2x2(
      38, 44,
      98, 116
    )
    assert(m*Mat3x2(M) == mul32)

    val mul33 = Mat2x3(
      38, 44,
      98, 116,
      158, 188
    )
    assert(m*Mat3x3(M) == mul33)

    val mul34 = Mat2x4(
      38, 44,
      98, 116,
      158, 188,
      218, 260
    )
    assert(m*Mat3x4(M) == mul34)

    assert(m*Vec3(1, 2, 3) == Vec2(38, 44))
  }

  test("Mutable math") {
    val m = Mat2x3(0)
    val i = ConstMat2x3(
      m00, m10,
      m01, m11,
      m02, m12
    )

    var t = Mat2x3(
      2*m00, 2*m10,
      2*m01, 2*m11,
      2*m02, 2*m12
    )
    m := i; m *= 2; assert(m == t)

    t = Mat2x3(
      m00/2, m10/2,
      m01/2, m11/2,
      m02/2, m12/2
    )
    m := i; m /= 2; assert(m == t)

    t = Mat2x3(
      m00+2, m10+2,
      m01+2, m11+2,
      m02+2, m12+2
    )
    m := i; m += 2; assert(m == t)

    t = Mat2x3(
      m00-2, m10-2,
      m01-2, m11-2,
      m02-2, m12-2
    )
    m := i; m -= 2; assert(m == t)

    val n: ConstMat2x3 = i*3

    t = Mat2x3(
      4*m00, 4*m10,
      4*m01, 4*m11,
      4*m02, 4*m12
    )
    m := i; m += n; assert(m == t)

    t = Mat2x3(
      -2*m00, -2*m10,
      -2*m01, -2*m11,
      -2*m02, -2*m12
    )
    m := i; m -= n; assert(m == t)

    t = Mat2x3(
      38, 44,
      98, 116,
      158, 188
    )
    m := i; m *= Mat3x3(M); assert(m == t)

    t = Mat2x3(
      1, 1,
      1, 1,
      1, 1
    )
    m := i; m/= m; assert(m == t)
  }

}