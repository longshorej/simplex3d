package simplex3d.example.engine

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.data._
import simplex3d.data.double._
import simplex3d.algorithm.noise._
import simplex3d.algorithm.mesh._
import simplex3d.engine._
import simplex3d.engine.renderer._
import simplex3d.engine.bounding._
import simplex3d.engine.input._
import simplex3d.engine.input.handler._
import simplex3d.engine.scenegraph._
import simplex3d.engine.graphics._


object PointSpritesMesh extends default.App {
  
  def main(args: Array[String]) {
    launch()
  }
  
  val pointCount = 30000
  val title = "PointSprites: " + pointCount + " points."
  
  override lazy val settings = new Settings(
    fullscreen = false,
    verticalSync = false,
    logCapabilities = true,
    logPerformance = true,
    resolution = Some(Vec2i(800, 600))
  )
  
  val seed = 2
  val nx = new ClassicalGradientNoise(0)
  val ny = new ClassicalGradientNoise(seed + 1)
  val nz = new ClassicalGradientNoise(seed + 2)
  val offsetScale = 0.05
  def curve(index: Int, uptime: Double): Vec3 = {
    val a = index*offsetScale + uptime
    val b = 28.25896 //uptime*curveSpeed
    Vec3(nx(a, b), ny(a, b), nz(a, b))*100
  }

  
  val mesh = new Mesh("PointSprites")
  
  def init() {
    world.camera.transformation.update.translation := Vec3(0, 0, 200)
    
    addInputListener(new MouseGrabber(false)(KeyCode.Num_Enter, KeyCode.K_Enter))
    addInputListener(new FirstPersonHandler(world.camera.transformation))
    
    
    val objectTexture = Texture2d[Vec4](Vec2i(128))
    objectTexture.fillWith { p =>
      val innerRadius = 44
      val outerRadius = 64
  
      val len = length(p - Vec2(64))
      
      if (len < innerRadius - 2) {
        Vec4(0, 0.8, 0.8, 1)
      }
      else if (len < innerRadius + 2) {
        val factor = (innerRadius - len)*0.25
        mix(Vec4(0.1, 0.1, 1, 1), Vec4(0, 0.8, 0.8, 1), clamp(factor, 0, 1))
      }
      else if (len < outerRadius - 2) {
        Vec4(0.1, 0.1, 1, 1)
      }
      else if (len < outerRadius + 2) {
        val factor = (outerRadius - len)*0.25
        mix(Vec4(0.1, 0.1, 1, 0), Vec4(0.1, 0.1, 1, 1), clamp(factor, 0, 1))
      }
      else {
        Vec4.Zero
      }
    }
    
    
    mesh.geometry.mode := PointSprites(1.7)
    mesh.geometry.vertices := Attributes[Vec3, RFloat](pointCount)
    mesh.customBoundingVolume := new Oabb(Vec3(-200), Vec3(200))
    mesh.material.faceCulling.update := FaceCulling.Back
    mesh.material.textureUnits.update += new TextureUnit(objectTexture)
    
    var vertices = mesh.geometry.vertices.write//XXX replace attribute.read/wreite with attribute.get/update
    for (i <- 0 until pointCount) {
      vertices(i) = curve(i, 0)
    }
    
    world.attach(mesh)
    
    
    // This is temp hack
    import org.lwjgl.opengl.{ GL11, GL12, GL14 }
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
  }

  
  def update(time: TimeStamp) {
    //Sort point sprites
    val camPosition = world.camera.transformation.get.translation
    
    if (!mesh.transformation.isDefined) mesh.transformation.update
    val meshPosition = mesh.transformation.get.translation
    
    val offset = meshPosition - camPosition
    
    // Simple but very slow way to sort.
    val sorted = mesh.geometry.vertices.read.sortBy{ p => val dir = p + offset; dot(dir, dir) }
    mesh.geometry.vertices.write.put(sorted.reverse)
  }
}
