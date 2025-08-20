import Dependencies.*

lazy val debugPlugin = (project in file("."))
  .settings(
    name := "debug",
    scalaVersion := scala3Version,
    libraryDependencies ++= List(scala3Compiler)
  )
