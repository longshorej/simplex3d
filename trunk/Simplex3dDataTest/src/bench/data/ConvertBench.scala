/*
 * Simplex3d, DataTest package
 * Copyright (C) 2010, Simplex3d Team
 *
 * This file is part of Simplex3dDataTest.
 *
 * Simplex3dDataTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dDataTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package bench.buffer


import java.nio._

import simplex3d.math._
import simplex3d.math.floatx._
import simplex3d.math.floatx.FloatMath._
import simplex3d.data._
import simplex3d.data.floatm._


/**
 * @author Aleksey Nikiforov (lex)
 */
object ConvertBench {
  def main(args: Array[String]) {
    new ConvertBenchTC().run()
  }
}

class ConvertBenchTC {
  val length = 100000
  val loops = 10000

  val random = new java.util.Random()

  val floatArray = new Array[Float](length)
  random.setSeed(1)
  for (i <- 0 until length) {
    floatArray(i) = random.nextFloat
  }

  val intArray = new Array[Int](length)
  random.setSeed(1)
  for (i <- 0 until length) {
    intArray(i) = random.nextInt
  }

  val floatByteBuffer = ByteBuffer.allocateDirect(4*length).order(ByteOrder.nativeOrder)
  val floatBuffer = floatByteBuffer.asFloatBuffer
  random.setSeed(1)
  for (i <- 0 until length) {
    floatBuffer.put(i, random.nextFloat)
  }

  val intByteBuffer = ByteBuffer.allocateDirect(4*length).order(ByteOrder.nativeOrder)
  val intBuffer = intByteBuffer.asIntBuffer
  random.setSeed(1)
  for (i <- 0 until length) {
    intBuffer.put(i, random.nextInt)
  }

  def run() {
    var start = 0L

    start = System.currentTimeMillis
    testConvertPutDataBuffer(floatByteBuffer, loops)
    val convertPutDataBufferTime = System.currentTimeMillis - start
    
    start = System.currentTimeMillis
    testConvertPutHFloat(floatByteBuffer, loops)
    val convertPutHFloatTime = System.currentTimeMillis - start

    start = System.currentTimeMillis
    testConvertPutDataBuffer(floatByteBuffer, loops)
    val convertPutDataBufferTime2 = System.currentTimeMillis - start

    start = System.currentTimeMillis
    testConvertPutHFloat(floatByteBuffer, loops)
    val convertPutHFloatTime2 = System.currentTimeMillis - start


    println("ConvertPutDataBuffer time: " + convertPutDataBufferTime + ".")
    println("ConvertPutHFloat time: " + convertPutHFloatTime + ".")
    println("ConvertPutDataBuffer time: " + convertPutDataBufferTime2 + ".")
    println("ConvertPutHFloat time: " + convertPutHFloatTime2 + ".")
  }

  def testConvertPutDataBuffer(data: ByteBuffer, loops: Int) {
    var answer = 0
    val size = data.capacity/4
    val offset = 2
    val stride = 2
    val dest = DataView[RFloat, UInt](
      ByteBuffer.allocateDirect(
        size*4*(stride + 1) + offset*4
      ),
      offset, stride
    )
    val src = DataBuffer[RFloat, SInt](data)

    var l = 0; while (l < loops) {
      dest.put(src)
      answer += toInt(dest(l % size)*1000)

      l += 1
    }

    println(answer)
  }

  def testConvertPutHFloat(data: ByteBuffer, loops: Int) {
    var answer = 0
    val size = data.capacity/4
    val offset = 2
    val stride = 2
    val dest = DataView[RFloat, HFloat](
      ByteBuffer.allocateDirect(
        size*2*(stride + 1) + offset*4
      ),
      offset, stride
    )
    val src = DataBuffer[RFloat, RFloat](data)

    var l = 0; while (l < loops) {
      dest.put(src)
      answer += toInt(dest(l % size)*1000)

      l += 1
    }

    println(answer)
  }
}