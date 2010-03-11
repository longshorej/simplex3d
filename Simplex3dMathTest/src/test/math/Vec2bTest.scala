/*
 * Simplex3d, MathTest package
 * Copyright (C) 2009-2010 Simplex3d Team
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
import simplex3d.math.BaseMath._
import simplex3d.math.intm._
import simplex3d.math.floatm._
import simplex3d.math.doublem._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Vec2bTest extends FunSuite {

  test("Factories") {
    var u: AnyVec2b = Vec2b(true)
    expect(classOf[Vec2b]) { u.getClass }
    expect(true) { u.x }
    expect(true) { u.y }

    u = Vec2b(false)
    expect(classOf[Vec2b]) { u.getClass }
    expect(false) { u.x }
    expect(false) { u.y }

    BooleanCombinations.test { (x, y, z, w) =>
      var u: AnyVec2b = Vec2b(x, y)
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec2b(x, y))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec3b(x, y, z))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec4b(x, y, z, w))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec2i(int(x), int(y)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec3i(int(x), int(y), int(z)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec4i(int(x), int(y), int(z), int(w)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec2f(float(x), float(y)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec3f(float(x), float(y), float(z)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec4f(float(x), float(y), float(z), float(w)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec2d(double(x), double(y)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec3d(double(x), double(y), double(z)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      u = Vec2b(Vec4d(double(x), double(y), double(z), double(w)))
      expect(classOf[Vec2b]) { u.getClass }
      expect(x) { u.x }
      expect(y) { u.y }

      var c: AnyVec2b = ConstVec2b(x, y)
      expect(classOf[ConstVec2b]) { c.getClass }
      expect(x) { c.x }
      expect(y) { c.y }

      c = ConstVec2b(Vec2b(x, y))
      expect(classOf[ConstVec2b]) { c.getClass }
      expect(x) { c.x }
      expect(y) { c.y }

      c = ConstVec2b(Vec2i(int(x), int(y)))
      expect(classOf[ConstVec2b]) { c.getClass }
      expect(x) { c.x }
      expect(y) { c.y }

      c = ConstVec2b(Vec2f(float(x), float(y)))
      expect(classOf[ConstVec2b]) { c.getClass }
      expect(x) { c.x }
      expect(y) { c.y }

      c = ConstVec2b(Vec2d(double(x), double(y)))
      expect(classOf[ConstVec2b]) { c.getClass }
      expect(x) { c.x }
      expect(y) { c.y }
    }
  }

  test("Unapply") {
    BooleanCombinations.test { (x, y, z, w) =>
      Vec2b(x, y) match {
        case Vec2b(ux, uy) =>
          if (ux != x || uy != y) throw new AssertionError()
      }
      ConstVec2b(x, y) match {
        case Vec2b(ux, uy) =>
          if (ux != x || uy != y) throw new AssertionError()
      }
    }
  }

  test("Const conversions") {
    BooleanCombinations.test { (x, y, z, w) =>
      val t: ConstVec2b = Vec2b(x, y)
      expect(classOf[ConstVec2b]) { t.getClass }
      assert(Vec2b(x, y) == t)

      var c: ConstVec2b = Vec2b(x, y); var v = Vec2b(false)
      expect(classOf[ConstVec2b]) { c.getClass }
      v = c; assert(Vec2b(x, y) == v)
      expect(classOf[Vec2b]) { v.getClass }

      c = Vec2b(true); v = Vec2b(x, y)
      expect(classOf[Vec2b]) { v.getClass }
      c = v; assert(Vec2b(x, y) == c)
      expect(classOf[ConstVec2b]) { c.getClass }
    }
  }

  test("Equality methods") {
    BooleanCombinations.test { (x, y, z, w) =>
      val m = Vec2b(x, y)
      val c = ConstVec2b(x, y)

      assert(m == m)
      assert(m == c)
      assert(c == m)
      assert(c == c)

      assert(m.equals(c))
      assert(!m.equals(Nil))

      assert(Vec2b(x, y) != Vec2b(!x, y))
      assert(Vec2b(x, y) != Vec2b(x, !y))
    }
  }

  test("Indexed read") {
    BooleanCombinations.test { (x, y, z, w) =>
      val u = ConstVec2b(x, y)

      expect(x) { u(0) }
      expect(y) { u(1) }

      intercept[IndexOutOfBoundsException] {
        u(2)
      }
      intercept[IndexOutOfBoundsException] {
        u(-1)
      }
    }
  }

  test("Indexed write") {
    BooleanCombinations.test { (x, y, z, w) =>
      val u = Vec2b(x, y)

      u(0) = !x
      assert(Vec2b(!x, y) == u)

      u(1) = !y
      assert(Vec2b(!x, !y) == u)

      intercept[IndexOutOfBoundsException] {
        u(2) = true
      }
      intercept[IndexOutOfBoundsException] {
        u(-1) = true
      }
    }
  }

  test("Setters") {
    BooleanCombinations.test { (x, y, z, w) =>
      val u = Vec2b(!x)

      u := Vec2b(x, y)
      expect(x) { u.x }
      expect(y) { u.y }

      u.set(!x, !y)
      expect(!x) { u.x }
      expect(!y) { u.y }
    }
  }

  test("Getters") {
    BooleanCombinations.test { (x, y, z, w) =>
      val u = Vec2b(x, y)
      expect(x) { u.r }
      expect(y) { u.g }
    }
  }

  test("Collection") {
    def test(u: AnyVec2b) = {
      assert(u.head == u.x)
      assert(u.last == u.y)
      assert(u.size == 2)

      val iterator = u.iterator
      assert(iterator.hasNext)
      assert(iterator.next == u.x)
      assert(iterator.hasNext)
      assert(iterator.next == u.y)
      assert(!iterator.hasNext)
      intercept[NoSuchElementException] {
        iterator.next
      }

      var i = 0
      u.foreach { element =>
        assert(element == u(i))
        i += 1
      }
    }

    BooleanCombinations.test { (x, y, z, w) =>
      test(Vec2b(x, y))
      test(ConstVec2b(x, y))
    }
  }
}
