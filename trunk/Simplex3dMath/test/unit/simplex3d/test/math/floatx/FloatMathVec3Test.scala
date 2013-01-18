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

package simplex3d.test.math.floatx

import org.scalatest._

import simplex3d.math._
import simplex3d.math.float.{functions => fn, _}
import simplex3d.math.float.functions._
import scala.Float.{
  NaN => nan,
  PositiveInfinity => posinf,
  NegativeInfinity => neginf
}


/**
 * @author Aleksey Nikiforov (lex)
 */
class FloatMathVec3Test extends FunSuite {

  val random = new java.util.Random
  import random._
  def randomFloat = random.nextFloat

  private final def isposinf(u: inVec3) :Vec3b = {
    Vec3b(fn.isposinf(u.x), fn.isposinf(u.y), fn.isposinf(u.z))
  }
  private final def isneginf(u: inVec3) :Vec3b = {
    Vec3b(fn.isneginf(u.x), fn.isneginf(u.y), fn.isneginf(u.z))
  }

  test("Vec3f numeric functions") {
    {
      val u = Vec3(0)
      val i = Vec3(0)

      u := modf(Vec3(1.25f, 2.125f, 3.5f), i)
      assert(Vec3(0.25f, 0.125f, 0.5f) == u)
      assert(Vec3(1, 2, 3) == i)

      u := modf(Vec3(-1.25f, -2.125f, -3.5f), i)
      assert(Vec3(-0.25f, -0.125f, -0.5f) == u)
      assert(Vec3(-1, -2, -3) == i)

      u := modf(Vec3(nan, nan, nan), i)
      assert(all(isnan(u)))
      assert(all(isnan(i)))

      u := modf(Vec3(posinf, posinf, posinf), i)
      assert(all(isnan(u)))
      assert(all(isposinf(i)))

      u := modf(Vec3(neginf, neginf, neginf), i)
      assert(all(isnan(u)))
      assert(all(isneginf(i)))
    }

    assert(sqrt(3) == length(Vec3(1, 1, 1)))
    assert(approxEqual(5*sqrt(2), length(Vec3(-3, -4, -5)), 1e-6f))

    assert(2 == distance(Vec3(1, 1, 1), Vec3(3, 1, 1)))

    assert(26 == dot(Vec3(1, 2, 3), Vec3(3, 4, 5)))

    assert(Vec3.UnitZ == cross(Vec3.UnitX, Vec3.UnitY))
    assert(Vec3(-3, 6, -3) == cross(Vec3(1, 2, 3), Vec3(4, 5, 6)))

    assert(Vec3(1/sqrt(3), 1/sqrt(3), 1/sqrt(3)) == normalize(Vec3(1, 1, 1)))
    assert(Vec3(-1/sqrt(3), -1/sqrt(3), -1/sqrt(3)) == normalize(Vec3(-1, -1, -1)))

    assert(Vec3(-2) == faceforward(Vec3(2), Vec3(0, 1, 0), Vec3.UnitX))
    assert(Vec3(-2) == faceforward(Vec3(2), Vec3(1, 0, 0), Vec3.UnitX))
    assert(Vec3(-2) == faceforward(Vec3(2), Vec3(1, 1, 1), Vec3.UnitX))
    assert(Vec3(2) == faceforward(Vec3(2), Vec3(-1, -1, -1), Vec3.UnitX))

    assert(Vec3(2, -2, 2) == reflect(Vec3(2, 2, 2), Vec3(0, 1, 0)))
    assert(Vec3(-2, 2, -2) == reflect(Vec3(-2, -2, -2), Vec3(0, 1, 0)))

    assert(approxEqual(
        Vec3(0.3f, -0.9539392f, 0),
        refract(
          Vec3(1, 0, 0),
          Vec3(0, 1, 0),
          0.3f
        ),
        1e-6f
    ))

    assert(approxEqual(
        Vec3(0.17320508f, -0.9695359f, 0.17320508f),
        refract(
          normalize(Vec3(1, 1, 1)),
          Vec3(0, 1, 0),
          0.3f
        ),
        1e-6f
    ))
  }
  
  test("Vec3f forward functions") {
    // test functions agnostic to range
    def testRange(
      x: Float, y: Float, z: Float,
      r: Float, g: Float, b: Float,
      s: Float, t: Float, p: Float
    ) {
      expectResult(Vec3(radians(x), radians(y), radians(z))) {
        radians(Vec3(x, y, z))
      }
      expectResult(Vec3(degrees(x), degrees(y), degrees(z))) {
        degrees(Vec3(x, y, z))
      }

      expectResult(Vec3(sin(x), sin(y), sin(z))) { sin(Vec3(x, y, z)) }
      expectResult(Vec3(cos(x), cos(y), cos(z))) { cos(Vec3(x, y, z)) }
      expectResult(Vec3(tan(x), tan(y), tan(z))) { tan(Vec3(x, y, z)) }

      expectResult(Vec3(atan(x, r), atan(y, g), atan(z, b))) {
        atan(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3(atan(x), atan(y), atan(z))) { atan(Vec3(x, y, z)) }

      expectResult(Vec3(sinh(x), sinh(y), sinh(z))) { sinh(Vec3(x, y, z)) }
      expectResult(Vec3(cosh(x), cosh(y), cosh(z))) { cosh(Vec3(x, y, z)) }
      expectResult(Vec3(tanh(x), tanh(y), tanh(z))) { tanh(Vec3(x, y, z)) }

      expectResult(Vec3(asinh(x), asinh(y), asinh(z))) { asinh(Vec3(x, y, z)) }

      expectResult(Vec3(exp(x), exp(y), exp(z))) { exp(Vec3(x, y, z)) }
      expectResult(Vec3(exp2(x), exp2(y), exp2(z))) { exp2(Vec3(x, y, z)) }

      expectResult(Vec3(abs(x), abs(y), abs(z))) { abs(Vec3(x, y, z)) }
      expectResult(Vec3(sign(x), sign(y), sign(z))) { sign(Vec3(x, y, z)) }
      expectResult(Vec3(floor(x), floor(y), floor(z))) { floor(Vec3(x, y, z)) }
      expectResult(Vec3(trunc(x), trunc(y), trunc(z))) { trunc(Vec3(x, y, z)) }
      expectResult(Vec3(round(x), round(y), round(z))) { round(Vec3(x, y, z)) }
      expectResult(Vec3(roundEven(x), roundEven(y), roundEven(z))) {
        roundEven(Vec3(x, y, z))
      }
      expectResult(Vec3(ceil(x), ceil(y), ceil(z))) { ceil(Vec3(x, y, z)) }
      expectResult(Vec3(fract(x), fract(y), fract(z))) { fract(Vec3(x, y, z)) }
      expectResult(Vec3(mod(x, s), mod(y, s), mod(z, s))) {
        mod(Vec3(x, y, z), s)
      }
      expectResult(Vec3(mod(x, r), mod(y, g), mod(z, b))) {
        mod(Vec3(x, y, z), Vec3(r, g, b))
      }

      expectResult(Vec3(min(x, s), min(y, s), min(z, s))) {
        min(Vec3(x, y, z), s)
      }
      expectResult(Vec3(min(x, r), min(y, g), min(z, b))) {
        min(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3(max(x, s), max(y, s), max(z, s))) {
        max(Vec3(x, y, z), s)
      }
      expectResult(Vec3(max(x, r), max(y, g), max(z, b))) {
        max(Vec3(x, y, z), Vec3(r, g, b))
      }


      def customExpect(u: inVec3)(f: => Vec3) {
        val v = f
        for (i <- 0 until 3) {
          if (isnan(u(i))) assert(isnan(v(i)))
          else assert(u(i) == v(i))
        }
      }

      customExpect(Vec3(clamp(x, s, t), clamp(y, s, t), clamp(z, s, t))) {
        clamp(Vec3(x, y, z), s, t)
      }
      customExpect(Vec3(clamp(x, r, s), clamp(y, g, t), clamp(z, b, p))) {
        clamp(Vec3(x, y, z), Vec3(r, g, b), Vec3(s, t, p))
      }

      expectResult(Vec3(mix(x, r, s), mix(y, g, s), mix(z, b, s))) {
        mix(Vec3(x, y, z), Vec3(r, g, b), s)
      }
      expectResult(Vec3(mix(x, r, s), mix(y, g, t), mix(z, b, p))) {
        mix(Vec3(x, y, z), Vec3(r, g, b), Vec3(s, t, p))
      }
      val bool1 = s > 0; val bool2 = t > 0; val bool3 = p > 0
      expectResult(Vec3(
          mix(x, r, toFloat(bool1)),
          mix(y, g, toFloat(bool2)),
          mix(z, b, toFloat(bool3)))
      ) {
        mix(Vec3(x, y, z), Vec3(r, g, b), Vec3b(bool1, bool2, bool3))
      }

      expectResult(Vec3(step(s, x), step(s, y), step(s, z))) {
        step(s, Vec3(x, y, z))
      }
      expectResult(Vec3(step(x, r), step(y, g), step(z, b))) {
        step(Vec3(x, y, z), Vec3(r, g, b))
      }
      
      customExpect(Vec3(
          smoothstep(s, t, x),
          smoothstep(s, t, y),
          smoothstep(s, t, z))
      ) {
        smoothstep(s, t, Vec3(x, y, z))
      }
      customExpect(Vec3(
          smoothstep(x, r, s),
          smoothstep(y, g, t),
          smoothstep(z, b, p))
      ) {
        smoothstep(Vec3(x, y, z), Vec3(r, g, b), Vec3(s, t, p))
      }
    }
    setSeed(1)
    def floatRange = (randomFloat*2 - 1)*1000
    for (i <- 0 until 1000) {
      testRange(
        floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange
      )
    }

    // range -1 to 1
    def test1(x: Float, y: Float, z: Float) {
      expectResult(Vec3(asin(x), asin(y), asin(z))) { asin(Vec3(x, y, z)) }
      expectResult(Vec3(acos(x), acos(y), acos(z))) { acos(Vec3(x, y, z)) }

      expectResult(Vec3(atanh(x), atanh(y), atanh(z))) { atanh(Vec3(x, y, z)) }
    }
    setSeed(1)
    def float1 = (randomFloat*2 - 1)
    for (i <- 0 until 1000) {
      test1(float1, float1, float1)
    }

    // test positive
    def testPositive(x: Float, y: Float, z: Float,
             r: Float, g: Float, b: Float)
    {
      expectResult(Vec3(acosh(x + 1), acosh(y + 1), acosh(z + 1))) {
        acosh(Vec3(x + 1, y + 1, z + 1))
      }

      expectResult(Vec3(pow(x, r), pow(y, g), pow(z, b))) {
        pow(Vec3(x, y, z), Vec3(r, g, b))
      }

      expectResult(Vec3(log(x), log(y), log(z))) { log(Vec3(x, y, z)) }
      expectResult(Vec3(log2(x), log2(y), log2(z))) { log2(Vec3(x, y, z)) }

      expectResult(Vec3(sqrt(x), sqrt(y), sqrt(z))) { sqrt(Vec3(x, y, z)) }
      expectResult(Vec3(inversesqrt(x), inversesqrt(y), inversesqrt(z))) {
        inversesqrt(Vec3(x, y, z))
      }
    }
    setSeed(1)
    def floatPos = randomFloat*1000
    for (i <- 0 until 1000) {
      testPositive(
        floatPos, floatPos, floatPos,
        floatPos, floatPos, floatPos
      )
    }
  }

  test("Vec3f boolean functions") {
    def test(x: Float, y: Float, z: Float, r: Float, g: Float, b: Float) {
      expectResult(Vec3b(x < r, y < g, z < b)) {
        lessThan(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3b(x <= r, y <= g, z <= b)) {
        lessThanEqual(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3b(x > r, y > g, z > b)) {
        greaterThan(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3b(x >= r, y >= g, z >= b)) {
        greaterThanEqual(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3b(x == r, y == g, z == b)) {
        equal(Vec3(x, y, z), Vec3(r, g, b))
      }
      expectResult(Vec3b(x != r, y != g, z != b)) {
        notEqual(Vec3(x, y, z), Vec3(r, g, b))
      }
    }

    setSeed(1)
    def floatRange = (randomFloat*2 - 1)*1000
    for (i <- 0 until 1000) {
      test(
        floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange
      )
    }
  }
}
