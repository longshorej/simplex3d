/*
 * Simplex3dEngine - Test Package
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

package test

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.algorithm.noise._
import simplex3d.engine._
import simplex3d.engine.graphics._
import simplex3d.engine.graphics.pluggable._
import simplex3d.engine.renderer._
import simplex3d.engine.bounding._
import simplex3d.engine.input._
import simplex3d.engine.input.handler._
import simplex3d.engine.scenegraph._
import simplex3d.engine.transformation._


object Main {

  def main(args: Array[String]) {
    
  }
}

class VTest extends VertexShader {
  uses("float foo()")
  
  attribute[Vec3]("atest")
  uniform[Vec3]("utest")
  
  
  val interface = new OutputBlock("textureStuff") {
    varying[Vec3]("smooth", "texCoords")
  }
}

class FTest extends FragmentShader {
  uses("float foo()")
  
  uniform[Vec3]("utest")
  
  new InputBlock("textureStuff") {
    varying[Vec3]("smooth", "texCoords")
  }

  val interface = new Function("int bar()")("""
    gl_FragColor = vec4(1);
  """)
}