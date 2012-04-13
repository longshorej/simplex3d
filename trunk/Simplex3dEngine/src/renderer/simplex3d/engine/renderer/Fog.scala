/*
 * Simplex3dEngine - Renderer Module
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dEngine.
 *
 * Simplex3dEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.engine
package renderer

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.engine.graphics._


sealed abstract class ReadFog extends ReadStruct[Fog] {
  def color: ReadVec3
  def density: ReadDoubleRef
  
  final override def equals(other: Any) :Boolean = {
    other match {
      case f: ReadFog =>
        color == f.color &&
        density == f.density
      case _ => false
    }
  }
  
  final override def hashCode() :Int = {
    41 * (
      41 + color.hashCode
    ) + density.hashCode
  }
}


final class Fog extends ReadFog with EnvironmentalEffect[Fog] with prototype.Struct[Fog]
{
  type Read = ReadFog
  protected def mkMutable() = new Fog
  
  
  val color = Vec3(1)
  val density = new DoubleRef(0)
  
  init(classOf[Fog])
  
  
  def propagate(parentVal: ReadFog, result: Fog) {
    result := this
  }
  
  protected def resolveBinding() = this
}


object Fog {
  val Default: ReadFog = new Fog
}
