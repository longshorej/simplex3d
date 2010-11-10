/*
 * Simplex3d, FloatMath module
 * Copyright (C) 2009-2010, Simplex3d Team
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

package simplex3d.math.floatm;

import java.io.Serializable;
import simplex3d.math.*;


/**
 * @author Aleksey Nikiforov (lex)
 */
abstract class ProtectedMat3x4f<P> extends AnyMat3x4<P> implements Serializable {
    public static final long serialVersionUID = 8104346712419693669L;
    float p00; float p10; float p20;
    float p01; float p11; float p21;
    float p02; float p12; float p22;
    float p03; float p13; float p23;
}