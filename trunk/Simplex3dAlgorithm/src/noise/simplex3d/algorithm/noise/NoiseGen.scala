/*
 * Simplex3dAlgorithm - Noise Module
 * Copyright (C) 2012, Aleksey Nikiforov
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


/** General purpose noise generator.
 *
 * @author Aleksey Nikiforov (lex)
 */
@SerialVersionUID(8104346712419693669L)
abstract class NoiseGen extends Serializable {
  def seed: Long
  def reseed(seed: Long) :NoiseGen
  
  def apply(x: Double) :Double
  def apply(x: Double, y: Double) :Double
  def apply(x: Double, y: Double, z:Double) :Double
  def apply(x: Double, y: Double, z:Double, w:Double) :Double
}
