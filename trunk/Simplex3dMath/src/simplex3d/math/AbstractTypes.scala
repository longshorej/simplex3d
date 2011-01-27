/*
 * Simplex3d, CoreMath module
 * Copyright (C) 2009-2011, Simplex3d Team
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

package simplex3d.math


/** <code>AnyQuat4</code> is a superclass of all the quaternions.
 * <p>
 *   There are double and float quaternions.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyQuat4[P] private[math] () {
  private[math] def fa: Float
  private[math] def fb: Float
  private[math] def fc: Float
  private[math] def fd: Float

  private[math] def da: Double
  private[math] def db: Double
  private[math] def dc: Double
  private[math] def dd: Double

  override def clone() = this
}

/** <code>ReadMat</code> is a superclass of all the matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat[P] private[math] () {
  private[math] def f00: Float
  private[math] def f10: Float
  private[math] def f20: Float = 0
  private[math] def f30: Float = 0

  private[math] def f01: Float
  private[math] def f11: Float
  private[math] def f21: Float = 0
  private[math] def f31: Float = 0

  private[math] def f02: Float = 0
  private[math] def f12: Float = 0
  private[math] def f22: Float = 1
  private[math] def f32: Float = 0

  private[math] def f03: Float = 0
  private[math] def f13: Float = 0
  private[math] def f23: Float = 0
  private[math] def f33: Float = 1


  private[math] def d00: Double
  private[math] def d10: Double
  private[math] def d20: Double = 0
  private[math] def d30: Double = 0

  private[math] def d01: Double
  private[math] def d11: Double
  private[math] def d21: Double = 0
  private[math] def d31: Double = 0

  private[math] def d02: Double = 0
  private[math] def d12: Double = 0
  private[math] def d22: Double = 1
  private[math] def d32: Double = 0

  private[math] def d03: Double = 0
  private[math] def d13: Double = 0
  private[math] def d23: Double = 0
  private[math] def d33: Double = 1

  override def clone() = this
}

/** <code>AnyMat2</code> is a superclass of all the 2x2 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat2[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat2x3</code> is a superclass of all the 2x3 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat2x3[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat2x4</code> is a superclass of all the 2x4 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat2x4[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat3x2</code> is a superclass of all the 3x2 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat3x2[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat3</code> is a superclass of all the 3x3 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat3[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat3x4</code> is a superclass of all the 3x4 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat3x4[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat4x2</code> is a superclass of all the 4x2 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat4x2[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat4x3</code> is a superclass of all the 4x3 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat4x3[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}

/** <code>AnyMat4</code> is a superclass of all the 4x4 matrices.
 * <p>
 *   There are double and float matrices.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class AnyMat4[P] private[math] () extends AnyMat[P] {
  override def clone() = this
}
