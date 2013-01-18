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
class FloatMathVec4Test extends FunSuite {

  val random = new java.util.Random
  import random._
  def randomFloat = random.nextFloat

  private final def isposinf(u: inVec4) :Vec4b = {
    Vec4b(fn.isposinf(u.x), fn.isposinf(u.y), fn.isposinf(u.z), fn.isposinf(u.w))
  }
  private final def isneginf(u: inVec4) :Vec4b = {
    Vec4b(fn.isneginf(u.x), fn.isneginf(u.y), fn.isneginf(u.z), fn.isneginf(u.w))
  }
  
  test("Vec4f numeric functions") {
    {
      val u = Vec4(0)
      val i = Vec4(0)

      u := modf(Vec4(1.25f, 2.125f, 3.5f, 4.75f), i)
      assert(Vec4(0.25f, 0.125f, 0.5f, 0.75f) == u)
      assert(Vec4(1, 2, 3, 4) == i)

      u := modf(Vec4(-1.25f, -2.125f, -3.5f, -4.75f), i)
      assert(Vec4(-0.25f, -0.125f, -0.5f, -0.75f) == u)
      assert(Vec4(-1, -2, -3, -4) == i)

      u := modf(Vec4(nan, nan, nan, nan), i)
      assert(all(isnan(u)))
      assert(all(isnan(i)))

      u := modf(Vec4(posinf, posinf, posinf, posinf), i)
      assert(all(isnan(u)))
      assert(all(isposinf(i)))

      u := modf(Vec4(neginf, neginf, neginf, neginf), i)
      assert(all(isnan(u)))
      assert(all(isneginf(i)))
    }

    assert(2 == length(Vec4(1, 1, 1, 1)))
    assert(approxEqual(sqrt(54), length(Vec4(-2, -3, -4, -5)), 1e-6f))

    assert(2 == distance(Vec4(1, 1, 1, 1), Vec4(3, 1, 1, 1)))

    assert(50 == dot(Vec4(1, 2, 3, 4), Vec4(3, 4, 5, 6)))

    assert(Vec4(0.5f, 0.5f, 0.5f, 0.5f) == normalize(Vec4(1, 1, 1, 1)))
    assert(Vec4(-0.5f, -0.5f, -0.5f, -0.5f) == normalize(Vec4(-1, -1, -1, -1)))

    assert(Vec4(-2) == faceforward(Vec4(2), Vec4(0, 1, 0, 0), Vec4.UnitX))
    assert(Vec4(-2) == faceforward(Vec4(2), Vec4(1, 0, 0, 0), Vec4.UnitX))
    assert(Vec4(-2) == faceforward(Vec4(2), Vec4(1), Vec4.UnitX))
    assert(Vec4(2) == faceforward(Vec4(2), Vec4(-1), Vec4.UnitX))

    assert(Vec4(2, -2, 2, 2) == reflect(Vec4(2), Vec4(0, 1, 0, 0)))
    assert(Vec4(-2, 2, -2, -2) == reflect(Vec4(-2), Vec4(0, 1, 0, 0)))

    assert(approxEqual(
        Vec4(0.3f, -0.9539392f, 0, 0),
        refract(
          Vec4(1, 0, 0, 0),
          Vec4(0, 1, 0, 0),
          0.3f
        ),
        1e-6f
    ))

    assert(approxEqual(
        Vec4(0.15f, -0.96566045f, 0.15f, 0.15f),
        refract(
          normalize(Vec4(1, 1, 1, 1)),
          Vec4(0, 1, 0, 0),
          0.3f
        ),
        1e-6f
    ))
  }

  test("Vec4f forward functions") {
    // test functions agnostic to range
    def testRange(
      x: Float, y: Float, z: Float, w: Float,
      r: Float, g: Float, b: Float, a: Float,
      s: Float, t: Float, p: Float, q: Float
    ) {
      expectResult(Vec4(radians(x), radians(y), radians(z), radians(w))) {
        radians(Vec4(x, y, z, w))
      }
      expectResult(Vec4(degrees(x), degrees(y), degrees(z), degrees(w))) {
        degrees(Vec4(x, y, z, w))
      }

      expectResult(Vec4(sin(x), sin(y), sin(z), sin(w))) {
        sin(Vec4(x, y, z, w))
      }
      expectResult(Vec4(cos(x), cos(y), cos(z), cos(w))) {
        cos(Vec4(x, y, z, w))
      }
      expectResult(Vec4(tan(x), tan(y), tan(z), tan(w))) {
        tan(Vec4(x, y, z, w))
      }

      expectResult(Vec4(atan(x, r), atan(y, g), atan(z, b), atan(w, a))) {
        atan(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4(atan(x), atan(y), atan(z), atan(w))) {
        atan(Vec4(x, y, z, w))
      }

      expectResult(Vec4(sinh(x), sinh(y), sinh(z), sinh(w))) {
        sinh(Vec4(x, y, z, w))
      }
      expectResult(Vec4(cosh(x), cosh(y), cosh(z), cosh(w))) {
        cosh(Vec4(x, y, z, w))
      }
      expectResult(Vec4(tanh(x), tanh(y), tanh(z), tanh(w))) {
        tanh(Vec4(x, y, z, w))
      }

      expectResult(Vec4(asinh(x), asinh(y), asinh(z), asinh(w))) {
        asinh(Vec4(x, y, z, w))
      }

      expectResult(Vec4(exp(x), exp(y), exp(z), exp(w))) {
        exp(Vec4(x, y, z, w))
      }
      expectResult(Vec4(exp2(x), exp2(y), exp2(z), exp2(w))) {
        exp2(Vec4(x, y, z, w))
      }

      expectResult(Vec4(abs(x), abs(y), abs(z), abs(w))) {
        abs(Vec4(x, y, z, w))
      }
      expectResult(Vec4(sign(x), sign(y), sign(z), sign(w))) {
        sign(Vec4(x, y, z, w))
      }
      expectResult(Vec4(floor(x), floor(y), floor(z), floor(w))) {
        floor(Vec4(x, y, z, w))
      }
      expectResult(Vec4(trunc(x), trunc(y), trunc(z), trunc(w))) {
        trunc(Vec4(x, y, z, w))
      }
      expectResult(Vec4(round(x), round(y), round(z), round(w))) {
        round(Vec4(x, y, z, w))
      }
      expectResult(Vec4(
          roundEven(x), roundEven(y),
          roundEven(z), roundEven(w)
      )) {
        roundEven(Vec4(x, y, z, w))
      }
      expectResult(Vec4(ceil(x), ceil(y), ceil(z), ceil(w))) {
        ceil(Vec4(x, y, z, w))
      }
      expectResult(Vec4(fract(x), fract(y), fract(z), fract(w))) {
        fract(Vec4(x, y, z, w))
      }
      expectResult(Vec4(mod(x, s), mod(y, s), mod(z, s), mod(w, s))) {
        mod(Vec4(x, y, z, w), s)
      }
      expectResult(Vec4(mod(x, r), mod(y, g), mod(z, b), mod(w, a))) {
        mod(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }

      expectResult(Vec4(min(x, s), min(y, s), min(z, s), min(w, s))) {
        min(Vec4(x, y, z, w), s)
      }
      expectResult(Vec4(min(x, r), min(y, g), min(z, b), min(w, a))) {
        min(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4(max(x, s), max(y, s), max(z, s), max(w, s))) {
        max(Vec4(x, y, z, w), s)
      }
      expectResult(Vec4(max(x, r), max(y, g), max(z, b), max(w, a))) {
        max(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }


      def customExpect(u: inVec4)(f: => Vec4) {
        val v = f
        for (i <- 0 until 4) {
          if (isnan(u(i))) assert(isnan(v(i)))
          else assert(u(i) == v(i))
        }
      }

      customExpect(Vec4(clamp(x, s, t), clamp(y, s, t), clamp(z, s, t), clamp(w, s, t))) {
        clamp(Vec4(x, y, z, w), s, t)
      }
      customExpect(Vec4(clamp(x, r, s), clamp(y, g, t), clamp(z, b, p), clamp(w, a, q))) {
        clamp(Vec4(x, y, z, w), Vec4(r, g, b, a), Vec4(s, t, p, q))
      }

      expectResult(Vec4(mix(x, r, s), mix(y, g, s), mix(z, b, s), mix(w, a, s)))
      {
        mix(Vec4(x, y, z, w), Vec4(r, g, b, a), s)
      }
      expectResult(Vec4(mix(x, r, s), mix(y, g, t), mix(z, b, p), mix(w, a, q)))
      {
        mix(Vec4(x, y, z, w), Vec4(r, g, b, a), Vec4(s, t, p, q))
      }
      val bool1 = s > 0; val bool2 = t > 0
      val bool3 = p > 0; val bool4 = q > 0
      expectResult(Vec4(
          mix(x, r, toFloat(bool1)),
          mix(y, g, toFloat(bool2)),
          mix(z, b, toFloat(bool3)),
          mix(w, a, toFloat(bool4))
      )) {
        mix(
          Vec4(x, y, z, w),
          Vec4(r, g, b, a),
          Vec4b(bool1, bool2, bool3, bool4)
        )
      }

      expectResult(Vec4(step(s, x), step(s, y), step(s, z), step(s, w))) {
        step(s, Vec4(x, y, z, w))
      }
      expectResult(Vec4(step(x, r), step(y, g), step(z, b), step(w, a))) {
        step(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      
      customExpect(Vec4(
          smoothstep(s, t, x), smoothstep(s, t, y),
          smoothstep(s, t, z), smoothstep(s, t, w)
      )) {
        smoothstep(s, t, Vec4(x, y, z, w))
      }
      customExpect(Vec4(
          smoothstep(x, r, s), smoothstep(y, g, t),
          smoothstep(z, b, p), smoothstep(w, a, q)
      )) {
        smoothstep(Vec4(x, y, z, w), Vec4(r, g, b, a), Vec4(s, t, p, q))
      }
    }
    setSeed(1)
    def floatRange = (randomFloat*2 - 1)*1000
    for (i <- 0 until 1000) {
      testRange(
        floatRange, floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange, floatRange
      )
    }

    // range -1 to 1
    def test1(x: Float, y: Float, z: Float, w: Float) {
      expectResult(Vec4(asin(x), asin(y), asin(z), asin(w))) {
        asin(Vec4(x, y, z, w))
      }
      expectResult(Vec4(acos(x), acos(y), acos(z), acos(w))) {
        acos(Vec4(x, y, z, w))
      }

      expectResult(Vec4(atanh(x), atanh(y), atanh(z), atanh(w))) {
        atanh(Vec4(x, y, z, w))
      }
    }
    setSeed(1)
    def float1 = (randomFloat*2 - 1)
    for (i <- 0 until 1000) {
      test1(float1, float1, float1, float1)
    }

    // test positive
    def testPositive(
      x: Float, y: Float, z: Float, w: Float,
      r: Float, g: Float, b: Float, a: Float
    ) {
      expectResult(Vec4(acosh(x + 1), acosh(y + 1), acosh(z + 1), acosh(w + 1)))
      {
        acosh(Vec4(x + 1, y + 1, z + 1, w + 1))
      }

      expectResult(Vec4(pow(x, r), pow(y, g), pow(z, b), pow(w, a))) {
        pow(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }

      expectResult(Vec4(log(x), log(y), log(z), log(w))) {
        log(Vec4(x, y, z, w))
      }
      expectResult(Vec4(log2(x), log2(y), log2(z), log2(w))) {
        log2(Vec4(x, y, z, w))
      }

      expectResult(Vec4(sqrt(x), sqrt(y), sqrt(z), sqrt(w))) {
        sqrt(Vec4(x, y, z, w))
      }
      expectResult(Vec4(
          inversesqrt(x), inversesqrt(y),
          inversesqrt(z), inversesqrt(w)
      )) {
        inversesqrt(Vec4(x, y, z, w))
      }
    }
    setSeed(1)
    def floatPos = randomFloat*1000
    for (i <- 0 until 1000) {
      testPositive(
        floatPos, floatPos, floatPos, floatPos,
        floatPos, floatPos, floatPos, floatPos
      )
    }
  }

  test("Vec4f boolean functions") {
    def test(
      x: Float, y: Float, z: Float, w: Float,
      r: Float, g: Float, b: Float, a: Float
    ) {
      expectResult(Vec4b(x < r, y < g, z < b, w < a)) {
        lessThan(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4b(x <= r, y <= g, z <= b, w <= a)) {
        lessThanEqual(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4b(x > r, y > g, z > b, w > a)) {
        greaterThan(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4b(x >= r, y >= g, z >= b, w >= a)) {
        greaterThanEqual(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4b(x == r, y == g, z == b, w == a)) {
        equal(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
      expectResult(Vec4b(x != r, y != g, z != b, w != a)) {
        notEqual(Vec4(x, y, z, w), Vec4(r, g, b, a))
      }
    }

    setSeed(1)
    def floatRange = (randomFloat*2 - 1)*1000
    for (i <- 0 until 1000) {
      test(
        floatRange, floatRange, floatRange, floatRange,
        floatRange, floatRange, floatRange, floatRange
      )
    }
  }
}
