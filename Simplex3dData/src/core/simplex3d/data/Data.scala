/*
 * Simplex3dData - Core Module
 * Copyright (C) 2011, Aleksey Nikiforov
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
import scala.collection._
import simplex3d.data.extension._


/**
 * @author Aleksey Nikiforov (lex)
 */
trait ReadData[A <: Accessor] extends ReadAbstractData[A#Const] {
  type Read <: ReadData[A]
  
  type Format <: simplex3d.data.Format { type Accessor <: A }
  
  
  /** First and stride are in units of T, count is the number of strides.
   * If this sequence is a view, the result must be a view with the same offset and stride.
   * All the data within sort stride and view stride is copied to the destination.
   */
  final def reorder(
    sortContext: SortContext,
    first: Int, count: Int, stride: Int,
    dest: Data[A], destFirst: Int
  ) {
    
    if (first < 0) throw new IndexOutOfBoundsException("First = " + first + ", must be greater than or equal to 0.")
    if (count < 0) throw new IndexOutOfBoundsException("Count = " + count + ", must be greater than or equal to 0.")
    if (stride < 1) throw new IndexOutOfBoundsException("Stride = " + stride + ", must be greater than or equal to 1.")
      
    if (first + count*stride > size) {
      if (first > size) throw new IndexOutOfBoundsException(
        "First = " + first + " exceeds size = " + size + "."
      )
      else throw new BufferUnderflowException()
    }
    
    if (destFirst < 0) throw new IndexOutOfBoundsException(
      "DestFirst = " + destFirst + ", must be greater than or equal to 0."
    )
    if (destFirst + count*stride > dest.size) {
      if (destFirst > dest.size) throw new IndexOutOfBoundsException(
        "DestFirst = " + destFirst + " exceeds dest.size = " + dest.size + "."
      )
      else throw new BufferOverflowException()
    }
    
    
    if (dest.isReadOnly) throw new ReadOnlyBufferException()
    
    if (this.formatManifest != dest.formatManifest) throw new IllegalArgumentException(
      "Destination format must match the source."
    )
    if (this.rawType != dest.rawType) throw new IllegalArgumentException(
      "Destination raw type must match the source."
    )
    if (this.offset != dest.offset) throw new IllegalArgumentException(
      "Destination offset must match the source."
    )
    if (this.stride != dest.stride) throw new IllegalArgumentException(
      "Destination stride must match the source."
    )
    
    
    if (this.sharesStorageWith(dest) && (first + count*stride > destFirst)) {
      throw new IllegalArgumentException("Source memory must not overlap with destination.")
    }
    
    
    if (sortContext.capacity < count) throw new IllegalArgumentException("SortContext has fewer elements than count.")
    
    
    if (!this.isInstanceOf[Contiguous[_, _]]) {
      Util.reorderByteBuffer(sortContext, dest, destFirst, this, first, stride, count)
    }
    else {
      primitives.rawType match {
        
        case RawType.SByte | RawType.UByte =>
          Util.reorderByteBuffer(sortContext, dest, destFirst, this, first, stride, count)
          
        case RawType.SShort | RawType.HFloat =>
          Util.reorderShortBuffer(sortContext, dest, destFirst, this, first, stride, count)
          
        case RawType.UShort =>
          Util.reorderCharBuffer(sortContext, dest, destFirst, this, first, stride, count)
          
        case RawType.SInt | RawType.UInt =>
          Util.reorderIntBuffer(sortContext, dest, destFirst, this, first, stride, count)
          
        case RawType.RFloat =>
          Util.reorderFloatBuffer(sortContext, dest, destFirst, this, first, stride, count)
          
        case RawType.RDouble =>
          Util.reorderDoubleBuffer(sortContext, dest, destFirst, this, first, stride, count)
      }
    }
  }
  
  /** Stride is in units of T.
   * If this sequence is a view, the result must be a view with the same offset and stride.
   * All the data within sort stride and view stride is copied to the destination.
   */
  final def reorder(sortContext: SortContext, stride: Int, dest: Data[A]) {
    if (stride < 1) throw new IndexOutOfBoundsException("Stride = " + stride + ", must be greater than or equal to 1.")
    reorder(sortContext, 0, size/stride, stride, dest, 0)
  }
}

trait Data[A <: Accessor] extends AbstractData[A#Const, A#Read] with ReadData[A]