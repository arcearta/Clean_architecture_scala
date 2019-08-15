package com.co

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, concat, entity, onSuccess, pathEnd, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{get, post}
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import com.google.inject._
import entities.User
import scala.concurrent.Future

//#user-routes-class
trait UserRoutes extends JsonSupport {

  import adapters.presenters.{UserActorRepositoryImp, UserServiceApi}
  import usecases.{BankRepository, UserRepository}
  import wiring.MyModule

  import scala.concurrent.duration.Duration
  import scala.concurrent.{Await, ExecutionContext}
  //#user-routes-class

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  // we leave these abstract, since they will be provided by the App
  implicit  val system: ActorSystem
  lazy val log = Logging(system, classOf[UserRoutes])

  //implicit val system: ActorSystem  = ActorSystem("helloAkkaHttpServer")


  //Se agregan las inyecciones
  val injector = Guice.createInjector(new MyModule())

  /*val userRep = injector.getInstance(classOf[UserRepository])*/
  val service = injector.getInstance(classOf[MyComponent])

  println("Testing service from injector : Dato_ " + service.callTheService())


  ////Fin inyecciones

  //test bd hd
  /*private val repositoryImpl: BankRepository = injector.getInstance(classOf[BankRepository]) //de esta forma hace uso de la inyeccion de dependencia
  repositoryImpl.ddl.onComplete {
    _ =>
      import repository.Bank

      import scala.concurrent.Await
      import scala.concurrent.duration.Duration
      repositoryImpl.createBank(Bank("SBI"))
      repositoryImpl.createBank(Bank("PNB"))
      repositoryImpl.createBank(Bank("RBS"))


      Await.result(repositoryImpl.getAllBanks().map(println), Duration.Inf)
  }*/
  val userRep:UserRepository = new UserActorRepositoryImp()
  userRep.saveUser(User(id = 1, name = "Jose", email = "some eamil",  mobile = "12364", password = "some pasword"))

  Await.result(userRep.getUsers().map(println), Duration.Inf)


  //test bd hd
 private val repositoryImplH2: UserRepository = injector.getInstance(classOf[UserRepository]) //de esta forma hace uso de la inyeccion de dependencia
  repositoryImplH2.saveUser(User(id = 1, name = "Jose", email = "some eamil",  mobile = "12364", password = "some pasword") ).onComplete {
    _ =>
      repositoryImplH2.saveUser(User(id = 1, name = "Jose", email = "some eamil",  mobile = "12364", password = "some pasword") )
      repositoryImplH2.saveUser(User(id = 2, name = "Pepe", email = "some eamil",  mobile = "1236454", password = "some pasword") )
      repositoryImplH2.saveUser(User(id = 3, name = "Miguel", email = "some eamil",  mobile = "12456364", password = "some pasword") )

  }

  val dato:UserServiceApi = injector.getInstance(classOf[UserServiceApi])

  Await.result(repositoryImplH2.getUsers().map(println), Duration.Inf)

  val bankRep = injector.getInstance(classOf[BankRepository])
  /*Await.result(bankRep.ddl.map(println), Duration.Inf)

  dato.insertUser(User(id = 8, name = "Miguel", email = "some eamil",  mobile = "12364", password = "some pasword"))
  dato.getUser.onComplete{
    x => x.foreach(println)
  }


  bankRep.getAllBanks().onComplete(x => x.foreach(println))*/
/*
 val datos = for {
   a <- repositoryImplH2.saveUser(User(id = 1, name = "Jose", email = "some eamil",  mobile = "12364", password = "some pasword") )
   b <- repositoryImplH2.saveUser(User(id = 2, name = "Pepe", email = "some eamil",  mobile = "1236454", password = "some pasword") )
   c <-  repositoryImplH2.saveUser(User(id = 3, name = "Miguel", email = "some eamil",  mobile = "12456364", password = "some pasword") )

   d <-  repositoryImplH2.getUser("Jose").map(println)
   e <-  repositoryImplH2.getUsers().map(println)
 } yield {

   import scala.concurrent.Await
   import scala.concurrent.duration.Duration
   println("se ha completado1")
   Await.result(repositoryImplH2.getUser("Jose").map(println), Duration.Inf)
   Await.result(repositoryImplH2.getUsers().map(println), Duration.Inf)
 }
  datos.onComplete{ _ =>
    import scala.concurrent.Await
    import scala.concurrent.duration.Duration
    Await.result(repositoryImplH2.saveUser(User(id = 1, name = "Jose", email = "some eamil",  mobile = "12364", password = "some pasword") ), Duration.Inf)
      Await.result( repositoryImplH2.saveUser(User(id = 2, name = "Pepe", email = "some eamil",  mobile = "1236454", password = "some pasword") ), Duration.Inf)
      Await.result(repositoryImplH2.saveUser(User(id = 3, name = "Miguel", email = "some eamil",  mobile = "12456364", password = "some pasword") ), Duration.Inf)

      Await.result(repositoryImplH2.getUser("Jose").map(println), Duration.Inf)
      Await.result(repositoryImplH2.getUsers().map(println), Duration.Inf)
    println("se ha completado")
  }*/

 // Await.result(repositoryImplH2.getUser("Jose").map(println), Duration.Inf)
 // Await.result(repositoryImplH2.getUsers().map(println), Duration.Inf)

  //#all-routes
  //#users-get-post
  //#users-get-delete
  lazy val userRoutes: Route =
    pathPrefix("users") {
      concat(
        //#users-get-delete
        pathEnd {
          concat(
            get {
              //val userRep = UserRepositoryImp
            // val userModule = new Module {
             // bind [UserRepository] to new UserRepositoryImp
           //  }
              complete(repositoryImplH2.getUsers())
            //  complete("userRep.getUsers()")
            },
            post {
              entity(as[User]) { user =>
                println
               // val userRep = UserRepositoryImp
                val userCreated: Future[Int] = userRep.saveUser(user)
                //complete("Usuario creado")
                onSuccess(userCreated) { performed =>
                  //log.info("Deleted user [{}]: {}", name, performed.description)
                  complete((StatusCodes.OK, "Usuario Creado"))
                }

               /* val rep = userCreated.onSuccess {
                  case _ =>
                    Thread.sleep(10000)
                    println("The program waited patiently for this callback to finish.")
                    complete("userRep.getUsers()")
                    //complete((StatusCodes.Created, "Usuario creado"))
                }
                rep*/
              }
            }
          )

        } /*,
        //#users-get-post
        //#users-get-delete
        path(Segment) { name =>
          concat(
            get {
              //#retrieve-user-info
              val maybeUser: Future[Option[User]] =
                (userRegistryActor ? GetUser(name)).mapTo[Option[User]]
              rejectEmptyResponse {
                complete(maybeUser)
              }
              //#retrieve-user-info
            },
            delete {
              //#users-delete-logic
              val userDeleted: Future[ActionPerformed] =
                (userRegistryActor ? DeleteUser(name)).mapTo[ActionPerformed]
              onSuccess(userDeleted) { performed =>
                log.info("Deleted user [{}]: {}", name, performed.description)
                complete((StatusCodes.OK, performed))
              }
              //#users-delete-logic
            }
          )
        }*/
      )
      //#users-get-delete
    }
  //#all-routes
}
