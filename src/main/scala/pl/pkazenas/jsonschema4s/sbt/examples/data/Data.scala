package pl.pkazenas.jsonschema4s.sbt.examples.data

import pl.pkazenas.jsonschema4s.annotation.{Description, JsonDataContract}


case class Pet(name: String, petType: String)

@JsonDataContract
case class Person(name: String,
                  age: Int,
                  pet: Option[Pet])

@JsonDataContract
case class Thing(name: String,
                 description: String)

abstract class DataType

@Description("data 1")
case class Data1(@Description("name") name: String) extends DataType

@Description("data 2")
case class Data2(@Description("value") value: Int) extends DataType

@JsonDataContract
@Description("request")
case class Request(@Description("id")
                   id: String,
                   @Description("data type")
                   data: DataType)

@JsonDataContract
@Description("test")
case class Test(@Description("a string")
                a: String,
                @Description("b int")
                b: Int)

