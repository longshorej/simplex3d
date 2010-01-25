/*
 * Simplex3d, MathTest package
 * Copyright (C) 2009-2010 Simplex3d Team
 *
 * This file is part of Simplex3dMathTest.
 *
 * Simplex3dMathTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dMathTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.math.floatm

import org.scalatest._

import simplex3d.math.floatm.renamed._
import simplex3d.math.floatm.FloatMath._


/**
 * @author Aleksey Nikiforov (lex)
 */
class FloatMathExtraTest extends FunSuite {

    test("Convert to quat") {
        def testMatrix(a: Float) {
            val m0: ConstMat3 = rotationMat(radians(a), normalize(Vec3(1, 2, 3)))
            val q: ConstQuat4 = quaternion(m0)
            val m1: ConstMat3 = rotationMat(q)

            assert(approxEqual(m0, m1, 1e-6f))
        }

        def testAngleAxis(angle: Float) {
            val angle0 = radians(angle)
            val axis0 = ConstVec3(0, 1, 0)

            val q = quaternion(angle0, axis0)

            val axis1 = Vec3(0)
            val angle1 = angleAxis(q, axis1)

            if (approxEqual(abs(angle0), 2*Pi, 1e-6f)) {
                assert(approxEqual(0, angle1, 1e-6f))
                assert(approxEqual(Vec3.UnitX, axis1, 1e-6f))
            }
            else if (approxEqual(angle0, 0, 1e-6f)) {
                assert(approxEqual(angle0, angle1, 1e-6f))
                assert(approxEqual(Vec3.UnitX, axis1, 1e-6f))
            }
            else {
                assert(
                    (
                        approxEqual(angle0, angle1, 1e-6f) &&
                        approxEqual(axis0, axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, -angle1, 1e-6f) &&
                        approxEqual(axis0, -axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, -(radians(360) - angle1), 1e-6f) &&
                        approxEqual(axis0, axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, (radians(360) - angle1), 1e-6f) &&
                        approxEqual(axis0, -axis1, 1e-6f)
                    )
                )
            }
        }

        testMatrix(-360)
        testMatrix(-270)
        testMatrix(-180)
        testMatrix(-90)
        testMatrix(-44)
        testMatrix(0)
        testMatrix(36)
        testMatrix(90)
        testMatrix(180)
        testMatrix(270)
        testMatrix(360)

        testAngleAxis(-360)
        testAngleAxis(-270)
        testAngleAxis(-180)
        testAngleAxis(-90)
        testAngleAxis(-44)
        testAngleAxis(0)
        testAngleAxis(36)
        testAngleAxis(90)
        testAngleAxis(180)
        testAngleAxis(270)
        testAngleAxis(360)
    }

    test("Convert to matrix") {
        def testQuaternion(a: Float) {
            val q0: ConstQuat4 = quaternion(radians(a), normalize(Vec3(4, 5, 6)))
            val m: ConstMat3 = rotationMat(q0)
            val q1: ConstQuat4 = quaternion(m)

            assert(approxEqual(q0, q1, 1e-6f) || approxEqual(q0, -q1, 1e-6f))
        }

        def testAngleAxis(angle: Float) {
            val angle0 = radians(angle)
            val axis0 = ConstVec3(0, 0, 1)

            val m: ConstMat3 = rotationMat(angle0, axis0)

            val axis1 = Vec3(0)
            val angle1 = angleAxis(m, axis1)

            if (approxEqual(abs(angle0), 2*Pi, 1e-6f)) {
                assert(approxEqual(0, angle1, 1e-6f))
                assert(approxEqual(Vec3.UnitX, axis1, 1e-6f))
            }
            else if (approxEqual(angle0, 0, 1e-6f)) {
                assert(approxEqual(angle0, angle1, 1e-6f))
                assert(approxEqual(Vec3.UnitX, axis1, 1e-6f))
            }
            else {
                assert(
                    (
                        approxEqual(angle0, angle1, 1e-6f) &&
                        approxEqual(axis0, axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, -angle1, 1e-6f) &&
                        approxEqual(axis0, -axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, -(radians(360) - angle1), 1e-6f) &&
                        approxEqual(axis0, axis1, 1e-6f)
                    )||
                    (
                        approxEqual(angle0, (radians(360) - angle1), 1e-6f) &&
                        approxEqual(axis0, -axis1, 1e-6f)
                    )
                )
            }
        }

        testQuaternion(-360)
        testQuaternion(-270)
        testQuaternion(-180)
        testQuaternion(-90)
        testQuaternion(-44)
        testQuaternion(0)
        testQuaternion(36)
        testQuaternion(90)
        testQuaternion(180)
        testQuaternion(270)
        testQuaternion(360)

        testAngleAxis(-360)
        testAngleAxis(-270)
        testAngleAxis(-180)
        testAngleAxis(-90)
        testAngleAxis(-44)
        testAngleAxis(0)
        testAngleAxis(36)
        testAngleAxis(90)
        testAngleAxis(180)
        testAngleAxis(270)
        testAngleAxis(360)
    }

    test("Convert to angleAxis") {
        def testQuaternion(a: Float) {
            val q0: ConstQuat4 = quaternion(radians(a), normalize(Vec3(4, 5, 6)))

            val axis = Vec3(0)
            val angle = angleAxis(q0, axis)

            val q1: ConstQuat4 = quaternion(angle, axis)

            assert(approxEqual(q0, q1, 1e-6f) || approxEqual(q0, -q1, 1e-6f))
        }

        def testMatrix(a: Float) {
            val m0: ConstMat3 = rotationMat(radians(a), normalize(Vec3(1, 2, 3)))

            val axis = Vec3(0)
            val angle = angleAxis(m0, axis)

            val m1: ConstMat3 = rotationMat(angle, axis)

            assert(approxEqual(m0, m1, 1e-6f))
        }

        testQuaternion(-360)
        testQuaternion(-270)
        testQuaternion(-180)
        testQuaternion(-90)
        testQuaternion(-44)
        testQuaternion(0)
        testQuaternion(36)
        testQuaternion(90)
        testQuaternion(180)
        testQuaternion(270)
        testQuaternion(360)

        testMatrix(-360)
        testMatrix(-270)
        testMatrix(-180)
        testMatrix(-90)
        testMatrix(-44)
        testMatrix(0)
        testMatrix(36)
        testMatrix(90)
        testMatrix(180)
        testMatrix(270)
        testMatrix(360)
    }

    test("All if branches, quat from mat") {
        def testMatrix(angle: Float, axis: AnyVec3) {
            val m0: ConstMat3 = rotationMat(
                radians(angle), normalize(axis)
            )
            val q: ConstQuat4 = quaternion(m0)
            val m1: ConstMat3 = rotationMat(q)

            assert(approxEqual(m0, m1, 1e-6f))
        }

        // branch 1
        testMatrix(336.7842f, Vec3(0.42147923f, -0.98776567f, -0.6945276f))

        // branch 2
        testMatrix(-210.44534f, Vec3(-0.92752934f, -0.334566f, 0.31773436f))

        // branch 3
        testMatrix(175.66881f, Vec3(0.41001987f, -0.71595466f, -0.4499402f))
        
        // branch 4
        testMatrix(-231.80054f, Vec3(-0.024970055f, 0.080794096f, -0.74685013f))
    }

    test("All if branches, angle axis from mat") {
        def testMatrix(ax: AnyVec3) {
            val m0: ConstMat3 = rotationMat(radians(180), normalize(ax))

            val axis = Vec3(0)
            val angle = angleAxis(m0, axis)

            val m1: ConstMat3 = rotationMat(angle, axis)

            assert(approxEqual(m0, m1, 1e-6f))
        }

        // sub-branch 1
        testMatrix(Vec3(0.39596772f, -0.080019474f, 0.35837686f))

        // sub-branch 2
        testMatrix(Vec3(-0.11932039f, 0.7943535f, -0.09355426f))

        // sub-branch 3
        testMatrix(Vec3(-0.19381118f, 0.30482996f, -0.4189638f))
    }

    test("Projection") {
        //gl.glMatrixMode(GL.GL_PROJECTION);
        //gl.glPushMatrix();
        //GLU g = new GLU();
        //float[] mat = new float[16];
        //
        //gl.glLoadIdentity();
        //g.gluPerspective(90, 640/480f, 10, 1000);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj1: " + java.util.Arrays.toString(mat));
        //
        //gl.glLoadIdentity();
        //g.gluPerspective(120, 800/600f, 10, 10000);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj2: " + java.util.Arrays.toString(mat));
        //
        //gl.glLoadIdentity();
        //g.gluPerspective(100, 1680/1050f, 1, 800);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj3: " + java.util.Arrays.toString(mat));
        //
        //gl.glLoadIdentity();
        //gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj4: " + java.util.Arrays.toString(mat));
        //
        //gl.glLoadIdentity();
        //gl.glOrtho(0, 300, -200, 400, -20, 500);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj5: " + java.util.Arrays.toString(mat));
        //
        //gl.glLoadIdentity();
        //gl.glOrtho(-500, 22, -800, -222, 100, 1000);
        //gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, mat, 0);
        //System.out.println("Proj6: " + java.util.Arrays.toString(mat));
        //
        //gl.glPopMatrix();

        // gluPerspective(90, 640/480f, 10, 1000)
        val p1 = Mat4(
                0.75f, 0, 0, 0,
                0, 1.0f, 0, 0,
                0, 0, -1.020202f, -1.0f,
                0, 0, -20.20202f, 0.0f
        )
        val m1 = perspective(radians(90), 640/480f, 10, 1000)
        assert(approxEqual(m1, p1, 1e-7f))
        
        // gluPerspective(120, 800/600f, 10, 10000)
        val p2 = Mat4(
                0.4330127f, 0, 0, 0,
                0, 0.57735026f, 0, 0,
                0, 0, -1.002002f, -1.0f,
                0, 0, -20.02002f, 0
        )
        val m2 = perspective(radians(120), 800/600f, 10, 10000)
        assert(approxEqual(m2, p2, 1e-7f))

        // gluPerspective(100, 1680/1050f, 1, 800)
        val p3 = Mat4(
                0.52443725f, 0, 0,
                0, 0, 0.83909965f, 0,
                0, 0, 0, -1.0025032f, -1.0f,
                0, 0, -2.0025032f, 0
        )
        val m3 = perspective(radians(100), 1680/1050f, 1, 800)
        assert(approxEqual(m3, p3, 1e-7f))

        // glOrtho(-100, 100, -100, 100, -100, 100)
        val o1 = Mat4(
                0.01f, 0, 0, 0,
                0, 0.01f, 0, 0,
                0, 0, -0.01f, 0,
                0, 0, 0, 1.0f
        )
        val n1 = ortho(-100, 100, -100, 100, -100, 100)
        assert(approxEqual(n1, o1, 1e-7f))

        // gl.glOrtho(0, 300, -200, 400, -20, 500)
        val o2 = Mat4(
                0.006666667f, 0, 0, 0,
                0, 0.0033333334f, 0, 0,
                0, 0, -0.0038461538f, 0,
                -1.0f, -0.33333334f, -0.9230769f, 1.0f
        )
        val n2 = ortho(0, 300, -200, 400, -20, 500)
        assert(approxEqual(n2, o2, 1e-7f))

        // gl.glOrtho(-500, 22, -800, -222, 100, 1000)
        val o3 = Mat4(
                0.0038314175f, 0, 0, 0,
                0, 0.0034602077f, 0, 0,
                0, 0, -0.0022222223f, 0,
                0.91570884f, 1.7681661f, -1.2222222f, 1.0f
        )
        val n3 = ortho(-500, 22, -800, -222, 100, 1000)
        assert(approxEqual(n3, o3, 1e-6f))
    }
}
