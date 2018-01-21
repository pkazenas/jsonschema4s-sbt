import java.net.URLClassLoader

import pl.pkazenas.jsonschema4s.Api._
import pl.pkazenas.jsonschema4s.JsonDataContract
import pl.pkazenas.jsonschema4s.util.ClasspathScanner

import scala.reflect.internal.util.ScalaClassLoader

name := "jsonschema4s-sbt"

version := "0.1.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.8", "2.12.4")

sbtPlugin := true

libraryDependencies ++= Seq(
  "pl.pkazenas" %% "jsonschema4s" % "0.1.1",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.reflections" % "reflections" % "0.9.11",
)

lazy val reflect = taskKey[Unit]("Prints 'Hello World'")

reflect := {
  import scala.reflect.runtime.universe._

  val classes = (fullClasspath in Runtime).value.files
  val classLoader =  ScalaClassLoader.fromURLs(classes.map(_.asURL))// new URLClassLoader( classes.map(_.asURL).toArray, ClassLoader.getSystemClassLoader)

  val ss = ClasspathScanner(classLoader)
  val subclasses = ss.findSubclasses(typeOf[JsonDataContract])
  subclasses.foreach(sc => {
    println(toModel(sc))
  })
}