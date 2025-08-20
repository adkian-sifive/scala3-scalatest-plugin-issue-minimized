import Dependencies.*

ThisBuild / version := "0.0.1-SNAPSHOT"
ThisBuild / sbtPlugin := false
ThisBuild / organization := "debug-plugin-org"

lazy val root = (project in file("."))
  .aggregate(
    debugPlugin,
  )
  .settings(
    crossScalaVersions := List(scala3Version),
    publish / skip := true
  )

lazy val debugPlugin = project in file("plugins/debug")

lazy val usePlugin = project in file("use-plugins/debug")

resolvers += Resolver.mavenLocal

lazy val publishPluginsLocal = taskKey[Unit]("myTask")
publishPluginsLocal := {
  Command.process("clean", state.value)
  Command.process("compile", state.value)
  Command.process("package", state.value)
  Command.process("publishLocal", state.value)
}
