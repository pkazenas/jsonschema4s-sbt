package pl.pkazenas.jsonschema4s.sbt.core.schema.impl


import scala.reflect.runtime.universe._
import scala.util.Try
import pl.pkazenas.jsonschema4s.Api._
import pl.pkazenas.jsonschema4s.sbt.core.schema.JsonSchemaGenerator

object JsonSchema4sGenerator extends JsonSchemaGenerator {
  override def generate(`type`: Type, classLoader: ClassLoader): Try[String] =
    Try {
      implicit val apiConfig = ApiConfig(classLoader = classLoader)
      modelAsJsonSchema(toModel(`type`))
    }
}

