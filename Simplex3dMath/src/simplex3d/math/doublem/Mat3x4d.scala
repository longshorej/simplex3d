/*
 * Simplex3d, DoubleMath module
 * Copyright (C) 2009-2010, Simplex3d Team
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

import scala.reflect.Manifest._
import simplex3d.math.integration.buffer._
import simplex3d.math.integration.property._
import simplex3d.math._
import simplex3d.math.doublem.DoubleMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class ReadMat3x4d
extends ProtectedMat3x4d[Double]
{
  // Column major order.
  final def m00= p00; final def m10= p10; final def m20= p20
  final def m01= p01; final def m11= p11; final def m21= p21
  final def m02= p02; final def m12= p12; final def m22= p22
  final def m03= p03; final def m13= p13; final def m23= p23


  protected def m00_=(s: Double) { throw new UnsupportedOperationException }
  protected def m10_=(s: Double) { throw new UnsupportedOperationException }
  protected def m20_=(s: Double) { throw new UnsupportedOperationException }

  protected def m01_=(s: Double) { throw new UnsupportedOperationException }
  protected def m11_=(s: Double) { throw new UnsupportedOperationException }
  protected def m21_=(s: Double) { throw new UnsupportedOperationException }

  protected def m02_=(s: Double) { throw new UnsupportedOperationException }
  protected def m12_=(s: Double) { throw new UnsupportedOperationException }
  protected def m22_=(s: Double) { throw new UnsupportedOperationException }

  protected def m03_=(s: Double) { throw new UnsupportedOperationException }
  protected def m13_=(s: Double) { throw new UnsupportedOperationException }
  protected def m23_=(s: Double) { throw new UnsupportedOperationException }


  private[math] final override def f00 = float(m00)
  private[math] final override def f10 = float(m10)
  private[math] final override def f20 = float(m20)

  private[math] final override def f01 = float(m01)
  private[math] final override def f11 = float(m11)
  private[math] final override def f21 = float(m21)

  private[math] final override def f02 = float(m02)
  private[math] final override def f12 = float(m12)
  private[math] final override def f22 = float(m22)

  private[math] final override def f03 = float(m03)
  private[math] final override def f13 = float(m13)
  private[math] final override def f23 = float(m23)


  private[math] final override def d00 = m00
  private[math] final override def d10 = m10
  private[math] final override def d20 = m20

  private[math] final override def d01 = m01
  private[math] final override def d11 = m11
  private[math] final override def d21 = m21

  private[math] final override def d02 = m02
  private[math] final override def d12 = m12
  private[math] final override def d22 = m22

  private[math] final override def d03 = m03
  private[math] final override def d13 = m13
  private[math] final override def d23 = m23


  final def apply(c: Int) :ConstVec3d = {
    c match {
      case 0 => new ConstVec3d(m00, m10, m20)
      case 1 => new ConstVec3d(m01, m11, m21)
      case 2 => new ConstVec3d(m02, m12, m22)
      case 3 => new ConstVec3d(m03, m13, m23)
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 3, got " + j
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
      case 2 =>
        r match {
          case 0 => m02
          case 1 => m12
          case 2 => m22
          case _ => error
        }
      case 3 =>
        r match {
          case 0 => m03
          case 1 => m13
          case 2 => m23
          case _ => error
        }
      case _ => error
    }
  }

  final def unary_+() :ReadMat3x4d = this
  final def unary_-() = new Mat3x4d(
    -m00, -m10, -m20,
    -m01, -m11, -m21,
    -m02, -m12, -m22,
    -m03, -m13, -m23
  )
  final def *(s: Double) = new Mat3x4d(
    s*m00, s*m10, s*m20,
    s*m01, s*m11, s*m21,
    s*m02, s*m12, s*m22,
    s*m03, s*m13, s*m23
  )
  final def /(s: Double) = this * (1/s)

  final def +(s: Double) = new Mat3x4d(
    m00 + s, m10 + s, m20 + s,
    m01 + s, m11 + s, m21 + s,
    m02 + s, m12 + s, m22 + s,
    m03 + s, m13 + s, m23 + s
  )
  final def -(s: Double) = this + (-s)

  final def +(m: inMat3x4d) = new Mat3x4d(
    m00 + m.m00, m10 + m.m10, m20 + m.m20,
    m01 + m.m01, m11 + m.m11, m21 + m.m21,
    m02 + m.m02, m12 + m.m12, m22 + m.m22,
    m03 + m.m03, m13 + m.m13, m23 + m.m23
  )
  final def -(m: inMat3x4d) = new Mat3x4d(
    m00 - m.m00, m10 - m.m10, m20 - m.m20,
    m01 - m.m01, m11 - m.m11, m21 - m.m21,
    m02 - m.m02, m12 - m.m12, m22 - m.m22,
    m03 - m.m03, m13 - m.m13, m23 - m.m23
  )

  /**
   * Component-wise devision.
   */
  final def /(m: inMat3x4d) = new Mat3x4d(
    m00/m.m00, m10/m.m10, m20/m.m20,
    m01/m.m01, m11/m.m11, m21/m.m21,
    m02/m.m02, m12/m.m12, m22/m.m22,
    m03/m.m03, m13/m.m13, m23/m.m23
  )
  private[math] final def divideByComponent(s: Double) = new Mat3x4d(
    s/m00, s/m10, s/m20,
    s/m01, s/m11, s/m21,
    s/m02, s/m12, s/m22,
    s/m03, s/m13, s/m23
  )

  final def *(m: inMat4x2d) = new Mat3x2d(
    m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
    m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
    m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

    m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
    m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
    m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31
  )
  final def *(m: inMat4x3d) = new Mat3d(
    m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
    m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
    m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

    m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
    m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
    m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31,

    m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32,
    m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32,
    m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32
  )
  final def *(m: inMat4d) = new Mat3x4d(
    m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30,
    m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30,
    m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30,

    m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31,
    m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31,
    m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31,

    m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32,
    m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32,
    m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32,

    m00*m.m03 + m01*m.m13 + m02*m.m23 + m03*m.m33,
    m10*m.m03 + m11*m.m13 + m12*m.m23 + m13*m.m33,
    m20*m.m03 + m21*m.m13 + m22*m.m23 + m23*m.m33
  )

  final def *(u: inVec4d) = new Vec3d(
    m00*u.x + m01*u.y + m02*u.z + m03*u.w,
    m10*u.x + m11*u.y + m12*u.z + m13*u.w,
    m20*u.x + m21*u.y + m22*u.z + m23*u.w
  )
  private[math] final def transposeMul(u: inVec3d) = new Vec4d(
    m00*u.x + m10*u.y + m20*u.z,
    m01*u.x + m11*u.y + m21*u.z,
    m02*u.x + m12*u.y + m22*u.z,
    m03*u.x + m13*u.y + m23*u.z
  )

  final def scale(s: Double) :Mat3x4d = this*s
  final def scale(s: inVec3d) :Mat3x4d = new Mat3x4d(
    m00*s.x, m10*s.y, m20*s.z,
    m01*s.x, m11*s.y, m21*s.z,
    m02*s.x, m12*s.y, m22*s.z,
    m03*s.x, m13*s.y, m23*s.z
  )

  /** Appends rotation to the current transformation. The rotation quaternion
   * is normalized first and then transformed into a rotation matrix which
   * is concatenated with the current transformation. If you want to avoid
   * normalization, use <code>concatenate(rotationMat(q))</code> instead.
   * @param q rotation quaternion.
   * @return a new transformation wtih the specified rotation as
   *         the last operation.
   */
  final def rotate(q: inQuat4d) :Mat3x4d = {
    concatenate(rotationMat(normalize(q)))
  }
  final def rotate(angle: Double, axis: inVec3d) :Mat3x4d = {
    concatenate(rotationMat(angle, normalize(axis)))
  }

  final def rotateX(angle: Double) :Mat3x4d = {
    concatenate(rotationMat(angle, Vec3d.UnitX))
  }
  final def rotateY(angle: Double) :Mat3x4d = {
    concatenate(rotationMat(angle, Vec3d.UnitY))
  }
  final def rotateZ(angle: Double) :Mat3x4d = {
    concatenate(rotationMat(angle, Vec3d.UnitZ))
  }

  final def translate(u: inVec3d) :Mat3x4d = new Mat3x4d(
    m00, m10, m20,
    m01, m11, m21,
    m02, m12, m22,
    m03 + u.x, m13 + u.y, m23 + u.z
  )

  final def concatenate(m: inMat3x4d) :Mat3x4d = new Mat3x4d(
    m.m00*m00 + m.m01*m10 + m.m02*m20,
    m.m10*m00 + m.m11*m10 + m.m12*m20,
    m.m20*m00 + m.m21*m10 + m.m22*m20,

    m.m00*m01 + m.m01*m11 + m.m02*m21,
    m.m10*m01 + m.m11*m11 + m.m12*m21,
    m.m20*m01 + m.m21*m11 + m.m22*m21,

    m.m00*m02 + m.m01*m12 + m.m02*m22,
    m.m10*m02 + m.m11*m12 + m.m12*m22,
    m.m20*m02 + m.m21*m12 + m.m22*m22,

    m.m00*m03 + m.m01*m13 + m.m02*m23 + m.m03,
    m.m10*m03 + m.m11*m13 + m.m12*m23 + m.m13,
    m.m20*m03 + m.m21*m13 + m.m22*m23 + m.m23
  )
  final def concatenate(m: inMat3d) :Mat3x4d = m*this

  final def transformPoint(p: inVec3d) :Vec3d = new Vec3d(
    m00*p.x + m01*p.y + m02*p.z + m03,
    m10*p.x + m11*p.y + m12*p.z + m13,
    m20*p.x + m21*p.y + m22*p.z + m23
  )
  final def transformVector(v: inVec3d) :Vec3d = new Vec3d(
    m00*v.x + m01*v.y + m02*v.z,
    m10*v.x + m11*v.y + m12*v.z,
    m20*v.x + m21*v.y + m22*v.z
  )


  override def clone() = this

  final override def equals(other: Any) :Boolean = {
    other match {
      case m: AnyMat3x4[_] =>
        d00 == m.d00 && d10 == m.d10 && d20 == m.d20 &&
        d01 == m.d01 && d11 == m.d11 && d21 == m.d21 &&
        d02 == m.d02 && d12 == m.d12 && d22 == m.d22 &&
        d03 == m.d03 && d13 == m.d13 && d23 == m.d23
      case _ =>
        false
    }
  }

  final override def hashCode() :Int = {
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
                          41 + m00.hashCode
                        ) + m10.hashCode
                      ) + m20.hashCode
                    ) + m01.hashCode
                  ) + m11.hashCode
                ) + m21.hashCode
              ) + m02.hashCode
            ) + m12.hashCode
          ) + m22.hashCode
        ) + m03.hashCode
      ) + m13.hashCode
    ) + m23.hashCode
  }

  final override def toString() :String = {
    this.getClass.getSimpleName +
    "(" +
      m00 + ", " + m10 + ", " + m20 + "; " + 
      m01 + ", " + m11 + ", " + m21 + "; " + 
      m02 + ", " + m12 + ", " + m22 + "; " + 
      m03 + ", " + m13 + ", " + m23 +
    ")"
  }
}


@serializable @SerialVersionUID(5359695191257934190L)
final class ConstMat3x4d private[math] (
  c00: Double, c10: Double, c20: Double,
  c01: Double, c11: Double, c21: Double,
  c02: Double, c12: Double, c22: Double,
  c03: Double, c13: Double, c23: Double
) extends ReadMat3x4d with Immutable
{
  p00 = c00; p10 = c10; p20 = c20
  p01 = c01; p11 = c11; p21 = c21
  p02 = c02; p12 = c12; p22 = c22
  p03 = c03; p13 = c13; p23 = c23

  override def clone() = this
}

object ConstMat3x4d {
  def apply(s: Double) = new ConstMat3x4d(
    s, 0, 0,
    0, s, 0,
    0, 0, s,
    0, 0, 0
  )

  /*main factory*/ def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double,
    m02: Double, m12: Double, m22: Double,
    m03: Double, m13: Double, m23: Double
  ) = new ConstMat3x4d(
    m00, m10, m20,
    m01, m11, m21,
    m02, m12, m22,
    m03, m13, m23
  )

  def apply(c0: AnyVec3[_], c1: AnyVec3[_], c2: AnyVec3[_], c3: AnyVec3[_]) = 
  new ConstMat3x4d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz,
    c2.dx, c2.dy, c2.dz,
    c3.dx, c3.dy, c3.dz
  )

  def apply(m: AnyMat[_]) = new ConstMat3x4d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22,
    m.d03, m.d13, m.d23
  )

  implicit def toConst(m: ReadMat3x4d) = ConstMat3x4d(m)
}


@serializable @SerialVersionUID(5359695191257934190L)
final class Mat3x4d private[math] (
  c00: Double, c10: Double, c20: Double,
  c01: Double, c11: Double, c21: Double,
  c02: Double, c12: Double, c22: Double,
  c03: Double, c13: Double, c23: Double
) extends ReadMat3x4d
  with PropertyObject[ReadMat3x4d] with Implicits[On] with Composite
{
  p00 = c00; p10 = c10; p20 = c20
  p01 = c01; p11 = c11; p21 = c21
  p02 = c02; p12 = c12; p22 = c22
  p03 = c03; p13 = c13; p23 = c23

  override def m00_=(s: Double) { p00 = s }
  override def m10_=(s: Double) { p10 = s }
  override def m20_=(s: Double) { p20 = s }

  override def m01_=(s: Double) { p01 = s }
  override def m11_=(s: Double) { p11 = s }
  override def m21_=(s: Double) { p21 = s }

  override def m02_=(s: Double) { p02 = s }
  override def m12_=(s: Double) { p12 = s }
  override def m22_=(s: Double) { p22 = s }

  override def m03_=(s: Double) { p03 = s }
  override def m13_=(s: Double) { p13 = s }
  override def m23_=(s: Double) { p23 = s }

  type Element = ReadMat3x4d
  type Component = Double1

  def *=(s: Double) {
    m00 *= s; m10 *= s; m20 *= s;
    m01 *= s; m11 *= s; m21 *= s;
    m02 *= s; m12 *= s; m22 *= s;
    m03 *= s; m13 *= s; m23 *= s
  }
  def /=(s: Double) { this *= (1/s) }

  def +=(s: Double) {
    m00 += s; m10 += s; m20 += s
    m01 += s; m11 += s; m21 += s
    m02 += s; m12 += s; m22 += s
    m03 += s; m13 += s; m23 += s
  }
  def -=(s: Double) { this += (-s) }

  def +=(m: inMat3x4d) {
    m00 += m.m00; m10 += m.m10; m20 += m.m20;
    m01 += m.m01; m11 += m.m11; m21 += m.m21;
    m02 += m.m02; m12 += m.m12; m22 += m.m22;
    m03 += m.m03; m13 += m.m13; m23 += m.m23
  }
  def -=(m: inMat3x4d) {
    m00 -= m.m00; m10 -= m.m10; m20 -= m.m20;
    m01 -= m.m01; m11 -= m.m11; m21 -= m.m21;
    m02 -= m.m02; m12 -= m.m12; m22 -= m.m22;
    m03 -= m.m03; m13 -= m.m13; m23 -= m.m23
  }

  def *=(m: inMat4d) {
    val a00 = m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30
    val a10 = m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30
    val a20 = m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30

    val a01 = m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31
    val a11 = m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31
    val a21 = m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31

    val a02 = m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32
    val a12 = m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32
    val a22 = m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32

    val a03 = m00*m.m03 + m01*m.m13 + m02*m.m23 + m03*m.m33
    val a13 = m10*m.m03 + m11*m.m13 + m12*m.m23 + m13*m.m33
    val a23 = m20*m.m03 + m21*m.m13 + m22*m.m23 + m23*m.m33

    m00 = a00; m10 = a10; m20 = a20
    m01 = a01; m11 = a11; m21 = a21
    m02 = a02; m12 = a12; m22 = a22
    m03 = a03; m13 = a13; m23 = a23
  }
  /**
   * Component-wise devision.
   */
  def /=(m: inMat3x4d) {
    m00 /= m.m00; m10 /= m.m10; m20 /= m.m20
    m01 /= m.m01; m11 /= m.m11; m21 /= m.m21
    m02 /= m.m02; m12 /= m.m12; m22 /= m.m22
    m03 /= m.m03; m13 /= m.m13; m23 /= m.m23
  }

  def cloneValue() = ConstMat3x4d(this)
  def asReadInstance() :ReadMat3x4d = this /*asReadInstance*/
  override def clone() = Mat3x4d(this)
  
  override def :=(m: inMat3x4d) {
    m00 = m.m00; m10 = m.m10; m20 = m.m20;
    m01 = m.m01; m11 = m.m11; m21 = m.m21;
    m02 = m.m02; m12 = m.m12; m22 = m.m22;
    m03 = m.m03; m13 = m.m13; m23 = m.m23
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
      case 3 =>
        r match {
          case 0 => m03 = s
          case 1 => m13 = s
          case 2 => m23 = s
          case _ => error
        }
      case _ => error
    }
  }

  def update(c: Int, v: inVec2d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y
      case 1 => m01 = v.x; m11 = v.y
      case 2 => m02 = v.x; m12 = v.y
      case 3 => m03 = v.x; m13 = v.y
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 3, got " + j
        )
    }
  }

  def update(c: Int, v: inVec3d) {
    c match {
      case 0 => m00 = v.x; m10 = v.y; m20 = v.z
      case 1 => m01 = v.x; m11 = v.y; m21 = v.z
      case 2 => m02 = v.x; m12 = v.y; m22 = v.z
      case 3 => m03 = v.x; m13 = v.y; m23 = v.z
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 3, got " + j
        )
    }
  }
}

object Mat3x4d {
  final val Zero = ConstMat3x4d(0)
  final val Identity = ConstMat3x4d(1)
  final val Manifest = classType[ReadMat3x4d](classOf[ReadMat3x4d])

  def apply(s: Double) = new Mat3x4d(
    s, 0, 0,
    0, s, 0,
    0, 0, s,
    0, 0, 0
  )

  /*main factory*/ def apply(
    m00: Double, m10: Double, m20: Double,
    m01: Double, m11: Double, m21: Double,
    m02: Double, m12: Double, m22: Double,
    m03: Double, m13: Double, m23: Double
  ) = new Mat3x4d(
    m00, m10, m20,
    m01, m11, m21,
    m02, m12, m22,
    m03, m13, m23
  )

  def apply(c0: AnyVec3[_], c1: AnyVec3[_], c2: AnyVec3[_], c3: AnyVec3[_]) = 
  new Mat3x4d(
    c0.dx, c0.dy, c0.dz,
    c1.dx, c1.dy, c1.dz,
    c2.dx, c2.dy, c2.dz,
    c3.dx, c3.dy, c3.dz
  )

  def apply(m: AnyMat[_]) = new Mat3x4d(
    m.d00, m.d10, m.d20,
    m.d01, m.d11, m.d21,
    m.d02, m.d12, m.d22,
    m.d03, m.d13, m.d23
  )

  def unapply(m: ReadMat3x4d) = Some((m(0), m(1), m(2), m(3)))

  def scale(s: Double) :Mat3x4d = Mat3x4d(s)
  def scale(s: inVec3d) :Mat3x4d = {
    val m = Mat3x4d(s.x)
    m.m11 = s.y
    m.m22 = s.z
    m
  }

  def rotate(q: inQuat4d) :Mat3x4d = {
    Mat3x4d(rotationMat(normalize(q)))
  }
  def rotate(angle: Double, axis: inVec3d) :Mat3x4d = {
    Mat3x4d(rotationMat(angle, normalize(axis)))
  }

  def rotateX(angle: Double) :Mat3x4d = {
    Mat3x4d(rotationMat(angle, Vec3d.UnitX))
  }
  def rotateY(angle: Double) :Mat3x4d = {
    Mat3x4d(rotationMat(angle, Vec3d.UnitY))
  }
  def rotateZ(angle: Double) :Mat3x4d = {
    Mat3x4d(rotationMat(angle, Vec3d.UnitZ))
  }

  def translate(u: inVec3d) :Mat3x4d = {
    val m = Mat3x4d(1)
    m(3) = u
    m
  }

  def concatenate(m: inMat3x4d) :Mat3x4d = Mat3x4d(m)
  def concatenate(m: inMat3d) :Mat3x4d = Mat3x4d(m)

  implicit def toMutable(m: ReadMat3x4d) = Mat3x4d(m)
  implicit def castFloat(m: AnyMat3x4[Float]) = apply(m)
}
