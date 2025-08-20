import sbt.*

object Dependencies {
  lazy val scala3Version = "3.3.4"
  lazy val scala3NightlyVersion = "3.2.2-RC1-bin-20221103-bf808b3-NIGHTLY"
  lazy val scala3Compiler = "org.scala-lang" %% "scala3-compiler" % scala3Version
}
