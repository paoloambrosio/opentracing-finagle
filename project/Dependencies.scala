import sbt._

object Dependencies {
  lazy val openTracingLibs = Seq(
    "io.opentracing" % "opentracing-api" % "0.31.0",
    "io.opentracing" % "opentracing-util" % "0.31.0",
    "io.opentracing" % "opentracing-mock" % "0.31.0" % Test
  )
  lazy val finagleCore = "com.twitter" %% "finagle-core" % "18.2.0" % Provided
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
}
