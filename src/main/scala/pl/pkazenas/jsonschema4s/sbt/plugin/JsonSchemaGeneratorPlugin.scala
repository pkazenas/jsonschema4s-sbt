package pl.pkazenas.jsonschema4s.sbt.plugin

import java.net.URLClassLoader

import pl.pkazenas.jsonschema4s.sbt.core.Generator
import pl.pkazenas.jsonschema4s.sbt.core.Generator.Params
import sbt.Keys.fullClasspath
import sbt._

import scala.reflect.internal.util.ScalaClassLoader

object JsonSchemaGeneratorPlugin extends sbt.AutoPlugin {
  object autoImport extends AnyRef {
    val generateJsonSchema = inputKey[Unit]("generate json schema")
    val packages = settingKey[List[String]]("packages to scan")
    val outputPath = settingKey[Option[String]]("output")

    lazy val defaultSettings: Seq[Def.Setting[_]] = Seq(
      generateJsonSchema := {
        println("Json schema generation started")
        val classes = (fullClasspath in Runtime).value.files
        val classLoader =  ScalaClassLoader.fromURLs(classes.map(_.asURL))

        Generator
          .generate(
            Params(
              (packages in generateJsonSchema).value,
              (file) => println(file),
              classLoader
            )
          )

        println("Json schema generation finished")
      },
      packages in generateJsonSchema := List("pl.test"),
      outputPath in generateJsonSchema := None)
  }

  import autoImport._

  override def projectSettings = inConfig(Compile)(defaultSettings)
}
