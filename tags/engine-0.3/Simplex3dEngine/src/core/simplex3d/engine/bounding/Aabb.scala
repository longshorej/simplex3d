/*
 * Simplex3dEngine - Core Module
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
package bounding

import simplex3d.math.double._


/** Axis Aligned Bounding Box is defined in world coordinates.
 * 
 */
final class Aabb(cmin: inVec3 = Vec3.Zero, cmax: inVec3 = Vec3.Zero) extends BoundingVolume {
  
  final class MutableSubtext {
    def min: Vec3 = _min
    def max: Vec3 = _max
  }
  private val mutableSubtext = new MutableSubtext
  
  def update = {
    dataChanges = true
    mutableSubtext
  }
  
  
  private val _min = cmin.mutableCopy()
  private val _max = cmax.mutableCopy()
  
  def min: ReadVec3 = _min
  def max: ReadVec3 = _max
  
  def :=(r: Aabb) {
    update.min := r.min
    update.max := r.max
  }
  
  override def toString() :String = "Aabb(" + min + ", " + max + ")"
}
