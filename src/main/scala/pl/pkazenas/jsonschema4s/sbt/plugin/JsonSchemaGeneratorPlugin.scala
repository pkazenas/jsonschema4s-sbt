package pl.pkazenas.jsonschema4s.sbt.plugin

import pl.pkazenas.jsonschema4s.sbt.core.Generator
import pl.pkazenas.jsonschema4s.sbt.core.Generator.Params
import sbt._

object JsonSchemaGeneratorPlugin extends sbt.AutoPlugin {

  object autoImport extends AnyRef {
    val generateJsonSchema = taskKey[Unit]("generate json schema")
    val classLoader = settingKey[ClassLoader]("classloader to use")
    val packages = settingKey[List[String]]("packages to scan")
    val outputPath = settingKey[Option[String]]("output")

    lazy val defaultSettings: Seq[Def.Setting[_]] = Seq(
      generateJsonSchema := {
        println("Json schema generation started")

        Generator
          .generate(
            Params(
              (packages in generateJsonSchema).value,
              (file) => println(file),
              (classLoader in generateJsonSchema).value))

        println("Json schema generation finished")
      },
      classLoader in generateJsonSchema := ClassLoader.getSystemClassLoader,
      packages in generateJsonSchema := List("pl.test"),
      outputPath in generateJsonSchema := None)
  }

  import autoImport._

  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  override def projectSettings = inConfig(Compile)(defaultSettings)
}
