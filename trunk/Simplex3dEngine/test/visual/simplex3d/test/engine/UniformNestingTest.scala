/*
 * Simplex3dEngine - Test Package
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dEngineTest.
 *
 * Simplex3dEngineTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dEngineTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.test.engine

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.engine._
import simplex3d.engine.util._
import simplex3d.engine.graphics._


object UniformNestingTest extends simplex3d.vanilla.FullscreenEffectApp {
  
  def main(args: Array[String]) {
    launch()
  }
  
  val title = "Uniform Nesting Test"
  
  override lazy val settings = new Settings(
    verticalSync = true,
    resolution = Some(Vec2i(800, 600))
  )
  
  
  // Read part of Struct2 type.
  sealed abstract class ReadStruct2 extends prototype.ReadStruct {
    type Read = ReadStruct2
    type Mutable = Struct2
    
    def value2: ReadVec3
    def value2Array: BindingSeq[Vec3]
    
    def texture2: ReadTextureBinding[Texture2d[Vec3]]
  }
  
  // Read-write part of Struct2 type.
  // Instances of this class can be auto-mapped to uniform Struct2 values in the shader.
  final class Struct2 extends ReadStruct2 with prototype.Struct {
    protected def mkMutable() = new Struct2
    
    val value2 = Vec3(1)
    val value2Array = BindingList[Vec3](Vec3.One, Vec3.One)
    
    val texture2 = new TextureBinding[Texture2d[Vec3]]
    
    init(classOf[Struct2])
  }
  
  
  sealed abstract class ReadStruct1 extends prototype.ReadStruct {
    type Read = ReadStruct1
    type Mutable = Struct1
    
    def value1: ReadVec3
    def value1Array: BindingSeq[Vec3]
    
    def texture1: ReadTextureBinding[Texture2d[Vec3]]
    def texture1Array: BindingSeq[TextureBinding[Texture2d[Vec3]]]
    
    def struct2: ReadStruct2
    def struct2Array: BindingList[Struct2]
  }
  
  final class Struct1 extends ReadStruct1 with prototype.Struct {
    protected def mkMutable() = new Struct1
    
    val value1 = Vec3(1)
    val value1Array = BindingList[Vec3](Vec3.One, Vec3.One)
    
    val texture1 = new TextureBinding[Texture2d[Vec3]]
    val texture1Array = new BindingList[TextureBinding[Texture2d[Vec3]]]
    
    val struct2 = new Struct2
    val struct2Array = BindingList[Struct2](new Struct2, new Struct2)
    
    init(classOf[Struct1])
  }
  
  
  def mkTexture() = {
    val texture = Texture2d[Vec3](Vec2i(4)).fillWith(p => Vec3(1))
    new TextureBinding(texture)
  }
  

  val effect = new FullscreenEffect("Uniform Test") {
    
    // These values are auto-mapped to uniform values the shader based on name.
    val value0 = Value(Vec3(1))
    val value0Array = Value(BindingArray[Vec3](Vec3.One, Vec3.One))
    
    val texture0 = Value(new TextureBinding[Texture2d[Vec3]])
    val texture0Array = Value(new BindingList[TextureBinding[Texture2d[Vec3]]])
    
    val struct1 = Value(new Struct1)
    val struct1Array = Value(new BindingArray[Struct1](2))
    
    // Init textures.
    {
      texture0.update := mkTexture()
      
      texture0Array.update += mkTexture()
      texture0Array.update += mkTexture()
      
      
      struct1.update.texture1 := mkTexture()
      struct1.update.texture1Array += mkTexture()
      struct1.update.texture1Array += mkTexture()
      
      struct1.update.struct2.texture2 := mkTexture()
      struct1.update.struct2Array(0).texture2 := mkTexture()
      struct1.update.struct2Array(1).texture2 := mkTexture()
      
      
      struct1Array.update(0).texture1 := mkTexture()
      struct1Array.update(0).texture1Array += mkTexture()
      struct1Array.update(0).texture1Array += mkTexture()
      
      struct1Array.update(0).struct2.texture2 := mkTexture()
      struct1Array.update(0).struct2Array(0).texture2 := mkTexture()
      struct1Array.update(0).struct2Array(1).texture2 := mkTexture()
      
      
      struct1Array.update(1).texture1 := mkTexture()
      struct1Array.update(1).texture1Array += mkTexture()
      struct1Array.update(1).texture1Array += mkTexture()
      
      struct1Array.update(1).struct2.texture2 := mkTexture()
      struct1Array.update(1).struct2Array(0).texture2 := mkTexture()
      struct1Array.update(1).struct2Array(1).texture2 := mkTexture()
    }
    
    val fragmentShader =
    """
      // This struct mirrors Struct2 class definition to allow auto-mapping of values.
      struct Struct2 {
        vec3 value2;
        vec3 value2Array[2];
      
        sampler2D texture2;
      };
      
      struct Struct1 {
        vec3 value1;
        vec3 value1Array[2];
      
        sampler2D texture1;
        sampler2D texture1Array[2];
      
        Struct2 struct2;
        Struct2 struct2Array[2];
      };
      
      
      // These uniform values are auto-mapped from Defined values based on name.
      uniform vec3 value0;
      uniform vec3 value0Array[2];
      uniform sampler2D texture0;
      uniform sampler2D texture0Array[2];
      uniform Struct1 struct1;
      uniform Struct1 struct1Array[2];
      
      
      void main() {
        vec3 color = vec3(1);
      
        if (value0 != vec3(1)) color = vec3(0);
        if (value0Array[0] != vec3(1)) color = vec3(0);
        if (value0Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(texture0, vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(texture0Array[0], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(texture0Array[1], vec2(0)).rgb != vec3(1)) color = vec3(0);
    
        if (struct1.value1 != vec3(1)) color = vec3(0);
        if (struct1.value1Array[0] != vec3(1)) color = vec3(0);
        if (struct1.value1Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1.texture1, vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1.texture1Array[0], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1.texture1Array[1], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (struct1.struct2.value2 != vec3(1)) color = vec3(0);
        if (struct1.struct2.value2Array[0] != vec3(1)) color = vec3(0);
        if (struct1.struct2.value2Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1.struct2.texture2, vec2(0)).rgb != vec3(1)) color = vec3(0);
    
        if (struct1Array[0].value1 != vec3(1)) color = vec3(0);
        if (struct1Array[0].value1Array[0] != vec3(1)) color = vec3(0);
        if (struct1Array[0].value1Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[0].texture1, vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[0].texture1Array[0], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[0].texture1Array[1], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (struct1Array[0].struct2.value2 != vec3(1)) color = vec3(0);
        if (struct1Array[0].struct2.value2Array[0] != vec3(1)) color = vec3(0);
        if (struct1Array[0].struct2.value2Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[0].struct2.texture2, vec2(0)).rgb != vec3(1)) color = vec3(0);
    
        if (struct1Array[1].value1 != vec3(1)) color = vec3(0);
        if (struct1Array[1].value1Array[0] != vec3(1)) color = vec3(0);
        if (struct1Array[1].value1Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[1].texture1, vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[1].texture1Array[0], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[1].texture1Array[1], vec2(0)).rgb != vec3(1)) color = vec3(0);
        if (struct1Array[1].struct2.value2 != vec3(1)) color = vec3(0);
        if (struct1Array[1].struct2.value2Array[0] != vec3(1)) color = vec3(0);
        if (struct1Array[1].struct2.value2Array[1] != vec3(1)) color = vec3(0);
        if (texture2D(struct1Array[1].struct2.texture2, vec2(0)).rgb != vec3(1)) color = vec3(0);
      
      
        gl_FragColor = vec4(color, 1);
      }
    """
  }
}
