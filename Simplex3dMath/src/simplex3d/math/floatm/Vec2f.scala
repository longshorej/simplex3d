/*
 * Simplex3d, FloatMath module
 * Copyright (C) 2009-2010 Simplex3d Team
 *
 * This file is part of Simplex3dMath.
 *
 * Simplex3dMath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dMath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.math.floatm

import simplex3d.math._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyVec2f extends Read2[Float] {

  private[math] type R2 = ConstVec2f
  private[math] type R3 = ConstVec3f
  private[math] type R4 = ConstVec4f
  
  protected final def make2(x: Float, y: Float) =
    new ConstVec2f(x, y)
  protected final def make3(x: Float, y: Float, z: Float) =
    new ConstVec3f(x, y, z)
  protected final def make4(x: Float, y: Float, z: Float, w: Float) =
    new ConstVec4f(x, y, z, w)

  private[math] final def bx: Boolean = bool(x)
  private[math] final def by: Boolean = bool(y)

  private[math] final def ix: Int = int(x)
  private[math] final def iy: Int = int(y)

  private[math] final def fx: Float = x
  private[math] final def fy: Float = y

  private[math] final def dx: Double = x
  private[math] final def dy: Double = y


  def x: Float
  def y: Float

  def r = x
  def g = y

  def s = x
  def t = y

  
  final def apply(i: Int) :Float = {
    i match {
      case 0 => x
      case 1 => y
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }

  final def unary_+() :AnyVec2f = this
  final def unary_-() = new Vec2f(-x, -y)
  final def *(s: Float) = new Vec2f(x * s, y * s)
  final def /(s: Float) = { val inv = 1/s; new Vec2f(x * inv, y * inv) }

  final def +(s: Float) = new Vec2f(x + s, y + s)
  final def -(s: Float) = new Vec2f(x - s, y - s)

  private[math] final def divideByComponent(s: Float) = new Vec2f(s / x, s / y)

  final def +(u: inVec2f) = new Vec2f(x + u.x, y + u.y)
  final def -(u: inVec2f) = new Vec2f(x - u.x, y - u.y)
  final def *(u: inVec2f) = new Vec2f(x * u.x, y * u.y)
  final def /(u: inVec2f) = new Vec2f(x / u.x, y / u.y)

  final def *(m: inMat2f) :Vec2f = m.transposeMul(this)
  final def *(m: inMat2x3f) :Vec3f = m.transposeMul(this)
  final def *(m: inMat2x4f) :Vec4f = m.transposeMul(this)

  final def ==(u: inVec2f) :Boolean = {
    if (u eq null) false
    else x == u.x && y == u.y
  }

  final def !=(u: inVec2f) :Boolean = !(this == u)

  private[math] final def hasErrors: Boolean = {
    import java.lang.Float._
    (
      isNaN(x) || isInfinite(x) ||
      isNaN(y) || isInfinite(y)
    )
  }

  final override def equals(other: Any) :Boolean = {
    other match {
      case u: inVec2f => this == u
      case _ => false
    }
  }

  final override def hashCode() :Int = {
    41 * (
      41 + x.hashCode
    ) + y.hashCode
  }

  final override def toString() :String = {
    this.getClass.getSimpleName + "(" + x + ", " + y + ")"
  }
}


@serializable @SerialVersionUID(5359695191257934190L)
final class ConstVec2f private[math] (val x: Float, val y: Float)
extends AnyVec2f with Immutable

object ConstVec2f {
  /* main factory */ def apply(x: Float, y: Float) = new ConstVec2f(x, y)
  def apply(u: Read2[_]) = new ConstVec2f(u.fx, u.fy)

  implicit def toConst(u: AnyVec2f) = new ConstVec2f(u.x, u.y)
}


@serializable @SerialVersionUID(5359695191257934190L)
final class Vec2f private[math] (var x: Float, var y: Float)
extends AnyVec2f with Mutable with Implicits[On] with Composite
{
  type Element = AnyVec2f
  type Component = Float1

  override def r = x
  override def g = y

  override def s = x
  override def t = y

  def r_=(r: Float) { x = r }
  def g_=(g: Float) { y = g }

  def s_=(s: Float) { x = s }
  def t_=(t: Float) { y = t }

  
  def *=(s: Float) { x *= s; y *= s }
  def /=(s: Float) { val inv = 1/s; x *= inv; y *= inv }

  def +=(s: Float) { x += s; y += s }
  def -=(s: Float) { x -= s; y -= s }

  def +=(u: inVec2f) { x += u.x; y += u.y }
  def -=(u: inVec2f) { x -= u.x; y -= u.y }
  def *=(u: inVec2f) { x *= u.x; y *= u.y }
  def /=(u: inVec2f) { x /= u.x; y /= u.y }

  def *=(m: inMat2f) { this := m.transposeMul(this) }

  def :=(u: inVec2f) { x = u.x; y = u.y }
  def set(x: Float, y: Float) { this.x = x; this.y = y }

  def update(i: Int, s: Float) {
    i match {
      case 0 => x = s
      case 1 => y = s
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }

  // Swizzling
  override def xy: ConstVec2f = new ConstVec2f(x, y)
  override def yx: ConstVec2f = new ConstVec2f(y, x)

  override def rg = xy
  override def gr = yx

  override def st = xy
  override def ts = yx


  def xy_=(u: inVec2f) { x = u.x; y = u.y }
  def yx_=(u: inVec2f) { var t = u.y; y = u.x; x = t }

  def rg_=(u: inVec2f) { xy_=(u) }
  def gr_=(u: inVec2f) { yx_=(u) }

  def st_=(u: inVec2f) { xy_=(u) }
  def ts_=(u: inVec2f) { yx_=(u) }
}

object Vec2f {
  val Zero = new ConstVec2f(0, 0)
  val UnitX = new ConstVec2f(1, 0)
  val UnitY = new ConstVec2f(0, 1)
  val One = new ConstVec2f(1, 1)

  def apply(s: Float) = new Vec2f(s, s)
  /* main factory */ def apply(x: Float, y: Float) = new Vec2f(x, y)
  def apply(u: Read2[_]) = new Vec2f(u.fx, u.fy)
  def apply(u: Read3[_]) = new Vec2f(u.fx, u.fy)
  def apply(u: Read4[_]) = new Vec2f(u.fx, u.fy)

  def unapply(u: AnyVec2f) = Some((u.x, u.y))

  implicit def toMutable(u: AnyVec2f) = new Vec2f(u.x, u.y)
  implicit def castInt(u: Read2[Int]) = new Vec2f(u.fx, u.fy)
}