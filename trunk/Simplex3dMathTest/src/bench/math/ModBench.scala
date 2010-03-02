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
import simplex3d.math.floatm.FloatMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
object ModBench {
    def main(args: Array[String]) {
        new ModFloat().run()
    }
}

class ModFloat {
    val length = 10000
    val loops = 20000

    def run() {
        var start = 0L

        start = System.currentTimeMillis
        testRegular(length, loops)
        val regularTime = System.currentTimeMillis - start

        start = System.currentTimeMillis
        testAnother(length, loops)
        val anotherTime = System.currentTimeMillis - start

        println("Float. another time: " + anotherTime +
                ", regular time: " + regularTime + ".")
    }

    def modReg(x: Float, y: Float) :Float = {
        x - y*floor(x/y)
    }

    def modAnother(x: Float, y: Float) :Float = {
        if (x*y < 0) {
            y + x % y
        } else {
            x % y
        }
    }

    def testRegular(length: Int, loops: Int) {
        var answer = 0

        var l = 0; while (l < loops) {
            var i = 0; while (i < length - 1) {

                // Bench code
                answer += int(modReg(-i*123 + 0.1234f, -i + 0.2345f))

                i += 1
            }
            l += 1
        }

        println(answer)
    }

    def testAnother(length: Int, loops: Int) {
        var answer = 0

        var l = 0; while (l < loops) {
            var i = 0; while (i < length - 1) {

                // Bench code
                answer += int(modAnother(-i*123 + 0.1234f, -i + 0.2345f))

                i += 1
            }
            l += 1
        }

        println(answer)
    }
}
