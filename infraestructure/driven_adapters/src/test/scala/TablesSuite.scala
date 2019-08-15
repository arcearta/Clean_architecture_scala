/*import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import slick.jdbc.H2Profile.api._
import slick.jdbc.meta._
import sura.project.slik.DUser

import scala.language.higherKinds
import slick.lifted._

class Users(tag: Tag) extends Table[DUser](tag, "USERS") {

  import sura.project.slik.DUser

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


class TablesSuite extends FunSuite with BeforeAndAfter with ScalaFutures {
  //extends UserComponent with PictureComponent with DriverComponent
  import entities.Users
  import slick.jdbc.H2Profile
  import sura.project.slik.{DAO, DUser}

  implicit override val patienceConfig = PatienceConfig(timeout = Span(5, Seconds))

  val suppliers = TableQuery[Users]
  //val coffees = TableQuery[Coffees]
  
  var db: Database = _

  def createSchema() = {
    val dao = new DAO(H2Profile)
    dao.create
  }
  //db.run((suppliers.schema ++ coffees.schema).create).futureValue
 // db.run((suppliers.).create).futureValue
  
  def insertSupplier(): Int = {
    val dao = new DAO(H2Profile)
    val user = DUser(Some(1), "Pedro", "Ramirez", "Pedro Ramirez", 20, true)
    dao.insert(user)
    return 1
  }
  
  before { db = Database.forConfig("h2mem1") }
  
  test("Creating the Schema works") {
    createSchema()


    val tables = db.run(MTable.getTables).futureValue

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("suppliers")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("coffees")) == 1)
  }*/
/*
  test("Inserting a Supplier works") {
    createSchema()
    
    val insertCount = insertSupplier()
    assert(insertCount == 1)
  }
  
  test("Query Suppliers works") {
    createSchema()
    insertSupplier()
    val results = db.run(suppliers.result).futureValue
    assert(results.size == 1)
    //assert(results.head._== 101)
  }
  
  after { db.close }
}
*/