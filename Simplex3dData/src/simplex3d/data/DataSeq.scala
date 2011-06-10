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
trait ReadDataSeq[F <: Meta, +R <: Raw]
extends ReadAbstractData[F, F#Const, R] {
  type Read <: ReadDataSeq[F, R]
}

trait DataSeq[F <: Meta, +R <: Raw]
extends AbstractData[F, F#Const, F#Read, R] with ReadDataSeq[F, R]

object DataSeq {
  def apply[F <: Meta, R <: Defined](
    implicit composition: CompositionFactory[F, _ >: R], primitives: PrimitiveFactory[F#Component, R]
  ) :DataSeq[F, R] = {
    composition.mkDataArray(primitives.mkDataArray(0))
  }
}
