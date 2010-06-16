/*
 * Simplex3d, BaseBuffer module
 * Copyright (C) 2010, Simplex3d Team
 *
 * This file is part of Simplex3dBuffer.
 *
 * Simplex3dBuffer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dBuffer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.buffer

import simplex3d.math._


/**
 * @author Aleksey Nikiforov (lex)
 */
trait ReadOnlyDataSeq[E <: ElemType, +R <: RawType]
extends ReadOnlyBaseSeq[E, E#Element, R]

trait DataSeq[E <: ElemType, +R <: RawType]
extends BaseSeq[E, E#Element, R] with ReadOnlyDataSeq[E, R]

object DataSeq {
  def apply[E <: ElemType, R <: ReadableType](
    implicit ref: FactoryRef[E, R]
  ) :DataSeq[E, R] = {
    ref.factory
  }
}

trait ReadOnlyContiguousSeq[E <: ElemType, +R <: RawType]
extends ReadOnlyDataSeq[E, R] {
  assert(offset == 0)
  assert(stride == components)
}

trait ContiguousSeq[E <: ElemType, +R <: RawType]
extends DataSeq[E, R] with ReadOnlyContiguousSeq[E, R]
