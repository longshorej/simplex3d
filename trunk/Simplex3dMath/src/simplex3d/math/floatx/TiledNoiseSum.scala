/*
 * Simplex3d, FloatMath module
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dMath.
 *
 * Simplex3dMath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dMath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.math.floatx

import java.io._
import simplex3d.math._
import simplex3d.math.floatx.functions._


/** Noise sum.
 *
 * @author Aleksey Nikiforov (lex)
 */
@SerialVersionUID(8104346712419693669L)
final class TiledNoiseSum(
  val tile: ConstVec4f,
  val frequency: Float,
  val octaves: Int,
  val lacunarity: Float = 2.0f,
  val persistence: Float = 0.5f,
  val source: TiledNoiseSource = NoiseDefaults.DefaultTiledSource
) extends Serializable {

  @transient private[this] var tiles: Array[ConstVec4i] = _
  @transient private[this] var frequencyFactors: Array[ConstVec4f] = _
  @transient private[this] var amplitudeFactors: Array[Float] = _
  initTransient()

  private[this] def initTransient() {
    tiles = new Array[ConstVec4i](octaves)

    var i = 0; while (i < octaves) {
      val octaveFreq = pow(lacunarity, i)*frequency

      tiles(i) = ConstVec4i(
        round(tile.x*octaveFreq/source.tileSizeXf).toInt,
        round(tile.y*octaveFreq/source.tileSizeYf).toInt,
        round(tile.z*octaveFreq/source.tileSizeZf).toInt,
        round(tile.w*octaveFreq/source.tileSizeWf).toInt
      )

      i += 1
    }


    frequencyFactors = new Array[ConstVec4f](octaves)

    i = 0; while (i < octaves) {
      val t = tiles(i)

      frequencyFactors(i) = ConstVec4f(
        (t.x*source.tileSizeXf)/tile.x,
        (t.y*source.tileSizeYf)/tile.y,
        (t.z*source.tileSizeZf)/tile.z,
        (t.w*source.tileSizeWf)/tile.w
      )

      i += 1
    }


    amplitudeFactors = new Array[Float](octaves)

    i = 0; while (i < octaves) {
      amplitudeFactors(i) = pow(persistence, i)
      i += 1
    }
  }

  
  def apply(x: Float) :Float = {
    var sum = 0.0f; var i = 0; while (i < octaves) {
      val t = tiles(i)
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(
        t.x,
        x*f.x + (i << 4)
      )*a

      i += 1
    }

    sum
  }
  def apply(u: inVec2f) :Float = {
    var sum = 0.0f; var i = 0; while (i < octaves) {
      val t = tiles(i)
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(
        t.x, t.y,
        u.x*f.x + (i << 4), u.y*f.y
      )*a

      i += 1
    }

    sum
  }
  def apply(u: inVec3f) :Float = {
    var sum = 0.0f; var i = 0; while (i < octaves) {
      val t = tiles(i)
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(
        t.x, t.y, t.z,
        u.x*f.x + (i << 4), u.y*f.y, u.z*f.z
      )*a

      i += 1
    }

    sum
  }
  def apply(u: inVec4f) :Float = {
    var sum = 0.0f; var i = 0; while (i < octaves) {
      val t = tiles(i)
      val f = frequencyFactors(i)
      val a = amplitudeFactors(i)

      sum += source(
        t.x, t.y, t.z, t.w,
        u.x*f.x + (i << 4), u.y*f.y, u.z*f.z, u.w*f.w
      )*a

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
