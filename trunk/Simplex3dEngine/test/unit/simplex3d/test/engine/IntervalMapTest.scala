/*
 * Simplex3dEngine - Test Package
 * Copyright (C) 2013, Aleksey Nikiforov
 *
 * This file is part of Simplex3dEngineTest.
 *
 * Simplex3dEngineTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dEngineTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.test.engine

import org.scalatest._
import java.util._
import scala.collection.mutable.ArrayBuffer
import simplex3d.engine._
import simplex3d.engine.graphics._


/**
 * @author Aleksey Nikiforov (lex)
 */
class IntervalMapTest extends FunSuite {
   
  private val random = new Random(0)
  private def randomInt(from: Int, distance: Int) = if (distance == 0) from else from + random.nextInt(distance)
  
  private def genIntervals(minSize: Int, maxSize: Int, minDistance: Int, maxDistance: Int)(count: Int)
  :IndexedSeq[(Int, Int)] = {
    
    val buffer = new ArrayBuffer[(Int, Int)]
    
    var last = -minDistance
    var i = 0; while (i < count) {
      
      val first = randomInt(last + minDistance, maxDistance - minDistance)
      val size = randomInt(minSize, maxSize - minSize)
      buffer += ((first, size))
      
      last = first + size
      i += 1
    }
    
    buffer
  }
  
  private def testWithTolerance(mergeTolerance: Int) {
    val intervals = new IntervalMap(mergeTolerance)
    val testSet = genIntervals(1, 100, mergeTolerance + 1, mergeTolerance + 21)(1000)
    
    val javaList = Arrays.asList(testSet.toArray: _*)
    Collections.shuffle(javaList)
    val shuffled = javaList.toArray().asInstanceOf[Array[(Int, Int)]]
      
    for ((first, count) <- shuffled) {
      for (i <- 0 until 100) {
        val subFirst = randomInt(first, count)
        val subCount = randomInt(0, count - (subFirst - first))
        
        intervals.put(subFirst, subCount)
      }
      val withGap = math.min(count/4 + mergeTolerance, count - 1)
      intervals.put(first, math.max(count/4, 1))
      intervals.put(first + withGap, count - withGap)
    }
    
    intervals.merge()
    
    for (i <- 0 until testSet.size) {
      val (first, count) = testSet(i)
      assert(first == intervals.first(i))
      assert(count == intervals.count(i))
    }
  }
  
  test("Interval Map") {
    for (i <- 0 until 10) {
      testWithTolerance(i)
    }
  }
}
