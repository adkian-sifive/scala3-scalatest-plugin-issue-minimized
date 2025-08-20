import Dependencies.*

lazy val debugPlugin = (project in file("scala3"))
  .settings(
    crossScalaVersions := List(scala3Version),
    publish / skip := true
  )
