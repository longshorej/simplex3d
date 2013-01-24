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

package simplex3d.test.math.doublex

import org.scalatest._
import simplex3d.test.math.BooleanCombinations

import simplex3d.math._
import simplex3d.math.floatx._
import simplex3d.math.double._
import simplex3d.math.doublex.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Vec3dTest extends FunSuite {

  test("Clone") {
    var t: ReadVec3 = Vec3(1)
    assert(t.clone ne t)
    assert(t.clone == t)

    t = ConstVec3(1)
    assert(t.clone eq t)
  }

  test("Factories") {
    def test(x: Double, y: Double, z: Double, w: Double) {
      var u: ReadVec3 = Vec3(x)
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(x) { u.y }
      expectResult(x) { u.z }

      u = ConstVec3(x)
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(x) { u.y }
      expectResult(x) { u.z }

      u = Vec3(x, y, z)
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = Vec3(Vec3i(toInt(x), toInt(y), toInt(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = Vec3(x, Vec2i(toInt(y), toInt(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = Vec3(Vec2i(toInt(x), toInt(y)), z)
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(z) { u.z }

      u = Vec3(Vec4i(toInt(x), toInt(y), toInt(z), toInt(w)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = Vec3(Vec3f(toFloat(x), toFloat(y), toFloat(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = Vec3(x, Vec2f(toFloat(y), toFloat(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = Vec3(Vec2f(toFloat(x), toFloat(y)), z)
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(z) { u.z }

      u = Vec3(Vec4f(toFloat(x), toFloat(y), toFloat(z), toFloat(w)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = Vec3(Vec3(toDouble(x), toDouble(y), toDouble(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = Vec3(x, Vec2(toDouble(y), toDouble(z)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = Vec3(Vec2(toDouble(x), toDouble(y)), z)
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = Vec3(Vec4(toDouble(x), toDouble(y), toDouble(z), toDouble(w)))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(x, y, z)
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(ConstVec3i(toInt(x), toInt(y), toInt(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = ConstVec3(x, Vec2i(toInt(y), toInt(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = ConstVec3(Vec2i(toInt(x), toInt(y)), z)
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(Vec4i(toInt(x), toInt(y), toInt(z), toInt(w)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toInt(x)) { u.x }
      expectResult(toInt(y)) { u.y }
      expectResult(toInt(z)) { u.z }

      u = ConstVec3(ConstVec3f(toFloat(x), toFloat(y), toFloat(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = ConstVec3(x, Vec2f(toFloat(y), toFloat(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = ConstVec3(Vec2f(toFloat(x), toFloat(y)), z)
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(Vec4f(toFloat(x), toFloat(y), toFloat(z), toFloat(w)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(toFloat(x)) { u.x }
      expectResult(toFloat(y)) { u.y }
      expectResult(toFloat(z)) { u.z }

      u = ConstVec3(ConstVec3(toDouble(x), toDouble(y), toDouble(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(x, Vec2(toDouble(y), toDouble(z)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(Vec2(toDouble(x), toDouble(y)), z)
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }

      u = ConstVec3(Vec4(toDouble(x), toDouble(y), toDouble(z), toDouble(w)))
      expectResult(classOf[ConstVec3]) { u.getClass }
      expectResult(x) { u.x }
      expectResult(y) { u.y }
      expectResult(z) { u.z }
    }

    test(2, 3, 4, 5)
    val eps = 1e-15
    test(2 + eps, 3 + eps, 4 + eps, 5 + eps)
  }

  test("Boolean factories") {
    BooleanCombinations.test { (x, y, z, w) =>
      var u: ReadVec3 = Vec3(Vec3b(x, y, z))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toDouble(x)) { u.x }
      expectResult(toDouble(y)) { u.y }
      expectResult(toDouble(z)) { u.z }

      u = Vec3(toDouble(x), Vec2b(y, z))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toDouble(x)) { u.x }
      expectResult(toDouble(y)) { u.y }
      expectResult(toDouble(z)) { u.z }

      u = Vec3(Vec2b(x, y), toDouble(z))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toDouble(x)) { u.x }
      expectResult(toDouble(y)) { u.y }
      expectResult(toDouble(z)) { u.z }

      u = Vec3(Vec4b(x, y, z, w))
      expectResult(classOf[Vec3]) { u.getClass }
      expectResult(toDouble(x)) { u.x }
      expectResult(toDouble(y)) { u.y }
      expectResult(toDouble(z)) { u.z }

      var c: ReadVec3 = ConstVec3(Vec3b(x, y, z))
      expectResult(classOf[ConstVec3]) { c.getClass }
      expectResult(toDouble(x)) { c.x }
      expectResult(toDouble(y)) { c.y }
      expectResult(toDouble(z)) { c.z }
    }
  }

  test("Unapply") {
    val x = 1+1e-15; val y = 2+1e-15; val z = 3+1e-15
    Vec3(x, y, z) match {
      case Vec3(ux, uy, uz) =>
        if (ux != x || uy != y || uz != z)
          throw new AssertionError()
    }
    ConstVec3(x, y, z) match {
      case Vec3(ux, uy, uz) =>
        if (ux != x || uy != y || uz != z)
          throw new AssertionError()
    }
  }

  test("Const conversions") {
    val x = 1d + 1e-15
    val y = 2d + 1e-15
    val z = 3d + 1e-15

    val t: ConstVec3 = Vec3(x, y, z)
    expectResult(classOf[ConstVec3]) { t.getClass }
    assert(Vec3(x, y, z) == t)

    var c = ConstVec3(5); val v = Vec3(x, y, z)
    expectResult(classOf[Vec3]) { v.getClass }
    c = v; assert(Vec3(x, y, z) == c)
    expectResult(classOf[ConstVec3]) { c.getClass }
  }

  test("Equality methods") {
    val m = Vec3(4, 7, 9)
    val c = ConstVec3(4, 7, 9)

    assert(m == m)
    assert(m == c)
    assert(c == m)
    assert(c == c)

    assert(m.equals(c))
    assert(!m.equals(Nil))

    assert(Vec3(1, 2, 3) != Vec3(9, 2, 3))
    assert(Vec3(1, 2, 3) != Vec3(1, 9, 3))
    assert(Vec3(1, 2, 3) != Vec3(1, 2, 9))

    assert(Vec3(0) != Vec3b(false))

    assert(Vec3(1, 2, 3) == Vec3i(1, 2, 3))
    assert(Vec3(1, 2, 3) != Vec3i(9, 2, 3))
    assert(Vec3(1, 2, 3) != Vec3i(1, 9, 3))
    assert(Vec3(1, 2, 3) != Vec3i(1, 2, 9))

    assert(Vec3(1, 2, 3) == Vec3f(1, 2, 3))
    assert(Vec3(1, 2, 3) != Vec3f(9, 2, 3))
    assert(Vec3(1, 2, 3) != Vec3f(1, 9, 3))
    assert(Vec3(1, 2, 3) != Vec3f(1, 2, 9))
  }

  test("Indexed read") {
    val u = ConstVec3(3, 4, 5)

    assert(+u eq u)

    expectResult(3) { u(0) }
    expectResult(4) { u(1) }
    expectResult(5) { u(2) }

    intercept[IndexOutOfBoundsException] {
      u(3)
    }
    intercept[IndexOutOfBoundsException] {
      u(-1)
    }
  }

  test("Indexed write") {
    val u = Vec3(3, 4, 5)

    u(0) = 5
    assert(Vec3(5, 4, 5) == u)

    u(1) = 6
    assert(Vec3(5, 6, 5) == u)

    u(2) = 7
    assert(Vec3(5, 6, 7) == u)

    intercept[IndexOutOfBoundsException] {
      u(3) = 1
    }
    intercept[IndexOutOfBoundsException] {
      u(-1) = 1
    }
  }

  test("Setters") {
    val u = Vec3(0)

    u := Vec3(1, 2, 3)
    expectResult(1) { u.x }
    expectResult(2) { u.y }
    expectResult(3) { u.z }
  }

  test("Const math") {
    val u = ConstVec3(7, 8, 9)

    assert(Vec3(-7, -8, -9) == -u)

    assert(Vec3(14, 16, 18) == u*2)
    assert(Vec3(3.5, 4, 4.5) == u/2)

    assert(Vec3(9, 10, 11) == u + 2)
    assert(Vec3(5, 6, 7) == u - 2)

    val v = ConstVec3(2, 4, 3)

    assert(Vec3(9, 12, 12) == u + v)
    assert(Vec3(5, 4, 6) == u - v)
    assert(Vec3(14, 32, 27) == u*v)
    assert(Vec3(3.5, 2, 3) == u/v)

    val t = ConstVec3(2, 3, 4)

    val m2x3 = ConstMat2x3(
      2, 5, 4,
      3, 4, 8
    )
    assert(Vec2(35, 50) == t*m2x3)

    val m3 = ConstMat3(
      2, 5, 4,
      3, 4, 8,
      7, 4, 2
    )
    assert(Vec3(35, 50, 34) == t*m3)

    val m4x3 = ConstMat4x3(
      2, 5, 4,
      3, 4, 8,
      7, 4, 2,
      5, 9, 2
    )
    assert(Vec4(35, 50, 34, 45) == t*m4x3)
  }

  test("Mutable math") {
    val u = Vec3(0)
    val i = ConstVec3(2, 3, 4)

    u := i; u *= 2; assert(Vec3(4, 6, 8) == u)
    u := i; u /= 2; assert(Vec3(1, 1.5, 2) == u)

    u := i; u += 2; assert(Vec3(4, 5, 6) == u)
    u := i; u -= 2; assert(Vec3(0, 1, 2) == u)

    u := i; u += Vec3(3, 4, 5); assert(Vec3(5, 7, 9) == u)
    u := i; u += u; assert(Vec3(4, 6, 8) == u)
    u := i; u -= Vec3(2, 3, 4); assert(Vec3(0, 0, 0) == u)
    u := i; u -= u; assert(Vec3(0, 0, 0) == u)

    u := i; u *= Vec3(5, 10, 15); assert(Vec3(10, 30, 60) == u)
    u := i; u *= u; assert(Vec3(4, 9, 16) == u)
    u := i; u /= Vec3(2, 6, 2); assert(Vec3(1, 0.5, 2) == u)
    u := i; u /= u; assert(Vec3(1, 1, 1) == u)

    u := i
    val m3 = ConstMat3(
      2, 5, 4,
      3, 4, 8,
      7, 4, 2
    )
    u *= m3
    assert(Vec3(35, 50, 34) == u)
  }

}
