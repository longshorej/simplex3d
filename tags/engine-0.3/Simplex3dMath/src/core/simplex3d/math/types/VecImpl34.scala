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

package simplex3d.math.types


/** <code>AnyVec3or4</code> is a base class for all the 3 and 4 dimensional vectors.
 *
 * @author Aleksey Nikiforov (lex)
 */
private[math] abstract class VecImpl34[P] extends VecImpl234[P] {

  private[math] def bz: Boolean
  private[math] def iz: Int
  private[math] def fz: Float
  private[math] def dz: Double


  final def xz: C2 = make2(dx, dz)
  final def yz: C2 = make2(dy, dz)
  final def zx: C2 = make2(dz, dx)
  final def zy: C2 = make2(dz, dy)
  final def zz: C2 = make2(dz, dz)

  final def xxz: C3 = make3(dx, dx, dz)
  final def xyz: C3 = make3(dx, dy, dz)
  final def xzx: C3 = make3(dx, dz, dx)
  final def xzy: C3 = make3(dx, dz, dy)
  final def xzz: C3 = make3(dx, dz, dz)
  final def yxz: C3 = make3(dy, dx, dz)
  final def yyz: C3 = make3(dy, dy, dz)
  final def yzx: C3 = make3(dy, dz, dx)
  final def yzy: C3 = make3(dy, dz, dy)
  final def yzz: C3 = make3(dy, dz, dz)
  final def zxx: C3 = make3(dz, dx, dx)
  final def zxy: C3 = make3(dz, dx, dy)
  final def zxz: C3 = make3(dz, dx, dz)
  final def zyx: C3 = make3(dz, dy, dx)
  final def zyy: C3 = make3(dz, dy, dy)
  final def zyz: C3 = make3(dz, dy, dz)
  final def zzx: C3 = make3(dz, dz, dx)
  final def zzy: C3 = make3(dz, dz, dy)
  final def zzz: C3 = make3(dz, dz, dz)

  final def xxxz: C4 = make4(dx, dx, dx, dz)
  final def xxyz: C4 = make4(dx, dx, dy, dz)
  final def xxzx: C4 = make4(dx, dx, dz, dx)
  final def xxzy: C4 = make4(dx, dx, dz, dy)
  final def xxzz: C4 = make4(dx, dx, dz, dz)
  final def xyxz: C4 = make4(dx, dy, dx, dz)
  final def xyyz: C4 = make4(dx, dy, dy, dz)
  final def xyzx: C4 = make4(dx, dy, dz, dx)
  final def xyzy: C4 = make4(dx, dy, dz, dy)
  final def xyzz: C4 = make4(dx, dy, dz, dz)
  final def xzxx: C4 = make4(dx, dz, dx, dx)
  final def xzxy: C4 = make4(dx, dz, dx, dy)
  final def xzxz: C4 = make4(dx, dz, dx, dz)
  final def xzyx: C4 = make4(dx, dz, dy, dx)
  final def xzyy: C4 = make4(dx, dz, dy, dy)
  final def xzyz: C4 = make4(dx, dz, dy, dz)
  final def xzzx: C4 = make4(dx, dz, dz, dx)
  final def xzzy: C4 = make4(dx, dz, dz, dy)
  final def xzzz: C4 = make4(dx, dz, dz, dz)
  final def yxxz: C4 = make4(dy, dx, dx, dz)
  final def yxyz: C4 = make4(dy, dx, dy, dz)
  final def yxzx: C4 = make4(dy, dx, dz, dx)
  final def yxzy: C4 = make4(dy, dx, dz, dy)
  final def yxzz: C4 = make4(dy, dx, dz, dz)
  final def yyxz: C4 = make4(dy, dy, dx, dz)
  final def yyyz: C4 = make4(dy, dy, dy, dz)
  final def yyzx: C4 = make4(dy, dy, dz, dx)
  final def yyzy: C4 = make4(dy, dy, dz, dy)
  final def yyzz: C4 = make4(dy, dy, dz, dz)
  final def yzxx: C4 = make4(dy, dz, dx, dx)
  final def yzxy: C4 = make4(dy, dz, dx, dy)
  final def yzxz: C4 = make4(dy, dz, dx, dz)
  final def yzyx: C4 = make4(dy, dz, dy, dx)
  final def yzyy: C4 = make4(dy, dz, dy, dy)
  final def yzyz: C4 = make4(dy, dz, dy, dz)
  final def yzzx: C4 = make4(dy, dz, dz, dx)
  final def yzzy: C4 = make4(dy, dz, dz, dy)
  final def yzzz: C4 = make4(dy, dz, dz, dz)
  final def zxxx: C4 = make4(dz, dx, dx, dx)
  final def zxxy: C4 = make4(dz, dx, dx, dy)
  final def zxxz: C4 = make4(dz, dx, dx, dz)
  final def zxyx: C4 = make4(dz, dx, dy, dx)
  final def zxyy: C4 = make4(dz, dx, dy, dy)
  final def zxyz: C4 = make4(dz, dx, dy, dz)
  final def zxzx: C4 = make4(dz, dx, dz, dx)
  final def zxzy: C4 = make4(dz, dx, dz, dy)
  final def zxzz: C4 = make4(dz, dx, dz, dz)
  final def zyxx: C4 = make4(dz, dy, dx, dx)
  final def zyxy: C4 = make4(dz, dy, dx, dy)
  final def zyxz: C4 = make4(dz, dy, dx, dz)
  final def zyyx: C4 = make4(dz, dy, dy, dx)
  final def zyyy: C4 = make4(dz, dy, dy, dy)
  final def zyyz: C4 = make4(dz, dy, dy, dz)
  final def zyzx: C4 = make4(dz, dy, dz, dx)
  final def zyzy: C4 = make4(dz, dy, dz, dy)
  final def zyzz: C4 = make4(dz, dy, dz, dz)
  final def zzxx: C4 = make4(dz, dz, dx, dx)
  final def zzxy: C4 = make4(dz, dz, dx, dy)
  final def zzxz: C4 = make4(dz, dz, dx, dz)
  final def zzyx: C4 = make4(dz, dz, dy, dx)
  final def zzyy: C4 = make4(dz, dz, dy, dy)
  final def zzyz: C4 = make4(dz, dz, dy, dz)
  final def zzzx: C4 = make4(dz, dz, dz, dx)
  final def zzzy: C4 = make4(dz, dz, dz, dy)
  final def zzzz: C4 = make4(dz, dz, dz, dz)

  final def rb = xz
  final def gb = yz
  final def br = zx
  final def bg = zy
  final def bb = zz

  final def rrb = xxz
  final def rgb = xyz
  final def rbr = xzx
  final def rbg = xzy
  final def rbb = xzz
  final def grb = yxz
  final def ggb = yyz
  final def gbr = yzx
  final def gbg = yzy
  final def gbb = yzz
  final def brr = zxx
  final def brg = zxy
  final def brb = zxz
  final def bgr = zyx
  final def bgg = zyy
  final def bgb = zyz
  final def bbr = zzx
  final def bbg = zzy
  final def bbb = zzz

  final def rrrb = xxxz
  final def rrgb = xxyz
  final def rrbr = xxzx
  final def rrbg = xxzy
  final def rrbb = xxzz
  final def rgrb = xyxz
  final def rggb = xyyz
  final def rgbr = xyzx
  final def rgbg = xyzy
  final def rgbb = xyzz
  final def rbrr = xzxx
  final def rbrg = xzxy
  final def rbrb = xzxz
  final def rbgr = xzyx
  final def rbgg = xzyy
  final def rbgb = xzyz
  final def rbbr = xzzx
  final def rbbg = xzzy
  final def rbbb = xzzz
  final def grrb = yxxz
  final def grgb = yxyz
  final def grbr = yxzx
  final def grbg = yxzy
  final def grbb = yxzz
  final def ggrb = yyxz
  final def gggb = yyyz
  final def ggbr = yyzx
  final def ggbg = yyzy
  final def ggbb = yyzz
  final def gbrr = yzxx
  final def gbrg = yzxy
  final def gbrb = yzxz
  final def gbgr = yzyx
  final def gbgg = yzyy
  final def gbgb = yzyz
  final def gbbr = yzzx
  final def gbbg = yzzy
  final def gbbb = yzzz
  final def brrr = zxxx
  final def brrg = zxxy
  final def brrb = zxxz
  final def brgr = zxyx
  final def brgg = zxyy
  final def brgb = zxyz
  final def brbr = zxzx
  final def brbg = zxzy
  final def brbb = zxzz
  final def bgrr = zyxx
  final def bgrg = zyxy
  final def bgrb = zyxz
  final def bggr = zyyx
  final def bggg = zyyy
  final def bggb = zyyz
  final def bgbr = zyzx
  final def bgbg = zyzy
  final def bgbb = zyzz
  final def bbrr = zzxx
  final def bbrg = zzxy
  final def bbrb = zzxz
  final def bbgr = zzyx
  final def bbgg = zzyy
  final def bbgb = zzyz
  final def bbbr = zzzx
  final def bbbg = zzzy
  final def bbbb = zzzz

  final def sp = xz
  final def tp = yz
  final def ps = zx
  final def pt = zy
  final def pp = zz

  final def ssp = xxz
  final def stp = xyz
  final def sps = xzx
  final def spt = xzy
  final def spp = xzz
  final def tsp = yxz
  final def ttp = yyz
  final def tps = yzx
  final def tpt = yzy
  final def tpp = yzz
  final def pss = zxx
  final def pst = zxy
  final def psp = zxz
  final def pts = zyx
  final def ptt = zyy
  final def ptp = zyz
  final def pps = zzx
  final def ppt = zzy
  final def ppp = zzz

  final def sssp = xxxz
  final def sstp = xxyz
  final def ssps = xxzx
  final def sspt = xxzy
  final def sspp = xxzz
  final def stsp = xyxz
  final def sttp = xyyz
  final def stps = xyzx
  final def stpt = xyzy
  final def stpp = xyzz
  final def spss = xzxx
  final def spst = xzxy
  final def spsp = xzxz
  final def spts = xzyx
  final def sptt = xzyy
  final def sptp = xzyz
  final def spps = xzzx
  final def sppt = xzzy
  final def sppp = xzzz
  final def tssp = yxxz
  final def tstp = yxyz
  final def tsps = yxzx
  final def tspt = yxzy
  final def tspp = yxzz
  final def ttsp = yyxz
  final def tttp = yyyz
  final def ttps = yyzx
  final def ttpt = yyzy
  final def ttpp = yyzz
  final def tpss = yzxx
  final def tpst = yzxy
  final def tpsp = yzxz
  final def tpts = yzyx
  final def tptt = yzyy
  final def tptp = yzyz
  final def tpps = yzzx
  final def tppt = yzzy
  final def tppp = yzzz
  final def psss = zxxx
  final def psst = zxxy
  final def pssp = zxxz
  final def psts = zxyx
  final def pstt = zxyy
  final def pstp = zxyz
  final def psps = zxzx
  final def pspt = zxzy
  final def pspp = zxzz
  final def ptss = zyxx
  final def ptst = zyxy
  final def ptsp = zyxz
  final def ptts = zyyx
  final def pttt = zyyy
  final def pttp = zyyz
  final def ptps = zyzx
  final def ptpt = zyzy
  final def ptpp = zyzz
  final def ppss = zzxx
  final def ppst = zzxy
  final def ppsp = zzxz
  final def ppts = zzyx
  final def pptt = zzyy
  final def pptp = zzyz
  final def ppps = zzzx
  final def pppt = zzzy
  final def pppp = zzzz


  protected def xz_=(u: R2) { throw new UnsupportedOperationException }
  protected def yz_=(u: R2) { throw new UnsupportedOperationException }
  protected def zx_=(u: R2) { throw new UnsupportedOperationException }
  protected def zy_=(u: R2) { throw new UnsupportedOperationException }

  protected def xyz_=(u: R3) { throw new UnsupportedOperationException }
  protected def xzy_=(u: R3) { throw new UnsupportedOperationException }
  protected def yxz_=(u: R3) { throw new UnsupportedOperationException }
  protected def yzx_=(u: R3) { throw new UnsupportedOperationException }
  protected def zxy_=(u: R3) { throw new UnsupportedOperationException }
  protected def zyx_=(u: R3) { throw new UnsupportedOperationException }

  protected def rb_=(u: R2) { throw new UnsupportedOperationException }
  protected def gb_=(u: R2) { throw new UnsupportedOperationException }
  protected def br_=(u: R2) { throw new UnsupportedOperationException }
  protected def bg_=(u: R2) { throw new UnsupportedOperationException }

  protected def rgb_=(u: R3) { throw new UnsupportedOperationException }
  protected def rbg_=(u: R3) { throw new UnsupportedOperationException }
  protected def grb_=(u: R3) { throw new UnsupportedOperationException }
  protected def gbr_=(u: R3) { throw new UnsupportedOperationException }
  protected def brg_=(u: R3) { throw new UnsupportedOperationException }
  protected def bgr_=(u: R3) { throw new UnsupportedOperationException }

  protected def sp_=(u: R2) { throw new UnsupportedOperationException }
  protected def tp_=(u: R2) { throw new UnsupportedOperationException }
  protected def ps_=(u: R2) { throw new UnsupportedOperationException }
  protected def pt_=(u: R2) { throw new UnsupportedOperationException }

  protected def stp_=(u: R3) { throw new UnsupportedOperationException }
  protected def spt_=(u: R3) { throw new UnsupportedOperationException }
  protected def tsp_=(u: R3) { throw new UnsupportedOperationException }
  protected def tps_=(u: R3) { throw new UnsupportedOperationException }
  protected def pst_=(u: R3) { throw new UnsupportedOperationException }
  protected def pts_=(u: R3) { throw new UnsupportedOperationException }
}
