package com.co

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

//#main-class
object QuickstartServer extends App with UserRoutes {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem                = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer    = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  //#server-bootstrapping

  //#main-class
  // from the com.co.UserRoutes trait
  lazy val routes: Route = userRoutes
  //#main-class

  //#http-server
  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8090)

  serverBinding.onComplete {
    case Success(bound) =>
      // userRep.getUsers()
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
  //#http-server
  //#main-class
}
