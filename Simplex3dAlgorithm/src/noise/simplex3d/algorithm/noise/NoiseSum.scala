/*
 * Simplex3dAlgorithm - Noise Module
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dAlgorithm.
 *
 * Simplex3dAlgorithm is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dAlgorithm is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.algorithm.noise

import java.io._
import simplex3d.math._
import simplex3d.math.doublex._
import simplex3d.math.doublex.functions.{abs, pow}


/** Noise sum.
 *
 * @author Aleksey Nikiforov (lex)
 */
@SerialVersionUID(8104346712419693669L)
final class NoiseSum(
  val source: NoiseSource,
  val frequency: Double,
  val octaves: Int,
  val lacunarity: Double = 2.0,
  val persistence: Double = 0.5
) extends NoiseGen with Serializable {
  
  final def seed: Long = source.seed
  def reseed(seed: Long) = new NoiseSum(source.reseed(seed), frequency, octaves, lacunarity, persistence)
  
  @transient private[this] var frequencyFactors: Array[Double] = _
  @transient private[this] var amplitudeFactors: Array[Double] = _
  initTransient()

  private[this] def initTransient() {
    frequencyFactors = new Array[Double](octaves)

    var i = 0; while (i < octaves) {
      frequencyFactors(i) = pow(lacunarity, i)*frequency
      i += 1
    }


    amplitudeFactors = new Array[Double](octaves)

    i = 0; while (i < octaves) {
      amplitudeFactors(i) = pow(persistence, i)
      i += 1
    }
  }

  
  def apply(x: Double) :Double = {
    var sum = 0.0; var i = 0; while (i < octaves) {
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(x*f + (i << 4))*a

      i += 1
    }

    sum
  }
  def apply(x: Double, y: Double) :Double = {
    var sum = 0.0; var i = 0; while (i < octaves) {
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(x*f + (i << 4), y*f)*a

      i += 1
    }

    sum
  }
  def apply(x: Double, y: Double, z:Double) :Double = {
    var sum = 0.0; var i = 0; while (i < octaves) {
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(x*f + (i << 4), y*f, z*f)*a

      i += 1
    }

    sum
  }
  def apply(x: Double, y: Double, z:Double, w:Double) :Double = {
    var sum = 0.0; var i = 0; while (i < octaves) {
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(x*f + (i << 4), y*f, z*f, w*f)*a

      i += 1
    }

    sum
  }


  @throws(classOf[IOException])
  private[this] def writeObject(out: ObjectOutputStream) {
    out.defaultWriteObject()
  }

  @throws(classOf[IOException]) @throws(classOf[ClassNotFoundException])
  private[this] def readObject(in: ObjectInputStream) {
    in.defaultReadObject()
    initTransient()
  }
}
