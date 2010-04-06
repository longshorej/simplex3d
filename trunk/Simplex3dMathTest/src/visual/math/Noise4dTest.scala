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

package visual.math

import visual.math.draw._

import simplex3d.math.doublem.renamed._
import simplex3d.math.doublem.DoubleMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
object Noise4dTest {

  def main(args: Array[String]) {
    val scale = 1.0/50
    val noiseSpeed = 1.0/3
    val scrollSpeed = 10

    FunFrame.launch(new Fun {
    final def apply(pixel: AnyVec2, t: Double) = {
      val p = pixel + t*scrollSpeed
      val s = t*noiseSpeed
      Vec3((noise1(Vec4(p*scale, s, s)) + 1)/2)
    }})
  }
}