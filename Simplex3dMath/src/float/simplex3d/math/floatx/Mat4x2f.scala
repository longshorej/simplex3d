/*
 * Simplex3dMath - Float Module
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

import scala.language.implicitConversions
import scala.reflect._
import simplex3d.math.integration._
import simplex3d.math.types._
import simplex3d.math.floatx.functions._


/**
 * @author Aleksey Nikiforov (lex)
 */
@SerialVersionUID(8104346712419693669L)
sealed abstract class ReadMat4x2f extends ProtectedMat4x2f[Float]
with Protected with Serializable
{

  type Clone <: ReadMat4x2f
  def toConst :ConstMat4x2f
  
  type Read = ReadMat4x2f
  type Mutable = Mat4x2f
  final def mutableCopy = Mat4x2f(this)

  // Column major order.
  final def m00 = p00; final def m01 = p01
  final def m10 = p10; final def m11 = p11
  final def m20 = p20; final def m21 = p21
  final def m30 = p30; final def m31 = p31


  protected def m00_=(s: Float) { throw new UnsupportedOperationException }
  protected def m01_=(s: Float) { throw new UnsupportedOperationException }

  protected def m10_=(s: Float) { throw new UnsupportedOperationException }
  protected def m11_=(s: Float) { throw new UnsupportedOperationException }

  protected def m20_=(s: Float) { throw new UnsupportedOperationException }
  protected def m21_=(s: Float) { throw new UnsupportedOperationException }

  protected def m30_=(s: Float) { throw new UnsupportedOperationException }
  protected def m31_=(s: Float) { throw new UnsupportedOperationException }


  private[math] final override def f00 = m00
  private[math] final override def f01 = m01

  private[math] final override def f10 = m10
  private[math] final override def f11 = m11

  private[math] final override def f20 = m20
  private[math] final override def f21 = m21

  private[math] final override def f30 = m30
  private[math] final override def f31 = m31


  private[math] final override def d00 = m00
  private[math] final override def d01 = m01

  private[math] final override def d10 = m10
  private[math] final override def d11 = m11

  private[math] final override def d20 = m20
  private[math] final override def d21 = m21

  private[math] final override def d30 = m30
  private[math] final override def d31 = m31


  final def apply(c: Int) :ConstVec2f = {
    c match {
      case 0 => new ConstVec2f(m00, m01)
      case 1 => new ConstVec2f(m10, m11)
      case 2 => new ConstVec2f(m20, m21)
      case 3 => new ConstVec2f(m30, m31)
      case j => throw new IndexOutOfBoundsException(
          "Trying to read column (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }

  final def apply(c: Int, r: Int) :Float = {
    def error() :Float = throw new IndexOutOfBoundsException(
      "Trying to read index (" + c + ", " + r + ") in " + this.getClass.getSimpleName + "."
    )

    c match {
      case 0 =>
        r match {
          case 0 => m00
          case 1 => m01
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m10
          case 1 => m11
          case _ => error
        }
      case 2 =>
        r match {
          case 0 => m20
          case 1 => m21
          case _ => error
        }
      case 3 =>
        r match {
          case 0 => m30
          case 1 => m31
          case _ => error
        }
      case _ => error
    }
  }

  final def unary_+() :ReadMat4x2f = this
  final def unary_-() = new Mat4x2f(
    -m00, -m01,
    -m10, -m11,
    -m20, -m21,
    -m30, -m31
  )
  final def *(s: Float) = new Mat4x2f(
    s*m00, s*m01,
    s*m10, s*m11,
    s*m20, s*m21,
    s*m30, s*m31
  )
  final def /(s: Float) = this * (1/s)

  final def +(s: Float) = new Mat4x2f(
    m00 + s, m01 + s,
    m10 + s, m11 + s,
    m20 + s, m21 + s,
    m30 + s, m31 + s
  )
  final def -(s: Float) = this + (-s)

  final def +(m: inMat4x2f) = new Mat4x2f(
    m00 + m.m00, m01 + m.m01,
    m10 + m.m10, m11 + m.m11,
    m20 + m.m20, m21 + m.m21,
    m30 + m.m30, m31 + m.m31
  )
  final def -(m: inMat4x2f) = new Mat4x2f(
    m00 - m.m00, m01 - m.m01,
    m10 - m.m10, m11 - m.m11,
    m20 - m.m20, m21 - m.m21,
    m30 - m.m30, m31 - m.m31
  )

  /**
   * Component-wise division.
   */
  final def /(m: inMat4x2f) = new Mat4x2f(
    m00/m.m00, m01/m.m01,
    m10/m.m10, m11/m.m11,
    m20/m.m20, m21/m.m21,
    m30/m.m30, m31/m.m31
  )
  private[math] final def divByComp(s: Float) = new Mat4x2f(
    s/m00, s/m01,
    s/m10, s/m11,
    s/m20, s/m21,
    s/m30, s/m31
  )

  final def *(m: inMat2x4f) = new Mat2f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13
  )
  final def *(m: inMat3x4f) = new Mat3x2f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13,

    m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23,
    m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23
  )
  final def *(m: inMat4f) = new Mat4x2f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13,

    m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23,
    m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23,

    m00*m.m30 + m10*m.m31 + m20*m.m32 + m30*m.m33,
    m01*m.m30 + m11*m.m31 + m21*m.m32 + m31*m.m33
  )

  final def *(u: inVec4f) = new Vec2f(
    m00*u.x + m10*u.y + m20*u.z + m30*u.w,
    m01*u.x + m11*u.y + m21*u.z + m31*u.w
  )
  private[math] final def transposeMult(u: inVec2f) = new Vec4f(
    m00*u.x + m01*u.y,
    m10*u.x + m11*u.y,
    m20*u.x + m21*u.y,
    m30*u.x + m31*u.y
  )


  final override def equals(other: Any) :Boolean = {
    other match {
      case m: AnyMat4x2[_] =>
        d00 == m.d00 && d01 == m.d01 &&
        d10 == m.d10 && d11 == m.d11 &&
        d20 == m.d20 && d21 == m.d21 &&
        d30 == m.d30 && d31 == m.d31
      case _ =>
        false
    }
  }

  final override def hashCode :Int = {
    41 * (
      41 * (
        41 * (
          41 * (
            41 * (
              41 * (
                41 * (
                  41 + simplex3d.math.floatHashCode(m00)
                ) + simplex3d.math.floatHashCode(m01)
              ) + simplex3d.math.floatHashCode(m10)
            ) + simplex3d.math.floatHashCode(m11)
          ) + simplex3d.math.floatHashCode(m20)
        ) + simplex3d.math.floatHashCode(m21)
      ) + simplex3d.math.floatHashCode(m30)
    ) + simplex3d.math.floatHashCode(m31)
  }

  final override def toString :String = {
    val prefix = this match {
      case self: Immutable => "Const"
      case _ => ""
    }
    prefix + "Mat4x2" +
    "(" +
      m00 + "f, " + m01 + "f,   " + 
      m10 + "f, " + m11 + "f,   " + 
      m20 + "f, " + m21 + "f,   " + 
      m30 + "f, " + m31 + "f" +
    ")"
  }
}


@SerialVersionUID(8104346712419693669L)
final class ConstMat4x2f private[math] (
  c00: Float, c01: Float,
  c10: Float, c11: Float,
  c20: Float, c21: Float,
  c30: Float, c31: Float
) extends ReadMat4x2f with Immutable with Serializable
{
  p00 = c00; p01 = c01
  p10 = c10; p11 = c11
  p20 = c20; p21 = c21
  p30 = c30; p31 = c31


  type Clone = ConstMat4x2f
  override def clone = this
  def toConst = this
}

object ConstMat4x2f {
  def apply(s: Float) = new ConstMat4x2f(
    s, 0,
    0, s,
    0, 0,
    0, 0
  )

  /*main factory*/ def apply(
    m00: Float, m01: Float,
    m10: Float, m11: Float,
    m20: Float, m21: Float,
    m30: Float, m31: Float
  ) = new ConstMat4x2f(
    m00, m01,
    m10, m11,
    m20, m21,
    m30, m31
  )

  def apply(c0: AnyVec2[_], c1: AnyVec2[_], c2: AnyVec2[_], c3: AnyVec2[_]) = 
  new ConstMat4x2f(
    c0.fx, c0.fy,
    c1.fx, c1.fy,
    c2.fx, c2.fy,
    c3.fx, c3.fy
  )

  def apply(m: AnyMat[_]) = new ConstMat4x2f(
    m.f00, m.f01,
    m.f10, m.f11,
    m.f20, m.f21,
    m.f30, m.f31
  )

  implicit def toConst(m: ReadMat4x2f) = ConstMat4x2f(m)
}


@SerialVersionUID(8104346712419693669L)
final class Mat4x2f private[math] (
  c00: Float, c01: Float,
  c10: Float, c11: Float,
  c20: Float, c21: Float,
  c30: Float, c31: Float
)
extends ReadMat4x2f with Accessor with CompositeFormat
with Accessible with Serializable
{
  p00 = c00; p01 = c01
  p10 = c10; p11 = c11
  p20 = c20; p21 = c21
  p30 = c30; p31 = c31

  private[math] def this() = this(
    1, 0,
    0, 1,
    0, 0,
    0, 0
  )
  
  type Clone = Mat4x2f
  type Const = ConstMat4x2f

  type Accessor = Mat4x2f
  type Component = RFloat

  override def clone = Mat4x2f(this)
  def toConst = ConstMat4x2f(this)

  def :=(m: inMat4x2f) {
    m00 = m.m00; m01 = m.m01
    m10 = m.m10; m11 = m.m11
    m20 = m.m20; m21 = m.m21
    m30 = m.m30; m31 = m.m31
  }


  override def m00_=(s: Float) { p00 = s }
  override def m01_=(s: Float) { p01 = s }

  override def m10_=(s: Float) { p10 = s }
  override def m11_=(s: Float) { p11 = s }

  override def m20_=(s: Float) { p20 = s }
  override def m21_=(s: Float) { p21 = s }

  override def m30_=(s: Float) { p30 = s }
  override def m31_=(s: Float) { p31 = s }


  def *=(s: Float) {
    m00 *= s; m01 *= s;
    m10 *= s; m11 *= s;
    m20 *= s; m21 *= s;
    m30 *= s; m31 *= s
  }
  def /=(s: Float) { this *= (1/s) }

  def +=(s: Float) {
    m00 += s; m01 += s
    m10 += s; m11 += s
    m20 += s; m21 += s
    m30 += s; m31 += s
  }
  def -=(s: Float) { this += (-s) }

  def +=(m: inMat4x2f) {
    m00 += m.m00; m01 += m.m01;
    m10 += m.m10; m11 += m.m11;
    m20 += m.m20; m21 += m.m21;
    m30 += m.m30; m31 += m.m31
  }
  def -=(m: inMat4x2f) {
    m00 -= m.m00; m01 -= m.m01;
    m10 -= m.m10; m11 -= m.m11;
    m20 -= m.m20; m21 -= m.m21;
    m30 -= m.m30; m31 -= m.m31
  }

  def *=(m: inMat4f) {
    val t00 = m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03
    val t01 = m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03

    val t10 = m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13
    val t11 = m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13

    val t20 = m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23
    val t21 = m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23

    val t30 = m00*m.m30 + m10*m.m31 + m20*m.m32 + m30*m.m33
        m31 = m01*m.m30 + m11*m.m31 + m21*m.m32 + m31*m.m33

    m00 = t00; m01 = t01
    m10 = t10; m11 = t11
    m20 = t20; m21 = t21
    m30 = t30
  }
  /**
   * Component-wise division.
   */
  def /=(m: inMat4x2f) {
    m00 /= m.m00; m01 /= m.m01
    m10 /= m.m10; m11 /= m.m11
    m20 /= m.m20; m21 /= m.m21
    m30 /= m.m30; m31 /= m.m31
  }


  def update(c: Int, r: Int, s: Float) {
    def error() = throw new IndexOutOfBoundsException(
      "Trying to update index (" + c + ", " + r + ") in " + this.getClass.getSimpleName + "."
    )

    c match {
      case 0 =>
        r match {
          case 0 => m00 = s
          case 1 => m01 = s
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m10 = s
          case 1 => m11 = s
          case _ => error
        }
      case 2 =>
        r match {
          case 0 => m20 = s
          case 1 => m21 = s
          case _ => error
        }
      case 3 =>
        r match {
          case 0 => m30 = s
          case 1 => m31 = s
          case _ => error
        }
      case _ => error
    }
  }

  def update(c: Int, v: inVec2f) {
    c match {
      case 0 => m00 = v.x; m01 = v.y
      case 1 => m10 = v.x; m11 = v.y
      case 2 => m20 = v.x; m21 = v.y
      case 3 => m30 = v.x; m31 = v.y
      case j => throw new IndexOutOfBoundsException(
          "Trying to update column (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }
}

object Mat4x2f {
  final val Zero = ConstMat4x2f(0)
  final val Identity = ConstMat4x2f(1)

  final val Tag = classTag[Mat4x2f]
  final val ConstTag = classTag[ConstMat4x2f]
  final val ReadTag = classTag[ReadMat4x2f]

  def apply(s: Float) = new Mat4x2f(
    s, 0,
    0, s,
    0, 0,
    0, 0
  )

  /*main factory*/ def apply(
    m00: Float, m01: Float,
    m10: Float, m11: Float,
    m20: Float, m21: Float,
    m30: Float, m31: Float
  ) = new Mat4x2f(
    m00, m01,
    m10, m11,
    m20, m21,
    m30, m31
  )

  def apply(c0: AnyVec2[_], c1: AnyVec2[_], c2: AnyVec2[_], c3: AnyVec2[_]) = 
  new Mat4x2f(
    c0.fx, c0.fy,
    c1.fx, c1.fy,
    c2.fx, c2.fy,
    c3.fx, c3.fy
  )

  def apply(m: AnyMat[_]) = new Mat4x2f(
    m.f00, m.f01,
    m.f10, m.f11,
    m.f20, m.f21,
    m.f30, m.f31
  )

  def unapply(m: ReadMat4x2f) = Some((m(0), m(1), m(2), m(3)))
}
