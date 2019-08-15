package adapters.presenters

import com.google.inject.Inject
import entities.{User, Users}
import javax.inject.Singleton
import usecases.UserRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * Memory implementation of [[usecases.UserRepository]].
  */
@Singleton
@Inject
@com.google.inject.name.Named(value="Prueba")
class UserRepositoryMemImp extends UserRepository {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Stores the [[entities.User]]s in memory.
    */
  private var userRep: Seq[User] = Seq.empty[User]

  /**
    * Retrieves all session events of user.
    *
    * @param name user name
    * @return user information
    */
   override def getUser(name: String): Future[Option[User]] = {
      Future(userRep.find(_.name == name))
  }

  /**
    * Retrieves all session events of user.
    *
    * @return user information
    */
  override def getUsers(): Future[Users] = {
    Future {
      Users(userRep)
    }
  }

  /**
    * Stores user's
    *
    * @param user user to save
    */
  override def saveUser(user: User): Future[Int] = {
    this.userRep = userRep :+ user
    Future{(0)}
  }

  /**
    * Deletes user's all session events.
    *
    * @param name user to delete
    */
  override def deleteUser(name: String): Future[Int] = {
    this.userRep = userRep.filterNot(_.name == name)
    Future{(0)}
  }

  /**
    * Create the schema
    */
  override def creatSchema(): Future[Unit] = {
    Future{(0)}
  }

  /**
    * Update user
    * @param user
    * @return
    */
  override def update(user: User): Future[Int] = ???
}

