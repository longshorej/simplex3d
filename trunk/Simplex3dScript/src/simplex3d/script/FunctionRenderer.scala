/*
 * Simplex3dScript
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dScript.
 *
 * Simplex3dScript is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dScript is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.script

import java.util.concurrent.ExecutorService

import java.util.concurrent.Executors
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
trait FunctionRenderer {
  def nextFrame(dims: inVec2i, time: Double) :Array[Int]
  def dispose() :Unit
}


private[script] object FunctionRenderer {

  private class Painter (
    function: (inVec2i, Double, inVec2) => ReadVec3,
    es: ExecutorService,
    exceptionHandler: Throwable => Unit
  ) extends Job(es, exceptionHandler) {

    var buffer: Array[Int] = null
    var dims = ConstVec2i(0)
    private var time: java.lang.Double = _

    private var yoffset = 0

    
    def setData(
      buffer: Array[Int],
      dims: inVec2i,
      time: Double
    ) {
      if (isExecuting) throw new IllegalStateException(
        "Cannot change while executing.")

      this.buffer = buffer
      this.dims = dims
      yoffset = 0
      this.time = time
    }

    def runSingleThreaded() {
      val pixel = Vec2(0)

      var y = 0; while(y < dims.y) {
        val h = dims.y - 1 - y

        var x = 0; while(x < dims.x) {

          pixel.x = x; pixel.y = h
          buffer(x + y*dims.x) = ImageUtils.rgb(function(dims, time.asInstanceOf[Double], pixel))

          x += 1
        }

        y += 1
      }
    }

    final def hasMoreChunks() = yoffset < dims.y

    final def nextChunk() = {
      val ystart = yoffset
      yoffset += 5
      if (yoffset > dims.y) yoffset = dims.y
      val yend = yoffset

      new Chunk() {
        final def run() {
          val pixel = Vec2(0)

          var y = ystart; while (y < yend) {
            loop(y, dims.y - 1 - y, pixel)
            y += 1
          }
        }

        final def loop(y :Int, h: Int, pixel: Vec2) {
          var x = 0; while(x < dims.x) {

            pixel.x = x; pixel.y = h
            buffer(x + y*dims.x) = ImageUtils.rgb(function(dims, time.asInstanceOf[Double], pixel))

            x += 1
          }
        }
      }

    }
  }

  def apply(
    animate: Boolean,
    function: (inVec2i, Double, inVec2) => ReadVec3,
    exceptionHandler: Throwable => Unit = _.printStackTrace()
  ) = new FunctionRenderer() {
    private val start = System.currentTimeMillis
    val threadPool = if (animate) Executors.newCachedThreadPool() else null
    private val job = new Painter(function, threadPool, exceptionHandler)

    private var current = 0

    // the rendering algorithm only uses 2 frames
    private val frameBuffers = new Array[Array[Int]](2)
    frameBuffers(0) = new Array[Int](0)
    frameBuffers(1) = new Array[Int](0)

    def nextFrame(dims: inVec2i, time: Double) :Array[Int] = {

      // Handle resize and/or wait for rendering to finish.
      val retBuffer: Array[Int] = {
        if (job.dims != dims) {
          job.cancelAndWait()
          val buffer = new Array[Int](dims.x*dims.y)
          frameBuffers(current) = buffer
          job.setData(buffer, dims, time)
          job.execAndWait()
          job.buffer
        }
        else {
          job.waitForCompletion()
          job.buffer
        }
      }

      if (animate) {
        // Setup the next rendering (handle resize if necessary).
        current += 1
        if (current >= frameBuffers.length) current = 0
        
        var buffer = frameBuffers(current)
        if (buffer.length != dims.x*dims.y) {
          buffer = new Array[Int](dims.x*dims.y)
          frameBuffers(current) = buffer
        }

        // Start the next rendering in a parallel thread.
        job.setData(buffer, dims, time)
        job.exec()
      }

      // Return perviously competed rendering.
      retBuffer
    }

    def dispose() {
      job.dispose()
    }
  }

  def testLoad(function: (inVec2i, Double, inVec2) => ReadVec3, iterations: Int) {
    val timer = new SystemTimer()

    val dims = ConstVec2i(640, 480)
    val buffer = new Array[Int](dims.x*dims.y)

    val job = new Painter(function, Executors.newCachedThreadPool(), _.printStackTrace())

    var i = 0; while (i < iterations) {
      timer.update()

      job.setData(buffer, dims, timer.uptime)
      job.execAndWait()

      if (i % 10 == 0) println("fps: " + timer.averageFps)
      i += 1
    }
  }
}
