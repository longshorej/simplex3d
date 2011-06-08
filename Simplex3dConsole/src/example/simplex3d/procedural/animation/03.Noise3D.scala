package example.simplex3d.procedural.animation

import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.noise._
import simplex3d.console.extension.ImageUtils._


/**
 * @author Aleksey Nikiforov (lex)
 */
object Noise3D extends App {

  val zoom = 1.0/50
  val changeSpeed = 1.0/3
  val scrollSpeed = 10

  val noise = new Noise(ClassicalGradientNoise)

  animateFunction("Noise3D") { (dims, time, pixel) =>
    val p = pixel + time*scrollSpeed
    Vec3((noise(Vec3(p*zoom, time*changeSpeed)) + 1)/2)
  }
}
