/*
 * Simplex3d, DoubleMath module
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

package simplex3d.math.doublem

import simplex3d.math._
import simplex3d.math.BaseMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyVec4d extends Read4[Double] {

    private[math] type R2 = ConstVec2d
    private[math] type R3 = ConstVec3d
    private[math] type R4 = ConstVec4d

    protected def make2(x: Double, y: Double) =
        new ConstVec2d(x, y)
    protected def make3(x: Double, y: Double, z: Double) =
        new ConstVec3d(x, y, z)
    protected def make4(x: Double, y: Double, z: Double, w: Double) =
        new ConstVec4d(x, y, z, w)

    private[math] def bx: Boolean = bool(x)
    private[math] def by: Boolean = bool(y)
    private[math] def bz: Boolean = bool(z)
    private[math] def bw: Boolean = bool(w)

    private[math] def ix: Int = int(x)
    private[math] def iy: Int = int(y)
    private[math] def iz: Int = int(z)
    private[math] def iw: Int = int(w)

    private[math] def fx: Float = float(x)
    private[math] def fy: Float = float(y)
    private[math] def fz: Float = float(z)
    private[math] def fw: Float = float(w)

    private[math] def dx: Double = x
    private[math] def dy: Double = y
    private[math] def dz: Double = z
    private[math] def dw: Double = w


    def x: Double
    def y: Double
    def z: Double
    def w: Double

    def r = x
    def g = y
    def b = z
    def a = w

    def s = x
    def t = y
    def p = z
    def q = w
    

    def apply(i: Int) :Double = {
        i match {
            case 0 => x
            case 1 => y
            case 2 => z
            case 3 => w
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }

    def unary_+() :this.type = this
    def unary_-() = new Vec4d(-x, -y, -z, -w)
    def *(s: Double) = new Vec4d(x * s, y * s, z * s, w * s)
    def /(s: Double) = { val inv = 1/s;
        new Vec4d(x * inv, y * inv, z * inv, w * inv)
    }

    def +(s: Double) = new Vec4d(x + s, y + s, z + s, w + s)
    def -(s: Double) = new Vec4d(x - s, y - s, z - s, w - s)

    private[math] def divideByComponent(s: Double) = {
        new Vec4d(s / x, s / y, s / z, s / w)
    }

    def +(u: AnyVec4d) = new Vec4d(x + u.x, y + u.y, z + u.z, w + u.w)
    def -(u: AnyVec4d) = new Vec4d(x - u.x, y - u.y, z - u.z, w - u.w)
    def *(u: AnyVec4d) = new Vec4d(x * u.x, y * u.y, z * u.z, w * u.w)
    def /(u: AnyVec4d) = new Vec4d(x / u.x, y / u.y, z / u.z, w / u.w)

    def *(m: AnyMat4x2d) :Vec2d = m.transposeMul(this)
    def *(m: AnyMat4x3d) :Vec3d = m.transposeMul(this)
    def *(m: AnyMat4d) :Vec4d = m.transposeMul(this)

    def ==(u: AnyVec4d) :Boolean = {
        if (u eq null) false
        else x == u.x && y == u.y && z == u.z && w == u.w
    }

    def !=(u: AnyVec4d) :Boolean = !(this == u)

    private[math] def hasErrors: Boolean = {
        import java.lang.Double._
        (
            isNaN(x) || isInfinite(x) ||
            isNaN(y) || isInfinite(y) ||
            isNaN(z) || isInfinite(z) ||
            isNaN(w) || isInfinite(w)
        )
    }

    override def equals(other: Any) :Boolean = {
        other match {
            case u: AnyVec4d => this == u
            case _ => false
        }
    }

    override def hashCode() :Int = {
        41 * (
            41 * (
                41 * (
                    41 + x.hashCode
                ) + y.hashCode
            ) + z.hashCode
        ) + w.hashCode
    }

    override def toString() :String = {
        this.getClass.getSimpleName +
        "(" + x + ", " + y + ", " + z + ", " + w + ")"
    }
}

final class ConstVec4d private[math] (
    val x: Double, val y: Double, val z: Double, val w: Double
) extends AnyVec4d with ConstVec[Double]

object ConstVec4d {
    def apply(x: Double, y: Double, z: Double, w: Double) = {
        new ConstVec4d(x, y, z, w)
    }
    def apply(u: Read4[_]) = new ConstVec4d(u.dx, u.dy, u.dz, u.dw)

    implicit def toConst(u: AnyVec4d) = new ConstVec4d(u.x, u.y, u.z, u.w)
}


final class Vec4d private[math] (
    var x: Double, var y: Double, var z: Double, var w: Double
) extends AnyVec4d with Vec[Double]
{
    override def r = x
    override def g = y
    override def b = z
    override def a = w

    override def s = x
    override def t = y
    override def p = z
    override def q = w

    def r_=(r: Double) { x = r }
    def g_=(g: Double) { y = g }
    def b_=(b: Double) { z = b }
    def a_=(a: Double) { w = a }

    def s_=(s: Double) { x = s }
    def t_=(t: Double) { y = t }
    def p_=(p: Double) { z = p }
    def q_=(q: Double) { w = q }


    def *=(s: Double) { x *= s; y *= s; z *= s; w *= s }
    def /=(s: Double) { val inv = 1/s; x *= inv; y *= inv; z *= inv; w *= inv }

    def +=(s: Double) { x += s; y += s; z += s; w += s }
    def -=(s: Double) { x -= s; y -= s; z -= s; w -= s }

    def +=(u: AnyVec4d) { x += u.x; y += u.y; z += u.z; w += u.w }
    def -=(u: AnyVec4d) { x -= u.x; y -= u.y; z -= u.z; w -= u.w }
    def *=(u: AnyVec4d) { x *= u.x; y *= u.y; z *= u.z; w *= u.w }
    def /=(u: AnyVec4d) { x /= u.x; y /= u.y; z /= u.z; w /= u.w }

    def *=(m: AnyMat4d) { this := m.transposeMul(this) }

    def :=(u: AnyVec4d) { x = u.x; y = u.y; z = u.z; w = u.w }
    def set(x: Double, y: Double, z: Double, w: Double) {
        this.x = x; this.y = y; this.z = z; this.w = w
    }

    def update(i: Int, s: Double) {
        i match {
            case 0 => x = s
            case 1 => y = s
            case 2 => z = s
            case 3 => w = s
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }

    // Swizzling
    override def xy: ConstVec2d = new ConstVec2d(x, y)
    override def xz: ConstVec2d = new ConstVec2d(x, z)
    override def xw: ConstVec2d = new ConstVec2d(x, w)
    override def yx: ConstVec2d = new ConstVec2d(y, x)
    override def yz: ConstVec2d = new ConstVec2d(y, z)
    override def yw: ConstVec2d = new ConstVec2d(y, w)
    override def zx: ConstVec2d = new ConstVec2d(z, x)
    override def zy: ConstVec2d = new ConstVec2d(z, y)
    override def zw: ConstVec2d = new ConstVec2d(z, w)
    override def wx: ConstVec2d = new ConstVec2d(w, x)
    override def wy: ConstVec2d = new ConstVec2d(w, y)
    override def wz: ConstVec2d = new ConstVec2d(w, z)

    override def xyz: ConstVec3d = new ConstVec3d(x, y, z)
    override def xyw: ConstVec3d = new ConstVec3d(x, y, w)
    override def xzy: ConstVec3d = new ConstVec3d(x, z, y)
    override def xzw: ConstVec3d = new ConstVec3d(x, z, w)
    override def xwy: ConstVec3d = new ConstVec3d(x, w, y)
    override def xwz: ConstVec3d = new ConstVec3d(x, w, z)
    override def yxz: ConstVec3d = new ConstVec3d(y, x, z)
    override def yxw: ConstVec3d = new ConstVec3d(y, x, w)
    override def yzx: ConstVec3d = new ConstVec3d(y, z, x)
    override def yzw: ConstVec3d = new ConstVec3d(y, z, w)
    override def ywx: ConstVec3d = new ConstVec3d(y, w, x)
    override def ywz: ConstVec3d = new ConstVec3d(y, w, z)
    override def zxy: ConstVec3d = new ConstVec3d(z, x, y)
    override def zxw: ConstVec3d = new ConstVec3d(z, x, w)
    override def zyx: ConstVec3d = new ConstVec3d(z, y, x)
    override def zyw: ConstVec3d = new ConstVec3d(z, y, w)
    override def zwx: ConstVec3d = new ConstVec3d(z, w, x)
    override def zwy: ConstVec3d = new ConstVec3d(z, w, y)
    override def wxy: ConstVec3d = new ConstVec3d(w, x, y)
    override def wxz: ConstVec3d = new ConstVec3d(w, x, z)
    override def wyx: ConstVec3d = new ConstVec3d(w, y, x)
    override def wyz: ConstVec3d = new ConstVec3d(w, y, z)
    override def wzx: ConstVec3d = new ConstVec3d(w, z, x)
    override def wzy: ConstVec3d = new ConstVec3d(w, z, y)

    override def xyzw: ConstVec4d = new ConstVec4d(x, y, z, w)
    override def xywz: ConstVec4d = new ConstVec4d(x, y, w, z)
    override def xzyw: ConstVec4d = new ConstVec4d(x, z, y, w)
    override def xzwy: ConstVec4d = new ConstVec4d(x, z, w, y)
    override def xwyz: ConstVec4d = new ConstVec4d(x, w, y, z)
    override def xwzy: ConstVec4d = new ConstVec4d(x, w, z, y)
    override def yxzw: ConstVec4d = new ConstVec4d(y, x, z, w)
    override def yxwz: ConstVec4d = new ConstVec4d(y, x, w, z)
    override def yzxw: ConstVec4d = new ConstVec4d(y, z, x, w)
    override def yzwx: ConstVec4d = new ConstVec4d(y, z, w, x)
    override def ywxz: ConstVec4d = new ConstVec4d(y, w, x, z)
    override def ywzx: ConstVec4d = new ConstVec4d(y, w, z, x)
    override def zxyw: ConstVec4d = new ConstVec4d(z, x, y, w)
    override def zxwy: ConstVec4d = new ConstVec4d(z, x, w, y)
    override def zyxw: ConstVec4d = new ConstVec4d(z, y, x, w)
    override def zywx: ConstVec4d = new ConstVec4d(z, y, w, x)
    override def zwxy: ConstVec4d = new ConstVec4d(z, w, x, y)
    override def zwyx: ConstVec4d = new ConstVec4d(z, w, y, x)
    override def wxyz: ConstVec4d = new ConstVec4d(w, x, y, z)
    override def wxzy: ConstVec4d = new ConstVec4d(w, x, z, y)
    override def wyxz: ConstVec4d = new ConstVec4d(w, y, x, z)
    override def wyzx: ConstVec4d = new ConstVec4d(w, y, z, x)
    override def wzxy: ConstVec4d = new ConstVec4d(w, z, x, y)
    override def wzyx: ConstVec4d = new ConstVec4d(w, z, y, x)

    override def rg = xy
    override def rb = xz
    override def ra = xw
    override def gr = yx
    override def gb = yz
    override def ga = yw
    override def br = zx
    override def bg = zy
    override def ba = zw
    override def ar = wx
    override def ag = wy
    override def ab = wz

    override def rgb = xyz
    override def rga = xyw
    override def rbg = xzy
    override def rba = xzw
    override def rag = xwy
    override def rab = xwz
    override def grb = yxz
    override def gra = yxw
    override def gbr = yzx
    override def gba = yzw
    override def gar = ywx
    override def gab = ywz
    override def brg = zxy
    override def bra = zxw
    override def bgr = zyx
    override def bga = zyw
    override def bar = zwx
    override def bag = zwy
    override def arg = wxy
    override def arb = wxz
    override def agr = wyx
    override def agb = wyz
    override def abr = wzx
    override def abg = wzy

    override def rgba = xyzw
    override def rgab = xywz
    override def rbga = xzyw
    override def rbag = xzwy
    override def ragb = xwyz
    override def rabg = xwzy
    override def grba = yxzw
    override def grab = yxwz
    override def gbra = yzxw
    override def gbar = yzwx
    override def garb = ywxz
    override def gabr = ywzx
    override def brga = zxyw
    override def brag = zxwy
    override def bgra = zyxw
    override def bgar = zywx
    override def barg = zwxy
    override def bagr = zwyx
    override def argb = wxyz
    override def arbg = wxzy
    override def agrb = wyxz
    override def agbr = wyzx
    override def abrg = wzxy
    override def abgr = wzyx

    override def st = xy
    override def sp = xz
    override def sq = xw
    override def ts = yx
    override def tp = yz
    override def tq = yw
    override def ps = zx
    override def pt = zy
    override def pq = zw
    override def qs = wx
    override def qt = wy
    override def qp = wz

    override def stp = xyz
    override def stq = xyw
    override def spt = xzy
    override def spq = xzw
    override def sqt = xwy
    override def sqp = xwz
    override def tsp = yxz
    override def tsq = yxw
    override def tps = yzx
    override def tpq = yzw
    override def tqs = ywx
    override def tqp = ywz
    override def pst = zxy
    override def psq = zxw
    override def pts = zyx
    override def ptq = zyw
    override def pqs = zwx
    override def pqt = zwy
    override def qst = wxy
    override def qsp = wxz
    override def qts = wyx
    override def qtp = wyz
    override def qps = wzx
    override def qpt = wzy

    override def stpq = xyzw
    override def stqp = xywz
    override def sptq = xzyw
    override def spqt = xzwy
    override def sqtp = xwyz
    override def sqpt = xwzy
    override def tspq = yxzw
    override def tsqp = yxwz
    override def tpsq = yzxw
    override def tpqs = yzwx
    override def tqsp = ywxz
    override def tqps = ywzx
    override def pstq = zxyw
    override def psqt = zxwy
    override def ptsq = zyxw
    override def ptqs = zywx
    override def pqst = zwxy
    override def pqts = zwyx
    override def qstp = wxyz
    override def qspt = wxzy
    override def qtsp = wyxz
    override def qtps = wyzx
    override def qpst = wzxy
    override def qpts = wzyx

    
    def xy_=(u: AnyVec2d) { x = u.x; y = u.y }
    def xz_=(u: AnyVec2d) { x = u.x; z = u.y }
    def xw_=(u: AnyVec2d) { x = u.x; w = u.y }
    def yx_=(u: AnyVec2d) { y = u.x; x = u.y }
    def yz_=(u: AnyVec2d) { y = u.x; z = u.y }
    def yw_=(u: AnyVec2d) { y = u.x; w = u.y }
    def zx_=(u: AnyVec2d) { z = u.x; x = u.y }
    def zy_=(u: AnyVec2d) { z = u.x; y = u.y }
    def zw_=(u: AnyVec2d) { z = u.x; w = u.y }
    def wx_=(u: AnyVec2d) { w = u.x; x = u.y }
    def wy_=(u: AnyVec2d) { w = u.x; y = u.y }
    def wz_=(u: AnyVec2d) { w = u.x; z = u.y }

    def xyz_=(u: AnyVec3d) { x = u.x; y = u.y; z = u.z }
    def xyw_=(u: AnyVec3d) { x = u.x; y = u.y; w = u.z }
    def xzy_=(u: AnyVec3d) { x = u.x; z = u.y; y = u.z }
    def xzw_=(u: AnyVec3d) { x = u.x; z = u.y; w = u.z }
    def xwy_=(u: AnyVec3d) { x = u.x; w = u.y; y = u.z }
    def xwz_=(u: AnyVec3d) { x = u.x; w = u.y; z = u.z }
    def yxz_=(u: AnyVec3d) { y = u.x; x = u.y; z = u.z }
    def yxw_=(u: AnyVec3d) { y = u.x; x = u.y; w = u.z }
    def yzx_=(u: AnyVec3d) { y = u.x; z = u.y; x = u.z }
    def yzw_=(u: AnyVec3d) { y = u.x; z = u.y; w = u.z }
    def ywx_=(u: AnyVec3d) { y = u.x; w = u.y; x = u.z }
    def ywz_=(u: AnyVec3d) { y = u.x; w = u.y; z = u.z }
    def zxy_=(u: AnyVec3d) { z = u.x; x = u.y; y = u.z }
    def zxw_=(u: AnyVec3d) { z = u.x; x = u.y; w = u.z }
    def zyx_=(u: AnyVec3d) { z = u.x; y = u.y; x = u.z }
    def zyw_=(u: AnyVec3d) { z = u.x; y = u.y; w = u.z }
    def zwx_=(u: AnyVec3d) { z = u.x; w = u.y; x = u.z }
    def zwy_=(u: AnyVec3d) { z = u.x; w = u.y; y = u.z }
    def wxy_=(u: AnyVec3d) { w = u.x; x = u.y; y = u.z }
    def wxz_=(u: AnyVec3d) { w = u.x; x = u.y; z = u.z }
    def wyx_=(u: AnyVec3d) { w = u.x; y = u.y; x = u.z }
    def wyz_=(u: AnyVec3d) { w = u.x; y = u.y; z = u.z }
    def wzx_=(u: AnyVec3d) { w = u.x; z = u.y; x = u.z }
    def wzy_=(u: AnyVec3d) { w = u.x; z = u.y; y = u.z }

    def xyzw_=(u: AnyVec4d) { x = u.x; y = u.y; z = u.z; w = u.w }
    def xywz_=(u: AnyVec4d) { x = u.x; y = u.y; var t = u.w; w = u.z; z = t }
    def xzyw_=(u: AnyVec4d) { x = u.x; var t = u.z; z = u.y; y = t; w = u.w }
    def xzwy_=(u: AnyVec4d) { x = u.x; var t = u.z; z = u.y; y = u.w; w = t }
    def xwyz_=(u: AnyVec4d) { x = u.x; var t = u.w; w = u.y; y = u.z; z = t }
    def xwzy_=(u: AnyVec4d) { x = u.x; var t = u.w; w = u.y; y = t; z = u.z }
    def yxzw_=(u: AnyVec4d) { var t = u.y; y = u.x; x = t; z = u.z; w = u.w }
    def yxwz_=(u: AnyVec4d) { var t = u.y; y = u.x; x = t; t = u.w; w = u.z;z=t}
    def yzxw_=(u: AnyVec4d) { var t = u.y; y = u.x; x = u.z; z = t; w = u.w }
    def yzwx_=(u: AnyVec4d) { var t = u.y; y = u.x; x = u.w; w = u.z; z = t }
    def ywxz_=(u: AnyVec4d) { var t = u.y; y = u.x; x = u.z; z = u.w; w = t }
    def ywzx_=(u: AnyVec4d) { var t = u.y; y = u.x; x = u.w; w = t; z = u.z }
    def zxyw_=(u: AnyVec4d) { var t = u.z; z = u.x; x = u.y; y = t; w = u.w }
    def zxwy_=(u: AnyVec4d) { var t = u.z; z = u.x; x = u.y; y = u.w; w = t }
    def zyxw_=(u: AnyVec4d) { var t = u.z; z = u.x; x = t; y = u.y; w = u.w }
    def zywx_=(u: AnyVec4d) { var t = u.z; z = u.x; x = u.w; w = t; y = u.y }
    def zwxy_=(u: AnyVec4d) { var t = u.z; z = u.x; x = t; t = u.w; w = u.y;y=t}
    def zwyx_=(u: AnyVec4d) { var t = u.z; z = u.x; x = u.w; w = u.y; y = t }
    def wxyz_=(u: AnyVec4d) { var t = u.w; w = u.x; x = u.y; y = u.z; z = t }
    def wxzy_=(u: AnyVec4d) { var t = u.w; w = u.x; x = u.y; y = t; z = u.z }
    def wyxz_=(u: AnyVec4d) { var t = u.w; w = u.x; x = u.z; z = t; y = u.y }
    def wyzx_=(u: AnyVec4d) { var t = u.w; w = u.x; x = t; y = u.y; z = u.z }
    def wzxy_=(u: AnyVec4d) { var t = u.w; w = u.x; x = u.z; z = u.y; y = t }
    def wzyx_=(u: AnyVec4d) { var t = u.w; w = u.x; x = t; t = u.z; z = u.y;y=t}

    def rg_=(u: AnyVec2d) { xy_=(u) }
    def rb_=(u: AnyVec2d) { xz_=(u) }
    def ra_=(u: AnyVec2d) { xw_=(u) }
    def gr_=(u: AnyVec2d) { yx_=(u) }
    def gb_=(u: AnyVec2d) { yz_=(u) }
    def ga_=(u: AnyVec2d) { yw_=(u) }
    def br_=(u: AnyVec2d) { zx_=(u) }
    def bg_=(u: AnyVec2d) { zy_=(u) }
    def ba_=(u: AnyVec2d) { zw_=(u) }
    def ar_=(u: AnyVec2d) { wx_=(u) }
    def ag_=(u: AnyVec2d) { wy_=(u) }
    def ab_=(u: AnyVec2d) { wz_=(u) }

    def rgb_=(u: AnyVec3d) { xyz_=(u) }
    def rga_=(u: AnyVec3d) { xyw_=(u) }
    def rbg_=(u: AnyVec3d) { xzy_=(u) }
    def rba_=(u: AnyVec3d) { xzw_=(u) }
    def rag_=(u: AnyVec3d) { xwy_=(u) }
    def rab_=(u: AnyVec3d) { xwz_=(u) }
    def grb_=(u: AnyVec3d) { yxz_=(u) }
    def gra_=(u: AnyVec3d) { yxw_=(u) }
    def gbr_=(u: AnyVec3d) { yzx_=(u) }
    def gba_=(u: AnyVec3d) { yzw_=(u) }
    def gar_=(u: AnyVec3d) { ywx_=(u) }
    def gab_=(u: AnyVec3d) { ywz_=(u) }
    def brg_=(u: AnyVec3d) { zxy_=(u) }
    def bra_=(u: AnyVec3d) { zxw_=(u) }
    def bgr_=(u: AnyVec3d) { zyx_=(u) }
    def bga_=(u: AnyVec3d) { zyw_=(u) }
    def bar_=(u: AnyVec3d) { zwx_=(u) }
    def bag_=(u: AnyVec3d) { zwy_=(u) }
    def arg_=(u: AnyVec3d) { wxy_=(u) }
    def arb_=(u: AnyVec3d) { wxz_=(u) }
    def agr_=(u: AnyVec3d) { wyx_=(u) }
    def agb_=(u: AnyVec3d) { wyz_=(u) }
    def abr_=(u: AnyVec3d) { wzx_=(u) }
    def abg_=(u: AnyVec3d) { wzy_=(u) }

    def rgba_=(u: AnyVec4d) { xyzw_=(u) }
    def rgab_=(u: AnyVec4d) { xywz_=(u) }
    def rbga_=(u: AnyVec4d) { xzyw_=(u) }
    def rbag_=(u: AnyVec4d) { xzwy_=(u) }
    def ragb_=(u: AnyVec4d) { xwyz_=(u) }
    def rabg_=(u: AnyVec4d) { xwzy_=(u) }
    def grba_=(u: AnyVec4d) { yxzw_=(u) }
    def grab_=(u: AnyVec4d) { yxwz_=(u) }
    def gbra_=(u: AnyVec4d) { yzxw_=(u) }
    def gbar_=(u: AnyVec4d) { yzwx_=(u) }
    def garb_=(u: AnyVec4d) { ywxz_=(u) }
    def gabr_=(u: AnyVec4d) { ywzx_=(u) }
    def brga_=(u: AnyVec4d) { zxyw_=(u) }
    def brag_=(u: AnyVec4d) { zxwy_=(u) }
    def bgra_=(u: AnyVec4d) { zyxw_=(u) }
    def bgar_=(u: AnyVec4d) { zywx_=(u) }
    def barg_=(u: AnyVec4d) { zwxy_=(u) }
    def bagr_=(u: AnyVec4d) { zwyx_=(u) }
    def argb_=(u: AnyVec4d) { wxyz_=(u) }
    def arbg_=(u: AnyVec4d) { wxzy_=(u) }
    def agrb_=(u: AnyVec4d) { wyxz_=(u) }
    def agbr_=(u: AnyVec4d) { wyzx_=(u) }
    def abrg_=(u: AnyVec4d) { wzxy_=(u) }
    def abgr_=(u: AnyVec4d) { wzyx_=(u) }

    def st_=(u: AnyVec2d) { xy_=(u) }
    def sp_=(u: AnyVec2d) { xz_=(u) }
    def sq_=(u: AnyVec2d) { xw_=(u) }
    def ts_=(u: AnyVec2d) { yx_=(u) }
    def tp_=(u: AnyVec2d) { yz_=(u) }
    def tq_=(u: AnyVec2d) { yw_=(u) }
    def ps_=(u: AnyVec2d) { zx_=(u) }
    def pt_=(u: AnyVec2d) { zy_=(u) }
    def pq_=(u: AnyVec2d) { zw_=(u) }
    def qs_=(u: AnyVec2d) { wx_=(u) }
    def qt_=(u: AnyVec2d) { wy_=(u) }
    def qp_=(u: AnyVec2d) { wz_=(u) }

    def stp_=(u: AnyVec3d) { xyz_=(u) }
    def stq_=(u: AnyVec3d) { xyw_=(u) }
    def spt_=(u: AnyVec3d) { xzy_=(u) }
    def spq_=(u: AnyVec3d) { xzw_=(u) }
    def sqt_=(u: AnyVec3d) { xwy_=(u) }
    def sqp_=(u: AnyVec3d) { xwz_=(u) }
    def tsp_=(u: AnyVec3d) { yxz_=(u) }
    def tsq_=(u: AnyVec3d) { yxw_=(u) }
    def tps_=(u: AnyVec3d) { yzx_=(u) }
    def tpq_=(u: AnyVec3d) { yzw_=(u) }
    def tqs_=(u: AnyVec3d) { ywx_=(u) }
    def tqp_=(u: AnyVec3d) { ywz_=(u) }
    def pst_=(u: AnyVec3d) { zxy_=(u) }
    def psq_=(u: AnyVec3d) { zxw_=(u) }
    def pts_=(u: AnyVec3d) { zyx_=(u) }
    def ptq_=(u: AnyVec3d) { zyw_=(u) }
    def pqs_=(u: AnyVec3d) { zwx_=(u) }
    def pqt_=(u: AnyVec3d) { zwy_=(u) }
    def qst_=(u: AnyVec3d) { wxy_=(u) }
    def qsp_=(u: AnyVec3d) { wxz_=(u) }
    def qts_=(u: AnyVec3d) { wyx_=(u) }
    def qtp_=(u: AnyVec3d) { wyz_=(u) }
    def qps_=(u: AnyVec3d) { wzx_=(u) }
    def qpt_=(u: AnyVec3d) { wzy_=(u) }

    def stpq_=(u: AnyVec4d) { xyzw_=(u) }
    def stqp_=(u: AnyVec4d) { xywz_=(u) }
    def sptq_=(u: AnyVec4d) { xzyw_=(u) }
    def spqt_=(u: AnyVec4d) { xzwy_=(u) }
    def sqtp_=(u: AnyVec4d) { xwyz_=(u) }
    def sqpt_=(u: AnyVec4d) { xwzy_=(u) }
    def tspq_=(u: AnyVec4d) { yxzw_=(u) }
    def tsqp_=(u: AnyVec4d) { yxwz_=(u) }
    def tpsq_=(u: AnyVec4d) { yzxw_=(u) }
    def tpqs_=(u: AnyVec4d) { yzwx_=(u) }
    def tqsp_=(u: AnyVec4d) { ywxz_=(u) }
    def tqps_=(u: AnyVec4d) { ywzx_=(u) }
    def pstq_=(u: AnyVec4d) { zxyw_=(u) }
    def psqt_=(u: AnyVec4d) { zxwy_=(u) }
    def ptsq_=(u: AnyVec4d) { zyxw_=(u) }
    def ptqs_=(u: AnyVec4d) { zywx_=(u) }
    def pqst_=(u: AnyVec4d) { zwxy_=(u) }
    def pqts_=(u: AnyVec4d) { zwyx_=(u) }
    def qstp_=(u: AnyVec4d) { wxyz_=(u) }
    def qspt_=(u: AnyVec4d) { wxzy_=(u) }
    def qtsp_=(u: AnyVec4d) { wyxz_=(u) }
    def qtps_=(u: AnyVec4d) { wyzx_=(u) }
    def qpst_=(u: AnyVec4d) { wzxy_=(u) }
    def qpts_=(u: AnyVec4d) { wzyx_=(u) }
}

object Vec4d {
    val Zero = new ConstVec4d(0, 0, 0, 0)
    val UnitX = new ConstVec4d(1, 0, 0, 0)
    val UnitY = new ConstVec4d(0, 1, 0, 0)
    val UnitZ = new ConstVec4d(0, 0, 1, 0)
    val UnitW = new ConstVec4d(0, 0, 0, 1)
    val One = new ConstVec4d(1, 1, 1, 1)

    def apply(s: Double) =
        new Vec4d(s, s, s, s)

    def apply(x: Double, y: Double, z: Double, w: Double) =
        new Vec4d(x, y, z, w)

    def apply(u: Read4[_]) =
        new Vec4d(u.dx, u.dy, u.dz, u.dw)

    def apply(xy: Read2[_], z: Double, w: Double) =
        new Vec4d(xy.dx, xy.dy, z, w)

    def apply(x: Double, yz: Read2[_], w: Double) =
        new Vec4d(x, yz.dx, yz.dy, w)

    def apply(x: Double, y: Double, zw: Read2[_]) =
        new Vec4d(x, y, zw.dx, zw.dy)

    def apply(xy: Read2[_], zw: Read2[_]) =
        new Vec4d(xy.dx, xy.dy, zw.dx, zw.dy)

    def apply(xyz: Read3[_], w: Double) =
        new Vec4d(xyz.dx, xyz.dy, xyz.dz, w)

    def apply(x: Double, yzw: Read3[_]) =
        new Vec4d(x, yzw.dx, yzw.dy, yzw.dz)

    def apply(m: Read2x2[_]) =
        new Vec4d(m.d00, m.d10, m.d01, m.d11)

    def unapply(u: AnyVec4d) = Some((u.x, u.y, u.z, u.w))

    implicit def toMutable(u: AnyVec4d) = new Vec4d(u.x, u.y, u.z, u.w)
    implicit def castInt(u: Read4[Int]) = new Vec4d(u.dx, u.dy, u.dz, u.dw)
    implicit def castFloat(u: Read4[Float]) = new Vec4d(u.dx, u.dy, u.dz, u.dw)
}