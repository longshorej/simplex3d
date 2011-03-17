/*
 * Simplex3d, FloatMath module
 * Copyright (C) 2009-2011, Aleksey Nikiforov
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

package simplex3d.math
package floatx

import scala.reflect.ClassManifest.{classType}
import simplex3d.math.integration._
import simplex3d.math.floatx.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
@serializable @SerialVersionUID(8104346712419693669L)
sealed abstract class ReadVec2f extends ProtectedVec2f[Float] {

  type Clone <: ReadVec2f

  private[math] type R2 = ReadVec2f
  private[math] type R3 = ReadVec3f
  private[math] type R4 = ReadVec4f

  private[math] type C2 = ConstVec2f
  private[math] type C3 = ConstVec3f
  private[math] type C4 = ConstVec4f

  protected final def make2(x: Double, y: Double) =
    new ConstVec2f(x.toFloat, y.toFloat)
  protected final def make3(x: Double, y: Double, z: Double) =
    new ConstVec3f(x.toFloat, y.toFloat, z.toFloat)
  protected final def make4(x: Double, y: Double, z: Double, w: Double) =
    new ConstVec4f(x.toFloat, y.toFloat, z.toFloat, w.toFloat)

  private[math] final def bx: Boolean = Boolean(x)
  private[math] final def by: Boolean = Boolean(y)

  private[math] final def ix: Int = x.toInt
  private[math] final def iy: Int = y.toInt

  private[math] final def fx: Float = x
  private[math] final def fy: Float = y

  private[math] final def dx: Double = x
  private[math] final def dy: Double = y


  final def x = px
  final def y = py

  final def r = px
  final def g = py

  final def s = px
  final def t = py


  protected def x_=(s: Float) { throw new UnsupportedOperationException }
  protected def y_=(s: Float) { throw new UnsupportedOperationException }

  protected def r_=(s: Float) { throw new UnsupportedOperationException }
  protected def g_=(s: Float) { throw new UnsupportedOperationException }

  protected def s_=(s: Float) { throw new UnsupportedOperationException }
  protected def t_=(s: Float) { throw new UnsupportedOperationException }

  final def apply(i: Int) :Float = {
    i match {
      case 0 => x
      case 1 => y
      case j => throw new IndexOutOfBoundsException(
          "Expected from 0 to 1, got " + j + "."
        )
    }
  }

  final def unary_+() :ReadVec2f = this
  final def unary_-() = new Vec2f(-x, -y)

  final def *(s: Float) = new Vec2f(x * s, y * s)
  final def /(s: Float) = new Vec2f(x / s, y / s)
  private[math] final def divByComp(s: Float) = new Vec2f(s / x, s / y)
  final def +(s: Float) = new Vec2f(x + s, y + s)
  final def -(s: Float) = new Vec2f(x - s, y - s)

  final def *(u: inVec2f) = new Vec2f(x * u.x, y * u.y)
  final def /(u: inVec2f) = new Vec2f(x / u.x, y / u.y)
  final def +(u: inVec2f) = new Vec2f(x + u.x, y + u.y)
  final def -(u: inVec2f) = new Vec2f(x - u.x, y - u.y)

  final def *(m: inMat2f) :Vec2f = m.transposeMult(this)
  final def *(m: inMat2x3f) :Vec3f = m.transposeMult(this)
  final def *(m: inMat2x4f) :Vec4f = m.transposeMult(this)


  final override def equals(other: Any) :Boolean = {
    other match {
      case u: ReadVec2b => false
      case u: AnyVec2[_] => dx == u.dx && dy == u.dy
      case _ => false
    }
  }

  final override def hashCode() :Int = {
    41 * (
      41 + y.hashCode
    ) + x.hashCode
  }

  final override def toString() :String = {
    val prefix = this match {
      case self: Immutable => "Const"
      case _ => ""
    }
    prefix + "Vec2" + "(" + x + "f, " + y + "f)"
  }
}


@serializable @SerialVersionUID(8104346712419693669L)
final class ConstVec2f private[math] (cx: Float, cy: Float)
extends ReadVec2f with Immutable {
  px = cx; py = cy

  type Clone = ConstVec2f
  override def clone() = this
}


object ConstVec2f {

  def apply(s: Float) = new ConstVec2f(s, s)
  def apply(x: Float, y: Float) = new ConstVec2f(x, y)

  def apply(u: AnyVec2[_]) = new ConstVec2f(u.fx, u.fy)
  def apply(u: AnyVec3[_]) = new ConstVec2f(u.fx, u.fy)
  def apply(u: AnyVec4[_]) = new ConstVec2f(u.fx, u.fy)



  implicit def toConst(u: ReadVec2f) = apply(u)
}


@serializable @SerialVersionUID(8104346712419693669L)
final class Vec2f private[math] (cx: Float, cy: Float)
extends ReadVec2f with PropertyRef with Composite with Implicits[On]
{
  type Read = ReadVec2f
  type Const = ConstVec2f
  type Component = RFloat

  px = cx; py = cy

  @noinline override def x_=(s: Float) { px = s }
  @noinline override def y_=(s: Float) { py = s }

  override def r_=(s: Float) { px = s }
  override def g_=(s: Float) { py = s }

  override def s_=(s: Float) { px = s }
  override def t_=(s: Float) { py = s }

  def update(i: Int, s: Float) {
    i match {
      case 0 => x = s
      case 1 => y = s
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }

  def *=(s: Float) { x *= s; y *= s }
  def /=(s: Float) { x /= s; y /= s }
  def +=(s: Float) { x += s; y += s }
  def -=(s: Float) { x -= s; y -= s }

  def *=(u: inVec2f) { x *= u.x; y *= u.y }
  def /=(u: inVec2f) { x /= u.x; y /= u.y }
  def +=(u: inVec2f) { x += u.x; y += u.y }
  def -=(u: inVec2f) { x -= u.x; y -= u.y }

  def *=(m: inMat2f) { this := m.transposeMult(this) }


  type Clone = Vec2f
  override def clone() = Vec2f(this)
  def toConst() = ConstVec2f(this)
  def :=(u: inVec2f) { x = u.x; y = u.y }

  // Swizzling
  override def xy_=(u: inVec2f) { x = u.x; y = u.y }
  override def yx_=(u: inVec2f) { var t = u.y; y = u.x; x = t }

  override def rg_=(u: inVec2f) { xy_=(u) }
  override def gr_=(u: inVec2f) { yx_=(u) }

  override def st_=(u: inVec2f) { xy_=(u) }
  override def ts_=(u: inVec2f) { yx_=(u) }
}


object Vec2f {
  final val Zero = new ConstVec2f(0, 0)
  final val UnitX = new ConstVec2f(1, 0)
  final val UnitY = new ConstVec2f(0, 1)
  final val One = new ConstVec2f(1, 1)

  final val Manifest = classType[Vec2f](classOf[Vec2f])
  final val ConstManifest = classType[ConstVec2f](classOf[ConstVec2f])
  final val ReadManifest = classType[ReadVec2f](classOf[ReadVec2f])


  def apply(s: Float) = new Vec2f(s, s)
  def apply(x: Float, y: Float) = new Vec2f(x, y)

  def apply(u: AnyVec2[_]) = new Vec2f(u.fx, u.fy)
  def apply(u: AnyVec3[_]) = new Vec2f(u.fx, u.fy)
  def apply(u: AnyVec4[_]) = new Vec2f(u.fx, u.fy)



  def unapply(u: ReadVec2f) = Some((u.x, u.y))
  implicit def toMutable(u: ReadVec2f) = apply(u)

  implicit def castInt(u: AnyVec2[Int]) = new Vec2f(u.fx, u.fy)
}
