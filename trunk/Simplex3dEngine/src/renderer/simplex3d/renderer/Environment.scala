/*
 * Simplex3dEngine - Renderer Module
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

package simplex3d
package renderer

import simplex3d.engine.util._
import simplex3d.engine.graphics._
import simplex3d.engine.scene._


class Environment(controllerContext: ControllerContext) extends prototype.Environment(controllerContext) {
  val fog = Property(() => new renderer.fog.Fog)//XXX use a dedicated factory
  val lighting = Property(() => new renderer.lighting.Lighting)//XXX use a dedicated factory
  
  
  init(classOf[Environment])
}
