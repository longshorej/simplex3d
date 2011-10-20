/*
 * Simplex3dMath - Test Package
 * Copyright (C) 2009-2011, Aleksey Nikiforov
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
import simplex3d.math.floatx.functions._
import simplex3d.math.doublex._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Quat4fTest extends FunSuite {

  test("Clone") {
    var t: ReadQuat4 = Quat4(1, 1, 1, 1)
    assert(t.clone() ne t)
    assert(t.clone() == t)

    t = ConstQuat4(1, 1, 1, 1)
    assert(t.clone() eq t)
  }

  test("Factories") {
    val af = 1f + 1e-5f
    val bf = 2f + 1e-5f
    val cf = 3f + 1e-5f
    val df = 4f + 1e-5f

    val ad = 1 + 1e-5
    val bd = 2 + 1e-5
    val cd = 3 + 1e-5
    val dd = 4 + 1e-5

    var q: ReadQuat4 = Quat4(af, bf, cf, df)
    expect(classOf[Quat4]) { q.getClass }
    expect(af) { q.a }
    expect(bf) { q.b }
    expect(cf) { q.c }
    expect(df) { q.d }

    q = Quat4(Quat4(af, bf, cf, df))
    expect(classOf[Quat4]) { q.getClass }
    expect(af) { q.a }
    expect(bf) { q.b }
    expect(cf) { q.c }
    expect(df) { q.d }

    q = Quat4(Vec4(bf, cf, df, af))
    expect(classOf[Quat4]) { q.getClass }
    expect(af) { q.a }
    expect(bf) { q.b }
    expect(cf) { q.c }
    expect(df) { q.d }

    q = Quat4(Quat4d(ad, bd, cd, dd))
    expect(classOf[Quat4]) { q.getClass }
    expect(toFloat(ad)) { q.a }
    expect(toFloat(bd)) { q.b }
    expect(toFloat(cd)) { q.c }
    expect(toFloat(dd)) { q.d }

    q = Quat4(Vec4d(bd, cd, dd, ad))
    expect(classOf[Quat4]) { q.getClass }
    expect(toFloat(ad)) { q.a }
    expect(toFloat(bd)) { q.b }
    expect(toFloat(cd)) { q.c }
    expect(toFloat(dd)) { q.d }

    var p: ReadQuat4 = ConstQuat4(af, bf, cf, df)
    expect(classOf[ConstQuat4]) { p.getClass }
    expect(af) { p.a }
    expect(bf) { p.b }
    expect(cf) { p.c }
    expect(df) { p.d }

    p = ConstQuat4(Quat4(af, bf, cf, df))
    expect(classOf[ConstQuat4]) { p.getClass }
    expect(af) { p.a }
    expect(bf) { p.b }
    expect(cf) { p.c }
    expect(df) { p.d }

    p = ConstQuat4(Vec4(bf, cf, df, af))
    expect(classOf[ConstQuat4]) { p.getClass }
    expect(af) { p.a }
    expect(bf) { p.b }
    expect(cf) { p.c }
    expect(df) { p.d }

    p = ConstQuat4(Quat4d(ad, bd, cd, dd))
    expect(classOf[ConstQuat4]) { p.getClass }
    expect(toFloat(ad)) { p.a }
    expect(toFloat(bd)) { p.b }
    expect(toFloat(cd)) { p.c }
    expect(toFloat(dd)) { p.d }

    p = ConstQuat4(Vec4d(bd, cd, dd, ad))
    expect(classOf[ConstQuat4]) { p.getClass }
    expect(toFloat(ad)) { p.a }
    expect(toFloat(bd)) { p.b }
    expect(toFloat(cd)) { p.c }
    expect(toFloat(dd)) { p.d }
  }

  test("Unapply") {
    val a = 1+1e-5f; val b = 2+1e-5f; val c = 3+1e-5f; val d = 4+1e-5f
    Quat4(a, b, c, d) match {
      case Quat4(qa, qb, qc, qd) =>
        if (qa != a || qb != b || qc != c || qd != d)
          throw new AssertionError()
    }
    ConstQuat4(a, b, c, d) match {
      case Quat4(qa, qb, qc, qd) =>
        if (qa != a || qb != b || qc != c || qd != d)
          throw new AssertionError()
    }
  }

  test("Const conversions") {
    val a = 1f + 1e-5f
    val b = 2f + 1e-5f
    val c = 3f + 1e-5f
    val d = 4f + 1e-5f
    
    val t: ConstQuat4 = Quat4(a, b, c, d)
    expect(classOf[ConstQuat4]) { t.getClass }
    assert(Quat4(a, b, c, d) == t)

    var con = ConstQuat4(1, 0, 0, 0); val mut = Quat4(a, b, c, d)
    expect(classOf[Quat4]) { mut.getClass }
    con = mut; assert(Quat4(a, b, c, d) == con)
    expect(classOf[ConstQuat4]) { con.getClass }
  }

  test("Equality methods") {
    val m = Quat4(4, 7, 9, 1)
    val c = ConstQuat4(4, 7, 9, 1)

    assert(m == m)
    assert(m == c)
    assert(c == m)
    assert(c == c)

    assert(m.equals(c))
    assert(!m.equals(Nil))

    assert(Quat4(1, 2, 3, 4) != Quat4(9, 2, 3, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4(1, 9, 3, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4(1, 2, 9, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4(1, 2, 3, 9))

    assert(Quat4(1, 2, 3, 4) == Quat4d(1, 2, 3, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4d(9, 2, 3, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4d(1, 9, 3, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4d(1, 2, 9, 4))
    assert(Quat4(1, 2, 3, 4) != Quat4d(1, 2, 3, 9))
  }

  test("Indexed read") {
    val u = ConstQuat4(3, 4, 5, 6)

    expect(3) { u(0) }
    expect(4) { u(1) }
    expect(5) { u(2) }
    expect(6) { u(3) }

    intercept[IndexOutOfBoundsException] {
      u(4)
    }
    intercept[IndexOutOfBoundsException] {
      u(-1)
    }
  }

  test("Indexed write") {
    val u = Quat4(3, 4, 5, 6)

    u(0) = 5
    assert(Quat4(5, 4, 5, 6) == u)

    u(1) = 6
    assert(Quat4(5, 6, 5, 6) == u)

    u(2) = 7
    assert(Quat4(5, 6, 7, 6) == u)

    u(3) = 8
    assert(Quat4(5, 6, 7, 8) == u)

    intercept[IndexOutOfBoundsException] {
      u(4) = 1
    }
    intercept[IndexOutOfBoundsException] {
      u(-1) = 1
    }
  }

  test("Setters") {
    val u = Quat4(0, 0, 0, 0)

    u := Quat4(1, 2, 3, 4)
    expect(1) { u.a }
    expect(2) { u.b }
    expect(3) { u.c }
    expect(4) { u.d }
  }

  test("Const math") {
    val q = ConstQuat4(6, 7, 8, 9)

    assert(+q eq q)

    assert(Quat4(-6, -7, -8, -9) == -q)

    assert(Quat4(12, 14, 16, 18) == q*2)
    assert(Quat4(3, 3.5f, 4, 4.5f) == q/2)

    assert(Quat4(8, 9, 10, 11) == q + 2)
    assert(Quat4(4, 5, 6, 7) == q - 2)

    val p = ConstQuat4(2, 3, 4, 5)

    assert(Quat4(8, 10, 12, 14) == q + p)
    assert(Quat4(4, 4, 4, 4) == q - p)

    assert(Quat4(-86, 36, 32, 52) == q*p)
  }

  test("Mutable math") {
    val q = Quat4(1, 0, 0, 0)
    val i = ConstQuat4(2, 3, 4, 5)

    q := i; q *= 2; assert(Quat4(4, 6, 8, 10) == q)
    q := i; q /= 2; assert(Quat4(1, 1.5f, 2, 2.5f) == q)

    q := i; q += 2; assert(Quat4(4, 5, 6, 7) == q)
    q := i; q -= 2; assert(Quat4(0, 1, 2, 3) == q)

    q := i; q += Quat4(3, 4, 5, 6); assert(Quat4(5, 7, 9, 11) == q)
    q := i; q += q; assert(Quat4(4, 6, 8, 10) == q)
    q := i; q -= Quat4(2, 3, 4, 5); assert(Quat4(0, 0, 0, 0) == q)
    q := i; q -= q; assert(Quat4(0, 0, 0, 0) == q)

    q := i; q *= Quat4(6, 7, 8, 9); assert(Quat4(-86, 28, 48, 44) == q)
    q := i; q *= q; assert(Quat4(-46, 12, 16, 20) == q)
  }

  test("Rotation") {
    val random = new java.util.Random(1)
    def float = random.nextFloat
    def axis = normalize(Vec3(float, float, float))

    def testInstance(q: inQuat4, angle: Float, axis: inVec3) {
      assert(q.rotate(quaternion(angle, axis)) == quaternion(angle, axis)*q)

      assert(q.rotateX(angle) == quaternion(angle, Vec3.UnitX)*q)
      assert(q.rotateY(angle) == quaternion(angle, Vec3.UnitY)*q)
      assert(q.rotateZ(angle) == quaternion(angle, Vec3.UnitZ)*q)

      for (i <- 0 until 100) {
        val v = Vec3(float, float, float)
        assert(approxEqual(
            q.rotateVector(v),
            rotationMat(q)*v,
            1e-6f
        ))
        assert(approxEqual(
            (q*float).rotateVector(v),
            rotationMat(q)*v,
            1e-6f
        ))
      }

      val c = Quat4(q); c.applyRotation(quaternion(angle, axis))
      assert(c == q.rotate(quaternion(angle, axis)))

      val cx = Quat4(q); cx.applyRotationX(angle)
      assert(cx == q.rotate(quaternion(angle, Vec3.UnitX)))

      val cy = Quat4(q); cy.applyRotationY(angle)
      assert(cy == q.rotate(quaternion(angle, Vec3.UnitY)))

      val cz = Quat4(q); cz.applyRotationZ(angle)
      assert(cz == q.rotate(quaternion(angle, Vec3.UnitZ)))

      val self = Quat4(q); self.applyRotation(self)
      assert(self == q.rotate(q))
    }
    def testObject(angle: Float, axis: inVec3) {
      assert(Quat4.rotate(quaternion(angle, axis)) == quaternion(angle, axis))

      assert(Quat4.rotateX(angle) == quaternion(angle, Vec3.UnitX))
      assert(Quat4.rotateY(angle) == quaternion(angle, Vec3.UnitY))
      assert(Quat4.rotateZ(angle) == quaternion(angle, Vec3.UnitZ))
    }

    for (i <- 0 until 1000) {
      testInstance(quaternion(float, axis), float, axis)
      testInstance(Quat4.Identity, float, axis)
      testObject(float, axis)
    }
  }

}