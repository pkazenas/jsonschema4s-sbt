package pl.pkazenas.jsonschema4s.sbt.core.reflection.impl

import pl.pkazenas.jsonschema4s.annotation.JsonDataContract
import pl.pkazenas.jsonschema4s.sbt.core.reflection.ClassScanner
import pl.pkazenas.jsonschema4s.util.ClasspathScanner

import scala.util.{Failure, Success, Try}
import scala.reflect.runtime.universe._

object AnnotationBasedClassScanner extends ClassScanner[JsonDataContract] {
  override def scan(packages: List[String], classLoader: ClassLoader): List[Type] =
    Try {
      ClasspathScanner(classLoader, packages).findAnnotatedTypes(classOf[JsonDataContract])
    } match {
      case Success(result) => result
      case Failure(exception) => println(exception); List()
    }
}
