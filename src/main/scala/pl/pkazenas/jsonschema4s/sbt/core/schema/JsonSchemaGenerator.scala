package pl.pkazenas.jsonschema4s.sbt.core.schema

import pl.pkazenas.jsonschema4s.sbt.core.schema.impl.JsonSchema4sGenerator

import scala.util.Try
import scala.reflect.runtime.universe._

trait JsonSchemaGenerator {
  def generate(`type`: Type, classLoader: ClassLoader): Try[String]
}

object JsonSchemaGenerator {

  sealed trait Type
  case object JsonSchema4s extends Type

  def generator(`type`: Type) =
    `type` match {
      case JsonSchema4s => JsonSchema4sGenerator
    }
}