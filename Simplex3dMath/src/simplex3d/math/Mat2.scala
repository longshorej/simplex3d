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

import VecMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
sealed abstract class AnyMat2 {
    // Column major order.
    def m00: Float; def m10: Float // column
    def m01: Float; def m11: Float // column

    def apply(c: Int) :ConstVec2 = {
        c match {
            case 0 => ConstVec2(m00, m10)
            case 1 => ConstVec2(m01, m11)
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
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01
                    case 1 => m11
                    case _ => error
                }
            case _ => error
        }
    }

    def unary_-() = Mat2(
        -m00, -m10,
        -m01, -m11
    )
    def *(s: Float) = Mat2(
        s*m00, s*m10,
        s*m01, s*m11
    )
    def /(s: Float) = { val inv = 1/s; Mat2(
        inv*m00, inv*m10,
        inv*m01, inv*m11
    )}

    def +(m: AnyMat2) = Mat2(
        m00 + m.m00, m10 + m.m10,
        m01 + m.m01, m11 + m.m11
    )
    def -(m: AnyMat2) = Mat2(
        m00 - m.m00, m10 - m.m10,
        m01 - m.m01, m11 - m.m11
    )

    def *(m: AnyMat2) = Mat2(
        m00*m.m00 + m01*m.m10,
        m10*m.m00 + m11*m.m10,

        m00*m.m01 + m01*m.m11,
        m10*m.m01 + m11*m.m11
    )
    def *(m: AnyMat2x3) = Mat2x3(
        m00*m.m00 + m01*m.m10,
        m10*m.m00 + m11*m.m10,

        m00*m.m01 + m01*m.m11,
        m10*m.m01 + m11*m.m11,

        m00*m.m02 + m01*m.m12,
        m10*m.m02 + m11*m.m12
    )
    def *(m: AnyMat2x4) = Mat2x4(
        m00*m.m00 + m01*m.m10,
        m10*m.m00 + m11*m.m10,

        m00*m.m01 + m01*m.m11,
        m10*m.m01 + m11*m.m11,

        m00*m.m02 + m01*m.m12,
        m10*m.m02 + m11*m.m12,

        m00*m.m03 + m01*m.m13,
        m10*m.m03 + m11*m.m13
    )

    def *(u: AnyVec2) = Vec2(
        m00*u.x + m01*u.y,
        m10*u.x + m11*u.y
    )
    protected[math] def transposeMul(u: AnyVec2) = Vec2(
        m00*u.x + m10*u.y,
        m01*u.x + m11*u.y
    )


    def ==(m: AnyMat2) :Boolean = {
        if (m eq null) false
        else
            m00 == m.m00 && m10 == m.m10 &&
            m01 == m.m01 && m11 == m.m11
    }

    def !=(m: AnyMat2) :Boolean = !(this == m)

    /**
     * Approximate comparision.
     * Read: "this isApproximately m".
     */
    def ~=(m: AnyMat2) :Boolean = {
        if (m == null)
            false
        else (
            abs(m00 - m.m00) < ApproximationDelta &&
            abs(m10 - m.m10) < ApproximationDelta &&

            abs(m01 - m.m01) < ApproximationDelta &&
            abs(m11 - m.m11) < ApproximationDelta
        )
    }

    /**
     * Inverse of approximate comparision.
     * Read: "this isNotApproximately m" or "this isDistinctFrom m".
     */
    def !~(m: AnyMat2) :Boolean = !(this ~= m)

    def isValid: Boolean = {
        import java.lang.Float._

        !(
            isNaN(m00) || isInfinite(m00) ||
            isNaN(m10) || isInfinite(m10) ||

            isNaN(m01) || isInfinite(m01) ||
            isNaN(m11) || isInfinite(m11)
        )
    }

    override def toString = {
        this.getClass.getSimpleName +
        "(" +
            m00 + ", " + m10 + "; " + 
            m01 + ", " + m11 +
        ")"
    }
}

final class ConstMat2 private (
    val m00: Float, val m10: Float,
    val m01: Float, val m11: Float
) extends AnyMat2

object ConstMat2 {

    def apply(s: Float) = new ConstMat2(
        s, 0,
        0, s
    )

    def apply(
        m00: Float, m10: Float,
        m01: Float, m11: Float
      ) = new ConstMat2(
            m00, m10,
            m01, m11
      )

    private def read(arg: ReadAny[Float], mat: Array[Float], index: Int) :Int = {
        var i = index
        arg match {
            case s: ExtendedFloat => {
                mat(i) = s.value
                i += 1
            }
            case v2: AnyVec2 => {
                mat(i) = v2.x
                i += 1
                mat(i) = v2.y
                i += 1
            }
            case v3: AnyVec3 => {
                mat(i) = v3.x
                i += 1
                mat(i) = v3.y
                i += 1
                mat(i) = v3.z
                i += 1
            }
            case v4: AnyVec4 => {
                mat(i) = v4.x
                i += 1
                mat(i) = v4.y
                i += 1
                mat(i) = v4.z
                i += 1
                mat(i) = v4.w
                i += 1
            }
            case _ => throw new IllegalArgumentException(
                "Expected a scalar or a vector of type Float, " +
                "got " + arg.getClass.getName)
        }
        i
    }

    def apply(a0: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new ConstMat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new ConstMat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new ConstMat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(m: AnyMat2) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat2x3) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat2x4) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3x2) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3x4) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4x2) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4x3) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4) = new ConstMat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    implicit def mutableToConst(m: Mat2) = ConstMat2(m)
}


final class Mat2 private (
    var m00: Float, var m10: Float,
    var m01: Float, var m11: Float
) extends AnyMat2
{
    def *=(s: Float) {
        m00 *= s; m10 *= s;
        m01 *= s; m11 *= s
    }
    def /=(s: Float) { val inv = 1/s;
        m00 *= inv; m10 *= inv;
        m01 *= inv; m11 *= inv
    }

    def +=(m: AnyMat2) {
        m00 += m.m00; m10 += m.m10;
        m01 += m.m01; m11 += m.m11
    }
    def -=(m: AnyMat2) {
        m00 -= m.m00; m10 -= m.m10;
        m01 -= m.m01; m11 -= m.m11
    }

    def *=(m: AnyMat2) {
        val a00 = m00*m.m00 + m01*m.m10
        val a10 = m10*m.m00 + m11*m.m10

        val a01 = m00*m.m01 + m01*m.m11
        val a11 = m10*m.m01 + m11*m.m11

        m00 = a00; m10 = a10
        m01 = a01; m11 = a11
    }

    def :=(m: AnyMat2) {
        m00 = m.m00; m10 = m.m10;
        m01 = m.m01; m11 = m.m11
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
                    case _ => error
                }
            case 1 =>
                r match {
                    case 0 => m01 = s
                    case 1 => m11 = s
                    case _ => error
                }
            case _ => error
        }
    }

    def update(c: Int, v: AnyVec2) {
        c match {
            case 0 => m00 = v.x; m10 = v.y
            case 1 => m01 = v.x; m11 = v.y
            case j => throw new IndexOutOfBoundsException(
                    "excpected from 0 to 1, got " + j)
        }
    }

}

object Mat2 {

    def apply(s: Float) = new Mat2(
        s, 0,
        0, s
    )

    def apply(
        m00: Float, m10: Float,
        m01: Float, m11: Float
      ) = new Mat2(
            m00, m10,
            m01, m11
      )

    private def read(arg: ReadAny[Float], mat: Array[Float], index: Int) :Int = {
        var i = index
        arg match {
            case s: ExtendedFloat => {
                mat(i) = s.value
                i += 1
            }
            case v2: AnyVec2 => {
                mat(i) = v2.x
                i += 1
                mat(i) = v2.y
                i += 1
            }
            case v3: AnyVec3 => {
                mat(i) = v3.x
                i += 1
                mat(i) = v3.y
                i += 1
                mat(i) = v3.z
                i += 1
            }
            case v4: AnyVec4 => {
                mat(i) = v4.x
                i += 1
                mat(i) = v4.y
                i += 1
                mat(i) = v4.z
                i += 1
                mat(i) = v4.w
                i += 1
            }
            case _ => throw new IllegalArgumentException(
                "Expected a scalar or a vector of type Float, " +
                "got " + arg.getClass.getName)
        }
        i
    }

    def apply(a0: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new Mat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new Mat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(a0: ReadAny[Float], a1: ReadAny[Float], a2: ReadAny[Float]) =
    {
        val mat = new Array[Float](4)
        mat(0) = 1
        mat(3) = 1

        var index = 0
        try {
            index = read(a0, mat, index)
            index = read(a1, mat, index)
            index = read(a2, mat, index)
        } catch {
            case iae: IllegalArgumentException => {
                throw new IllegalArgumentException(iae.getMessage)
            }
            case aob: ArrayIndexOutOfBoundsException => {
                throw new IllegalArgumentException(
                    "Too many values for this matrix.")
            }
        }

        if (index < 4) throw new IllegalArgumentException(
            "Too few values for this matrix.")

        new Mat2(
            mat(0), mat(1),
            mat(2), mat(3)
        )
    }

    def apply(m: AnyMat2) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat2x3) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat2x4) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3x2) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat3x4) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4x2) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4x3) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )

    def apply(m: AnyMat4) = new Mat2(
        m.m00, m.m10,
        m.m01, m.m11
    )
}
