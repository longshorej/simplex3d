/*
 * Simplex3d, CoreData module
 * Copyright (C) 2010-2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dData.
 *
 * Simplex3dData is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dData is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.data


/**
 * @author Aleksey Nikiforov (lex)
 */
trait ReadContiguous[E <: Meta, +R <: Raw]
extends ReadDataSeq[E, R] {
  assert(offset == 0)
  assert(stride == components)
}

trait Contiguous[E <: Meta, +R <: Raw]
extends DataSeq[E, R] with ReadContiguous[E, R]


object ReadContiguous {
  def apply[E <: Meta, R <: Defined](dc: ReadContiguous[_, R])(
    implicit composition: CompositionFactory[E, _ >: R], primitives: PrimitiveFactory[E#Component, R]
  ) :ReadContiguous[E, R] = {
    val res = dc match {
      case d: DataArray[_, _] => composition.mkDataArray(primitives.mkDataArray(dc.sharedArray))
      case d: DataBuffer[_, _] => composition.mkDataBuffer(primitives.mkDataBuffer(dc.sharedBuffer))
    }
    if (dc.isReadOnly) res.asReadOnly() else res
  }
}

object Contiguous {
  def apply[E <: Meta, R <: Defined](dc: Contiguous[_, R])(
    implicit composition: CompositionFactory[E, _ >: R], primitives: PrimitiveFactory[E#Component, R]
  ) :Contiguous[E, R] = {
    if (dc.isReadOnly) throw new IllegalArgumentException(
      "The Sequence must not be read-only."
    )
    dc match {
      case d: DataArray[_, _] => composition.mkDataArray(primitives.mkDataArray(dc.sharedArray))
      case d: DataBuffer[_, _] => composition.mkDataBuffer(primitives.mkDataBuffer(dc.sharedBuffer))
    }
  }
}