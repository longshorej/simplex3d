/*
 * Simplex3d, CoreBuffer module
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

import java.nio._
import scala.annotation.unchecked._


/**
 * @author Aleksey Nikiforov (lex)
 */
@serializable @SerialVersionUID(8104346712419693669L)
trait ReadDataArray[E <: Meta, +R <: Raw]
extends ReadDataSeq[E, R] with ReadContiguous[E, R] {
  type Backing <: ReadDataArray[E#Component, R]
  type RawBuffer = Buffer
  override def asReadOnly() = readOnlySeq.asInstanceOf[ReadDataArray[E, R]]
}

trait DataArray[E <: Meta, +R <: Raw]
extends DataSeq[E, R] with Contiguous[E, R] with ReadDataArray[E, R] {
  def array: R#Array = buff.array.asInstanceOf[R#Array]
  type Backing = DataArray[E#Component, R @uncheckedVariance]
}


object ReadDataArray {
  def apply[E <: Meta, R <: Defined](da: ReadDataArray[_, R])(
    implicit composition: CompositionFactory[E, _ >: R], primitive: DataFactory[E#Component, R]
  ) :ReadDataArray[E, R] = {
    val res = composition.mkDataArray(primitive.mkDataArray(da.sharedArray))
    if (da.readOnly) res.asReadOnly() else res
  }
}

object DataArray {
  def apply[E <: Meta, R <: Defined](array: R#Array)(
    implicit composition: CompositionFactory[E, _ >: R], primitive: DataFactory[E#Component, R]
  ) :DataArray[E, R] = {
    composition.mkDataArray(primitive.mkDataArray(array))
  }

  def apply[E <: Meta, R <: Defined](size: Int)(
    implicit composition: CompositionFactory[E, _ >: R], primitive: DataFactory[E#Component, R]
  ) :DataArray[E, R] = {
    composition.mkDataArray(primitive.mkDataArray(size*composition.components))
  }

  def apply[E <: Meta, R <: Defined](vals: E#Read*)(
    implicit composition: CompositionFactory[E, _ >: R], primitive: DataFactory[E#Component, R]
  ) :DataArray[E, R] = {
    val data = composition.mkDataArray(primitive.mkDataArray(vals.size*composition.components))
    data.put(vals)
    data
  }

  def apply[E <: Meta, R <: Defined](da: DataArray[_, R])(
    implicit composition: CompositionFactory[E, _ >: R], primitive: DataFactory[E#Component, R]
  ) :DataArray[E, R] = {
    if (da.readOnly) throw new IllegalArgumentException(
      "The DataArray must not be read-only."
    )
    composition.mkDataArray(primitive.mkDataArray(da.sharedArray))
  }
}
