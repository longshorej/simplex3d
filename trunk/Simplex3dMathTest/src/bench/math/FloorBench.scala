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

package bench.math

import simplex3d.math.BaseMath._
import java.lang.StrictMath

/**
 * @author Aleksey Nikiforov (lex)
 */
object FloorBench {
  def main(args: Array[String]) {
    new FloorDouble().run()
  }
}

class FloorFloat {
  val length = 10000
  val loops = 20000

  def run() {
    var start = 0L

    start = System.currentTimeMillis
    testRegular(length, loops)
    val regularTime = System.currentTimeMillis - start

    start = System.currentTimeMillis
    testOptimised(length, loops)
    val optimisedTime = System.currentTimeMillis - start

    println("Float. optimised time: " + optimisedTime +
            ", regular time: " + regularTime + ".")
  }

  def floorOpt(x: Float) :Float = {
    if (x > Int.MaxValue || x < Int.MinValue) x
    else {
      val i = int(x)
      if (x > 0 || x == i) i else if(java.lang.Float.isNaN(x)) x else i - 1
    }
  }

  def testRegular(length: Int, loops: Int) {
    var answer = 0

    var l = 0; while (l < loops) {
      var i = 0; while (i < length - 1) {

        // Bench code
        answer += int(StrictMath.floor((-i + 0.12345f)*100)/23.4567f)

        i += 1
      }
      l += 1
    }

    println(answer)
  }

  def testOptimised(length: Int, loops: Int) {
    var answer = 0

    var l = 0; while (l < loops) {
      var i = 0; while (i < length - 1) {

        // Bench code
        answer += int(floorOpt((-i + 0.12345f)*100)/23.4567f)

        i += 1
      }
      l += 1
    }

    println(answer)
  }
}

class FloorDouble {
  val length = 10000
  val loops = 20000
  
  def run() {
    var start = 0L

    start = System.currentTimeMillis
    testRegular(length, loops)
    val regularTime = System.currentTimeMillis - start

    start = System.currentTimeMillis
    testOptimised(length, loops)
    val optimisedTime = System.currentTimeMillis - start

    println("Double. optimised time: " + optimisedTime +
        ", regular time: " + regularTime + ".")
  }

  def floorOpt(x: Double) :Double = {
    if (x > Long.MaxValue || x < Long.MinValue) x
    else {
      val i = long(x)
      if (x > 0 || x == i) i else if(java.lang.Double.isNaN(x)) x else i - 1
    }
  }

  def testRegular(length: Int, loops: Int) {
    var answer = 0

    var l = 0; while (l < loops) {
      var i = 0; while (i < length - 1) {

        // Bench code
        answer += int(StrictMath.floor((-i + 0.12345)*100)/23.4567)

        i += 1
      }
      l += 1
    }

    println(answer)
  }

  def testOptimised(length: Int, loops: Int) {
    var answer = 0

    var l = 0; while (l < loops) {
      var i = 0; while (i < length - 1) {

        // Bench code
        answer += int(floorOpt((-i + 0.12345)*100)/23.4567)

        i += 1
      }
      l += 1
    }

    println(answer)
  }
}