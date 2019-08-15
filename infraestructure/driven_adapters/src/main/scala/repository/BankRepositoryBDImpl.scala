package repository


import connection._
import entities.Bank
import usecases.BankRepository

import scala.concurrent.Future


 trait BankTable extends DBComponent  {

  import driver.api._

   class BankTable(tag: Tag) extends Table[Bank](tag, "bank") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    def * = (name, id.?) <> (Bank.tupled, Bank.unapply)

  }

  protected val bankTableQuery = TableQuery[BankTable]

  protected def bankTableAutoInc = bankTableQuery returning bankTableQuery.map(_.id)

}

class BankRepositoryBDImpl extends BankTable  with BankRepository  {

  import driver.api._
  val db = database.db

  def createBank(bank: Bank): Future[Int] = db.run { bankTableAutoInc += bank }

  def updateBank(bank: Bank): Future[Int] = db.run { bankTableQuery.filter(_.id === bank.id.get).update(bank) }

  def getBankById(id: Int): Future[Option[Bank]] = db.run { bankTableQuery.filter(_.id === id).result.headOption }

  def getAllBanks(): Future[List[Bank]] = {
    db.run { bankTableQuery.to[List].result }
  }

  def deleteBank(id: Int): Future[Int] = db.run { bankTableQuery.filter(_.id === id).delete }

  def ddl=db.run {bankTableQuery.schema.create}

}


