/*
 * Simplex3d, CoreMath module
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

package simplex3d.math

import simplex3d.math.integration._


/** 
 * @author Aleksey Nikiforov (lex)
 */
trait ReadMathRef {
  type Clone <: ReadMathRef
  override def clone() :Clone = throw new UnsupportedOperationException();
}

trait MathRef extends ReadMathRef with Mutable with Meta {
  type Clone <: MathRef
  def :=(v: Read)
  def toConst() :Const
}
