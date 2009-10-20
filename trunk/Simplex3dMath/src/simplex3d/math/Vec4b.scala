/*
 * Simplex3D, Math package
 * Copyright (C) 2009 Simplex3D team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * CLASSPATH EXCEPTION FOR UNMODIFIED WORK:
 * Linking this library statically or dynamically with other modules is making
 * a combined work based on this library. Thus, the terms and conditions of
 * the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce
 * an executable, regardless of the license terms of these independent modules,
 * and to copy and distribute the resulting executable under terms of your
 * choice, provided that you also meet, for each linked independent module,
 * the terms and conditions of the license of that module. An independent module
 * is a module which is not derived from or based on this library. If you modify
 * this library in any way, then this exception is null and void and no longer
 * applies, in this case delete this exception statement from your version.
 */

package simplex3d.math

import simplex3d.math.VecMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyVec4b extends Read4[Boolean] {

    def x: Boolean
    def y: Boolean
    def z: Boolean
    def w: Boolean


    def apply(i: Int) :Boolean = {
        i match {
            case 0 => x
            case 1 => y
            case 2 => z
            case 3 => w
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }

    def ==(u: AnyVec4b) :Boolean = {
        if (u eq null) false
        else x == u.x && y == u.y && z == u.z && w == u.w
    }

    def !=(u: AnyVec4b) :Boolean = !(this == u)
    
    override def toString = {
        this.getClass.getSimpleName +
        "(" + x + ", " + y + ", " + z + ", " + w + ")"
    }
}

final class ConstVec4b private (val x: Boolean, val y: Boolean,
                               val z: Boolean, val w: Boolean)
extends AnyVec4b

object ConstVec4b {
    def apply(s: Boolean) =
        new ConstVec4b(s, s, s, s)

    def apply(x: Boolean, y: Boolean, z: Boolean, w: Boolean) =
        new ConstVec4b(x, y, z, w)

    def apply(u: AnyVec4b) =
        new ConstVec4b(u.x, u.y, u.z, u.w)

    def apply(xy: AnyVec2b, z: Boolean, w: Boolean) =
        new ConstVec4b(xy.x, xy.y, z, w)

    def apply(x: Boolean, yz: AnyVec2b, w: Boolean) =
        new ConstVec4b(x, yz.x, yz.y, w)

    def apply(x: Boolean, y: Boolean, zw: AnyVec2b) =
        new ConstVec4b(x, y, zw.x, zw.y)

    def apply(xy: AnyVec2b, zw: AnyVec2b) =
        new ConstVec4b(xy.x, xy.y, zw.x, zw.y)

    def apply(xyz: AnyVec3b, w: Boolean) =
        new ConstVec4b(xyz.x, xyz.y, xyz.z, w)

    def apply(x: Boolean, yzw: AnyVec3b) =
        new ConstVec4b(x, yzw.x, yzw.y, yzw.z)
        
    implicit def mutableToConst(u: Vec4b) = ConstVec4b(u)
    implicit def constVec4bToSwizzled(u: ConstVec4b) = new ConstVec4bSwizzled(u)
}

final class Vec4b private (var x: Boolean, var y: Boolean,
                          var z: Boolean, var w: Boolean)
extends AnyVec4b
{
    def :=(u: AnyVec4b) { x = u.x; y = u.y; z = u.z; w = u.w }
    def set(x: Boolean, y: Boolean, z: Boolean, w: Boolean) {
        this.x = x; this.y = y; this.z = z; this.w = w
    }

    def update(i: Int, s: Boolean) {
        i match {
            case 0 => x = s
            case 1 => y = s
            case 2 => z = s
            case 3 => w = s
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 3, got " + j)
        }
    }
}

object Vec4b {
    def apply(s: Boolean) =
        new Vec4b(s, s, s, s)

    def apply(x: Boolean, y: Boolean, z: Boolean, w: Boolean) =
        new Vec4b(x, y, z, w)

    def apply(u: AnyVec4b) =
        new Vec4b(u.x, u.y, u.z, u.w)

    def apply(xy: AnyVec2b, z: Boolean, w: Boolean) =
        new Vec4b(xy.x, xy.y, z, w)

    def apply(x: Boolean, yz: AnyVec2b, w: Boolean) =
        new Vec4b(x, yz.x, yz.y, w)

    def apply(x: Boolean, y: Boolean, zw: AnyVec2b) =
        new Vec4b(x, y, zw.x, zw.y)

    def apply(xy: AnyVec2b, zw: AnyVec2b) =
        new Vec4b(xy.x, xy.y, zw.x, zw.y)

    def apply(xyz: AnyVec3b, w: Boolean) =
        new Vec4b(xyz.x, xyz.y, xyz.z, w)

    def apply(x: Boolean, yzw: AnyVec3b) =
        new Vec4b(x, yzw.x, yzw.y, yzw.z)

    implicit def vec4bToSwizzled(u: Vec4b) = new Vec4bSwizzled(u)
}

class ConstVec4bSwizzled(u: AnyVec4b) extends BooleanVecFactory
with Swizzle4Read[Boolean, ConstVec2b, ConstVec3b, ConstVec4b]
{
    def x = u.x
    def y = u.y
    def z = u.z
    def w = u.w
}

class Vec4bSwizzled(u: Vec4b) extends ConstVec4bSwizzled(u)
with Swizzle4Write[Boolean, ConstVec2b, ConstVec3b, ConstVec4b]
{
    def x_=(x: Boolean) { u.x = x }
    def y_=(y: Boolean) { u.y = y }
    def z_=(z: Boolean) { u.z = z }
    def w_=(w: Boolean) { u.w = w }
}
