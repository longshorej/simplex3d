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

package simplex3d.engine
package graphics.pluggable

import simplex3d.math._
import simplex3d.engine.util._
import simplex3d.engine.scene._
import simplex3d.engine.scene.api._
import simplex3d.engine.graphics._


class PassManager[G <: graphics.GraphicsContext] extends graphics.PassManager[G] {
  
  private val singlePass = new Pass(new FrameBuffer(Vec2i(100))) //XXX get framebuffer from renderContext, keep track of viewport changes
  private val renderArray = new SortBuffer[AbstractMesh]()
  
  def render(renderManager: RenderManager, time: TimeStamp, scene: ManagedScene[G]) {
    renderManager.renderContext.clearFrameBuffer()
    renderArray.clear()
    
    scene.buildRenderArray(singlePass, time, renderArray)
    renderManager.sortRenderArray(singlePass, renderArray)
    renderManager.render(time, scene.camera, renderArray)
  }
}
