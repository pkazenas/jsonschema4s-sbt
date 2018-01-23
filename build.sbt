name := "jsonschema4s-sbt"

organization := "pl.pkazenas"

version := "0.1.3"

scalaVersion := "2.12.4"
sbtVersion := "1.1.0"

sbtPlugin := true

libraryDependencies ++= Seq(
  "pl.pkazenas" %% "jsonschema4s" % "0.1.5",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.reflections" % "reflections" % "0.9.11",
)

// publish configuration

// POM settings for Sonatype
homepage := Some(url("https://github.com/pkazenas/jsonschema4s-sbt"))

scmInfo := Some(ScmInfo(url("https://github.com/pkazenas/jsonschema4s-sbt"), "git@github.com:pkazenas/jsonschema4s-sbt.git"))

developers :=
  List(
    Developer(
      "pkazenas",
      "Piotr Kazenas",
      "admin@pkazenas.pl",
      url("https://github.com/pkazenas")))

licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

publishMavenStyle := true

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)