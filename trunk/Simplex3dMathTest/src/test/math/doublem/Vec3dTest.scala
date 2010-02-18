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

package test.math.doublem

import org.scalatest._
import test.math.BooleanCombinations

import simplex3d.math.BaseMath._
import simplex3d.math._
import simplex3d.math.intm._
import simplex3d.math.floatm._
import simplex3d.math.doublem.renamed._


/**
 * @author Aleksey Nikiforov (lex)
 */
class Vec3dTest extends FunSuite {

    test("Factories") {
        def test(x: Double, y: Double, z: Double, w: Double) {
            var u: AnyVec3 = Vec3(x)
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(x) { u.y }
            expect(x) { u.z }

            u = Vec3(x, y, z)
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(y) { u.y }
            expect(z) { u.z }

            u = Vec3(Vec3i(int(x), int(y), int(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(int(x)) { u.x }
            expect(int(y)) { u.y }
            expect(int(z)) { u.z }

            u = Vec3(x, Vec2i(int(y), int(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(int(y)) { u.y }
            expect(int(z)) { u.z }

            u = Vec3(Vec2i(int(x), int(y)), z)
            expect(classOf[Vec3]) { u.getClass }
            expect(int(x)) { u.x }
            expect(int(y)) { u.y }
            expect(z) { u.z }

            u = Vec3(Vec4i(int(x), int(y), int(z), int(w)))
            expect(classOf[Vec3]) { u.getClass }
            expect(int(x)) { u.x }
            expect(int(y)) { u.y }
            expect(int(z)) { u.z }

            u = Vec3(Vec3f(float(x), float(y), float(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(float(x)) { u.x }
            expect(float(y)) { u.y }
            expect(float(z)) { u.z }

            u = Vec3(x, Vec2f(float(y), float(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(float(y)) { u.y }
            expect(float(z)) { u.z }

            u = Vec3(Vec2f(float(x), float(y)), z)
            expect(classOf[Vec3]) { u.getClass }
            expect(float(x)) { u.x }
            expect(float(y)) { u.y }
            expect(z) { u.z }

            u = Vec3(Vec4f(float(x), float(y), float(z), float(w)))
            expect(classOf[Vec3]) { u.getClass }
            expect(float(x)) { u.x }
            expect(float(y)) { u.y }
            expect(float(z)) { u.z }

            u = Vec3(Vec3(double(x), double(y), double(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(y) { u.y }
            expect(z) { u.z }

            u = Vec3(x, Vec2(double(y), double(z)))
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(y) { u.y }
            expect(z) { u.z }

            u = Vec3(Vec2(double(x), double(y)), z)
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(y) { u.y }
            expect(z) { u.z }

            u = Vec3(Vec4(double(x), double(y), double(z), double(w)))
            expect(classOf[Vec3]) { u.getClass }
            expect(x) { u.x }
            expect(y) { u.y }
            expect(z) { u.z }

            var c: AnyVec3 = ConstVec3(x, y, z)
            expect(classOf[ConstVec3]) { c.getClass }
            expect(x) { c.x }
            expect(y) { c.y }
            expect(z) { c.z }

            c = ConstVec3(Vec3i(int(x), int(y), int(z)))
            expect(classOf[ConstVec3]) { c.getClass }
            expect(int(x)) { c.x }
            expect(int(y)) { c.y }
            expect(int(z)) { c.z }

            c = ConstVec3(Vec3f(float(x), float(y), float(z)))
            expect(classOf[ConstVec3]) { c.getClass }
            expect(float(x)) { c.x }
            expect(float(y)) { c.y }
            expect(float(z)) { c.z }

            c = ConstVec3(Vec3(double(x), double(y), double(z)))
            expect(classOf[ConstVec3]) { c.getClass }
            expect(x) { c.x }
            expect(y) { c.y }
            expect(z) { c.z }
        }

        test(2, 3, 4, 5)
        val eps = 1e-15
        test(2 + eps, 3 + eps, 4 + eps, 5 + eps)
    }

    test("Boolean factories") {
        BooleanCombinations.test { (x, y, z, w) =>
            var u: AnyVec3 = Vec3(Vec3b(x, y, z))
            expect(classOf[Vec3]) { u.getClass }
            expect(double(x)) { u.x }
            expect(double(y)) { u.y }
            expect(double(z)) { u.z }

            u = Vec3(double(x), Vec2b(y, z))
            expect(classOf[Vec3]) { u.getClass }
            expect(double(x)) { u.x }
            expect(double(y)) { u.y }
            expect(double(z)) { u.z }

            u = Vec3(Vec2b(x, y), double(z))
            expect(classOf[Vec3]) { u.getClass }
            expect(double(x)) { u.x }
            expect(double(y)) { u.y }
            expect(double(z)) { u.z }

            u = Vec3(Vec4b(x, y, z, w))
            expect(classOf[Vec3]) { u.getClass }
            expect(double(x)) { u.x }
            expect(double(y)) { u.y }
            expect(double(z)) { u.z }

            var c: AnyVec3 = ConstVec3(Vec3b(x, y, z))
            expect(classOf[ConstVec3]) { c.getClass }
            expect(double(x)) { c.x }
            expect(double(y)) { c.y }
            expect(double(z)) { c.z }
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
        expect(classOf[ConstVec3]) { t.getClass }
        assert(Vec3(x, y, z) == t)

        var c: ConstVec3 = Vec3(x, y, z); var v = Vec3(3)
        expect(classOf[ConstVec3]) { c.getClass }
        v = c; assert(Vec3(x, y, z) == v)
        expect(classOf[Vec3]) { v.getClass }

        c = Vec3(5); v = Vec3(x, y, z)
        expect(classOf[Vec3]) { v.getClass }
        c = v; assert(Vec3(x, y, z) == c)
        expect(classOf[ConstVec3]) { c.getClass }
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
    }

    test("Indexed read") {
        val u = ConstVec3(3, 4, 5)

        assert(+u eq u)

        expect(3) { u(0) }
        expect(4) { u(1) }
        expect(5) { u(2) }

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
        expect(1) { u.x }
        expect(2) { u.y }
        expect(3) { u.z }

        u.set(5, 6, 7)
        expect(5) { u.x }
        expect(6) { u.y }
        expect(7) { u.z }
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

        val m3x2 = ConstMat3x2(
            2, 5, 4,
            3, 4, 8
        )
        assert(Vec2(35, 50) == t*m3x2)

        val m3 = ConstMat3(
            2, 5, 4,
            3, 4, 8,
            7, 4, 2
        )
        assert(Vec3(35, 50, 34) == t*m3)

        val m3x4 = ConstMat3x4(
            2, 5, 4,
            3, 4, 8,
            7, 4, 2,
            5, 9, 2
        )
        assert(Vec4(35, 50, 34, 45) == t*m3x4)
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