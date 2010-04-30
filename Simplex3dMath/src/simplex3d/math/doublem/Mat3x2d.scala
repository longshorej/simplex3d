/*
 * Simplex3d, DoubleMath module
 * Copyright (C) 2009 Simplex3d Team
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

package simplex3d.math.doublem

import simplex3d.math._
import simplex3d.math.BaseMath._
import simplex3d.math.doublem.DoubleMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyMat3x2d extends Read3x2[ConstVec3d]
{
  // Column major order.
  def m00: Double; def m10: Double; def m20: Double // column
  def m01: Double; def m11: Double; def m21: Double // column

  private[math] final override def f00 = float(m00)
  private[math] final override def f10 = float(m10)
  private[math] final override def f20 = float(m20)

  private[math] final override def f01 = float(m01)
  private[math] final override def f11 = float(m11)
  private[math] final override def f21 = float(m21)


  private[math] final override def d00 = m00
  private[math] final override def d10 = m10
  private[math] final override def d20 = m20

  private[math] final override def d01 = m01
  private[math] final override def d11 = m11
  private[math] final override def d21 = m21


  final def apply(c: Int) :ConstVec3d = {
    c match {
      case 0 => new ConstVec3d(m00, m10, m20)
      case 1 => new ConstVec3d(m01, m11, m21)
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }

  final def apply(c: Int, r: Int) :Double = {
    def error() :Double = throw new IndexOutOfBoundsException(
      "Trying to read index (" + c + ", " + r + ") in " +
      this.getClass.getSimpleName
    )

    c match {
      case 0 =>
        r match {
          case 0 => m00
          case 1 => m10
          case 2 => m20
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m01
          case 1 => m11
          case 2 => m21
          case _ => error
        }
      case _ => error
    }
  }

  final def unary_+() :AnyMat3x2d = this
  final def unary_-() = new Mat3x2d(
    -m00, -m10, -m20,
    -m01, -m11, -m21
  )
  final def *(s: Double) = new Mat3x2d(
    s*m00, s*m10, s*m20,
    s*m01, s*m11, s*m21
  )
  final def /(s: Double) = { val inv = 1/s; new Mat3x2d(
    inv*m00, inv*m10, inv*m20,
    inv*m01, inv*m11, inv*m21
  )}

  final def +(s: Double) = new Mat3x2d(
    m00 + s, m10 + s, m20 + s,
    m01 + s, m11 + s, m21 + s
  )
  final def -(s: Double) = new Mat3x2d(
    m00 - s, m10 - s, m20 - s,
    m01 - s, m11 - s, m21 - s
  )

  final def +(m: inMat3x2d) = new Mat3x2d(
    m00 + m.m00, m10 + m.m10, m20 + m.m20,
    m01 + m.m01, m11 + m.m11, m21 + m.m21
  )
  final def -(m: inMat3x2d) = new Mat3x2d(
    m00 - m.m00, m10 - m.m10, m20 - m.m20,
    m01 - m.m01, m11 - m.m11, m21 - m.m21
  )

  /**
   * Component-wise devision.
   */
  final def /(m: inMat3x2d) = new Mat3x2d(
    m00/m.m00, m10/m.m10, m20/m.m20,
    m01/m.m01, m11/m.m11, m21/m.m21
  )
  private[math] final def divideByComponent(s: Double) = new Mat3x2d(
    s/m00, s/m10, s/m20,
    s/m01, s/m11, s/m21
  )

  final def *(m: inMat2d) = new Mat3x2d(
    m00*m.m00 + m01*m.m10,
    m10*m.m00 + m11*m.m10,
    m20*m.m00 + m21*m.m10,

    m00*m.m01 + m01*m.m11,
    m10*m.m01 + m11*m.m11,
    m20*m.m01 + m21*m.m11
  )
  final def *(m: inMat2x3d) = new Mat3d(
    m00*m.m00 + m01*m.m10,
    m10*m.m00 + m11*m.m10,
    m20*m.m00 + m21*m.m10,

    m00*m.m01 + m01*m.m11,
    m10*m.m01 + m11*m.m11,
    m20*m.m01 + m21*m.m11,

    m00*m.m02 + m01*m.m12,
    m10*m.m02 + m11*m.m12,
    m20*m.m02 + m21*m.m12
  )
  final def *(m: inMat2x4d) = new Mat3x4d(
    m00*m.m00 + m01*m.m10,
    m10*m.m00 + m11*m.m10,
    m20*m.m00 + m21*m.m10,

    m00*m.m01 + m01*m.m11,
    m10*m.m01 + m11*m.m11,
    m20*m.m01 + m21*m.m11,

    m00*m.m02 + m01*m.m12,
    m10*m.m02 + m11*m.m12,
    m20*m.m02 + m21*m.m12,

    m00*m.m03 + m01*m.m13,
    m10*m.m03 + m11*m.m13,
    m20*m.m03 + m21*m.m13
  )

  final def *(u: inVec2d) = new Vec3d(
    m00*u.x + m01*u.y,
    m10*u.x + m11*u.y,
    m20*u.x + m21*u.y
  )
  private[math] final def transposeMul(u: inVec3d) = new Vec2d(
    m00*u.x + m10*u.y + m20*u.z,
    m01*u.x + m11*u.y + m21*u.z
  )

  final def ==(m: inMat3x2d) :Boolean = {
    if (m eq null) false
    else
      m00 == m.m00 && m10 == m.m10 && m20 == m.m20 &&
      m01 == m.m01 && m11 == m.m11 && m21 == m.m21
  }

  final def !=(m: inMat3x2d) :Boolean = !(this == m)

  private[math] final def hasErrors: Boolean = {
    import java.lang.Double._

    (
      isNaN(m00) || isInfinite(m00) ||
      isNaN(m10) || isInfinite(m10) ||
      isNaN(m20) || isInfinite(m20) ||

      isNaN(m01) || isInfinite(m01) ||
      isNaN(m11) || isInfinite(m11) ||
      isNaN(m21) || isInfinite(m21)
    )
  }

  final override def equals(other: Any) :Boolean = {
    other match {
      case m: inMat3x2d => this == m
      case _ => false
    }
  }

  final override def hashCode() :Int = {
    41 * (
      41 * (
        41 * (
          41 * (
            41 * (
              41 + m00.hashCode
            ) + m10.hashCode
          ) + m20.hashCode
        ) + m01.hashCode
      ) + m11.hashCode
    ) + m21.hashCode
  }

  final override def toString() :String = {
    this.getClass.getSimpleName +
    "(" +
      m00 + ", " + m10 + ", " + m20 + "; " + 
      m01 + ", " + m11 + ", " + m21 +
    ")"
  }
}


@serializable @SerialVersionUID(5359695191257934190L)
final class ConstMat3x2d private[math] (
  val m00: Double, val m10: Double, val m20: Double,
  val m01: Double, val m11: Double, val m21: Double
) extends AnyMat3x2d with Immutable

object ConstMat3x2d {

  def apply(s: Double) = new ConstMat3x2d(
    s, 0, 0,
    0, s, 0
  )

  /* @inline */ def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double
  ) = new ConstMat3x2d(
    m00, m10, m20,
    m01, m11, m21
  )

  def apply(c0: Read3[_], c1: Read3[_]) = 
  new ConstMat3x2d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz
  )

  def apply(m: ReadMat[_]) = new ConstMat3x2d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21
  )

  implicit def toConst(m: AnyMat3x2d) = ConstMat3x2d(m)
}


@serializable @SerialVersionUID(5359695191257934190L)
final class Mat3x2d private[math] (
  var m00: Double, var m10: Double, var m20: Double,
  var m01: Double, var m11: Double, var m21: Double
) extends AnyMat3x2d with Mutable with Implicits[On]
{
  def *=(s: Double) {
    m00 *= s; m10 *= s; m20 *= s;
    m01 *= s; m11 *= s; m21 *= s
  }
  def /=(s: Double) { val inv = 1/s;
    m00 *= inv; m10 *= inv; m20 *= inv;
    m01 *= inv; m11 *= inv; m21 *= inv
  }

  def +=(s: Double) {
    m00 += s; m10 += s; m20 += s
    m01 += s; m11 += s; m21 += s
  }
  def -=(s: Double) {
    m00 -= s; m10 -= s; m20 -= s
    m01 -= s; m11 -= s; m21 -= s
  }

  def +=(m: inMat3x2d) {
    m00 += m.m00; m10 += m.m10; m20 += m.m20;
    m01 += m.m01; m11 += m.m11; m21 += m.m21
  }
  def -=(m: inMat3x2d) {
    m00 -= m.m00; m10 -= m.m10; m20 -= m.m20;
    m01 -= m.m01; m11 -= m.m11; m21 -= m.m21
  }

  def *=(m: inMat2d) {
    val a00 = m00*m.m00 + m01*m.m10
    val a10 = m10*m.m00 + m11*m.m10
    val a20 = m20*m.m00 + m21*m.m10

    val a01 = m00*m.m01 + m01*m.m11
    val a11 = m10*m.m01 + m11*m.m11
    val a21 = m20*m.m01 + m21*m.m11

    m00 = a00; m10 = a10; m20 = a20
    m01 = a01; m11 = a11; m21 = a21
  }
  /**
   * Component-wise devision.
   */
  def /=(m: inMat3x2d) {
    m00 /= m.m00; m10 /= m.m10; m20 /= m.m20
    m01 /= m.m01; m11 /= m.m11; m21 /= m.m21
  }

  def :=(m: inMat3x2d) {
    m00 = m.m00; m10 = m.m10; m20 = m.m20;
    m01 = m.m01; m11 = m.m11; m21 = m.m21
  }

  def set(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double
  ) {
    this.m00 = m00; this.m10 = m10; this.m20 = m20;
    this.m01 = m01; this.m11 = m11; this.m21 = m21
  }

  def update(c: Int, r: Int, s: Double) {
    def error() = throw new IndexOutOfBoundsException(
      "Trying to update index (" + c + ", " + r + ") in " +
      this.getClass.getSimpleName
    )

    c match {
      case 0 =>
        r match {
          case 0 => m00 = s
          case 1 => m10 = s
          case 2 => m20 = s
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m01 = s
          case 1 => m11 = s
          case 2 => m21 = s
          case _ => error
        }
      case _ => error
    }
  }

  def update(c: Int, v: inVec2d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y
      case 1 => m01 = v.x; m11 = v.y
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }

  def update(c: Int, v: inVec3d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y; m20 = v.z
      case 1 => m01 = v.x; m11 = v.y; m21 = v.z
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 1, got " + j
        )
    }
  }
}

object Mat3x2d {

  val Zero = ConstMat3x2d(0)
  val Identity = ConstMat3x2d(1)

  def apply(s: Double) = new Mat3x2d(
    s, 0, 0,
    0, s, 0
  )

  /* @inline */ def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double
  ) = new Mat3x2d(
    m00, m10, m20,
    m01, m11, m21
  )

  def apply(c0: Read3[_], c1: Read3[_]) = 
  new Mat3x2d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz
  )

  def apply(m: ReadMat[_]) = new Mat3x2d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21
  )

  def unapply(m: AnyMat3x2d) = Some((m(0), m(1)))

  implicit def toMutable(m: AnyMat3x2d) = Mat3x2d(m)
}
