/*
 * Simplex3dMath - Core Module
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

import scala.language.implicitConversions
import scala.reflect._
import simplex3d.math.integration._
import simplex3d.math.types._


/**
 * @author Aleksey Nikiforov (lex)
 */
@SerialVersionUID(8104346712419693669L)
sealed abstract class ReadVec4i extends ProtectedVec4i[Int]
with Protected with Serializable
{

  type Clone <: ReadVec4i
  def toConst :ConstVec4i
  
  type Read = ReadVec4i
  type Mutable = Vec4i
  final def mutableCopy = Vec4i(this)

  private[math] type R2 = ReadVec2i
  private[math] type R3 = ReadVec3i
  private[math] type R4 = ReadVec4i

  private[math] type C2 = ConstVec2i
  private[math] type C3 = ConstVec3i
  private[math] type C4 = ConstVec4i

  protected final def make2(x: Double, y: Double) =
    new ConstVec2i(x.toInt, y.toInt)
  protected final def make3(x: Double, y: Double, z: Double) =
    new ConstVec3i(x.toInt, y.toInt, z.toInt)
  protected final def make4(x: Double, y: Double, z: Double, w: Double) =
    new ConstVec4i(x.toInt, y.toInt, z.toInt, w.toInt)

  private[math] final def bx: Boolean = simplex3d.math.toBoolean(x)
  private[math] final def by: Boolean = simplex3d.math.toBoolean(y)
  private[math] final def bz: Boolean = simplex3d.math.toBoolean(z)
  private[math] final def bw: Boolean = simplex3d.math.toBoolean(w)

  private[math] final def ix: Int = x
  private[math] final def iy: Int = y
  private[math] final def iz: Int = z
  private[math] final def iw: Int = w

  private[math] final def fx: Float = x
  private[math] final def fy: Float = y
  private[math] final def fz: Float = z
  private[math] final def fw: Float = w

  private[math] final def dx: Double = x
  private[math] final def dy: Double = y
  private[math] final def dz: Double = z
  private[math] final def dw: Double = w


  final def x = px
  final def y = py
  final def z = pz
  final def w = pw

  final def r = px
  final def g = py
  final def b = pz
  final def a = pw

  final def s = px
  final def t = py
  final def p = pz
  final def q = pw


  protected def x_=(s: Int) { throw new UnsupportedOperationException }
  protected def y_=(s: Int) { throw new UnsupportedOperationException }
  protected def z_=(s: Int) { throw new UnsupportedOperationException }
  protected def w_=(s: Int) { throw new UnsupportedOperationException }

  protected def r_=(s: Int) { throw new UnsupportedOperationException }
  protected def g_=(s: Int) { throw new UnsupportedOperationException }
  protected def b_=(s: Int) { throw new UnsupportedOperationException }
  protected def a_=(s: Int) { throw new UnsupportedOperationException }

  protected def s_=(s: Int) { throw new UnsupportedOperationException }
  protected def t_=(s: Int) { throw new UnsupportedOperationException }
  protected def p_=(s: Int) { throw new UnsupportedOperationException }
  protected def q_=(s: Int) { throw new UnsupportedOperationException }

  final def apply(i: Int) :Int = {
    i match {
      case 0 => x
      case 1 => y
      case 2 => z
      case 3 => w
      case j => throw new IndexOutOfBoundsException(
          "Trying to read index (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }

  final def unary_+() :ReadVec4i = this
  final def unary_-() = new Vec4i(-x, -y, -z, -w)

  final def *(s: Int) = new Vec4i(x * s, y * s, z * s, w * s)
  final def /(s: Int) = new Vec4i(x / s, y / s, z / s, w / s)
  private[math] final def divByComp(s: Int) = new Vec4i(s / x, s / y, s / z, s / w)
  final def +(s: Int) = new Vec4i(x + s, y + s, z + s, w + s)
  final def -(s: Int) = new Vec4i(x - s, y - s, z - s, w - s)

  final def *(u: inVec4i) = new Vec4i(x * u.x, y * u.y, z * u.z, w * u.w)
  final def /(u: inVec4i) = new Vec4i(x / u.x, y / u.y, z / u.z, w / u.w)
  final def +(u: inVec4i) = new Vec4i(x + u.x, y + u.y, z + u.z, w + u.w)
  final def -(u: inVec4i) = new Vec4i(x - u.x, y - u.y, z - u.z, w - u.w)

  final def unary_~() = new Vec4i(~x, ~y, ~z, ~w)

  final def %(s: Int) = new Vec4i(x % s, y % s, z % s, w % s)
  private[math] final def remByComp(s: Int) = new Vec4i(s % x, s % y, s % z, s % w)
  final def >>(s: Int) = new Vec4i(x >> s, y >> s, z >> s, w >> s)
  final def >>>(s: Int) = new Vec4i(x >>> s, y >>> s, z >>> s, w >>> s)
  final def <<(s: Int) = new Vec4i(x << s, y << s, z << s, w << s)
  final def &(s: Int) = new Vec4i(x & s, y & s, z & s, w & s)
  final def |(s: Int) = new Vec4i(x | s, y | s, z | s, w | s)
  final def ^(s: Int) = new Vec4i(x ^ s, y ^ s, z ^ s, w ^ s)

  final def %(u: inVec4i) = new Vec4i(x % u.x, y % u.y, z % u.z, w % u.w)
  final def >>(u: inVec4i) = new Vec4i(x >> u.x, y >> u.y, z >> u.z, w >> u.w)
  final def >>>(u: inVec4i) = new Vec4i(x >>> u.x, y >>> u.y, z >>> u.z, w >>> u.w)
  final def <<(u: inVec4i) = new Vec4i(x << u.x, y << u.y, z << u.z, w << u.w)
  final def &(u: inVec4i) = new Vec4i(x & u.x, y & u.y, z & u.z, w & u.w)
  final def |(u: inVec4i) = new Vec4i(x | u.x, y | u.y, z | u.z, w | u.w)
  final def ^(u: inVec4i) = new Vec4i(x ^ u.x, y ^ u.y, z ^ u.z, w ^ u.w)


  final override def equals(other: Any) :Boolean = {
    other match {
      case u: ReadVec4i => x == u.x && y == u.y && z == u.z && w == u.w
      case u: ReadVec4b => false
      case u: AnyVec4[_] => dx == u.dx && dy == u.dy && dz == u.dz && dw == u.dw
      case _ => false
    }
  }

  final override def hashCode :Int = {
    41 * (
      41 * (
        41 * (
          41 + simplex3d.math.intHashCode(x)
        ) + simplex3d.math.intHashCode(y)
      ) + simplex3d.math.intHashCode(z)
    ) + simplex3d.math.intHashCode(w)
  }

  final override def toString :String = {
    val prefix = this match {
      case self: Immutable => "Const"
      case _ => ""
    }
    prefix + "Vec4i" + "(" + x + ", " + y + ", " + z + ", " + w + ")"
  }
}


@SerialVersionUID(8104346712419693669L)
final class ConstVec4i private[math] (cx: Int, cy: Int, cz: Int, cw: Int)
extends ReadVec4i with Immutable with Serializable {
  px = cx; py = cy; pz = cz; pw = cw

  type Clone = ConstVec4i
  override def clone = this
  def toConst = this
}


object ConstVec4i {

  def apply(s: Int) = new ConstVec4i(s, s, s, s)
  def apply(x: Int, y: Int, z: Int, w: Int) = new ConstVec4i(x, y, z, w)

  def apply(u: AnyVec4[_]) = new ConstVec4i(u.ix, u.iy, u.iz, u.iw)
  def apply(xy: AnyVec2[_], z: Int, w: Int) = new ConstVec4i(xy.ix, xy.iy, z, w)
  def apply(x: Int, yz: AnyVec2[_], w: Int) = new ConstVec4i(x, yz.ix, yz.iy, w)
  def apply(x: Int, y: Int, zw: AnyVec2[_]) = new ConstVec4i(x, y, zw.ix, zw.iy)
  def apply(xy: AnyVec2[_], zw: AnyVec2[_]) = new ConstVec4i(xy.ix, xy.iy, zw.ix, zw.iy)
  def apply(xyz: AnyVec3[_], w: Int) = new ConstVec4i(xyz.ix, xyz.iy, xyz.iz, w)
  def apply(x: Int, yzw: AnyVec3[_]) = new ConstVec4i(x, yzw.ix, yzw.iy, yzw.iz)

  def apply(m: AnyMat2[_]) = new ConstVec4i(m.d00.toInt, m.d01.toInt, m.d10.toInt, m.d11.toInt)
  def apply(q: AnyQuat4[_]) = new ConstVec4i(q.db.toInt, q.dc.toInt, q.dd.toInt, q.da.toInt)

  implicit def toConst(u: ReadVec4i) = apply(u)
}


@SerialVersionUID(8104346712419693669L)
final class Vec4i private[math] (cx: Int, cy: Int, cz: Int, cw: Int)
extends ReadVec4i with Accessor with CompositeFormat
with Accessible with Serializable
{
  px = cx; py = cy; pz = cz; pw = cw

  private[math] def this() { this(0, 0, 0, 0) }
  
  type Clone = Vec4i

  type Const = ConstVec4i
  type Accessor = Vec4i
  type Component = SInt

  override def clone = Vec4i(this)
  def toConst = ConstVec4i(this)
  def :=(u: inVec4i) { x = u.x; y = u.y; z = u.z; w = u.w }


  @noinline override def x_=(s: Int) { px = s }
  @noinline override def y_=(s: Int) { py = s }
  @noinline override def z_=(s: Int) { pz = s }
  @noinline override def w_=(s: Int) { pw = s }

  override def r_=(s: Int) { px = s }
  override def g_=(s: Int) { py = s }
  override def b_=(s: Int) { pz = s }
  override def a_=(s: Int) { pw = s }

  override def s_=(s: Int) { px = s }
  override def t_=(s: Int) { py = s }
  override def p_=(s: Int) { pz = s }
  override def q_=(s: Int) { pw = s }

  def update(i: Int, s: Int) {
    i match {
      case 0 => x = s
      case 1 => y = s
      case 2 => z = s
      case 3 => w = s
      case j => throw new IndexOutOfBoundsException(
          "Trying to update index (" + j + ") in " + this.getClass.getSimpleName + "."
        )
    }
  }

  def *=(s: Int) { x *= s; y *= s; z *= s; w *= s }
  def /=(s: Int) { x /= s; y /= s; z /= s; w /= s }
  def +=(s: Int) { x += s; y += s; z += s; w += s }
  def -=(s: Int) { x -= s; y -= s; z -= s; w -= s }

  def *=(u: inVec4i) { x *= u.x; y *= u.y; z *= u.z; w *= u.w }
  def /=(u: inVec4i) { x /= u.x; y /= u.y; z /= u.z; w /= u.w }
  def +=(u: inVec4i) { x += u.x; y += u.y; z += u.z; w += u.w }
  def -=(u: inVec4i) { x -= u.x; y -= u.y; z -= u.z; w -= u.w }

  def %=(s: Int) { x %= s; y %= s; z %= s; w %= s }
  def >>=(s: Int) { x >>= s; y >>= s; z >>= s; w >>= s }
  def >>>=(s: Int) { x >>>= s; y >>>= s; z >>>= s; w >>>= s }
  def <<=(s: Int) { x <<= s; y <<= s; z <<= s; w <<= s }
  def &=(s: Int) { x &= s; y &= s; z &= s; w &= s }
  def |=(s: Int) { x |= s; y |= s; z |= s; w |= s }
  def ^=(s: Int) { x ^= s; y ^= s; z ^= s; w ^= s }

  def %=(u: inVec4i) { x %= u.x; y %= u.y; z %= u.z; w %= u.w }
  def >>=(u: inVec4i) { x >>= u.x; y >>= u.y; z >>= u.z; w >>= u.w }
  def >>>=(u: inVec4i) { x >>>= u.x; y >>>= u.y; z >>>= u.z; w >>>= u.w }
  def <<=(u: inVec4i) { x <<= u.x; y <<= u.y; z <<= u.z; w <<= u.w }
  def &=(u: inVec4i) { x &= u.x; y &= u.y; z &= u.z; w &= u.w }
  def |=(u: inVec4i) { x |= u.x; y |= u.y; z |= u.z; w |= u.w }
  def ^=(u: inVec4i) { x ^= u.x; y ^= u.y; z ^= u.z; w ^= u.w }

  // @SwizzlingStart
  override def xy_=(u: inVec2i) { x = u.x; y = u.y }
  override def xz_=(u: inVec2i) { x = u.x; z = u.y }
  override def xw_=(u: inVec2i) { x = u.x; w = u.y }
  override def yx_=(u: inVec2i) { y = u.x; x = u.y }
  override def yz_=(u: inVec2i) { y = u.x; z = u.y }
  override def yw_=(u: inVec2i) { y = u.x; w = u.y }
  override def zx_=(u: inVec2i) { z = u.x; x = u.y }
  override def zy_=(u: inVec2i) { z = u.x; y = u.y }
  override def zw_=(u: inVec2i) { z = u.x; w = u.y }
  override def wx_=(u: inVec2i) { w = u.x; x = u.y }
  override def wy_=(u: inVec2i) { w = u.x; y = u.y }
  override def wz_=(u: inVec2i) { w = u.x; z = u.y }

  override def xyz_=(u: inVec3i) { x = u.x; y = u.y; z = u.z }
  override def xyw_=(u: inVec3i) { x = u.x; y = u.y; w = u.z }
  override def xzy_=(u: inVec3i) { x = u.x; z = u.y; y = u.z }
  override def xzw_=(u: inVec3i) { x = u.x; z = u.y; w = u.z }
  override def xwy_=(u: inVec3i) { x = u.x; w = u.y; y = u.z }
  override def xwz_=(u: inVec3i) { x = u.x; w = u.y; z = u.z }
  override def yxz_=(u: inVec3i) { y = u.x; x = u.y; z = u.z }
  override def yxw_=(u: inVec3i) { y = u.x; x = u.y; w = u.z }
  override def yzx_=(u: inVec3i) { y = u.x; z = u.y; x = u.z }
  override def yzw_=(u: inVec3i) { y = u.x; z = u.y; w = u.z }
  override def ywx_=(u: inVec3i) { y = u.x; w = u.y; x = u.z }
  override def ywz_=(u: inVec3i) { y = u.x; w = u.y; z = u.z }
  override def zxy_=(u: inVec3i) { z = u.x; x = u.y; y = u.z }
  override def zxw_=(u: inVec3i) { z = u.x; x = u.y; w = u.z }
  override def zyx_=(u: inVec3i) { z = u.x; y = u.y; x = u.z }
  override def zyw_=(u: inVec3i) { z = u.x; y = u.y; w = u.z }
  override def zwx_=(u: inVec3i) { z = u.x; w = u.y; x = u.z }
  override def zwy_=(u: inVec3i) { z = u.x; w = u.y; y = u.z }
  override def wxy_=(u: inVec3i) { w = u.x; x = u.y; y = u.z }
  override def wxz_=(u: inVec3i) { w = u.x; x = u.y; z = u.z }
  override def wyx_=(u: inVec3i) { w = u.x; y = u.y; x = u.z }
  override def wyz_=(u: inVec3i) { w = u.x; y = u.y; z = u.z }
  override def wzx_=(u: inVec3i) { w = u.x; z = u.y; x = u.z }
  override def wzy_=(u: inVec3i) { w = u.x; z = u.y; y = u.z }

  override def xyzw_=(u: inVec4i) { x = u.x; y = u.y; z = u.z; w = u.w }
  override def xywz_=(u: inVec4i) { x = u.x; y = u.y; var t = u.w; w = u.z; z = t }
  override def xzyw_=(u: inVec4i) { x = u.x; var t = u.z; z = u.y; y = t; w = u.w }
  override def xzwy_=(u: inVec4i) { x = u.x; var t = u.z; z = u.y; y = u.w; w = t }
  override def xwyz_=(u: inVec4i) { x = u.x; var t = u.w; w = u.y; y = u.z; z = t }
  override def xwzy_=(u: inVec4i) { x = u.x; var t = u.w; w = u.y; y = t; z = u.z }
  override def yxzw_=(u: inVec4i) { var t = u.y; y = u.x; x = t; z = u.z; w = u.w }
  override def yxwz_=(u: inVec4i) { var t = u.y; y = u.x; x = t; t = u.w; w = u.z; z = t }
  override def yzxw_=(u: inVec4i) { var t = u.y; y = u.x; x = u.z; z = t; w = u.w }
  override def yzwx_=(u: inVec4i) { var t = u.y; y = u.x; x = u.w; w = u.z; z = t }
  override def ywxz_=(u: inVec4i) { var t = u.y; y = u.x; x = u.z; z = u.w; w = t }
  override def ywzx_=(u: inVec4i) { var t = u.y; y = u.x; x = u.w; w = t; z = u.z }
  override def zxyw_=(u: inVec4i) { var t = u.z; z = u.x; x = u.y; y = t; w = u.w }
  override def zxwy_=(u: inVec4i) { var t = u.z; z = u.x; x = u.y; y = u.w; w = t }
  override def zyxw_=(u: inVec4i) { var t = u.z; z = u.x; x = t; y = u.y; w = u.w }
  override def zywx_=(u: inVec4i) { var t = u.z; z = u.x; x = u.w; w = t; y = u.y }
  override def zwxy_=(u: inVec4i) { var t = u.z; z = u.x; x = t; t = u.w; w = u.y; y = t }
  override def zwyx_=(u: inVec4i) { var t = u.z; z = u.x; x = u.w; w = u.y; y = t }
  override def wxyz_=(u: inVec4i) { var t = u.w; w = u.x; x = u.y; y = u.z; z = t }
  override def wxzy_=(u: inVec4i) { var t = u.w; w = u.x; x = u.y; y = t; z = u.z }
  override def wyxz_=(u: inVec4i) { var t = u.w; w = u.x; x = u.z; z = t; y = u.y }
  override def wyzx_=(u: inVec4i) { var t = u.w; w = u.x; x = t; y = u.y; z = u.z }
  override def wzxy_=(u: inVec4i) { var t = u.w; w = u.x; x = u.z; z = u.y; y = t }
  override def wzyx_=(u: inVec4i) { var t = u.w; w = u.x; x = t; t = u.z; z = u.y; y = t }

  override def rg_=(u: inVec2i) { xy_=(u) }
  override def rb_=(u: inVec2i) { xz_=(u) }
  override def ra_=(u: inVec2i) { xw_=(u) }
  override def gr_=(u: inVec2i) { yx_=(u) }
  override def gb_=(u: inVec2i) { yz_=(u) }
  override def ga_=(u: inVec2i) { yw_=(u) }
  override def br_=(u: inVec2i) { zx_=(u) }
  override def bg_=(u: inVec2i) { zy_=(u) }
  override def ba_=(u: inVec2i) { zw_=(u) }
  override def ar_=(u: inVec2i) { wx_=(u) }
  override def ag_=(u: inVec2i) { wy_=(u) }
  override def ab_=(u: inVec2i) { wz_=(u) }

  override def rgb_=(u: inVec3i) { xyz_=(u) }
  override def rga_=(u: inVec3i) { xyw_=(u) }
  override def rbg_=(u: inVec3i) { xzy_=(u) }
  override def rba_=(u: inVec3i) { xzw_=(u) }
  override def rag_=(u: inVec3i) { xwy_=(u) }
  override def rab_=(u: inVec3i) { xwz_=(u) }
  override def grb_=(u: inVec3i) { yxz_=(u) }
  override def gra_=(u: inVec3i) { yxw_=(u) }
  override def gbr_=(u: inVec3i) { yzx_=(u) }
  override def gba_=(u: inVec3i) { yzw_=(u) }
  override def gar_=(u: inVec3i) { ywx_=(u) }
  override def gab_=(u: inVec3i) { ywz_=(u) }
  override def brg_=(u: inVec3i) { zxy_=(u) }
  override def bra_=(u: inVec3i) { zxw_=(u) }
  override def bgr_=(u: inVec3i) { zyx_=(u) }
  override def bga_=(u: inVec3i) { zyw_=(u) }
  override def bar_=(u: inVec3i) { zwx_=(u) }
  override def bag_=(u: inVec3i) { zwy_=(u) }
  override def arg_=(u: inVec3i) { wxy_=(u) }
  override def arb_=(u: inVec3i) { wxz_=(u) }
  override def agr_=(u: inVec3i) { wyx_=(u) }
  override def agb_=(u: inVec3i) { wyz_=(u) }
  override def abr_=(u: inVec3i) { wzx_=(u) }
  override def abg_=(u: inVec3i) { wzy_=(u) }

  override def rgba_=(u: inVec4i) { xyzw_=(u) }
  override def rgab_=(u: inVec4i) { xywz_=(u) }
  override def rbga_=(u: inVec4i) { xzyw_=(u) }
  override def rbag_=(u: inVec4i) { xzwy_=(u) }
  override def ragb_=(u: inVec4i) { xwyz_=(u) }
  override def rabg_=(u: inVec4i) { xwzy_=(u) }
  override def grba_=(u: inVec4i) { yxzw_=(u) }
  override def grab_=(u: inVec4i) { yxwz_=(u) }
  override def gbra_=(u: inVec4i) { yzxw_=(u) }
  override def gbar_=(u: inVec4i) { yzwx_=(u) }
  override def garb_=(u: inVec4i) { ywxz_=(u) }
  override def gabr_=(u: inVec4i) { ywzx_=(u) }
  override def brga_=(u: inVec4i) { zxyw_=(u) }
  override def brag_=(u: inVec4i) { zxwy_=(u) }
  override def bgra_=(u: inVec4i) { zyxw_=(u) }
  override def bgar_=(u: inVec4i) { zywx_=(u) }
  override def barg_=(u: inVec4i) { zwxy_=(u) }
  override def bagr_=(u: inVec4i) { zwyx_=(u) }
  override def argb_=(u: inVec4i) { wxyz_=(u) }
  override def arbg_=(u: inVec4i) { wxzy_=(u) }
  override def agrb_=(u: inVec4i) { wyxz_=(u) }
  override def agbr_=(u: inVec4i) { wyzx_=(u) }
  override def abrg_=(u: inVec4i) { wzxy_=(u) }
  override def abgr_=(u: inVec4i) { wzyx_=(u) }

  override def st_=(u: inVec2i) { xy_=(u) }
  override def sp_=(u: inVec2i) { xz_=(u) }
  override def sq_=(u: inVec2i) { xw_=(u) }
  override def ts_=(u: inVec2i) { yx_=(u) }
  override def tp_=(u: inVec2i) { yz_=(u) }
  override def tq_=(u: inVec2i) { yw_=(u) }
  override def ps_=(u: inVec2i) { zx_=(u) }
  override def pt_=(u: inVec2i) { zy_=(u) }
  override def pq_=(u: inVec2i) { zw_=(u) }
  override def qs_=(u: inVec2i) { wx_=(u) }
  override def qt_=(u: inVec2i) { wy_=(u) }
  override def qp_=(u: inVec2i) { wz_=(u) }

  override def stp_=(u: inVec3i) { xyz_=(u) }
  override def stq_=(u: inVec3i) { xyw_=(u) }
  override def spt_=(u: inVec3i) { xzy_=(u) }
  override def spq_=(u: inVec3i) { xzw_=(u) }
  override def sqt_=(u: inVec3i) { xwy_=(u) }
  override def sqp_=(u: inVec3i) { xwz_=(u) }
  override def tsp_=(u: inVec3i) { yxz_=(u) }
  override def tsq_=(u: inVec3i) { yxw_=(u) }
  override def tps_=(u: inVec3i) { yzx_=(u) }
  override def tpq_=(u: inVec3i) { yzw_=(u) }
  override def tqs_=(u: inVec3i) { ywx_=(u) }
  override def tqp_=(u: inVec3i) { ywz_=(u) }
  override def pst_=(u: inVec3i) { zxy_=(u) }
  override def psq_=(u: inVec3i) { zxw_=(u) }
  override def pts_=(u: inVec3i) { zyx_=(u) }
  override def ptq_=(u: inVec3i) { zyw_=(u) }
  override def pqs_=(u: inVec3i) { zwx_=(u) }
  override def pqt_=(u: inVec3i) { zwy_=(u) }
  override def qst_=(u: inVec3i) { wxy_=(u) }
  override def qsp_=(u: inVec3i) { wxz_=(u) }
  override def qts_=(u: inVec3i) { wyx_=(u) }
  override def qtp_=(u: inVec3i) { wyz_=(u) }
  override def qps_=(u: inVec3i) { wzx_=(u) }
  override def qpt_=(u: inVec3i) { wzy_=(u) }

  override def stpq_=(u: inVec4i) { xyzw_=(u) }
  override def stqp_=(u: inVec4i) { xywz_=(u) }
  override def sptq_=(u: inVec4i) { xzyw_=(u) }
  override def spqt_=(u: inVec4i) { xzwy_=(u) }
  override def sqtp_=(u: inVec4i) { xwyz_=(u) }
  override def sqpt_=(u: inVec4i) { xwzy_=(u) }
  override def tspq_=(u: inVec4i) { yxzw_=(u) }
  override def tsqp_=(u: inVec4i) { yxwz_=(u) }
  override def tpsq_=(u: inVec4i) { yzxw_=(u) }
  override def tpqs_=(u: inVec4i) { yzwx_=(u) }
  override def tqsp_=(u: inVec4i) { ywxz_=(u) }
  override def tqps_=(u: inVec4i) { ywzx_=(u) }
  override def pstq_=(u: inVec4i) { zxyw_=(u) }
  override def psqt_=(u: inVec4i) { zxwy_=(u) }
  override def ptsq_=(u: inVec4i) { zyxw_=(u) }
  override def ptqs_=(u: inVec4i) { zywx_=(u) }
  override def pqst_=(u: inVec4i) { zwxy_=(u) }
  override def pqts_=(u: inVec4i) { zwyx_=(u) }
  override def qstp_=(u: inVec4i) { wxyz_=(u) }
  override def qspt_=(u: inVec4i) { wxzy_=(u) }
  override def qtsp_=(u: inVec4i) { wyxz_=(u) }
  override def qtps_=(u: inVec4i) { wyzx_=(u) }
  override def qpst_=(u: inVec4i) { wzxy_=(u) }
  override def qpts_=(u: inVec4i) { wzyx_=(u) }
  // @SwizzlingEnd
}


object Vec4i {
  final val Zero = new ConstVec4i(0, 0, 0, 0)
  final val UnitX = new ConstVec4i(1, 0, 0, 0)
  final val UnitY = new ConstVec4i(0, 1, 0, 0)
  final val UnitZ = new ConstVec4i(0, 0, 1, 0)
  final val UnitW = new ConstVec4i(0, 0, 0, 1)
  final val One = new ConstVec4i(1, 1, 1, 1)

  final val Tag = classTag[Vec4i]
  final val ConstTag = classTag[ConstVec4i]
  final val ReadTag = classTag[ReadVec4i]


  def apply(s: Int) = new Vec4i(s, s, s, s)
  def apply(x: Int, y: Int, z: Int, w: Int) = new Vec4i(x, y, z, w)

  def apply(u: AnyVec4[_]) = new Vec4i(u.ix, u.iy, u.iz, u.iw)
  def apply(xy: AnyVec2[_], z: Int, w: Int) = new Vec4i(xy.ix, xy.iy, z, w)
  def apply(x: Int, yz: AnyVec2[_], w: Int) = new Vec4i(x, yz.ix, yz.iy, w)
  def apply(x: Int, y: Int, zw: AnyVec2[_]) = new Vec4i(x, y, zw.ix, zw.iy)
  def apply(xy: AnyVec2[_], zw: AnyVec2[_]) = new Vec4i(xy.ix, xy.iy, zw.ix, zw.iy)
  def apply(xyz: AnyVec3[_], w: Int) = new Vec4i(xyz.ix, xyz.iy, xyz.iz, w)
  def apply(x: Int, yzw: AnyVec3[_]) = new Vec4i(x, yzw.ix, yzw.iy, yzw.iz)

  def apply(m: AnyMat2[_]) = new Vec4i(m.d00.toInt, m.d01.toInt, m.d10.toInt, m.d11.toInt)
  def apply(q: AnyQuat4[_]) = new Vec4i(q.db.toInt, q.dc.toInt, q.dd.toInt, q.da.toInt)

  def unapply(u: ReadVec4i) = Some((u.x, u.y, u.z, u.w))
}
