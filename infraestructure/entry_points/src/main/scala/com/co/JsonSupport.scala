package com.co

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import entities.{User, Users}
import spray.json.DefaultJsonProtocol

// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userJsonFormat  = jsonFormat5(User)
  implicit val usersJsonFormat = jsonFormat1(Users)
  //implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
