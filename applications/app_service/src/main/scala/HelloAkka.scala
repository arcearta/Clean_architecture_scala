import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}
import akka.actor.ActorSystem

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.pathSingleSlash
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.stream.ActorMaterializer

//#main-class
object HelloAkka2 extends App {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem                = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer    = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  //#server-bootstrapping

  //#main-class

  lazy val routes: Route =
    pathSingleSlash {
      get {
        complete {
          Future { "Hello from AkkaHttp!" }
        }
      }
    }
  //#main-class

  //#http-server
  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) =>
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
