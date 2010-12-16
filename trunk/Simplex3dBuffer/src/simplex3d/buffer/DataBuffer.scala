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
trait ReadDataBuffer[E <: Meta, +R <: Raw]
extends ReadDataView[E, R] with ReadContiguous[E, R] {
  override def asReadOnly() = readOnlySeq.asInstanceOf[ReadDataBuffer[E, R]]
}

trait DataBuffer[E <: Meta, +R <: Raw]
extends DataView[E, R] with Contiguous[E, R] with ReadDataBuffer[E, R]


object ReadDataBuffer {
  def apply[E <: Meta, R <: Defined](buffer: ByteBuffer)(
    implicit factory: Factory[E, R]
  ) :ReadDataBuffer[E, R] = {
    factory.mkReadDataBuffer(buffer)
  }

  def apply[E <: Meta, R <: Defined](db: ReadDataBuffer[_, _])(
    implicit factory: Factory[E, R]
  ) :ReadDataBuffer[E, R] = {
    val res = factory.mkReadDataBuffer(db.sharedBuffer)
    if (db.readOnly) res.asReadOnly() else res
  }
}

object DataBuffer {
  def apply[E <: Meta, R <: Defined](buffer: ByteBuffer)(
    implicit factory: Factory[E, R]
  ) :DataBuffer[E, R] = {
    factory.mkDataBuffer(buffer)
  }

  def apply[E <: Meta, R <: Defined](size: Int)(
    implicit factory: Factory[E, R]
  ) :DataBuffer[E, R] = {
    factory.mkDataBuffer(size)
  }

  def apply[E <: Meta, R <: Defined](vals: E#Read*)(
    implicit factory: Factory[E, R]
  ) :DataBuffer[E, R] = {
    val data = factory.mkDataBuffer(vals.size)
    data.put(vals)
    data
  }

  def apply[E <: Meta, R <: Defined](vals: IndexedSeq[E#Read])(
    implicit factory: Factory[E, R]
  ) :DataBuffer[E, R] = {
    val data = factory.mkDataBuffer(vals.size)
    data.put(vals)
    data
  }

  def apply[E <: Meta, R <: Defined](db: DataBuffer[_, _])(
    implicit factory: Factory[E, R]
  ) :DataBuffer[E, R] = {
    if (db.readOnly) throw new IllegalArgumentException(
      "The DataBuffer must not be read-only."
    )
    factory.mkDataBuffer(db.sharedBuffer)
  }
}
