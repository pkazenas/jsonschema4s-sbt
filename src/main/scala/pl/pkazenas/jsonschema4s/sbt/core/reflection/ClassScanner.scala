package  pl.pkazenas.jsonschema4s.sbt.core.reflection

import pl.pkazenas.jsonschema4s.sbt.core.reflection.impl.TraitBasedClassScanner
import scala.reflect.runtime.universe._

trait ClassScanner[LowerBoundType] {
  def scan(packages: List[String], classLoader: ClassLoader): List[Type]
}

object ClassScanner {

  sealed trait Type
  case object TraitBased extends Type

  def apply[ClassScanner[_]](`type`: Type) =
    `type` match {
      case TraitBased => TraitBasedClassScanner
    }
}