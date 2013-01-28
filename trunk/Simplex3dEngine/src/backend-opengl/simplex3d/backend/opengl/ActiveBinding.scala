/*
 * Simplex3dEngine - GL Module
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dEngine.
 *
 * Simplex3dEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.backend.opengl

import simplex3d.data._
import simplex3d.engine.graphics._


final class ActiveAttribute(val name: String, val dataType: Int, val location: Int) {
  override def toString :String = {
    "GeometryAttributes(\"" + name +
    "\", type = " + EngineBindingTypes.toString(dataType) + ", location = " + location + ")"
  }
}


private[backend] class ActiveUniform(
  final val name: String,
  final val dataType: Int,
  final val location: Int
) {
  
  override def toString :String = {
    "Uniform('" + name + "', type = " +
    EngineBindingTypes.toString(dataType) + ", location = " + location + ")"
  }
}

private[backend] final class ActiveTexture(
  name: String, dataType: Int, location: Int, final val textureUnit: Int
) extends ActiveUniform(name, dataType, location) {
  override def toString :String = {
    "Texture('" + name + "', type = " +
    EngineBindingTypes.toString(dataType) + ", location = " + location + ", unit = " + textureUnit + ")"
  }
}
