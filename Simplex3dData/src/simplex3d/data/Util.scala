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
import scala.annotation._


// An empty class to make -Xno-forwarders work
private[data] class Util


/**
 * @author Aleksey Nikiforov (lex)
 */
private[data] object Util {

  final val emptyByte = new Array[Byte](0)
  final val emptyChar = new Array[Char](0)
  final val emptyShort = new Array[Short](0)
  final val emptyInt = new Array[Int](0)
  final val emptyFloat = new Array[Float](0)
  final val emptyDouble = new Array[Double](0)

  
  // ByteBuffer
  final def copyBuffer(
    components: Int,
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: ByteBuffer, destOffset: Int, destStride: Int,
    src: ByteBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // ShortBuffer
  final def copyBuffer(
    components: Int,
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: ShortBuffer, destOffset: Int, destStride: Int,
    src: ShortBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // CharBuffer
  final def copyBuffer(
    components: Int,
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: CharBuffer, destOffset: Int, destStride: Int,
    src: CharBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // IntBuffer
  final def copyBuffer(
    components: Int,
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: IntBuffer, destOffset: Int, destStride: Int,
    src: IntBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // FloatBuffer
  final def copyBuffer(
    components: Int,
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: FloatBuffer, destOffset: Int, destStride: Int,
    src: FloatBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // DoubleBuffer
  final def copyBuffer(
    components: Int,
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copyBuffer1(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copyBuffer2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copyBuffer3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copyBuffer4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copyBufferAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copyBuffer1(
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest.put(desti, src.get(srci))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer2(
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer3(
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBuffer4(
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest.put(desti, src.get(srci))
      dest.put(desti + 1, src.get(srci + 1))
      dest.put(desti + 2, src.get(srci + 2))
      dest.put(desti + 3, src.get(srci + 3))
      desti += destStride
      srci += srcStride
    }
  }

  final def copyBufferAny(
    components: Int,
    dest: DoubleBuffer, destOffset: Int, destStride: Int,
    src: DoubleBuffer, srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest.put(desti + j, src.get(srci + j))
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // SeqInt
  final def copySeqInt(
    components: Int,
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copySeqSInt(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copySeqInt2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copySeqInt3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copySeqInt4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copySeqIntAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copySeqSInt(
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest(desti) = src(srci)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqInt2(
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqInt3(
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqInt4(
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      dest(desti + 3) = src(srci + 3)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqIntAny(
    components: Int,
    dest: Contiguous[SInt, _], destOffset: Int, destStride: Int,
    src: inContiguous[SInt, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest(desti + j) = src(srci + j)
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }

  // SeqFloat
  final def copySeqFloat(
    components: Int,
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copySeqRFloat(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copySeqFloat2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copySeqFloat3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copySeqFloat4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copySeqFloatAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copySeqRFloat(
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest(desti) = src(srci)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqFloat2(
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqFloat3(
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqFloat4(
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      dest(desti + 3) = src(srci + 3)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqFloatAny(
    components: Int,
    dest: Contiguous[RFloat, _], destOffset: Int, destStride: Int,
    src: inContiguous[RFloat, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest(desti + j) = src(srci + j)
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }


  // SeqDouble
  final def copySeqDouble(
    components: Int,
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    (components: @switch) match {
      case 1 => copySeqRDouble(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 2 => copySeqDouble2(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 3 => copySeqDouble3(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case 4 => copySeqDouble4(
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
      case _ => copySeqDoubleAny(
          components,
          dest, destOffset, destStride,
          src, srcOffset, srcStride, srcLim
        )
    }
  }

  final def copySeqRDouble(
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim) {
      dest(desti) = src(srci)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqDouble2(
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 1) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqDouble3(
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 2) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqDouble4(
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci < srcLim - 3) {
      dest(desti) = src(srci)
      dest(desti + 1) = src(srci + 1)
      dest(desti + 2) = src(srci + 2)
      dest(desti + 3) = src(srci + 3)
      desti += destStride
      srci += srcStride
    }
  }

  final def copySeqDoubleAny(
    components: Int,
    dest: Contiguous[RDouble, _], destOffset: Int, destStride: Int,
    src: inContiguous[RDouble, _], srcOffset: Int, srcStride: Int, srcLim: Int
  ) {
    var desti = destOffset
    var srci = srcOffset

    while (srci <= srcLim - components) {

      var j = 0; while (j < components) {
        dest(desti + j) = src(srci + j)
        j += 1
      }

      desti += destStride
      srci += srcStride
    }
  }
}