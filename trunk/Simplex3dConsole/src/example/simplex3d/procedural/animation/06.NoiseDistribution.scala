package example.simplex3d.procedural.animation

import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.doublex.functions._
import simplex3d.console.extension.ImageUtils._


/**
 * @author Aleksey Nikiforov (lex)
 */
object NoiseDistribution extends App {

  val zoom = 1.0/50
  val changeSpeed1 = 1.0/3
  val changeSpeed2 = 1.0/5
  val scrollSpeed = 10

  animateFunction("Noise Distribution") { (dims, time, pixel) =>
    val p = pixel + time*scrollSpeed

    val timeSlot = (time.toInt/10)%4
    val noise = {
      if (timeSlot == 0) noise1(p.x*zoom)
      else if (timeSlot == 1) noise1(p*zoom)
      else if (timeSlot ==2) noise1(Vec3(p*zoom, time*changeSpeed1))
      else noise1(Vec4(p*zoom, time*changeSpeed1, time*changeSpeed2))
    }

    val scaledNoise = (noise + 1)/2
    if (scaledNoise > 0.75) color(Vec3(1, 0, 0), (scaledNoise - 0.75)/0.25)
    else if (scaledNoise > 0.5) color(Vec3(1, 0, 1), (scaledNoise - 0.5)/0.25)
    else if (scaledNoise > 0.25) color(Vec3(0, 0, 1), (scaledNoise - 0.25)/0.25)
    else color(Vec3(0, 1, 1), (scaledNoise)/0.25)
  }

  def color(shadeColor: inVec3, intencity: Double) :Vec3 = {
    mix(shadeColor/2, shadeColor, intencity)
  }
}
