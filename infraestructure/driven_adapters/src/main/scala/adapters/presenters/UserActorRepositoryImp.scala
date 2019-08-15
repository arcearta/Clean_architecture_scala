package adapters.presenters

import usecases.UserRepository
import adapters.presenters.UserRegistryActor._
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout
import com.google.inject.Inject
import entities.{User, Users}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}


/**
  * Data access facade for [[entities.User]] domain object.
  */

/**
  * Memory implementation of [[usecases.UserRepository]].
  */
class UserActorRepositoryImp @Inject() extends UserRepository{
  //val system = systemActor//ActorSystem("iot-system")
  // we leave these abstract, since they will be provided by the App
  val system = ActorSystem("UserRepositoryActorSystem")

  implicit val executionContext: ExecutionContext = system.dispatcher
  lazy val log = Logging(system, classOf[UserRepository])

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration
  val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistryActor")

  override def getUser(name: String): Future[Option[User]] = {

    val maybeUser: Future[Option[User]] =
      (userRegistryActor ? GetUser(name)).mapTo[Option[User]]
      maybeUser
  }


  override def getUsers(): Future[Users] = {

    val users: Future[Users] =
      (userRegistryActor ? GetUsers).mapTo[Users]
    users
  }


  override def saveUser(user: User): Future[Int] = {
    println("actor save")
    println(user)
    val userCreated: Future[ActionPerformed] =
    (userRegistryActor ? CreateUser (user) ).mapTo[ActionPerformed]
    println(userCreated)
    Future{0}
  }


  override def deleteUser(name: String): Future[Int] = {

    val userDeleted: Future[ActionPerformed] =
      (userRegistryActor ? DeleteUser(name)).mapTo[ActionPerformed]
     Future{0}
  }

  /**
    * Create the schema
    */
  override def creatSchema(): Future[Unit] = Future{Unit}


  /**
    * Update user
    * @param user
    * @return
    */
  override def update(user: User): Future[Int] = ???


}

object UserActorRepositoryImp {

}
