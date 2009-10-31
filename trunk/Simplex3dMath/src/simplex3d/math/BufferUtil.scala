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

import java.nio.FloatBuffer


/**
 * @author Aleksey Nikiforov (lex)
 */
object BufferUtil {

    // Matrix <-> buffer conversions
    /**
     * Column major order
     */
    def toArray(m: AnyMat3x4, a: Array[Float]) {
        toArray(m, a, true, 0)
    }
    /**
     * Column major order
     */
    def toBuffer(m: AnyMat3x4, buf: FloatBuffer) {
        toBuffer(m, buf, true, 0)
    }
    /**
     * Column major order
     */
    def toArray(m: AnyMat3x4, a: Array[Float], upcast: Boolean, offset: Int) {
        import m._

        if (upcast) {
            a(offset) = m00
            a(offset + 1) = m10
            a(offset + 2) = m20
            a(offset + 3) = 0
            a(offset + 4) = m01
            a(offset + 5) = m11
            a(offset + 6) = m21
            a(offset + 7) = 0
            a(offset + 8) = m02
            a(offset + 9) = m12
            a(offset + 10) = m22
            a(offset + 11) = 0
            a(offset + 12) = m03
            a(offset + 13) = m13
            a(offset + 14) = m23
            a(offset + 15) = 1
        } else {
            a(offset) = m00
            a(offset + 1) = m10
            a(offset + 2) = m20
            a(offset + 3) = m01
            a(offset + 4) = m11
            a(offset + 5) = m21
            a(offset + 6) = m02
            a(offset + 7) = m12
            a(offset + 8) = m22
            a(offset + 9) = m03
            a(offset + 10) = m13
            a(offset + 11) = m23
        }
    }
    /**
     * Column major order
     */
    def toBuffer(m: AnyMat3x4, buf: FloatBuffer, upcast: Boolean, offset: Int) {
        import m._

        buf.position(offset)

        if (upcast) {
            buf.put(m00)
            buf.put(m10)
            buf.put(m20)
            buf.put(0)
            buf.put(m01)
            buf.put(m11)
            buf.put(m21)
            buf.put(0)
            buf.put(m02)
            buf.put(m12)
            buf.put(m22)
            buf.put(0)
            buf.put(m03)
            buf.put(m13)
            buf.put(m23)
            buf.put(1)
        } else {
            buf.put(m00)
            buf.put(m10)
            buf.put(m20)
            buf.put(m01)
            buf.put(m11)
            buf.put(m21)
            buf.put(m02)
            buf.put(m12)
            buf.put(m22)
            buf.put(m03)
            buf.put(m13)
            buf.put(m23)
        }
    }

    /**
     * Column major order
     */
    def toArray(m: AnyMat4, a: Array[Float]) {
        toArray(m, a, 0)
    }
    /**
     * Column major order
     */
    def toBuffer(m: AnyMat4, buf: FloatBuffer) {
        toBuffer(m, buf, 0)
    }
    /**
     * Column major order
     */
    def toArray(m: AnyMat4, a: Array[Float], offset: Int) {
        import m._

        a(offset) = m00
        a(offset + 1) = m10
        a(offset + 2) = m20
        a(offset + 3) = m30
        a(offset + 4) = m01
        a(offset + 5) = m11
        a(offset + 6) = m21
        a(offset + 7) = m31
        a(offset + 8) = m02
        a(offset + 9) = m12
        a(offset + 10) = m22
        a(offset + 11) = m32
        a(offset + 12) = m03
        a(offset + 13) = m13
        a(offset + 14) = m23
        a(offset + 15) = m33
    }
    /**
     * Column major order
     */
    def toBuffer(m: AnyMat4, buf: FloatBuffer, offset: Int) {
        import m._

        buf.position(offset)
        
        buf.put(m00)
        buf.put(m10)
        buf.put(m20)
        buf.put(m30)
        buf.put(m01)
        buf.put(m11)
        buf.put(m21)
        buf.put(m31)
        buf.put(m02)
        buf.put(m12)
        buf.put(m22)
        buf.put(m32)
        buf.put(m03)
        buf.put(m13)
        buf.put(m23)
        buf.put(m33)
    }
}
