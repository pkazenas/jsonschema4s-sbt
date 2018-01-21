package pl.pkazenas.jsonschema4s.sbt.examples

import pl.pkazenas.jsonschema4s.sbt.core.Generator


object RunExamples extends App {
  val packageName = "pl.pkazenas.sbtJsonSchemaGenerator.examples.data"
  val packages = List(packageName)
  val params =
    Generator.Params(
      packages = packages)

  Generator.generate(params)
}
