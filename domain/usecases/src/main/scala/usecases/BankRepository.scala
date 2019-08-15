package usecases

import scala.concurrent.Future

/**
  * Data access facade for [[entities.Bank]] domain object.
  */
trait BankRepository {

  import entities.Bank

  def createBank(bank: Bank): Future[Int]

  def updateBank(bank: Bank): Future[Int]
  def getBankById(id: Int): Future[Option[Bank]]

  def getAllBanks(): Future[List[Bank]]

  def deleteBank(id: Int): Future[Int]
  def ddl : Future[Unit]

}
