package pl.pkazenas.jsonschema4s.sbt.plugin

import java.io.PrintWriter
import java.net.URLClassLoader
import java.nio.file.{Files, Paths}

import pl.pkazenas.jsonschema4s.sbt.core.Generator
import pl.pkazenas.jsonschema4s.sbt.core.Generator.Params
import sbt.Keys.{fullClasspath, target}
import sbt._

import scala.reflect.internal.util.ScalaClassLoader
import scala.util.{Failure, Success, Try}

object JsonSchemaGeneratorPlugin extends sbt.AutoPlugin {

  object autoImport extends AnyRef {
    val generateJsonSchema = taskKey[Unit]("generate json schema")
    val packages = settingKey[List[String]]("packages to scan")
    val outputPath = settingKey[Option[String]]("output directory")

    lazy val generateJsonSchemaSettings: Seq[Def.Setting[_]] = Seq(
      generateJsonSchema := {
        println("Json schema generation started")
        val classes = (fullClasspath in Runtime).value.files
        val classLoader = ScalaClassLoader.fromURLs(classes.map(_.asURL))

        Generator
          .generate(
            Params(
              (packages in generateJsonSchema).value,
              (typeName, contents) => {

                val out = (outputPath in generateJsonSchema).value
                println(s"outputPath set to ${out getOrElse ""}")

                out.fold {
                  println(s"type: $typeName \n contents: \n$contents")
                }(p => {
                  val path = Paths.get(p)
                  if(!Files.exists(path)) {
                    println(s"creating output directory: ${p}")
                    Files.createDirectory(path)
                  }

                  val filePath = Paths.get(p, s"$typeName.json")
                  println(s"Writing json schema to file: $filePath")

                  Try {
                    val writer = new PrintWriter(new File(filePath.toUri))
                    writer.write(contents)
                    writer.close()
                  } match {
                    case Success(_) => println(s"Successfully written file: $filePath")
                    case Failure(ex) => ex.printStackTrace()
                  }
                })
              },
              classLoader
            )
          )

        println("Json schema generation finished")
      },
      packages in generateJsonSchema := List(),
      outputPath in generateJsonSchema := Some((target.value / "json-schema").getAbsolutePath)
    )
  }

  import autoImport._

  override def requires = sbt.plugins.JvmPlugin

  override def trigger = allRequirements

  override val projectSettings = generateJsonSchemaSettings
}
