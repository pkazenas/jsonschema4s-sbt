package  pl.pkazenas.jsonschema4s.sbt.core.reflection

import pl.pkazenas.jsonschema4s.sbt.core.reflection.impl.TraitBasedClassScanner


trait ClassScanner[LowerBoundType] {
  def scan(packageName: String, classLoader: ClassLoader): List[Class[_ <: LowerBoundType]]
}

object ClassScanner {

  sealed trait Type
  case object TraitBased extends Type

  def scanner[ClassScanner[_]](`type`: Type) =
    `type` match {
      case TraitBased => TraitBasedClassScanner
    }
}