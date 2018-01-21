package pl.pkazenas.jsonschema4s.sbt.core

import pl.pkazenas.jsonschema4s.sbt.core.reflection.ClassScanner
import pl.pkazenas.jsonschema4s.sbt.core.schema.JsonSchemaGenerator

import scala.reflect.runtime.universe._

object Generator {

  case class Params(packages: List[String],
                    output: (String, String) => Unit = (name, contents) => println(s"$name\n$contents"),
                    classLoader: ClassLoader = ClassLoader.getSystemClassLoader,
                    classScanner: ClassScanner.Type = ClassScanner.TraitBased,
                    schemaGenerator: JsonSchemaGenerator.Type = JsonSchemaGenerator.JsonSchema4s)

  def generate(params: Params) = {
    println(
      s"""
         | classLoader: ${params.classLoader},
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
          val typeName = `type`.dealias.typeSymbol.name
          schemaGenerator
            .generate(`type`, params.classLoader)
            .fold(ex => ex.printStackTrace(), params.output(typeName.toString, _))
        })
    })
  }
}

