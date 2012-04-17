/*
 * Simplex3dEngine - Core Module
 * Copyright (C) 2011-2012, Aleksey Nikiforov
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
package default

import scala.collection.mutable.ArrayBuffer
import simplex3d.math._
import simplex3d.math.double.functions._
import simplex3d.engine.util._
import simplex3d.engine.graphics._
import simplex3d.engine.scene._
import simplex3d.engine.scenegraph._
import simplex3d.engine.asset._
import simplex3d.engine.transformation._


// Cannot be a trait, due to AccessControlException caused by method invocation routed via trait's implementation.
abstract class BaseApp extends simplex3d.engine.App {
  
  type Transformation <: TransformationContext
  type Graphics <: GraphicsContext
  
  type Bounded = scenegraph.Bounded[Transformation, Graphics]
  type Camera = scenegraph.Camera[Transformation, Graphics]
  type Entity = scenegraph.Entity[Transformation, Graphics]
  type InstancingNode = scenegraph.InstancingNode[Transformation, Graphics]
  type Mesh = scenegraph.Mesh[Transformation, Graphics]
  type Node = scenegraph.Node[Transformation, Graphics]
  type SceneElement = scenegraph.SceneElement[Transformation, Graphics]
  type Spatial = scenegraph.Spatial[Transformation]
  
  
  protected val world: SceneGraph[Transformation, Graphics]
  
  
  lazy val config = new Config
  lazy val settings = new Settings
  protected lazy val sceneGraphSettings = new SceneGraphSettings
  
  
  protected val assetManager = new AssetManager {
    loaders += new ClasspathLoader
  }
  
  
  import AccessScene._
  
  protected def preUpdate(time: TimeStamp) {
    world.update(time)
  }
  
  protected def render(time: TimeStamp) {
    world.render(renderManager, time)
  }
  
  protected def manage() {
    world.manage(renderManager.renderContext, timer.frameTimer, 0.01) //XXX make this value relate to the refresh rate
  }
  
  protected def reshape(position: inVec2i, dimensions: inVec2i) {
    val aspect = dimensions.x.toDouble/dimensions.y
    world.camera.projection := perspectiveProj(radians(60), aspect, 5, 1000)
  }
}
