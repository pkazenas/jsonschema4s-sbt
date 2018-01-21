package pl.pkazenas.jsonschema4s.sbt.core

import pl.pkazenas.jsonschema4s.sbt.core.reflection.ClassScanner
import pl.pkazenas.jsonschema4s.sbt.core.schema.JsonSchemaGenerator

import scala.reflect.runtime.universe._

object Generator {

  case class Params(packages: List[String],
                    output: String => Unit = (str) => println(str),
                    classLoader: ClassLoader = ClassLoader.getSystemClassLoader,
                    classScanner: ClassScanner.Type = ClassScanner.TraitBased,
                    schemaGenerator: JsonSchemaGenerator.Type = JsonSchemaGenerator.JsonSchema4s)

  def generate(params: Params) = {
    println(
      s"""
         | classLoader: ${params.classLoader},
         | classLoaderParent: ${params.classLoader.getParent},
         | output: ${params.output},
         | packages: ${params.packages}
       """.stripMargin)
    val classScanner = ClassScanner.scanner(params.classScanner)
    val schemaGenerator = JsonSchemaGenerator.generator(params.schemaGenerator)
    val mirror = runtimeMirror(params.classLoader)

    params.packages.foreach(packageName => {
      classScanner
        .scan(packageName, params.classLoader)
        .foreach(clazz => {
          val `type` = mirror.staticClass(clazz.getName).toType
          schemaGenerator
            .generate(`type`, params.classLoader)
            //.map(params.output(_))
            .fold(ex => ex.printStackTrace(), params.output(_))
        })
    })
  }
}

