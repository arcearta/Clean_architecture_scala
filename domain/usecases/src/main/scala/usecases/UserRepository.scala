package usecases


import entities.{User, Users}
import scala.concurrent.Future
/**
  * Data access facade for [[entities.User]] domain object.
  */
trait UserRepository {

  /**
    * Retrieves information of a specific user
    *
    * @param name user name
    * @return user information
    */
  def getUser(name: String): Future[Option[User]]

  /**
    * Retrieves all information of users.
    *
    * @return user information
    */
  def getUsers(): Future[Users]

  /**
    * Stores user's
    *
    * @param user user to save
    */
  def saveUser(user: User): Future[Int]


  def update(user: User): Future[Int]
  /**
    * Deletes user's all session events.
    *
    * @param name user to delete
    */
  def deleteUser(name: String): Future[Int]

  /**
    * Create schema if is necesary, used only for test applications
    */
  def creatSchema(): Future[Unit]

}
