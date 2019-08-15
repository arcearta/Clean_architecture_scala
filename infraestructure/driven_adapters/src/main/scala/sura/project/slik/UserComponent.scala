package sura.project.slik

/** A User contains a name, picture and ID */
//case class DUser(name: String, picture: Picture, id: Option[Int] = None)
import scala.concurrent.ExecutionContext.Implicits.global

case class DUser(id: Option[Int] = None,
                 firstName: String,
                 lastName: String,
                 fullName: String,
                 age: Int,
                 active: Boolean
               )


/** UserComponent provides database definitions for User objects */
trait UserComponent {

  this: DriverComponent with PictureComponent =>
  import profile.api._   // imports all the DSL goodies for the configured database

  class Users(tag: Tag) extends Table[DUser](tag, "USERS") {

    def id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def fullName = column[String]("fullName")
    def age = column[Int]("age")
    def active = column[Boolean]("active")


    override def * = (id, firstName, lastName, fullName, age, active) <> (DUser.tupled, DUser.unapply)

    /*override def * =
      (name, pictureId, id) <> (DUser.tupled, DUser.unapply)*/

  }
  val users = TableQuery[Users]

  private val usersAutoInc = users returning users.map(_.id)

  def insert(user: DUser): DBIO[DUser] =
    (usersAutoInc += user).map(id => user.copy(id = id))



  /*def insert(user: DUser): DBIO[DUser] = for {
    pic <-
      usersAutoInc += (user.name, user.picture)
  } yield user.copy(name = user.name, id = user.id)*/



/*
  def userById(id : Int) : Future[Option[DUser]] = {
    println("Coffees:")
    users.filter(_.id)
    db.run(coffees.result).map(_.foreach {
      case (name, supID, price, sales, total) =>
        println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    })



    for {
      users.filter( _.id = id)
    }
  }*/

  /*def fileById(id: Int): Future[Option[Array[Byte]]] = {
    dbConfig.db.run {
      rolesFilesTable
        .filter(_.cdfile === id)
        .map(_.file)
        .result
        .headOption
    }
  }*/

}
