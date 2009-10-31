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

import Read._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyMat3x2
extends ConstRotationSubMat2
{
    // Column major order.
    def m00: Float; def m10: Float; def m20: Float // column
    def m01: Float; def m11: Float; def m21: Float // column

    def apply(c: Int) :ConstVec3 = {
        c match {
            case 0 => ConstVec3(m00, m10, m20)
            case 1 => ConstVec3(m01, m11, m21)
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 1, got " + j)
        }
    }

    def apply(c: Int, r: Int) :Float = {
        def error() :Float = {
            throw new IndexOutOfBoundsException("Trying to read index (" +
                     c + ", " + r + ") in " + this.getClass.getSimpleName)
        }

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

    def unary_-() = Mat3x2(
        -m00, -m10, -m20,
        -m01, -m11, -m21
    )
    def *(s: Float) = Mat3x2(
        s*m00, s*m10, s*m20,
        s*m01, s*m11, s*m21
    )
    def /(s: Float) = { val inv = 1/s; Mat3x2(
        inv*m00, inv*m10, inv*m20,
        inv*m01, inv*m11, inv*m21
    )}

    def +(m: AnyMat3x2) = Mat3x2(
        m00 + m.m00, m10 + m.m10, m20 + m.m20,
        m01 + m.m01, m11 + m.m11, m21 + m.m21
    )
    def -(m: AnyMat3x2) = Mat3x2(
        m00 - m.m00, m10 - m.m10, m20 - m.m20,
        m01 - m.m01, m11 - m.m11, m21 - m.m21
    )

    /**
     * Component-wise devision.
     */
    def /(m: AnyMat3x2) = Mat3x2(
        m00/m.m00, m10/m.m10, m20/m.m20,
        m01/m.m01, m11/m.m11, m21/m.m21
    )
    private[math] def divideByComponent(s: Float) = Mat3x2(
        s/m00, s/m10, s/m20,
        s/m01, s/m11, s/m21
    )

    def *(m: AnyMat2) = Mat3x2(
        m00*m.m00 + m01*m.m10,
        m10*m.m00 + m11*m.m10,
        m20*m.m00 + m21*m.m10,

        m00*m.m01 + m01*m.m11,
        m10*m.m01 + m11*m.m11,
        m20*m.m01 + m21*m.m11
    )
    def *(m: AnyMat2x3) = Mat3(
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
    def *(m: AnyMat2x4) = Mat3x4(
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

    def *(u: AnyVec2) = Vec3(
        m00*u.x + m01*u.y,
        m10*u.x + m11*u.y,
        m20*u.x + m21*u.y
    )
    protected[math] def transposeMul(u: AnyVec3) = Vec2(
        m00*u.x + m10*u.y + m20*u.z,
        m01*u.x + m11*u.y + m21*u.z
    )

    def ==(m: AnyMat3x2) :Boolean = {
        if (m eq null) false
        else
            m00 == m.m00 && m10 == m.m10 && m20 == m.m20 &&
            m01 == m.m01 && m11 == m.m11 && m21 == m.m21
    }

    def !=(m: AnyMat3x2) :Boolean = !(this == m)

    private[math] def hasErrors: Boolean = {
        import java.lang.Float._

        (
            isNaN(m00) || isInfinite(m00) ||
            isNaN(m10) || isInfinite(m10) ||
            isNaN(m20) || isInfinite(m20) ||

            isNaN(m01) || isInfinite(m01) ||
            isNaN(m11) || isInfinite(m11) ||
            isNaN(m21) || isInfinite(m21)
        )
    }

    override def toString = {
        this.getClass.getSimpleName +
        "(" +
            m00 + ", " + m10 + ", " + m20 + "; " + 
            m01 + ", " + m11 + ", " + m21 +
        ")"
    }
}

final class ConstMat3x2 private (
    val m00: Float, val m10: Float, val m20: Float,
    val m01: Float, val m11: Float, val m21: Float
) extends AnyMat3x2

object ConstMat3x2 {

    def apply(s: Float) = new ConstMat3x2(
        s, 0, 0,
        0, s, 0
    )

    def apply(
        m00: Float, m10: Float, m20: Float,
        m01: Float, m11: Float, m21: Float
      ) = new ConstMat3x2(
            m00, m10, m20,
            m01, m11, m21
      )

    def apply(args: ReadAny[Float]*) :ConstMat3x2 = {
        val mat = new Array[Float](6)
        mat(0) = 1
        mat(4) = 1

        var index = 0
        try {
            var i = 0; while (i < args.length) {
                index = read(args(i), mat, index)
                i += 1
            }
        }
        catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 6) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new ConstMat3x2(
            mat(0), mat(1), mat(2),
            mat(3), mat(4), mat(5)
        )
    }

    def apply(m: AnyMat2) = new ConstMat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat2x3) = new ConstMat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat2x4) = new ConstMat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat3x2) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat3) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat3x4) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4x2) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4x3) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4) = new ConstMat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    implicit def mutableToConst(m: Mat3x2) = ConstMat3x2(m)
}


final class Mat3x2 private (
    var m00: Float, var m10: Float, var m20: Float,
    var m01: Float, var m11: Float, var m21: Float
) extends AnyMat3x2 with RotationSubMat2
{
    def *=(s: Float) {
        m00 *= s; m10 *= s; m20 *= s;
        m01 *= s; m11 *= s; m21 *= s
    }
    def /=(s: Float) { val inv = 1/s;
        m00 *= inv; m10 *= inv; m20 *= inv;
        m01 *= inv; m11 *= inv; m21 *= inv
    }

    def +=(m: AnyMat3x2) {
        m00 += m.m00; m10 += m.m10; m20 += m.m20;
        m01 += m.m01; m11 += m.m11; m21 += m.m21
    }
    def -=(m: AnyMat3x2) {
        m00 -= m.m00; m10 -= m.m10; m20 -= m.m20;
        m01 -= m.m01; m11 -= m.m11; m21 -= m.m21
    }

    def *=(m: AnyMat2) {
        val a00 = m00*m.m00 + m01*m.m10
        val a10 = m10*m.m00 + m11*m.m10
        val a20 = m20*m.m00 + m21*m.m10

        val a01 = m00*m.m01 + m01*m.m11
        val a11 = m10*m.m01 + m11*m.m11
        val a21 = m20*m.m01 + m21*m.m11

        m00 = a00; m10 = a10; m20 = a20
        m01 = a01; m11 = a11; m21 = a21
    }

    def :=(m: AnyMat3x2) {
        m00 = m.m00; m10 = m.m10; m20 = m.m20;
        m01 = m.m01; m11 = m.m11; m21 = m.m21
    }

    def set(
        m00: Float, m10: Float, m20: Float,
        m01: Float, m11: Float, m21: Float
    ) {
        this.m00 = m00; this.m10 = m10; this.m20 = m20;
        this.m01 = m01; this.m11 = m11; this.m21 = m21
    }

    def update(c: Int, r: Int, s: Float) {
        def error() {
            throw new IndexOutOfBoundsException("Trying to update index (" +
                     c + ", " + r + ") in " + this.getClass.getSimpleName)
        }

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

    def update(c: Int, v: AnyVec3) {
        c match {
            case 0 => m00 = v.x; m10 = v.y; m20 = v.z
            case 1 => m01 = v.x; m11 = v.y; m21 = v.z
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 1, got " + j)
        }
    }

}

object Mat3x2 {

    def apply(s: Float) = new Mat3x2(
        s, 0, 0,
        0, s, 0
    )

    def apply(
        m00: Float, m10: Float, m20: Float,
        m01: Float, m11: Float, m21: Float
      ) = new Mat3x2(
            m00, m10, m20,
            m01, m11, m21
      )

    def apply(args: ReadAny[Float]*) :Mat3x2 = {
        val mat = new Array[Float](6)
        mat(0) = 1
        mat(4) = 1

        var index = 0
        try {
            var i = 0; while (i < args.length) {
                index = read(args(i), mat, index)
                i += 1
            }
        }
        catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 6) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new Mat3x2(
            mat(0), mat(1), mat(2),
            mat(3), mat(4), mat(5)
        )
    }

    def apply(m: AnyMat2) = new Mat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat2x3) = new Mat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat2x4) = new Mat3x2(
        m.m00, m.m10, 0,
        m.m01, m.m11, 0
    )

    def apply(m: AnyMat3x2) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat3) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat3x4) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4x2) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4x3) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )

    def apply(m: AnyMat4) = new Mat3x2(
        m.m00, m.m10, m.m20,
        m.m01, m.m11, m.m21
    )
}
