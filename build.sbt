resolvers += "jitpack" at "https://jitpack.io"

name := "jsonschema4s-sbt"

organization := "pl.pkazenas"

version := "0.1.2"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.8", "2.12.4")

crossSbtVersions := Vector("0.13.16", "1.0.3")

publishM2 := {
  publishM2.value

  val dir = file(sys.env("HOME")) / s".m2/repository/pl/pkazenas/jsonschema4s-sbt_${scalaBinaryVersion.value}_${sbtBinaryVersion.value}"
  dir.renameTo(file(sys.env("HOME")) / ".m2/repository/pl/pkazenas/jsonschema4s-sbt")
}

sbtPlugin := true
publishMavenStyle := true

libraryDependencies ++= Seq(
  "pl.pkazenas" %% "jsonschema4s" % "0.1.3",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.reflections" % "reflections" % "0.9.11",
)
