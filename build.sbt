resolvers += "jitpack" at "https://jitpack.io"

name := "jsonschema4s-sbt"

organization := "pl.pkazenas"

version := "0.1.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.8", "2.12.4")

sbtPlugin := true

libraryDependencies ++= Seq(
  "pl.pkazenas" %% "jsonschema4s" % "0.1.3",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.reflections" % "reflections" % "0.9.11",
)
