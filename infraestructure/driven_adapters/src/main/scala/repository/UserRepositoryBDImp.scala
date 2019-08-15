package repository

import connection.DBComponent
import entities.{User, Users}
import usecases.UserRepository

import scala.concurrent.{ExecutionContext, Future}

trait UserTable extends DBComponent {

  import driver.api._

  class UserTable(tag:Tag) extends Table[User](tag,"TADB_USER2") {
   // val id = column[Int]("NMID", O.AutoInc, O.PrimaryKey)
    val id = column[Int]("NMID")
    //val name = column[String]("dsname", O.SqlType("VARCHAR2(200)"))
    val name = column[String]("DSNAME")
    val email  =column[String]("DSEMAIL")
    val mobile = column[String]("DSMOBILE")
    val password = column[String]("DSPASSWORD")

    def * = (id, name,email,mobile,password) <>(User.tupled,User.unapply)
  }

  val userTableQuery = TableQuery[UserTable]
}

//@ImplementedBy(classOf[UserImpl])
//@Singleton
//@Inject
//@com.google.inject.name.Named(value="Prueba")
class  UserRepositoryBDImp extends UserTable with UserRepository {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  import driver.api._
  val db = database.db

  /**
    * Stores user's
    *
    * @param user user to save
    */
   def saveUser(user: User): Future[Int] = {
     db.run  { userTableQuery += user }
   }

  /**}
    * Update user
    * @param user
    * @return
    */
  def update(user: User): Future[Int] = {
    db.run { userTableQuery.filter(_.id === user.id).update(user) }
  }


  /**
    * Deletes user's all session events.
    *
    * @param name user to delete
    */
  def deleteUser(name: String): Future[Int] = {
    db.run { userTableQuery.filter(_.name === name).delete }
  }

  def getAll(): Future[List[User]] = {
    db.run { userTableQuery.to[List].result }
  }

  /**
    * Retrieves all session events of user.
    *
    * @return user information
    */
   def getUsers(): Future[Users] = {
     db.run(userTableQuery.to[List].result.map(Users))
  }

  /**
    * Find a specific user
    * @param name user name
    * @return user information
    */
   def getUser(name: String): Future[Option[User]] = {
     db.run{ userTableQuery.filter(_.name === name).result.headOption}
   }

  /**
    * Create the DataBase schema if is necesary
    */
  def creatSchema(): Future[Unit] = {
    db.run  { userTableQuery.schema.create }
  }

}


