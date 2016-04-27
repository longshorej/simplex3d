# Summary #

Simplex3d Math DSL resembles GLSL and has the same set of functions. If you are familiar with GLSL, then you are ready to use Simplex3d Math. Conversely, when you are learning the Math DSL, you are also learning GLSL.


# Features #

  * **Consistent syntax.**
  * **Vector and matrix operators.**
  * **Swizzling.**
  * **A large set of standard functions.**
  * **Quaternions.**
  * **Easy to use 2d and 3d transformation matrices.**


# Example #
```
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._

/* Alternative imports to use float instead of double.
import simplex3d.math._
import simplex3d.math.float._
import simplex3d.math.float.functions._ */


object MathExample {

  // How to make a model-view matrix and transform a point.
  def main(args: Array[String]) {
    val p = Vec3(1, 5, 1)
    val model = Mat4x3 scale(Vec3(1, 1, 3)) rotateZ(radians(45)) translate(Vec3(10, 5, 10))
    val view = inverse(Mat4x3 rotateX(radians(30)) translate(Vec3(0, 0, -50)))
    val modelView = model concat view
    val transformed = modelView.transformPoint(p)
    println(transformed)
  }
}
```


# Snippets #

**Concise syntax:**
```
    val u = Vec3(2)
    val a = Vec2(1, 2)

    println(u.xy + a*2) // short and powerful expressions.
```

**Standard vector and matrix operations:**
```
    val u = Vec3(2)
    val v = Vec3(1, 2, 3)

    val vectorSum = v + u
    val componentMul = v*u

    val m = Mat3(Vec3(1), Vec3(2), Vec3(3))
    val n = Mat3(1)

    val matrixSum = m + n
    val matrixMul = m*n

    val matrixVector = m*u
    val vectorMatrix = u*m
```

**Swizzling:**
```
    println(u.yz)
    println(u.zx)
    println(u.xx + u.yx)

    // rgba and stpq are also supported
    println(u.rgb)
    println(u.stp)

    u.xy += 1
    u.rb *= 0.5
```

**Standard functions:**
```
    println(min(u, v))
    println(max(u, v))
    println(clamp(u, 1, 2.5))
    println(mix(u, v, 0.5))
    println(smoothstep(0, 4, u))
```

**Quaternions:**
```
    // Rotations are applied in the order they appear: from left to right.
    val q = Quat4 rotateX(radians(30)) rotateY(radians(45))
    val rotated = q.rotateVector(u)
```

**Easy to use 2d and 3d transformation matrices:**
```
    // Transformation operations are applied in the order they appear: from left to right.

    // 3d transformations.
    var t = Mat4x3 rotateX(radians(20)) scale(3) translate(Vec3(0, 10, 0))
    t = t rotateX(angle1) rotate(q1) concat(m1)

    // 2d transformations.
    val r = Mat3x2 translate(Vec2(2, 5)) scale(Vec2(1, 2)) rotate(radians(90))
```


# Tutorials #

Tutorials can be found [here](http://www.simplex3d.org/project/tutorials/).