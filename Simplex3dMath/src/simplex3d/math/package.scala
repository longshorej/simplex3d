/*
 * Simplex3d, BaseMath module
 * Copyright (C) 2010 Simplex3d Team
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

package simplex3d


/**
 * @author Aleksey Nikiforov (lex)
 */
package object math {

  // In and Out aliases

  /** <code>in</code> prefix for Vec2b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type inVec2b = AnyVec2b

  /** <code>out</code> prefix for Vec2b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type outVec2b = Vec2b with Implicits[Off]
  implicit def bmOut2(u: Vec2b) = u.asInstanceOf[outVec2b]


  /** <code>in</code> prefix for Vec3b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type inVec3b = AnyVec3b

  /** <code>out</code> prefix for Vec3b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type outVec3b = Vec3b with Implicits[Off]
  implicit def bmOut3(u: Vec3b) = u.asInstanceOf[outVec3b]


  /** <code>in</code> prefix for Vec4b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type inVec4b = AnyVec4b

  /** <code>out</code> prefix for Vec4b.
   * It is recommended to always use the prefixes when declaring functions.
   */
  type outVec4b = Vec4b with Implicits[Off]
  implicit def bmOut4(u: Vec4b) = u.asInstanceOf[outVec4b]
}
