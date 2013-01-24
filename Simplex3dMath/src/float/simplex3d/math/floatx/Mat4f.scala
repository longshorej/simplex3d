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
sealed abstract class ReadMat4f extends ProtectedMat4f[Float]
with Protected with Serializable
{

  type Clone <: ReadMat4f
  def toConst :ConstMat4f
  
  type Read = ReadMat4f
  type Mutable = Mat4f
  final def mutableCopy = Mat4f(this)

  // Column major order.
  final def m00 = p00; final def m01 = p01; final def m02 = p02; final def m03 = p03
  final def m10 = p10; final def m11 = p11; final def m12 = p12; final def m13 = p13
  final def m20 = p20; final def m21 = p21; final def m22 = p22; final def m23 = p23
  final def m30 = p30; final def m31 = p31; final def m32 = p32; final def m33 = p33


  protected def m00_=(s: Float) { throw new UnsupportedOperationException }
  protected def m01_=(s: Float) { throw new UnsupportedOperationException }
  protected def m02_=(s: Float) { throw new UnsupportedOperationException }
  protected def m03_=(s: Float) { throw new UnsupportedOperationException }

  protected def m10_=(s: Float) { throw new UnsupportedOperationException }
  protected def m11_=(s: Float) { throw new UnsupportedOperationException }
  protected def m12_=(s: Float) { throw new UnsupportedOperationException }
  protected def m13_=(s: Float) { throw new UnsupportedOperationException }

  protected def m20_=(s: Float) { throw new UnsupportedOperationException }
  protected def m21_=(s: Float) { throw new UnsupportedOperationException }
  protected def m22_=(s: Float) { throw new UnsupportedOperationException }
  protected def m23_=(s: Float) { throw new UnsupportedOperationException }

  protected def m30_=(s: Float) { throw new UnsupportedOperationException }
  protected def m31_=(s: Float) { throw new UnsupportedOperationException }
  protected def m32_=(s: Float) { throw new UnsupportedOperationException }
  protected def m33_=(s: Float) { throw new UnsupportedOperationException }


  private[math] final override def f00 = m00
  private[math] final override def f01 = m01
  private[math] final override def f02 = m02
  private[math] final override def f03 = m03

  private[math] final override def f10 = m10
  private[math] final override def f11 = m11
  private[math] final override def f12 = m12
  private[math] final override def f13 = m13

  private[math] final override def f20 = m20
  private[math] final override def f21 = m21
  private[math] final override def f22 = m22
  private[math] final override def f23 = m23

  private[math] final override def f30 = m30
  private[math] final override def f31 = m31
  private[math] final override def f32 = m32
  private[math] final override def f33 = m33


  private[math] final override def d00 = m00
  private[math] final override def d01 = m01
  private[math] final override def d02 = m02
  private[math] final override def d03 = m03

  private[math] final override def d10 = m10
  private[math] final override def d11 = m11
  private[math] final override def d12 = m12
  private[math] final override def d13 = m13

  private[math] final override def d20 = m20
  private[math] final override def d21 = m21
  private[math] final override def d22 = m22
  private[math] final override def d23 = m23

  private[math] final override def d30 = m30
  private[math] final override def d31 = m31
  private[math] final override def d32 = m32
  private[math] final override def d33 = m33


  final def apply(c: Int) :ConstVec4f = {
    c match {
      case 0 => new ConstVec4f(m00, m01, m02, m03)
      case 1 => new ConstVec4f(m10, m11, m12, m13)
      case 2 => new ConstVec4f(m20, m21, m22, m23)
      case 3 => new ConstVec4f(m30, m31, m32, m33)
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
          case 2 => m02
          case 3 => m03
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m10
          case 1 => m11
          case 2 => m12
          case 3 => m13
          case _ => error
        }
      case 2 =>
        r match {
          case 0 => m20
          case 1 => m21
          case 2 => m22
          case 3 => m23
          case _ => error
        }
      case 3 =>
        r match {
          case 0 => m30
          case 1 => m31
          case 2 => m32
          case 3 => m33
          case _ => error
        }
      case _ => error
    }
  }

  final def unary_+() :ReadMat4f = this
  final def unary_-() = new Mat4f(
    -m00, -m01, -m02, -m03,
    -m10, -m11, -m12, -m13,
    -m20, -m21, -m22, -m23,
    -m30, -m31, -m32, -m33
  )
  final def *(s: Float) = new Mat4f(
    s*m00, s*m01, s*m02, s*m03,
    s*m10, s*m11, s*m12, s*m13,
    s*m20, s*m21, s*m22, s*m23,
    s*m30, s*m31, s*m32, s*m33
  )
  final def /(s: Float) = this * (1/s)

  final def +(s: Float) = new Mat4f(
    m00 + s, m01 + s, m02 + s, m03 + s,
    m10 + s, m11 + s, m12 + s, m13 + s,
    m20 + s, m21 + s, m22 + s, m23 + s,
    m30 + s, m31 + s, m32 + s, m33 + s
  )
  final def -(s: Float) = this + (-s)

  final def +(m: inMat4f) = new Mat4f(
    m00 + m.m00, m01 + m.m01, m02 + m.m02, m03 + m.m03,
    m10 + m.m10, m11 + m.m11, m12 + m.m12, m13 + m.m13,
    m20 + m.m20, m21 + m.m21, m22 + m.m22, m23 + m.m23,
    m30 + m.m30, m31 + m.m31, m32 + m.m32, m33 + m.m33
  )
  final def -(m: inMat4f) = new Mat4f(
    m00 - m.m00, m01 - m.m01, m02 - m.m02, m03 - m.m03,
    m10 - m.m10, m11 - m.m11, m12 - m.m12, m13 - m.m13,
    m20 - m.m20, m21 - m.m21, m22 - m.m22, m23 - m.m23,
    m30 - m.m30, m31 - m.m31, m32 - m.m32, m33 - m.m33
  )

  /**
   * Component-wise division.
   */
  final def /(m: inMat4f) = new Mat4f(
    m00/m.m00, m01/m.m01, m02/m.m02, m03/m.m03,
    m10/m.m10, m11/m.m11, m12/m.m12, m13/m.m13,
    m20/m.m20, m21/m.m21, m22/m.m22, m23/m.m23,
    m30/m.m30, m31/m.m31, m32/m.m32, m33/m.m33
  )
  private[math] final def divByComp(s: Float) = new Mat4f(
    s/m00, s/m01, s/m02, s/m03,
    s/m10, s/m11, s/m12, s/m13,
    s/m20, s/m21, s/m22, s/m23,
    s/m30, s/m31, s/m32, s/m33
  )

  final def *(m: inMat2x4f) = new Mat2x4f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,
    m02*m.m00 + m12*m.m01 + m22*m.m02 + m32*m.m03,
    m03*m.m00 + m13*m.m01 + m23*m.m02 + m33*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13,
    m02*m.m10 + m12*m.m11 + m22*m.m12 + m32*m.m13,
    m03*m.m10 + m13*m.m11 + m23*m.m12 + m33*m.m13
  )
  final def *(m: inMat3x4f) = new Mat3x4f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,
    m02*m.m00 + m12*m.m01 + m22*m.m02 + m32*m.m03,
    m03*m.m00 + m13*m.m01 + m23*m.m02 + m33*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13,
    m02*m.m10 + m12*m.m11 + m22*m.m12 + m32*m.m13,
    m03*m.m10 + m13*m.m11 + m23*m.m12 + m33*m.m13,

    m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23,
    m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23,
    m02*m.m20 + m12*m.m21 + m22*m.m22 + m32*m.m23,
    m03*m.m20 + m13*m.m21 + m23*m.m22 + m33*m.m23
  )
  final def *(m: inMat4f) = new Mat4f(
    m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03,
    m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03,
    m02*m.m00 + m12*m.m01 + m22*m.m02 + m32*m.m03,
    m03*m.m00 + m13*m.m01 + m23*m.m02 + m33*m.m03,

    m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13,
    m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13,
    m02*m.m10 + m12*m.m11 + m22*m.m12 + m32*m.m13,
    m03*m.m10 + m13*m.m11 + m23*m.m12 + m33*m.m13,

    m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23,
    m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23,
    m02*m.m20 + m12*m.m21 + m22*m.m22 + m32*m.m23,
    m03*m.m20 + m13*m.m21 + m23*m.m22 + m33*m.m23,

    m00*m.m30 + m10*m.m31 + m20*m.m32 + m30*m.m33,
    m01*m.m30 + m11*m.m31 + m21*m.m32 + m31*m.m33,
    m02*m.m30 + m12*m.m31 + m22*m.m32 + m32*m.m33,
    m03*m.m30 + m13*m.m31 + m23*m.m32 + m33*m.m33
  )

  final def *(u: inVec4f) = new Vec4f(
    m00*u.x + m10*u.y + m20*u.z + m30*u.w,
    m01*u.x + m11*u.y + m21*u.z + m31*u.w,
    m02*u.x + m12*u.y + m22*u.z + m32*u.w,
    m03*u.x + m13*u.y + m23*u.z + m33*u.w
  )
  private[math] final def transposeMult(u: inVec4f) = new Vec4f(
    m00*u.x + m01*u.y + m02*u.z + m03*u.w,
    m10*u.x + m11*u.y + m12*u.z + m13*u.w,
    m20*u.x + m21*u.y + m22*u.z + m23*u.w,
    m30*u.x + m31*u.y + m32*u.z + m33*u.w
  )


  final override def equals(other: Any) :Boolean = {
    other match {
      case m: AnyMat4[_] =>
        d00 == m.d00 && d01 == m.d01 && d02 == m.d02 && d03 == m.d03 &&
        d10 == m.d10 && d11 == m.d11 && d12 == m.d12 && d13 == m.d13 &&
        d20 == m.d20 && d21 == m.d21 && d22 == m.d22 && d23 == m.d23 &&
        d30 == m.d30 && d31 == m.d31 && d32 == m.d32 && d33 == m.d33
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
                  41 * (
                    41 * (
                      41 * (
                        41 * (
                          41 * (
                            41 * (
                              41 * (
                                41 * (
                                  41 + simplex3d.math.floatHashCode(m00)
                                ) + simplex3d.math.floatHashCode(m01)
                              ) + simplex3d.math.floatHashCode(m02)
                            ) + simplex3d.math.floatHashCode(m03)
                          ) + simplex3d.math.floatHashCode(m10)
                        ) + simplex3d.math.floatHashCode(m11)
                      ) + simplex3d.math.floatHashCode(m12)
                    ) + simplex3d.math.floatHashCode(m13)
                  ) + simplex3d.math.floatHashCode(m20)
                ) + simplex3d.math.floatHashCode(m21)
              ) + simplex3d.math.floatHashCode(m22)
            ) + simplex3d.math.floatHashCode(m23)
          ) + simplex3d.math.floatHashCode(m30)
        ) + simplex3d.math.floatHashCode(m31)
      ) + simplex3d.math.floatHashCode(m32)
    ) + simplex3d.math.floatHashCode(m33)
  }

  final override def toString :String = {
    val prefix = this match {
      case self: Immutable => "Const"
      case _ => ""
    }
    prefix + "Mat4" +
    "(" +
      m00 + "f, " + m01 + "f, " + m02 + "f, " + m03 + "f,   " + 
      m10 + "f, " + m11 + "f, " + m12 + "f, " + m13 + "f,   " + 
      m20 + "f, " + m21 + "f, " + m22 + "f, " + m23 + "f,   " + 
      m30 + "f, " + m31 + "f, " + m32 + "f, " + m33 + "f" +
    ")"
  }
}


@SerialVersionUID(8104346712419693669L)
final class ConstMat4f private[math] (
  c00: Float, c01: Float, c02: Float, c03: Float,
  c10: Float, c11: Float, c12: Float, c13: Float,
  c20: Float, c21: Float, c22: Float, c23: Float,
  c30: Float, c31: Float, c32: Float, c33: Float
) extends ReadMat4f with Immutable with Serializable
{
  p00 = c00; p01 = c01; p02 = c02; p03 = c03
  p10 = c10; p11 = c11; p12 = c12; p13 = c13
  p20 = c20; p21 = c21; p22 = c22; p23 = c23
  p30 = c30; p31 = c31; p32 = c32; p33 = c33


  type Clone = ConstMat4f
  override def clone = this
  def toConst = this
}

object ConstMat4f {
  def apply(s: Float) = new ConstMat4f(
    s, 0, 0, 0,
    0, s, 0, 0,
    0, 0, s, 0,
    0, 0, 0, s
  )

  /*main factory*/ def apply(
    m00: Float, m01: Float, m02: Float, m03: Float,
    m10: Float, m11: Float, m12: Float, m13: Float,
    m20: Float, m21: Float, m22: Float, m23: Float,
    m30: Float, m31: Float, m32: Float, m33: Float
  ) = new ConstMat4f(
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33
  )

  def apply(c0: AnyVec4[_], c1: AnyVec4[_], c2: AnyVec4[_], c3: AnyVec4[_]) = 
  new ConstMat4f(
    c0.fx, c0.fy, c0.fz, c0.fw,
    c1.fx, c1.fy, c1.fz, c1.fw,
    c2.fx, c2.fy, c2.fz, c2.fw,
    c3.fx, c3.fy, c3.fz, c3.fw
  )

  def apply(m: AnyMat[_]) = new ConstMat4f(
    m.f00, m.f01, m.f02, m.f03,
    m.f10, m.f11, m.f12, m.f13,
    m.f20, m.f21, m.f22, m.f23,
    m.f30, m.f31, m.f32, m.f33
  )

  implicit def toConst(m: ReadMat4f) = ConstMat4f(m)
}


@SerialVersionUID(8104346712419693669L)
final class Mat4f private[math] (
  c00: Float, c01: Float, c02: Float, c03: Float,
  c10: Float, c11: Float, c12: Float, c13: Float,
  c20: Float, c21: Float, c22: Float, c23: Float,
  c30: Float, c31: Float, c32: Float, c33: Float
)
extends ReadMat4f with Accessor with CompositeFormat
with Accessible with Serializable
{
  p00 = c00; p01 = c01; p02 = c02; p03 = c03
  p10 = c10; p11 = c11; p12 = c12; p13 = c13
  p20 = c20; p21 = c21; p22 = c22; p23 = c23
  p30 = c30; p31 = c31; p32 = c32; p33 = c33

  private[math] def this() = this(
    1, 0, 0, 0,
    0, 1, 0, 0,
    0, 0, 1, 0,
    0, 0, 0, 1
  )
  
  type Clone = Mat4f
  type Const = ConstMat4f

  type Accessor = Mat4f
  type Component = RFloat

  override def clone = Mat4f(this)
  def toConst = ConstMat4f(this)

  def :=(m: inMat4f) {
    m00 = m.m00; m01 = m.m01; m02 = m.m02; m03 = m.m03
    m10 = m.m10; m11 = m.m11; m12 = m.m12; m13 = m.m13
    m20 = m.m20; m21 = m.m21; m22 = m.m22; m23 = m.m23
    m30 = m.m30; m31 = m.m31; m32 = m.m32; m33 = m.m33
  }


  override def m00_=(s: Float) { p00 = s }
  override def m01_=(s: Float) { p01 = s }
  override def m02_=(s: Float) { p02 = s }
  override def m03_=(s: Float) { p03 = s }

  override def m10_=(s: Float) { p10 = s }
  override def m11_=(s: Float) { p11 = s }
  override def m12_=(s: Float) { p12 = s }
  override def m13_=(s: Float) { p13 = s }

  override def m20_=(s: Float) { p20 = s }
  override def m21_=(s: Float) { p21 = s }
  override def m22_=(s: Float) { p22 = s }
  override def m23_=(s: Float) { p23 = s }

  override def m30_=(s: Float) { p30 = s }
  override def m31_=(s: Float) { p31 = s }
  override def m32_=(s: Float) { p32 = s }
  override def m33_=(s: Float) { p33 = s }


  def *=(s: Float) {
    m00 *= s; m01 *= s; m02 *= s; m03 *= s;
    m10 *= s; m11 *= s; m12 *= s; m13 *= s;
    m20 *= s; m21 *= s; m22 *= s; m23 *= s;
    m30 *= s; m31 *= s; m32 *= s; m33 *= s
  }
  def /=(s: Float) { this *= (1/s) }

  def +=(s: Float) {
    m00 += s; m01 += s; m02 += s; m03 += s
    m10 += s; m11 += s; m12 += s; m13 += s
    m20 += s; m21 += s; m22 += s; m23 += s
    m30 += s; m31 += s; m32 += s; m33 += s
  }
  def -=(s: Float) { this += (-s) }

  def +=(m: inMat4f) {
    m00 += m.m00; m01 += m.m01; m02 += m.m02; m03 += m.m03;
    m10 += m.m10; m11 += m.m11; m12 += m.m12; m13 += m.m13;
    m20 += m.m20; m21 += m.m21; m22 += m.m22; m23 += m.m23;
    m30 += m.m30; m31 += m.m31; m32 += m.m32; m33 += m.m33
  }
  def -=(m: inMat4f) {
    m00 -= m.m00; m01 -= m.m01; m02 -= m.m02; m03 -= m.m03;
    m10 -= m.m10; m11 -= m.m11; m12 -= m.m12; m13 -= m.m13;
    m20 -= m.m20; m21 -= m.m21; m22 -= m.m22; m23 -= m.m23;
    m30 -= m.m30; m31 -= m.m31; m32 -= m.m32; m33 -= m.m33
  }

  def *=(m: inMat4f) {
    val t00 = m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03
    val t01 = m01*m.m00 + m11*m.m01 + m21*m.m02 + m31*m.m03
    val t02 = m02*m.m00 + m12*m.m01 + m22*m.m02 + m32*m.m03
    val t03 = m03*m.m00 + m13*m.m01 + m23*m.m02 + m33*m.m03

    val t10 = m00*m.m10 + m10*m.m11 + m20*m.m12 + m30*m.m13
    val t11 = m01*m.m10 + m11*m.m11 + m21*m.m12 + m31*m.m13
    val t12 = m02*m.m10 + m12*m.m11 + m22*m.m12 + m32*m.m13
    val t13 = m03*m.m10 + m13*m.m11 + m23*m.m12 + m33*m.m13

    val t20 = m00*m.m20 + m10*m.m21 + m20*m.m22 + m30*m.m23
    val t21 = m01*m.m20 + m11*m.m21 + m21*m.m22 + m31*m.m23
    val t22 = m02*m.m20 + m12*m.m21 + m22*m.m22 + m32*m.m23
    val t23 = m03*m.m20 + m13*m.m21 + m23*m.m22 + m33*m.m23

    val t30 = m00*m.m30 + m10*m.m31 + m20*m.m32 + m30*m.m33
    val t31 = m01*m.m30 + m11*m.m31 + m21*m.m32 + m31*m.m33
    val t32 = m02*m.m30 + m12*m.m31 + m22*m.m32 + m32*m.m33
        m33 = m03*m.m30 + m13*m.m31 + m23*m.m32 + m33*m.m33

    m00 = t00; m01 = t01; m02 = t02; m03 = t03
    m10 = t10; m11 = t11; m12 = t12; m13 = t13
    m20 = t20; m21 = t21; m22 = t22; m23 = t23
    m30 = t30; m31 = t31; m32 = t32
  }
  /**
   * Component-wise division.
   */
  def /=(m: inMat4f) {
    m00 /= m.m00; m01 /= m.m01; m02 /= m.m02; m03 /= m.m03
    m10 /= m.m10; m11 /= m.m11; m12 /= m.m12; m13 /= m.m13
    m20 /= m.m20; m21 /= m.m21; m22 /= m.m22; m23 /= m.m23
    m30 /= m.m30; m31 /= m.m31; m32 /= m.m32; m33 /= m.m33
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
          case 2 => m02 = s
          case 3 => m03 = s
          case _ => error
        }
      case 1 =>
        r match {
          case 0 => m10 = s
          case 1 => m11 = s
          case 2 => m12 = s
          case 3 => m13 = s
          case _ => error
        }
      case 2 =>
        r match {
          case 0 => m20 = s
          case 1 => m21 = s
          case 2 => m22 = s
          case 3 => m23 = s
          case _ => error
        }
      case 3 =>
        r match {
          case 0 => m30 = s
          case 1 => m31 = s
          case 2 => m32 = s
          case 3 => m33 = s
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

  def update(c: Int, v: inVec3f) {
    c match {
      case 0 => m00 = v.x; m01 = v.y; m02 = v.z
      case 1 => m10 = v.x; m11 = v.y; m12 = v.z
      case 2 => m20 = v.x; m21 = v.y; m22 = v.z
      case 3 => m30 = v.x; m31 = v.y; m32 = v.z
      case j => throw new IndexOutOfBoundsException(
          "Trying to update column (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }

  def update(c: Int, v: inVec4f) {
    c match {
      case 0 => m00 = v.x; m01 = v.y; m02 = v.z; m03 = v.w
      case 1 => m10 = v.x; m11 = v.y; m12 = v.z; m13 = v.w
      case 2 => m20 = v.x; m21 = v.y; m22 = v.z; m23 = v.w
      case 3 => m30 = v.x; m31 = v.y; m32 = v.z; m33 = v.w
      case j => throw new IndexOutOfBoundsException(
          "Trying to update column (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }
}

object Mat4f {
  final val Zero = ConstMat4f(0)
  final val Identity = ConstMat4f(1)

  final val Tag = classTag[Mat4f]
  final val ConstTag = classTag[ConstMat4f]
  final val ReadTag = classTag[ReadMat4f]

  def apply(s: Float) = new Mat4f(
    s, 0, 0, 0,
    0, s, 0, 0,
    0, 0, s, 0,
    0, 0, 0, s
  )

  /*main factory*/ def apply(
    m00: Float, m01: Float, m02: Float, m03: Float,
    m10: Float, m11: Float, m12: Float, m13: Float,
    m20: Float, m21: Float, m22: Float, m23: Float,
    m30: Float, m31: Float, m32: Float, m33: Float
  ) = new Mat4f(
    m00, m01, m02, m03,
    m10, m11, m12, m13,
    m20, m21, m22, m23,
    m30, m31, m32, m33
  )

  def apply(c0: AnyVec4[_], c1: AnyVec4[_], c2: AnyVec4[_], c3: AnyVec4[_]) = 
  new Mat4f(
    c0.fx, c0.fy, c0.fz, c0.fw,
    c1.fx, c1.fy, c1.fz, c1.fw,
    c2.fx, c2.fy, c2.fz, c2.fw,
    c3.fx, c3.fy, c3.fz, c3.fw
  )

  def apply(m: AnyMat[_]) = new Mat4f(
    m.f00, m.f01, m.f02, m.f03,
    m.f10, m.f11, m.f12, m.f13,
    m.f20, m.f21, m.f22, m.f23,
    m.f30, m.f31, m.f32, m.f33
  )

  def unapply(m: ReadMat4f) = Some((m(0), m(1), m(2), m(3)))
}
