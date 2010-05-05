/*
 * Simplex3d, IntMath module
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

package simplex3d.math.intm

import simplex3d.math._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyVec3i extends Read3[Int] {

  private[math] type R2 = ConstVec2i
  private[math] type R3 = ConstVec3i
  private[math] type R4 = ConstVec4i

  protected final def make2(x: Int, y: Int) =
    new ConstVec2i(x, y)
  protected final def make3(x: Int, y: Int, z: Int) =
    new ConstVec3i(x, y, z)
  protected final def make4(x: Int, y: Int, z: Int, w: Int) =
    new ConstVec4i(x, y, z, w)

  private[math] final def bx: Boolean = bool(x)
  private[math] final def by: Boolean = bool(y)
  private[math] final def bz: Boolean = bool(z)

  private[math] final def ix: Int = x
  private[math] final def iy: Int = y
  private[math] final def iz: Int = z

  private[math] final def fx: Float = x
  private[math] final def fy: Float = y
  private[math] final def fz: Float = z

  private[math] final def dx: Double = x
  private[math] final def dy: Double = y
  private[math] final def dz: Double = z


  def x: Int
  def y: Int
  def z: Int

  def r = x
  def g = y
  def b = z

  def s = x
  def t = y
  def p = z


  final def apply(i: Int) :Int = {
    i match {
      case 0 => x
      case 1 => y
      case 2 => z
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 2, got " + j
        )
    }
  }

  final def unary_+() :AnyVec3i = this
  final def unary_-() = new Vec3i(-x, -y, -z)
  final def unary_~() = new Vec3i(~x, ~y, ~z)

  final def *(s: Int) = new Vec3i(x * s, y * s, z * s)
  final def /(s: Int) = new Vec3i(x / s, y / s, z / s)

  final def +(s: Int) = new Vec3i(x + s, y + s, z + s)
  final def -(s: Int) = new Vec3i(x - s, y - s, z - s)

  private[math] final def divideByComponent(s: Int) = new Vec3i(s / x, s / y, s / z)
  final def %(s: Int) = new Vec3i(x % s, y % s, z % s)
  private[math] final def modByComponent(s: Int) = new Vec3i(s % x, s % y, s % z)
  final def >>(s: Int) = new Vec3i( x >> s, y >> s, z >> s)
  final def >>>(s: Int) = new Vec3i( x >>> s, y >>> s, z >>> s)
  final def <<(s: Int) = new Vec3i( x << s, y << s, z << s)
  final def &(s: Int) = new Vec3i( x & s, y & s, z & s)
  final def |(s: Int) = new Vec3i( x | s, y | s, z | s)
  final def ^(s: Int) = new Vec3i( x ^ s, y ^ s, z ^ s)

  final def +(u: inVec3i) = new Vec3i(x + u.x, y + u.y, z + u.z)
  final def -(u: inVec3i) = new Vec3i(x - u.x, y - u.y, z - u.z)
  final def *(u: inVec3i) = new Vec3i(x * u.x, y * u.y, z * u.z)
  final def /(u: inVec3i) = new Vec3i(x / u.x, y / u.y, z / u.z)
  final def %(u: inVec3i) = new Vec3i(x % u.x, y % u.y, z % u.z)
  final def >>(u: inVec3i) = new Vec3i( x >> u.x, y >> u.y, z >> u.z)
  final def >>>(u: inVec3i) = new Vec3i( x >>> u.x, y >>> u.y, z >>> u.z)
  final def <<(u: inVec3i) = new Vec3i( x << u.x, y << u.y, z << u.z)
  final def &(u: inVec3i) = new Vec3i( x & u.x, y & u.y, z & u.z)
  final def |(u: inVec3i) = new Vec3i( x | u.x, y | u.y, z | u.z)
  final def ^(u: inVec3i) = new Vec3i( x ^ u.x, y ^ u.y, z ^ u.z)

  final def ==(u: inVec3i) :Boolean = {
    if (u eq null) false
    else x == u.x && y == u.y && z == u.z
  }

  final def !=(u: inVec3i) :Boolean = !(this == u)

  final override def equals(other: Any) :Boolean = {
    other match {
      case u: inVec3i => this == u
      case _ => false
    }
  }

  final override def hashCode() :Int = {
    41 * (
      41 * (
        41 + x.hashCode
      ) + y.hashCode
    ) + z.hashCode
  }

  final override def toString() :String = {
    this.getClass.getSimpleName + "(" + x + ", " + y + ", " + z + ")"
  }
}


@serializable @SerialVersionUID(5359695191257934190L)
final class ConstVec3i private[math] (val x: Int, val y: Int, val z: Int)
extends AnyVec3i with Immutable

object ConstVec3i {
  /* @inline */ def apply(x: Int, y: Int, z: Int) = new ConstVec3i(x, y, z)
  def apply(u: Read3[_]) = new ConstVec3i(u.ix, u.iy, u.iz)

  implicit def toConst(u: AnyVec3i) = new ConstVec3i(u.x, u.y, u.z)
}


@serializable @SerialVersionUID(5359695191257934190L)
final class Vec3i private[math] (var x: Int, var y: Int, var z: Int)
extends AnyVec3i with Mutable with Implicits[On] with Composite
{
  type Element = AnyVec3i
  type Component = Int1

  override def r = x
  override def g = y
  override def b = z

  override def s = x
  override def t = y
  override def p = z

  def r_=(r: Int) { x = r }
  def g_=(g: Int) { y = g }
  def b_=(b: Int) { z = b }

  def s_=(s: Int) { x = s }
  def t_=(t: Int) { y = t }
  def p_=(p: Int) { z = p }


  def *=(s: Int) { x *= s; y *= s; z *= s }
  def /=(s: Int) { x /= s; y /= s; z /= s }

  def +=(s: Int) { x += s; y += s; z += s }
  def -=(s: Int) { x -= s; y -= s; z -= s }
  
  def %=(s: Int) { x %= s; y %= s; z %= s }
  def >>=(s: Int) = { x >>= s; y >>= s; z >>= s }
  def >>>=(s: Int) = { x >>>= s; y >>>= s; z >>>= s }
  def <<=(s: Int) = { x <<= s; y <<= s; z <<= s }
  def &=(s: Int) = { x &= s; y &= s; z &= s }
  def |=(s: Int) = { x |= s; y |= s; z |= s }
  def ^=(s: Int) = { x ^= s; y ^= s; z ^= s }

  def +=(u: inVec3i) { x += u.x; y += u.y; z += u.z }
  def -=(u: inVec3i) { x -= u.x; y -= u.y; z -= u.z }
  def *=(u: inVec3i) { x *= u.x; y *= u.y; z *= u.z }
  def /=(u: inVec3i) { x /= u.x; y /= u.y; z /= u.z }
  def %=(u: inVec3i) { x %= u.x; y %= u.y; z %= u.z }
  def >>=(u: inVec3i) = { x >>= u.x; y >>= u.y; z >>= u.z }
  def >>>=(u: inVec3i) = { x >>>= u.x; y >>>= u.y; z >>>= u.z }
  def <<=(u: inVec3i) = { x <<= u.x; y <<= u.y; z <<= u.z }
  def &=(u: inVec3i) = { x &= u.x; y &= u.y; z &= u.z }
  def |=(u: inVec3i) = { x |= u.x; y |= u.y; z |= u.z }
  def ^=(u: inVec3i) = { x ^= u.x; y ^= u.y; z ^= u.z }

  def :=(u: inVec3i) { x = u.x; y = u.y; z = u.z }
  def set(x: Int, y: Int, z: Int) { this.x = x; this.y = y; this.z = z }

  def update(i: Int, s: Int) {
    i match {
      case 0 => x = s
      case 1 => y = s
      case 2 => z = s
      case j => throw new IndexOutOfBoundsException(
          "excpected from 0 to 2, got " + j
        )
    }
  }

  // Swizzling
  override def xy: ConstVec2i = new ConstVec2i(x, y)
  override def xz: ConstVec2i = new ConstVec2i(x, z)
  override def yx: ConstVec2i = new ConstVec2i(y, x)
  override def yz: ConstVec2i = new ConstVec2i(y, z)
  override def zx: ConstVec2i = new ConstVec2i(z, x)
  override def zy: ConstVec2i = new ConstVec2i(z, y)

  override def xyz: ConstVec3i = new ConstVec3i(x, y, z)
  override def xzy: ConstVec3i = new ConstVec3i(x, z, y)
  override def yxz: ConstVec3i = new ConstVec3i(y, x, z)
  override def yzx: ConstVec3i = new ConstVec3i(y, z, x)
  override def zxy: ConstVec3i = new ConstVec3i(z, x, y)
  override def zyx: ConstVec3i = new ConstVec3i(z, y, x)

  override def rg = xy
  override def rb = xz
  override def gr = yx
  override def gb = yz
  override def br = zx
  override def bg = zy

  override def rgb = xyz
  override def rbg = xzy
  override def grb = yxz
  override def gbr = yzx
  override def brg = zxy
  override def bgr = zyx

  override def st = xy
  override def sp = xz
  override def ts = yx
  override def tp = yz
  override def ps = zx
  override def pt = zy

  override def stp = xyz
  override def spt = xzy
  override def tsp = yxz
  override def tps = yzx
  override def pst = zxy
  override def pts = zyx


  def xy_=(u: inVec2i) { x = u.x; y = u.y }
  def xz_=(u: inVec2i) { x = u.x; z = u.y }
  def yx_=(u: inVec2i) { y = u.x; x = u.y }
  def yz_=(u: inVec2i) { y = u.x; z = u.y }
  def zx_=(u: inVec2i) { z = u.x; x = u.y }
  def zy_=(u: inVec2i) { z = u.x; y = u.y }

  def xyz_=(u: inVec3i) { x = u.x; y = u.y; z = u.z }
  def xzy_=(u: inVec3i) { x = u.x; var t = u.z; z = u.y; y = t }
  def yxz_=(u: inVec3i) { var t = u.y; y = u.x; x = t; z = u.z }
  def yzx_=(u: inVec3i) { var t = u.y; y = u.x; x = u.z; z = t }
  def zxy_=(u: inVec3i) { var t = u.z; z = u.x; x = u.y; y = t }
  def zyx_=(u: inVec3i) { var t = u.z; z = u.x; x = t; y = u.y }

  def rg_=(u: inVec2i) { xy_=(u) }
  def rb_=(u: inVec2i) { xz_=(u) }
  def gr_=(u: inVec2i) { yx_=(u) }
  def gb_=(u: inVec2i) { yz_=(u) }
  def br_=(u: inVec2i) { zx_=(u) }
  def bg_=(u: inVec2i) { zy_=(u) }

  def rgb_=(u: inVec3i) { xyz_=(u) }
  def rbg_=(u: inVec3i) { xzy_=(u) }
  def grb_=(u: inVec3i) { yxz_=(u) }
  def gbr_=(u: inVec3i) { yzx_=(u) }
  def brg_=(u: inVec3i) { zxy_=(u) }
  def bgr_=(u: inVec3i) { zyx_=(u) }

  def st_=(u: inVec2i) { xy_=(u) }
  def sp_=(u: inVec2i) { xz_=(u) }
  def ts_=(u: inVec2i) { yx_=(u) }
  def tp_=(u: inVec2i) { yz_=(u) }
  def ps_=(u: inVec2i) { zx_=(u) }
  def pt_=(u: inVec2i) { zy_=(u) }

  def stp_=(u: inVec3i) { xyz_=(u) }
  def spt_=(u: inVec3i) { xzy_=(u) }
  def tsp_=(u: inVec3i) { yxz_=(u) }
  def tps_=(u: inVec3i) { yzx_=(u) }
  def pst_=(u: inVec3i) { zxy_=(u) }
  def pts_=(u: inVec3i) { zyx_=(u) }
}

object Vec3i {
  val Zero = new ConstVec3i(0, 0, 0)
  val UnitX = new ConstVec3i(1, 0, 0)
  val UnitY = new ConstVec3i(0, 1, 0)
  val UnitZ = new ConstVec3i(0, 0, 1)
  val One = new ConstVec3i(1, 1, 1)

  def apply(s: Int) = new Vec3i(s, s, s)
  /* @inline */ def apply(x: Int, y: Int, z: Int) = new Vec3i(x, y, z)
  def apply(u: Read3[_]) = new Vec3i(u.ix, u.iy, u.iz)
  def apply(u: Read4[_]) = new Vec3i(u.ix, u.iy, u.iz)
  def apply(xy: Read2[_], z: Int) = new Vec3i(xy.ix, xy.iy, z)
  def apply(x: Int, yz: Read2[_]) = new Vec3i(x, yz.ix, yz.iy)

  def unapply(u: AnyVec3i) = Some((u.x, u.y, u.z))

  implicit def toMutable(u: AnyVec3i) = new Vec3i(u.x, u.y, u.z)
}
