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
package util

import simplex3d.math.types._


//XXX should this really be in util package?
final class Property[T <: Accessible] private[engine] (
  private[this] final val factory: () => T
) extends Updatable[T]
{
  
  //*** PropertyContext Code ******************************************************************************************
  
  protected final var propertyContext: PropertyContext = _
  
  private[engine] final def register(context: PropertyContext) {
    if (context == null) throw new NullPointerException
    if (this.propertyContext != null && (this.propertyContext ne context)) throw new IllegalStateException(
      "Property can register PropertyContext only once."
    )
    this.propertyContext = context
  }
  
  private[engine] final def unregister() {
    throw new UnsupportedOperationException("Property cannot unregister PropertyContexts.")
  }
    
  
  //*** Property Code *************************************************************************************************
  
  /** getter navigates to the desired field of the value of this property.
   * function modifies the field and returns true to run next frame or false to be removed.
   */
  final def controller[A](getter: T => A)(function: (A, TimeStamp) => Boolean) {
    if (propertyContext == null || propertyContext.controllerContext == null) {
      throw new UnsupportedOperationException("ControllerContext is not defined.")
    }
    PropertyUpdater.register(propertyContext.controllerContext, true, this)(getter, function)
  }
  
  /** function modifies the field and returns true to run next frame or false to be removed.
   */
  final def controller(function: (T, TimeStamp) => Boolean) {
    if (propertyContext == null || propertyContext.controllerContext == null) {
      throw new UnsupportedOperationException("ControllerContext is not defined.")
    }
    PropertyUpdater.register(propertyContext.controllerContext, true, this)(Property.identityGetter, function)
  }
  
  /** getter navigates to the desired field of the value of this property.
   * function modifies the field and returns true to run next frame or false to be removed.
   */
  final def animator[A](getter: T => A)(function: (A, TimeStamp) => Boolean) {
    if (propertyContext == null || propertyContext.controllerContext == null) {
      throw new UnsupportedOperationException("ControllerContext is not defined.")
    }
    PropertyUpdater.register(propertyContext.controllerContext, false, this)(getter, function)
  }
  
  /** function modifies the field and returns true to run next frame or false to be removed.
   */
  final def animator(function: (T, TimeStamp) => Boolean) {
    if (propertyContext == null || propertyContext.controllerContext == null) {
      throw new UnsupportedOperationException("ControllerContext is not defined.")
    }
    PropertyUpdater.register(propertyContext.controllerContext, false, this)(Property.identityGetter, function)
  }
  
  
  private[this] final var value: T = _
  protected final var changed = true // Initialize as changed.
  
  private[engine] def hasDataChanges = changed
  private[engine] def clearDataChanges() { changed = false }
  private[engine] def signalDataChanges() { changed = true }
  
  
  final def get: T#Read = if (value == null) throw new NoSuchElementException else value.asInstanceOf[T#Read]
  final def isDefined = (value != null)
  
  final def undefine() {
    if (isDefined) {
      if (propertyContext != null) propertyContext.signalStructuralChanges()
      changed = true
      value match { case d: PropertyContextDependent => d.unregister(); case _ => /* ignore */ }
      value = null.asInstanceOf[T]
    }
  }
  
  final def update: T = {
    if (!isDefined) {
      value = factory()
      value match { case d: PropertyContextDependent => d.register(propertyContext); case _ => /* ignore */ }
      if (propertyContext != null) propertyContext.signalStructuralChanges()
    }
    changed = true
    value
  }
  
  private final def init(value: T) {
    this.value = value
    value match { case d: PropertyContextDependent => d.register(propertyContext); case _ => /* ignore */ }
    changed = true
  }
  
  final def :=(p: Property[T]) {
    if (p.isDefined) {
      val stable = this.update
      stable := p.get.asInstanceOf[stable.Read]
    }
    else undefine()
  }
  
  final override def toString :String = {
    "Property(" + (if (isDefined) get.toString else "undefined" ) + ")"
  }
}

object Property {
  private[this] val identity = (t: Any) => t
  private[engine] def identityGetter[T] = identity.asInstanceOf[T => T]
  
  
  def apply[T <: Accessible](factory: () => T) :Property[T] = {
    new Property[T](factory)
  }
}
