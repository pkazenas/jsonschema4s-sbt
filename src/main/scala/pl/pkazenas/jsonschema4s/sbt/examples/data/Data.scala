package pl.pkazenas.jsonschema4s.sbt.examples.data

import pl.pkazenas.jsonschema4s.sbt.core.JsonDataContract


case class Pet(name: String, petType: String)

case class Person(name: String,
                  age: Int,
                  pet: Option[Pet]) extends JsonDataContract

case class Thing(name: String,
                 description: String) extends JsonDataContract

abstract class DataType
case class Data1(name: String) extends DataType
case class Data2(value: Int) extends DataType

case class Request(id: String, data: DataType) extends JsonDataContract

case class Test(a: String, b: Int) extends JsonDataContract

