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


/** The <code>ExtendedInt</code> class encapsulates glue code to make Ints
 * interact with Int vectors.
 * <p>
 *   Instances of this class are produces when required via implicit conversions
 *   from Int.
 * </p>
 *
 * @author Aleksey Nikiforov (lex)
 */
final class ExtendedInt(val value: Int) {

    /** Multiplies this scalar by a vector.
     * @param u a vector to multiply by.
     * @return u*scalar.
     */
    def *(u: AnyVec2i) = u*value

    /** Multiplies this scalar by a vector.
     * @param u a vector to multiply by.
     * @return u*scalar.
     */
    def *(u: AnyVec3i) = u*value

    /** Multiplies this scalar by a vector.
     * @param u a vector to multiply by.
     * @return u*scalar.
     */
    def *(u: AnyVec4i) = u*value

    /** Divides this scalar by a vector.
     * @param u a vector to divide by.
     * @return a vector with components s/u.x and s/u.y.
     */
    def /(u: AnyVec2i) = u.divideByComponent(value)

    /** Divides this scalar by a vector.
     * @param u a vector to divide by.
     * @return a vector with components s/u.x, s/u.y, and s/u.z.
     */
    def /(u: AnyVec3i) = u.divideByComponent(value)

    /** Divides this scalar by a vector.
     * @param u a vector to divide by.
     * @return a vector with components s/u.x, s/u.y, s/u.z, and s/u.w.
     */
    def /(u: AnyVec4i) = u.divideByComponent(value)

    /** Adds this scalar to each component of a vector.
     * @param u a vector to add to.
     * @return a vector with components s + u.x and s + u.y.
     */
    def +(u: AnyVec2i) = u + value

    /** Adds this scalar to each component of a vector.
     * @param u a vector to add to.
     * @return a vector with components s + u.x, s + u.y, and s + u.z.
     */
    def +(u: AnyVec3i) = u + value

    /** Adds this scalar to each component of a vector.
     * @param u a vector to add to.
     * @return a vector with components s + u.x, s + u.y, s + u.z, and s + u.w.
     */
    def +(u: AnyVec4i) = u + value

    /** Subtracts each component of a vector from this scalar.
     * @param u a vector to subtract.
     * @return a vector with components s - u.x and s - u.y.
     */
    def -(u: AnyVec2i) =
        new Vec2i(value - u.x, value - u.y)

    /** Subtracts each component of a vector from this scalar.
     * @param u a vector to subtract.
     * @return a vector with components s - u.x, s - u.y, and s - u.z.
     */
    def -(u: AnyVec3i) =
        new Vec3i(value - u.x, value - u.y, value - u.z)

    /** Subtracts each component of a vector from this scalar.
     * @param u a vector to subtract.
     * @return a vector with components s - u.x, s - u.y, s - u.z, and s - u.w.
     */
    def -(u: AnyVec4i) =
        new Vec4i(value - u.x, value - u.y, value - u.z, value - u.w)

    /** Computes remainders of divisions of this scalar
     * by each component of a vector.
     *
     * @param u a vector to divide by.
     * @return a vector with components s % u.x and s % u.y.
     */
    def %(u: AnyVec2i) = u.modByComponent(value)

    /** Computes remainders of divisions of this scalar
     * by each component of a vector.
     *
     * @param u a vector to divide by.
     * @return a vector with components s % u.x, s % u.y, and s % u.z.
     */
    def %(u: AnyVec3i) = u.modByComponent(value)

    /** Computes remainders of divisions of this scalar
     * by each component of a vector.
     *
     * @param u a vector to divide by.
     * @return a vector with components s % u.x, s % u.y, s % u.z, and s % u.w.
     */
    def %(u: AnyVec4i) = u.modByComponent(value)

    /** Computes bitwise AND of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s & u.x and s & u.y.
     */
    def &(u: AnyVec2i) = u & value

    /** Computes bitwise AND of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s & u.x, s & u.y, and s & u.z.
     */
    def &(u: AnyVec3i) = u & value

    /** Computes bitwise AND of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s & u.x, s & u.y, s & u.z, and s & u.w.
     */
    def &(u: AnyVec4i) = u & value

    /** Computes bitwise OR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s | u.x and s | u.y.
     */
    def |(u: AnyVec2i) = u | value

    /** Computes bitwise OR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s | u.x, s | u.y, and s | u.z.
     */
    def |(u: AnyVec3i) = u | value

    /** Computes bitwise OR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s | u.x, s | u.y, s | u.z, and s | u.w.
     */
    def |(u: AnyVec4i) = u | value

    /** Computes bitwise XOR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s ^ u.x and s ^ u.y.
     */
    def ^(u: AnyVec2i) = u ^ value

    /** Computes bitwise XOR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s ^ u.x, s ^ u.y, and s ^ u.z.
     */
    def ^(u: AnyVec3i) = u ^ value

    /** Computes bitwise XOR of this scalar with each component of a vector.
     * @param u a vector.
     * @return a vector with components s ^ u.x, s ^ u.y, s ^ u.z, and s ^ u.w.
     */
    def ^(u: AnyVec4i) = u ^ value
}
