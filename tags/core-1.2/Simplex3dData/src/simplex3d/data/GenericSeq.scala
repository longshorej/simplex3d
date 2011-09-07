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

import java.nio._
import scala.reflect._
import scala.annotation.unchecked._
import simplex3d.math._
import simplex3d.data._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class GenericSeq[E <: CompositeMeta, +R <: Raw, B <: Defined](
  adapter: DataAdapter[E, B], prim: ReadContiguous[E#Component, R], off: Int, str: Int
) extends CompositeSeq[E, R, B](prim, off, str) {
  final def metaManifest = adapter.metaManifest
  final def readManifest = adapter.readManifest
  final def components: Int = adapter.components

  def apply(i: Int) :E#Const = adapter.apply(primitives, offset + i*stride)
  def update(i: Int, v: E#Read) { adapter.update(primitives, offset + i*stride, v) }

  def mkReadDataArray[P <: B](primitives: ReadDataArray[E#Component, P])
  :ReadDataArray[E, P] = adapter.mkReadDataArray(primitives)
  def mkReadDataBuffer[P <: B](primitives: ReadDataBuffer[E#Component, P])
  :ReadDataBuffer[E, P] = adapter.mkReadDataBuffer(primitives)
  protected def mkReadDataViewInstance[P <: B](primitives: ReadDataBuffer[E#Component, P], offset: Int, stride: Int)
  :ReadDataView[E, P] = adapter.mkReadDataViewInstance(primitives, offset, stride)

  protected[data] final override def mkSerializableInstance() = new SerializableGeneric(adapter)
}

private[data] final class SerializableGeneric(val adapter: DataAdapter[_, _]) extends SerializableComposite {
  protected def toReadDataArray(primitives: ReadDataArray[_ <: PrimitiveMeta, _]): ReadDataArray[_ <: CompositeMeta, _] = {
    type E = T forSome { type T <: CompositeMeta }
    val primitiveArray = primitives.asInstanceOf[ReadDataArray[E#Component, Defined]]
    adapter.asInstanceOf[DataAdapter[E, Defined]].mkReadDataArray(primitiveArray)
  }
}

final class GenericArray[E<: CompositeMeta, +R <: Raw, B <: Defined](
  adapter: DataAdapter[E, B], prim: ReadDataArray[E#Component, R]
) extends GenericSeq[E, R, B](adapter, prim, 0, adapter.components) with DataArray[E, R]

final class GenericBuffer[E<: CompositeMeta, +R <: Raw, B <: Defined](
  adapter: DataAdapter[E, B], prim: ReadDataBuffer[E#Component, R]
) extends GenericSeq[E, R, B](adapter, prim, 0, adapter.components) with DataBuffer[E, R]

final class GenericView[E<: CompositeMeta, +R <: Raw, B <: Defined](
  adapter: DataAdapter[E, B], prim: ReadDataBuffer[E#Component, R], off: Int, str: Int
) extends GenericSeq[E, R, B](adapter, prim, off, str) with DataView[E, R]