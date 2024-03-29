/*
 * Simplex3d Build Script
 * Copyright (C) 2011, Aleksey Nikiforov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import sbt._
import Keys._


object Simplex3dData extends Build {
  
  val buildSettings = Common.buildSettings ++ Seq(
    version := Simplex3d.DataVersion,
    startYear := Some(2010),
    licenses := Seq(("LGPLv3+", new URL("http://www.gnu.org/licenses/lgpl.html")))
  )

  lazy val root = Project(
    id = "data",
    base = file("."),
    settings = buildSettings ++ Seq(
      target := new File("target/data"),
      publish := {},
      publishLocal := {}
    )
  ) aggregate(core, double, float, format)
  
  lazy val core = Project(
    id = "data-core",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      name := "simplex3d-data-core",
      description := "Data Binding API, Core Module.",
      target := new File("target/data/core"),
      scalaSource in Compile <<= baseDirectory(_ / "src/core")
    )
  ) dependsOn(Simplex3dMath.core)
  
  lazy val double = Project(
    id = "data-double",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      name := "simplex3d-data-double",
      description := "Data Binding API, Double Module.",
      target := new File("target/data/double"),
      scalaSource in Compile <<= baseDirectory(_ / "src/double")
    )
  ) dependsOn(core, Simplex3dMath.double)
  
  lazy val float = Project(
    id = "data-float",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      name := "simplex3d-data-float",
      description := "Data Binding API, Float Module.",
      target := new File("target/data/float"),
      scalaSource in Compile <<= baseDirectory(_ / "src/float")
    )
  ) dependsOn(core, Simplex3dMath.float)
  
  lazy val format = Project(
    id = "data-format",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      name := "simplex3d-data-format",
      description := "Additional data formats for Data Binding API.",
      target := new File("target/data/format"),
      scalaSource in Compile <<= baseDirectory(_ / "src/format")
    )
  ) dependsOn(core, double, Simplex3dMath.double)
  
  
  lazy val doc = Project(
    id = "data-doc",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      target := new File("target/data/doc"),
      scalaSource in Compile <<= baseDirectory(_ / "src"),
      publish := {},
      publishLocal := {}
    )
  ) dependsOn(Simplex3dMath.core, Simplex3dMath.double, Simplex3dMath.float)
  
  lazy val test = Project(
    id = "data-test",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Seq(
      name := "simplex3d-data-test",
      description := "Data Binding API, Tests.",
      licenses := Seq(("GPLv3+", new URL("http://www.gnu.org/licenses/gpl.html"))),
      target := new File("target/data/test"),
      libraryDependencies += "org.scalatest" %% "scalatest" % Simplex3d.ScalatestVersion % "test",
      scalaSource in Compile <<= baseDirectory(_ / "test/bench"),
      scalaSource in Test <<= baseDirectory(_ / "test/unit"),
      publish := {},
      publishLocal := {}
    )
  ) dependsOn(core, double, float, format)
  
  lazy val example = Project(
    id = "data-example",
    base = file("Simplex3dData"),
    settings = buildSettings ++ Common.exampleSettings ++ Seq(
      target := new File("target/data/example")
    )
  ) dependsOn(core, double, format)
}
