package pl.pkazenas.jsonschema4s.sbt.core.reflection.impl

import pl.pkazenas.jsonschema4s.JsonDataContract
import pl.pkazenas.jsonschema4s.sbt.core.reflection.ClassScanner
import pl.pkazenas.jsonschema4s.util.ClasspathScanner

import scala.util.{Failure, Success, Try}
import scala.reflect.runtime.universe._

object TraitBasedClassScanner extends ClassScanner[JsonDataContract] {
  override def scan(packages: List[String], classLoader: ClassLoader): List[Type] =
    Try {
      ClasspathScanner(classLoader, packages).findSubclasses(typeOf[JsonDataContract])
    } match {
      case Success(result) => result
      case Failure(exception) => println(exception); List()
    }
}
