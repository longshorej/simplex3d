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
package graphics

import simplex3d.math.types._


trait EnvironmentalEffect[E <: EnvironmentalEffect[E]] extends Writable[E]
{ self: E =>
  protected def mkMutable() :E
  
  override def mutableCopy(): E = {
    val copy = mkMutable()
    copy := this
    copy
  }
  
  def propagate(parentVal: E#Read, result: E) :Unit
  
  /** This method must return true to signal binding changes and indicate that a new binding must be resolved.
   */
  def hasBindingChanges: Boolean
  
  /** Must return a stable binding that will no change until the next binding change event.
   * Resolving the binding must clear bindingChanges flag.
   */
  def resolveBinding(): TechniqueBinding
}


trait UpdatableEnvironmentalEffect[E <: UpdatableEnvironmentalEffect[E]] extends EnvironmentalEffect[E]
{ self: E =>
  
  /** Update the stable binding with a set of predefined uniforms that are only available at the time of rendering.
   * 
   * Values that depend on camera must be taken from predefined uniforms,
   * so that passes with different cameras can be rendered correctly. 
   */
  def updateBinding(predefinedUniforms: ReadPredefinedUniforms) :Unit
}