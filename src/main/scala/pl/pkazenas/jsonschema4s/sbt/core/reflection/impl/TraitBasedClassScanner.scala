package pl.pkazenas.jsonschema4s.sbt.core.reflection.impl

import org.reflections.Reflections
import pl.pkazenas.jsonschema4s.JsonDataContract
import pl.pkazenas.jsonschema4s.sbt.core.reflection.ClassScanner

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

object TraitBasedClassScanner extends ClassScanner[JsonDataContract] {
  override def scan(packageName: String, classLoader: ClassLoader): List[Class[_ <: JsonDataContract]] =
    Try {
      (new Reflections(packageName, classLoader))
        .getSubTypesOf(classOf[JsonDataContract])
        .asScala
        .toList
    } match {
      case Success(result) => result
      case Failure(exception) => println(exception); List()
    }
}
