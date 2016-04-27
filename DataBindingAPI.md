# Summary #

Simplex3d Data API is designed to simplify access to raw numerical data that defines geometry and textures.


# Features #

  * **Seamless integration with Simplex3d Math DSL.**
  * **Simple and convinient API.**
  * **On-the-fly data conversion.**
  * **Support for arrays, direct buffers, and interleaved buffers.**
  * **Specialization for primitive types.**
  * **Integrated with scala.collection.**
  * **Compatible with any native API.**


# Example #

```
import simplex3d.math._
import simplex3d.math.double._
import simplex3d.math.double.functions._

import simplex3d.data._
import simplex3d.data.double._

/* Alternative imports to use float instead of double.
import simplex3d.math._
import simplex3d.math.float._
import simplex3d.math.float.functions._

import simplex3d.data._
import simplex3d.data.float._ */


object DataExample {

  // How to create DataArrays and interleaved DataViews.
  def main(args: Array[String]) {
    val size = 10

    // Read as Vec3, the backing store is an array of floats.
    val vertex = DataArray[Vec3, RFloat](size)

    // Read as Vec3, the backing store is an array of signed shorts.
    val normal = DataArray[Vec3, SShort](size)

    // Read as Vec4, the backing store is an array of unsigned bytes.
    val color = DataArray[Vec4, UByte](size)


    // Fill arrays with some random data.
    val random = new java.util.Random(1)
    def r = random.nextDouble
    for (i <- 0 until size) {
      vertex(i) = Vec3(r, r, r)*10
      normal(i) = normalize(Vec3(r, r, r))
      color(i) = Vec4(r, r, r, 1)
    }

    // interleave the arrays into one byte buffer
    val (
      interleavedVertex,
      interleavedNormal,
      interleavedColor
    ) = interleave(
      vertex,
      normal,
      color
    )(vertex.size)

    // You can read from and write into interleaved buffers as usual!
    interleavedVertex(0) = Vec3.Zero
    println(interleavedVertex(0))

    // Raw buffer contains the data from all the interleaved buffers.
    // You can pass it to OpenGL or another native API.
    val bytes = interleavedVertex.rawBuffer
  }
}
```


# Snippets #

**Integration with Math DSL:**
```
  def generateNormalLines(vertices: inData[Vec3], normals: inData[Vec3], lines: outData[Vec3])
    var i = 0; while (i < vertices.size) {
      val vertex = vertices(i) // reading Vec3

      lines(i*2) = vertex // writing Vec3
      lines(i*2 + 1) = vertex + normals(i) // combining math operations and writing

      i += 1
    }
  }
```

**Simple and convinient API:**
```
    // Making an empty sequence backed by a direct buffer:
    val normals = DataBuffer[Vec3, RFloat](size)

    // Wrapping an existing array:
    val vertices = DataArray[Vec3, RFloat](array)

    // Making a new data array from a list of values:
    val points = DataArray[Vec3, RFloat](
      Vec3(0, 0, 0),
      Vec3(0, 1, 0),
      Vec3(1, 1, 0),
      Vec3(1, 0, 0)
    )
```

**On the fly conversion:**
```
    // Making a sequence backed by an array of normalized unsigned bytes.
    // The data is converted to range [0, 1] for unsigned and [-1, 1] for signed integral numbers.
    val color = DataArray[Vec4, UByte](10)

    var i = 0; while (i < color.size) {
      val gradient = (i + 1).toDouble/color.size

      // A double vector representing an RGBA color is stored
      // as 4 bytes that contain RGBA components, one per byte.
      color(i) = Vec4(gradient, gradient, 0, 1)

      i += 1
    }
```

**Aarrays, direct buffers, and interleaved buffers:**
```
    // A sequence backed by an array:
    val normals = DataArray[Vec3, RFloat](100)

    // A sequence backed by a direct buffer:
    val vertices = DataBuffer[Vec3, RFloat](100)

    // iVertex and iNormal are interleaved and share the same buffer:
    val (iVertices, iNormals) = interleave(vertices, normals)(vertices.size)

    // Making empty interleaved sequences:
    val (emptyVertices, emptyNormals) = interleave(
      DataSeq[Vec3, RFloat], DataSeq[Vec3, RFloat]
    )(100)
```

**Specialization for primitive types:**
```
    // A sequence of ints, backed by an array of short values.
    val data = DataArray[SInt, SShort](1000)

    var i = 0; while (i < data.size) {
      // No boxing/unboxing takes place:
      data(i) = i
    }
```

**Integration with scala.collection:**
```
    // A sample transformation.
    val t = Mat4x3 scale(2) rotateX(radians(30)) translate(Vec3(4, 0, 1))

    // Checks if vertices have nan or inf values using forall()
    if (vertices.forall(!hasErrors(_))) {
      println("No abnormal floating point values found.")
    }

    // transformed is IndexedSeq[ConstVec3]
    val transformed =
      // Iterating using scala for-comprehension:
      for (vertex <- vertices) yield {
        t.transformPoint(vertex)
      }

    // copying IndexedSeq[Vec3] into DataSeq[Vec3, _]
    vertices.put(transformed)
```

**Integration with OpenGL:**
```
    // Sending buffer data with JOGL:
    gl.glBufferData(
      GL_ARRAY_BUFFER, vertices.byteCapacity,
      vertices.bindingBuffer, GL_DYNAMIC_DRAW
    )

    // Defining a vertex pointer with JOGL:
    gl.glverticesPointer(
      vertices.components, vertices.rawType,
      vertices.byteStride, vertices.byteOffset
    )
```


# Tutorials #

Tutorials can be found [here](http://www.simplex3d.org/project/tutorials/).