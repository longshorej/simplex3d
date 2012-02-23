package simplex3d.example.algorithm.noise

import simplex3d.math.double._
import simplex3d.math.double.functions._
import simplex3d.algorithm.noise._
import simplex3d.script.ImageUtils._


/**
 * @author Aleksey Nikiforov (lex)
 */
object Turbulence extends App {

  val zoom = 1.0/100
  val changeSpeed = 0.1

  val turbulence = new Noise1(new Turbulence(
    ClassicalGradientNoise,
    frequency = 1,
    octaves = 3, lacunarity = 2.2, persistence = 0.4,
    roundness = 0.3
  ))

  animateFunction("Turbulence") { (dims, time, pixel) =>
    val p = Vec3(pixel*zoom, time*changeSpeed)
    Vec3(turbulence(p)*0.6)
  }
}
