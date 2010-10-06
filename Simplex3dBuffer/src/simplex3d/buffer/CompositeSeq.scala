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

import java.nio._


/**
 * Extend this class and add implicit factories to your package object to enable constructor.
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class CompositeSeq[E <: Composite, +R <: RawData](
  backing: ContiguousSeq[E#Component, R],
  offset: Int, stride: Int, sz: java.lang.Integer
) extends BaseSeq[E, E#Immutable, E#Element, R](
  backing.sharedStore, backing, backing.isReadOnly,
  offset, stride, sz
) {
  final def componentManifest = backingSeq.elementManifest

  final def rawType = backingSeq.rawType
  final def normalized: Boolean = backingSeq.normalized
}
