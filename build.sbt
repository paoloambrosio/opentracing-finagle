import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.opentracing.contrib",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "opentracing-finagle",
    libraryDependencies ++= openTracingLibs ++ Seq(
      finagleCore,
      scalaTest
    )
  )
