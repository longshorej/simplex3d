/*
 * Simplex3dEngine - Core Module
 * Copyright (C) 2011-2012, Aleksey Nikiforov
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

import scala.collection.mutable.ArrayBuffer
import simplex3d.math.types._
import simplex3d.math.double.functions._
import simplex3d.engine.util._


sealed abstract class ReadBindingList[T <: Accessible with Binding](
  implicit val elementManifest: ClassManifest[T#Read]
)
extends Protected with Binding with PropertyContextDependent
{
  type Read = ReadBindingList[T]
  type Mutable = BindingList[T]
  final def readType = classOf[ReadBindingList[T]]
  
  def size: Int
  final def length: Int = size
  
  def apply(i: Int) :T//XXX must be T#Read
}


final class BindingList[T <: Accessible with Binding](
  implicit elementManifest: ClassManifest[T#Read]
)
extends ReadBindingList[T] with Accessible
{
  if(elementManifest.erasure == classOf[BindingList[_]]) throw new IllegalArgumentException(
    "Nested lists are not supported."
  )
  
  private var context: PropertyContext = _
  
  private[engine] override def register(context: PropertyContext) {
    this.context = context
    if (manageElems) registerElems(0, buff.size)
  }
  
  private[engine] override def unregister() {
    if (manageElems) unregisterElems(0, buff.size)
    context = null
  }
  
  protected def registerPropertyContext(context: PropertyContext) {}
  protected def unregisterPropertyContext() {}
  
  
  private val managable = classOf[PropertyContextDependent].isAssignableFrom(elementManifest.erasure)
  private def manageElems = (context != null && managable)
  
  private def registerElems(offset: Int, count: Int) {
    var i = offset; while (i < offset + count) {
      buff(i).asInstanceOf[PropertyContextDependent].register(context)
      i += 1
    }
  }
  private def unregisterElems(offset: Int, count: Int) {
    var i = offset; while (i < offset + count) {
      buff(i).asInstanceOf[PropertyContextDependent].unregister()
      i += 1
    }
  }
  
  private val buff = new ArrayBuffer[T]
  def size = buff.size
  
  final def mutableCopy(): BindingList[T] = {
    val copy = new BindingList[T]()(elementManifest)
    copy := this
    copy
  }
  
  def :=(r: ReadBindingList[T]) {
    this := r.asInstanceOf[BindingList[T]].buff.asInstanceOf[ArrayBuffer[T#Read]]
  }
  def :=(seq: Seq[T#Read]) {
    val s = size; var i = 0; for (e <- seq) {
      if (i < s) {
        val stable = buff(i)
        stable := e.asInstanceOf[stable.Read]
      }
      else {
        val mc = e.mutableCopy().asInstanceOf[T]
        if (manageElems) mc.asInstanceOf[PropertyContextDependent].register(context)
        buff += mc
      }
      i += 1
    }
    
    if (i < s) {
      if (manageElems) unregisterElems(i, s - i)
      buff.remove(i, s - i)
    }
    
    if (context != null && i != s) context.signalStructuralChanges()
  }
  
  def apply(i: Int) :T = buff(i)
  
  def +=(elem: T#Read) {
    val mc = elem.mutableCopy().asInstanceOf[T]
    if (manageElems) mc.asInstanceOf[PropertyContextDependent].register(context)
    buff += mc
    if (context != null) context.signalStructuralChanges()
  }
  
  def ++=(r: ReadBindingList[T]) {
    this ++= r.asInstanceOf[BindingList[T]].buff.asInstanceOf[ArrayBuffer[T#Read]]
  }
  def ++=(seq: Seq[T#Read]) {
    val size0 = buff.size
    buff ++= seq.map(_.mutableCopy().asInstanceOf[T])
    if (manageElems) registerElems(size0, buff.size - size0)
    if (context != null) context.signalStructuralChanges()
  }
  
  def insert(index: Int, elems: T#Read*) {
    this.insertAll(index, elems)
  }
  
  def insertAll(index: Int, r: ReadBindingList[T]) {
    this.insertAll(index, r.asInstanceOf[BindingList[T]].buff.asInstanceOf[ArrayBuffer[T#Read]])
  }
  def insertAll(index: Int, seq: Seq[T#Read]) {
    val size0 = buff.size
    buff.insertAll(index, seq.map(_.mutableCopy().asInstanceOf[T]))
    if (manageElems) registerElems(index, buff.size - size0)
    if (context != null) context.signalStructuralChanges()
  }
  
  def remove(index: Int) {
    remove(index, 1)
  }
  def remove(index: Int, count: Int) {
    if (index < 0 || index > buff.size) throw new IndexOutOfBoundsException()
    if (index + count > buff.size) throw new IndexOutOfBoundsException()
    
    if (manageElems) unregisterElems(index, count)
    buff.remove(index, count)
    if (context != null) context.signalStructuralChanges()
  }
  
  def take(count: Int) { remove(count, size - count) }
  def takeRigth(count: Int) { remove(0, size - count) }
  def drop(count: Int) { remove(size - count, count) }
  def dropLeft(count: Int) { remove(0, count) }
  
  
  def clear() {
    remove(0, buff.size)
  }
  
  override def toString() :String = {
    "BindingList(" + buff.mkString(", ") + ")"
  }
}


object BindingList {
  def apply[T <: Accessible with Binding]
    (elems: T#Read*)
    (implicit elementManifest: ClassManifest[T#Read])
  :BindingList[T] =
  {
    val list = new BindingList[T]
    list ++= elems
    list
  }
}
