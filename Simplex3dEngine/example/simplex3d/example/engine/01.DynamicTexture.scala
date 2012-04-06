package simplex3d.example.engine

import scala.collection.mutable.ArrayBuffer
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.algorithm.noise._
import simplex3d.algorithm.mesh._
import simplex3d.engine._
import simplex3d.engine.graphics._
import simplex3d.engine.renderer._
import simplex3d.engine.bounding._
import simplex3d.engine.input._
import simplex3d.engine.input.handler._
import simplex3d.engine.scenegraph._
import simplex3d.engine.default._


object DynamicTexture extends BasicApp {
  val title = "Dynamic Texture"
  
  override lazy val settings = new Settings(
    fullscreen = false,
    verticalSync = true,
    logCapabilities = true,
    logPerformance = true,
    resolution = Some(Vec2i(800, 600))
  )
  
  
  private def writeImg(img: Data[Vec3], dims: inVec2i)(function: (Int, Int) => ReadVec3) {
    var y = 0; while (y < dims.y) {
      var x = 0; while (x < dims.x) {
        
        val i = x + y*dims.x
        img(i) = function(x, y)
        
        x += 1
      }
      y += 1
    }
  }
  
  val noise = ClassicalGradientNoise
  val objectTexture = Texture2d(Vec2i(128), DataBuffer[Vec3, UByte](128*128))
  val subImgDims = ConstVec2i(64)
  val subImg = DataBuffer[Vec3, UByte](subImgDims.x*subImgDims.y)

  val detailTexture = Texture2d(Vec2i(512), DataBuffer[Vec3, UByte](512*512))
  writeImg(detailTexture.write, detailTexture.dimensions) { (x, y) =>
    val intensity = abs(noise(x*0.3, y*0.3, 0) + 0.3) + 0.3
    Vec3(0, intensity, intensity)
  }
  
  def init() {
    world.camera.transformation.mutable.translation := Vec3(0, 0, 100)
    
    val camControls = new FirstPersonHandler(world.camera.transformation)
    addInputListener(camControls)
    addInputListener(new MouseGrabber(true)(KeyCode.Num_Enter, KeyCode.K_Enter)(camControls)())
    
    
    val (indices, vertices, normals, texCoords) = Shapes.makeBox()
    
    val mesh = new Mesh("Cube")
    
    mesh.geometry.indices.defineAs(Attributes(indices))
    mesh.geometry.vertices.defineAs(Attributes(vertices))
    mesh.geometry.normals.defineAs(Attributes(normals))
    
    mesh.geometry.texCoords.defineAs(Attributes(texCoords))
    
    mesh.material.textureUnits.mutable += new TextureUnit(objectTexture)
    mesh.material.textureUnits.mutable += new TextureUnit(detailTexture, Mat2x3.scale(2))
    
    mesh.transformation.mutable.rotation := Quat4 rotateX(radians(25)) rotateY(radians(-30))
    mesh.transformation.mutable.scale := 50
    
    world.attach(mesh)
  }
  
  def update(time: TimeStamp) {
    
    // Updating the texture: the changes will be synchronized with OpenGL automatically.
    writeImg(objectTexture.write, objectTexture.dimensions) { (x, y) =>
      val intensity = (noise(x*0.06, y*0.06, time.total*0.4) + 1)*0.5
      Vec3(0, intensity, intensity)
    }
    
    // An example on how to update sub-image.
    if (true) {
      writeImg(subImg, subImgDims) { (x, y) =>
        val intensity = (noise(x*0.12, y*0.12, time.total*0.8) + 1)*0.5
        Vec3(0, intensity, 0)
      }
      objectTexture.write.put2d(objectTexture.dimensions, Vec2i(32), subImg, subImgDims)
    }
  }
}