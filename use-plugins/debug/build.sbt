import Dependencies.*

lazy val scalaTestVersion = "3.2.19"

lazy val useDebug = (project in file("."))
  .aggregate(debugPluginLocation)
  .settings(
    name := "Using the debug plugin.",
    crossScalaVersions := List(scala3Version),
    publish / skip := true,
  )

lazy val debugPluginLocation = (project in file("scala3"))
  .settings(
    name := "Using the debug plugin for Scala 3.",
    scalaVersion := scala3Version,
    autoCompilerPlugins := true,
    addCompilerPlugin("debug-plugin-org" % "debug_3" % "0.0.1-SNAPSHOT"),
    scalacOptions := Seq("-Vprint:pluginDebugPhase"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  )
