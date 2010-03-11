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
sealed abstract class AnyMat3d extends Read3x3[ConstVec3d]
{
  // Column major order.
  def m00: Double; def m10: Double; def m20: Double // column
  def m01: Double; def m11: Double; def m21: Double // column
  def m02: Double; def m12: Double; def m22: Double // column

  private[math] def f00 = float(m00)
  private[math] def f10 = float(m10)
  private[math] def f20 = float(m20)

  private[math] def f01 = float(m01)
  private[math] def f11 = float(m11)
  private[math] def f21 = float(m21)

  private[math] def f02 = float(m02)
  private[math] def f12 = float(m12)
  private[math] def f22 = float(m22)


  private[math] def d00 = m00
  private[math] def d10 = m10
  private[math] def d20 = m20

  private[math] def d01 = m01
  private[math] def d11 = m11
  private[math] def d21 = m21

  private[math] def d02 = m02
  private[math] def d12 = m12
  private[math] def d22 = m22


  def apply(c: Int) :ConstVec3d = {
    c match {
      case 0 => new ConstVec3d(m00, m10, m20)
      case 1 => new ConstVec3d(m01, m11, m21)
      case 2 => new ConstVec3d(m02, m12, m22)
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 2, got " + j
        )
    }
  }

  def apply(c: Int, r: Int) :Double = {
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
      case 2 =>
        r match {
          case 0 => m02
          case 1 => m12
          case 2 => m22
          case _ => error
        }
      case _ => error
    }
  }

  def unary_+() :this.type = this
  def unary_-() = new Mat3d(
    -m00, -m10, -m20,
    -m01, -m11, -m21,
    -m02, -m12, -m22
  )
  def *(s: Double) = new Mat3d(
    s*m00, s*m10, s*m20,
    s*m01, s*m11, s*m21,
    s*m02, s*m12, s*m22
  )
  def /(s: Double) = { val inv = 1/s; new Mat3d(
    inv*m00, inv*m10, inv*m20,
    inv*m01, inv*m11, inv*m21,
    inv*m02, inv*m12, inv*m22
  )}

  def +(s: Double) = new Mat3d(
    m00 + s, m10 + s, m20 + s,
    m01 + s, m11 + s, m21 + s,
    m02 + s, m12 + s, m22 + s
  )
  def -(s: Double) = new Mat3d(
    m00 - s, m10 - s, m20 - s,
    m01 - s, m11 - s, m21 - s,
    m02 - s, m12 - s, m22 - s
  )

  def +(m: AnyMat3d) = new Mat3d(
    m00 + m.m00, m10 + m.m10, m20 + m.m20,
    m01 + m.m01, m11 + m.m11, m21 + m.m21,
    m02 + m.m02, m12 + m.m12, m22 + m.m22
  )
  def -(m: AnyMat3d) = new Mat3d(
    m00 - m.m00, m10 - m.m10, m20 - m.m20,
    m01 - m.m01, m11 - m.m11, m21 - m.m21,
    m02 - m.m02, m12 - m.m12, m22 - m.m22
  )

  /**
   * Component-wise devision.
   */
  def /(m: AnyMat3d) = new Mat3d(
    m00/m.m00, m10/m.m10, m20/m.m20,
    m01/m.m01, m11/m.m11, m21/m.m21,
    m02/m.m02, m12/m.m12, m22/m.m22
  )
  private[math] def divideByComponent(s: Double) = new Mat3d(
    s/m00, s/m10, s/m20,
    s/m01, s/m11, s/m21,
    s/m02, s/m12, s/m22
  )

  def *(m: AnyMat3x2d) = new Mat3x2d(
    m00*m.m00 + m01*m.m10 + m02*m.m20,
    m10*m.m00 + m11*m.m10 + m12*m.m20,
    m20*m.m00 + m21*m.m10 + m22*m.m20,

    m00*m.m01 + m01*m.m11 + m02*m.m21,
    m10*m.m01 + m11*m.m11 + m12*m.m21,
    m20*m.m01 + m21*m.m11 + m22*m.m21
  )
  def *(m: AnyMat3d) = new Mat3d(
    m00*m.m00 + m01*m.m10 + m02*m.m20,
    m10*m.m00 + m11*m.m10 + m12*m.m20,
    m20*m.m00 + m21*m.m10 + m22*m.m20,

    m00*m.m01 + m01*m.m11 + m02*m.m21,
    m10*m.m01 + m11*m.m11 + m12*m.m21,
    m20*m.m01 + m21*m.m11 + m22*m.m21,

    m00*m.m02 + m01*m.m12 + m02*m.m22,
    m10*m.m02 + m11*m.m12 + m12*m.m22,
    m20*m.m02 + m21*m.m12 + m22*m.m22
  )
  def *(m: AnyMat3x4d) = new Mat3x4d(
    m00*m.m00 + m01*m.m10 + m02*m.m20,
    m10*m.m00 + m11*m.m10 + m12*m.m20,
    m20*m.m00 + m21*m.m10 + m22*m.m20,

    m00*m.m01 + m01*m.m11 + m02*m.m21,
    m10*m.m01 + m11*m.m11 + m12*m.m21,
    m20*m.m01 + m21*m.m11 + m22*m.m21,

    m00*m.m02 + m01*m.m12 + m02*m.m22,
    m10*m.m02 + m11*m.m12 + m12*m.m22,
    m20*m.m02 + m21*m.m12 + m22*m.m22,

    m00*m.m03 + m01*m.m13 + m02*m.m23,
    m10*m.m03 + m11*m.m13 + m12*m.m23,
    m20*m.m03 + m21*m.m13 + m22*m.m23
  )

  def *(u: AnyVec3d) = new Vec3d(
    m00*u.x + m01*u.y + m02*u.z,
    m10*u.x + m11*u.y + m12*u.z,
    m20*u.x + m21*u.y + m22*u.z
  )
  private[math] def transposeMul(u: AnyVec3d) = new Vec3d(
    m00*u.x + m10*u.y + m20*u.z,
    m01*u.x + m11*u.y + m21*u.z,
    m02*u.x + m12*u.y + m22*u.z
  )

  def ==(m: AnyMat3d) :Boolean = {
    if (m eq null) false
    else
      m00 == m.m00 && m10 == m.m10 && m20 == m.m20 &&
      m01 == m.m01 && m11 == m.m11 && m21 == m.m21 &&
      m02 == m.m02 && m12 == m.m12 && m22 == m.m22
  }

  def !=(m: AnyMat3d) :Boolean = !(this == m)

  private[math] def hasErrors: Boolean = {
    import java.lang.Double._

    (
      isNaN(m00) || isInfinite(m00) ||
      isNaN(m10) || isInfinite(m10) ||
      isNaN(m20) || isInfinite(m20) ||

      isNaN(m01) || isInfinite(m01) ||
      isNaN(m11) || isInfinite(m11) ||
      isNaN(m21) || isInfinite(m21) ||

      isNaN(m02) || isInfinite(m02) ||
      isNaN(m12) || isInfinite(m12) ||
      isNaN(m22) || isInfinite(m22)
    )
  }

  override def equals(other: Any) :Boolean = {
    other match {
      case m: AnyMat3d => this == m
      case _ => false
    }
  }

  override def hashCode() :Int = {
    41 * (
      41 * (
        41 * (
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
        ) + m02.hashCode
      ) + m12.hashCode
    ) + m22.hashCode
  }

  override def toString() :String = {
    this.getClass.getSimpleName +
    "(" +
      m00 + ", " + m10 + ", " + m20 + "; " + 
      m01 + ", " + m11 + ", " + m21 + "; " + 
      m02 + ", " + m12 + ", " + m22 +
    ")"
  }
}

final class ConstMat3d private[math] (
  val m00: Double, val m10: Double, val m20: Double,
  val m01: Double, val m11: Double, val m21: Double,
  val m02: Double, val m12: Double, val m22: Double
) extends AnyMat3d with ConstMat[ConstVec3d]

object ConstMat3d {

  def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double,
    m02: Double, m12: Double, m22: Double
  ) = new ConstMat3d(
    m00, m10, m20,
    m01, m11, m21,
    m02, m12, m22
  )

  def apply(c0: Read3[_], c1: Read3[_], c2: Read3[_]) = 
  new ConstMat3d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz,
    c2.dx, c2.dy, c2.dz
  )

  def apply(m: Read3x3[_]) = new ConstMat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22
  )

  implicit def toConst(m: AnyMat3d) = ConstMat3d(m)
}


final class Mat3d private[math] (
  var m00: Double, var m10: Double, var m20: Double,
  var m01: Double, var m11: Double, var m21: Double,
  var m02: Double, var m12: Double, var m22: Double
) extends AnyMat3d with Mat[ConstVec3d]
{
  def *=(s: Double) {
    m00 *= s; m10 *= s; m20 *= s;
    m01 *= s; m11 *= s; m21 *= s;
    m02 *= s; m12 *= s; m22 *= s
  }
  def /=(s: Double) { val inv = 1/s;
    m00 *= inv; m10 *= inv; m20 *= inv;
    m01 *= inv; m11 *= inv; m21 *= inv;
    m02 *= inv; m12 *= inv; m22 *= inv
  }

  def +=(s: Double) {
    m00 += s; m10 += s; m20 += s
    m01 += s; m11 += s; m21 += s
    m02 += s; m12 += s; m22 += s
  }
  def -=(s: Double) {
    m00 -= s; m10 -= s; m20 -= s
    m01 -= s; m11 -= s; m21 -= s
    m02 -= s; m12 -= s; m22 -= s
  }

  def +=(m: AnyMat3d) {
    m00 += m.m00; m10 += m.m10; m20 += m.m20;
    m01 += m.m01; m11 += m.m11; m21 += m.m21;
    m02 += m.m02; m12 += m.m12; m22 += m.m22
  }
  def -=(m: AnyMat3d) {
    m00 -= m.m00; m10 -= m.m10; m20 -= m.m20;
    m01 -= m.m01; m11 -= m.m11; m21 -= m.m21;
    m02 -= m.m02; m12 -= m.m12; m22 -= m.m22
  }

  def *=(m: AnyMat3d) {
    val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20
    val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20
    val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20

    val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21
    val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21
    val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21

    val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22
    val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22
    val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22

    m00 = a00; m10 = a10; m20 = a20
    m01 = a01; m11 = a11; m21 = a21
    m02 = a02; m12 = a12; m22 = a22
  }
  /**
   * Component-wise devision.
   */
  def /=(m: AnyMat3d) {
    m00 /= m.m00; m10 /= m.m10; m20 /= m.m20
    m01 /= m.m01; m11 /= m.m11; m21 /= m.m21
    m02 /= m.m02; m12 /= m.m12; m22 /= m.m22
  }

  def :=(m: AnyMat3d) {
    m00 = m.m00; m10 = m.m10; m20 = m.m20;
    m01 = m.m01; m11 = m.m11; m21 = m.m21;
    m02 = m.m02; m12 = m.m12; m22 = m.m22
  }

  def set(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double,
    m02: Double, m12: Double, m22: Double
  ) {
    this.m00 = m00; this.m10 = m10; this.m20 = m20;
    this.m01 = m01; this.m11 = m11; this.m21 = m21;
    this.m02 = m02; this.m12 = m12; this.m22 = m22
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
      case 2 =>
        r match {
          case 0 => m02 = s
          case 1 => m12 = s
          case 2 => m22 = s
          case _ => error
        }
      case _ => error
    }
  }

  def update(c: Int, v: AnyVec2d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y
      case 1 => m01 = v.x; m11 = v.y
      case 2 => m02 = v.x; m12 = v.y
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 2, got " + j
        )
    }
  }

  def update(c: Int, v: AnyVec3d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y; m20 = v.z
      case 1 => m01 = v.x; m11 = v.y; m21 = v.z
      case 2 => m02 = v.x; m12 = v.y; m22 = v.z
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 2, got " + j
        )
    }
  }
}

object Mat3d {

  val Zero: ConstMat3d = Mat3d(0)
  val Identity: ConstMat3d = Mat3d(1)

  def apply(s: Double) = new Mat3d(
    s, 0, 0,
    0, s, 0,
    0, 0, s
  )

  def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double,
    m02: Double, m12: Double, m22: Double
  ) = new Mat3d(
    m00, m10, m20,
    m01, m11, m21,
    m02, m12, m22
  )

  def apply(c0: Read3[_], c1: Read3[_], c2: Read3[_]) = 
  new Mat3d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz,
    c2.dx, c2.dy, c2.dz
  )

  def apply(m: Read2x2[_]) = new Mat3d(
    m.d00, m.d10, 0,
    m.d01, m.d11, 0,
    0, 0, 1
  )

  def apply(m: Read2x3[_]) = new Mat3d(
    m.d00, m.d10, 0,
    m.d01, m.d11, 0,
    m.d02, m.d12, 1
  )

  def apply(m: Read2x4[_]) = new Mat3d(
    m.d00, m.d10, 0,
    m.d01, m.d11, 0,
    m.d02, m.d12, 1
  )

  def apply(m: Read3x2[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    0, 0, 1
  )

  def apply(m: Read3x3[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22
  )

  def apply(m: Read3x4[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22
  )

  def apply(m: Read4x2[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    0, 0, 1
  )

  def apply(m: Read4x3[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22
  )

  def apply(m: Read4x4[_]) = new Mat3d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22
  )

  def unapply(m: AnyMat3d) = Some((m(0), m(1), m(2)))

  implicit def toMutable(m: AnyMat3d) = Mat3d(m)
}
