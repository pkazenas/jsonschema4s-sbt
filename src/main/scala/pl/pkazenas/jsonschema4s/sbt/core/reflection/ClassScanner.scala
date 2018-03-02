package  pl.pkazenas.jsonschema4s.sbt.core.reflection

import pl.pkazenas.jsonschema4s.sbt.core.reflection.impl.{AnnotationBasedClassScanner, TraitBasedClassScanner}

import scala.reflect.runtime.universe._

trait ClassScanner[LowerBoundType] {
  def scan(packages: List[String], classLoader: ClassLoader): List[Type]
}

object ClassScanner {

  sealed trait ScannerType
  case object TraitBased extends ScannerType
  case object AnnotationBased extends ScannerType

  def apply[ClassScanner[_]](scannerType: ScannerType) =
    scannerType match {
      case TraitBased => TraitBasedClassScanner
      case AnnotationBased => AnnotationBasedClassScanner
    }
}