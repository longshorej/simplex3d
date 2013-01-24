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
sealed abstract class ReadQuat4f extends ProtectedQuat4f[Float]
with Protected with Serializable
{

  type Clone <: ReadQuat4f
  def toConst :ConstQuat4f
  
  type Read = ReadQuat4f
  type Mutable = Quat4f
  final def mutableCopy = Quat4f(this)

  private[math] final def fa: Float = a
  private[math] final def fb: Float = b
  private[math] final def fc: Float = c
  private[math] final def fd: Float = d

  private[math] final def da: Double = a
  private[math] final def db: Double = b
  private[math] final def dc: Double = c
  private[math] final def dd: Double = d


  final def a = pa
  final def b = pb
  final def c = pc
  final def d = pd


  protected def a_=(s: Float) { throw new UnsupportedOperationException }
  protected def b_=(s: Float) { throw new UnsupportedOperationException }
  protected def c_=(s: Float) { throw new UnsupportedOperationException }
  protected def d_=(s: Float) { throw new UnsupportedOperationException }


  final def apply(i: Int) :Float = {
    i match {
      case 0 => a
      case 1 => b
      case 2 => c
      case 3 => d
      case j => throw new IndexOutOfBoundsException(
          "Trying to read index (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }

  final def unary_+() :ReadQuat4f = this
  /**
   * This methods negates every term of this rotationQuat.
   * Negating the rotationQuat produces another rotationQuat which represent
   * the same rotation. That is both q and -q represent exactly the
   * same rotation.
   */
  final def unary_-() = new Quat4f(-a, -b, -c, -d)
  final def *(s: Float) = new Quat4f(a * s, b * s, c * s, d * s)
  final def /(s: Float) = { val inv = 1/s;
    new Quat4f(a * inv, b * inv, c * inv, d * inv)
  }

  final def +(s: Float) = new Quat4f(a + s, b + s, c + s, d + s)
  final def -(s: Float) = new Quat4f(a - s, b - s, c - s, d - s)

  private[math] final def divByComp(s: Float) = {
    new Quat4f(s / a, s / b, s / c, s / d)
  }

  final def +(q: inQuat4f) = new Quat4f(a + q.a, b + q.b, c + q.c, d + q.d)
  final def -(q: inQuat4f) = new Quat4f(a - q.a, b - q.b, c - q.c, d - q.d)
  final def *(q: inQuat4f) = new Quat4f(
    a*q.a - b*q.b - c*q.c - d*q.d,
    a*q.b + b*q.a + c*q.d - d*q.c,
    a*q.c - b*q.d + c*q.a + d*q.b,
    a*q.d + b*q.c - c*q.b + d*q.a
  )

  final def rotate(q: inQuat4f) :Quat4f = q*this
  final def rotateX(angle: Float) :Quat4f = {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qb = sin(halfAngle)

    new Quat4f(qa*a - qb*b, qa*b + qb*a, qa*c - qb*d, qa*d + qb*c)
  }
  final def rotateY(angle: Float) :Quat4f = {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qc = sin(halfAngle)

    new Quat4f(qa*a - qc*c, qa*b + qc*d, qa*c + qc*a, qa*d - qc*b)
  }
  final def rotateZ(angle: Float) :Quat4f = {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qd = sin(halfAngle)

    new Quat4f(qa*a - qd*d, qa*b - qd*c, qa*c + qd*b, qa*d + qd*a)
  }

  final def rotateVector(u: inVec3f) :Vec3f =
    functions.rotateVector(u, normalize(this))


  final override def equals(other: Any) :Boolean = {
    other match {
      case q: AnyQuat4[_] => da == q.da && db == q.db && dc == q.dc && dd == q.dd
      case _ => false
    }
  }

  final override def hashCode :Int = {
    41 * (
      41 * (
        41 * (
          41 + simplex3d.math.floatHashCode(a)
        ) + simplex3d.math.floatHashCode(b)
      ) + simplex3d.math.floatHashCode(c)
    ) + simplex3d.math.floatHashCode(d)
  }

  final override def toString :String = {
    val prefix = this match {
      case self: Immutable => "Const"
      case _ => ""
    }
    prefix + "Quat4" + "(" + a + "f, " + b + "f, " + c + "f, " + d + "f)"
  }
}


@SerialVersionUID(8104346712419693669L)
final class ConstQuat4f private[math] (
  ca: Float, cb: Float, cc: Float, cd: Float
) extends ReadQuat4f with Immutable with Serializable {
  pa = ca; pb = cb; pc = cc; pd = cd

  type Clone = ConstQuat4f
  override def clone = this
  def toConst = this
}

object ConstQuat4f {
  /*main factory*/ def apply(a: Float, b: Float, c: Float, d: Float) =
    new ConstQuat4f(a, b, c, d)

  def apply(u: AnyQuat4[_]) = new ConstQuat4f(u.fa, u.fb, u.fc, u.fd)
  def apply(u: AnyVec4[_]) = new ConstQuat4f(u.fw, u.fx, u.fy, u.fz)

  implicit def toConst(u: ReadQuat4f) = new ConstQuat4f(u.a, u.b, u.c, u.d)
}


@SerialVersionUID(8104346712419693669L)
final class Quat4f private[math] (
  ca: Float, cb: Float, cc: Float, cd: Float
)
extends ReadQuat4f with Accessor with CompositeFormat
with Accessible with Serializable
{
  pa = ca; pb = cb; pc = cc; pd = cd

  private[math] def this() { this(1, 0, 0, 0) }
  
  type Clone = Quat4f
  type Const = ConstQuat4f
  
  type Accessor = Quat4f
  type Component = RFloat
  
  override def clone = Quat4f(this)
  def toConst = ConstQuat4f(this)
  def :=(q: inQuat4f) { a = q.a; b = q.b; c = q.c; d = q.d }


  override def a_=(s: Float) { pa = s }
  override def b_=(s: Float) { pb = s }
  override def c_=(s: Float) { pc = s }
  override def d_=(s: Float) { pd = s }


  def *=(s: Float) { a *= s; b *= s; c *= s; d *= s }
  def /=(s: Float) { val inv = 1/s; a *= inv; b *= inv; c *= inv; d *= inv }

  def +=(s: Float) { a += s; b += s; c += s; d += s }
  def -=(s: Float) { a -= s; b -= s; c -= s; d -= s }

  def +=(q: inQuat4f) { a += q.a; b += q.b; c += q.c; d += q.d }
  def -=(q: inQuat4f) { a -= q.a; b -= q.b; c -= q.c; d -= q.d }
  def *=(q: inQuat4f) {
    val na = a*q.a - b*q.b - c*q.c - d*q.d
    val nb = a*q.b + b*q.a + c*q.d - d*q.c
    val nc = a*q.c - b*q.d + c*q.a + d*q.b
         d = a*q.d + b*q.c - c*q.b + d*q.a

    a = na; b = nb; c = nc
  }

  final def applyRotation(q: inQuat4f) {
    val na = q.a*a - q.b*b - q.c*c - q.d*d
    val nb = q.a*b + q.b*a + q.c*d - q.d*c
    val nc = q.a*c - q.b*d + q.c*a + q.d*b
         d = q.a*d + q.b*c - q.c*b + q.d*a

    a = na; b = nb; c = nc
  }
  final def applyRotationX(angle: Float) {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qb = sin(halfAngle)

    val na = qa*a - qb*b
         b = qa*b + qb*a
    val nc = qa*c - qb*d
         d = qa*d + qb*c

    a = na; c = nc
  }
  final def applyRotationY(angle: Float) {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qc = sin(halfAngle)

    val na = qa*a - qc*c
    val nb = qa*b + qc*d
         c = qa*c + qc*a
         d = qa*d - qc*b

    a = na; b = nb
  }
  final def applyRotationZ(angle: Float) {
    val halfAngle = angle*0.5f
    val qa = cos(halfAngle)
    val qd = sin(halfAngle)

    val na = qa*a - qd*d
    val nb = qa*b - qd*c
         c = qa*c + qd*b
         d = qa*d + qd*a

    a = na; b = nb
  }

  def update(i: Int, s: Float) {
    i match {
      case 0 => a = s
      case 1 => b = s
      case 2 => c = s
      case 3 => d = s
      case j => throw new IndexOutOfBoundsException(
          "Trying to update index (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }
}

object Quat4f {
  final val Identity = new ConstQuat4f(1, 0, 0, 0)

  final val Tag = classTag[Quat4f]
  final val ConstTag = classTag[ConstQuat4f]
  final val ReadTag = classTag[ReadQuat4f]

  /*main factory*/ def apply(a: Float, b: Float, c: Float, d: Float) =
    new Quat4f(a, b, c, d)
    
  def apply() = new Quat4f(1, 0, 0, 0)

  def apply(q: AnyQuat4[_]) = new Quat4f(q.fa, q.fb, q.fc, q.fd)
  def apply(u: AnyVec4[_]) = new Quat4f(u.fw, u.fx, u.fy, u.fz)

  def unapply(q: ReadQuat4f) = Some((q.a, q.b, q.c, q.d))

  def rotate(q: inQuat4f) :Quat4f = Quat4f(q)
  def rotateX(angle: Float) :Quat4f = {
    quaternion(angle, Vec3f.UnitX)
  }
  def rotateY(angle: Float) :Quat4f = {
    quaternion(angle, Vec3f.UnitY)
  }
  def rotateZ(angle: Float) :Quat4f = {
    quaternion(angle, Vec3f.UnitZ)
  }
}
