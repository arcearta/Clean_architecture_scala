package adapters.presenters


import com.google.inject.{ImplementedBy, Inject}
import entities.User
import usecases.{BankRepository, UserRepository}

import scala.concurrent.Future

@ImplementedBy(classOf[UserService])
trait UserServiceApi{

  import entities.Users

  def insertUser(user:User):Future[Int]
  def updateUser(user:User):Future[Int]
  def deleteUser(name:String):Future[Int]
  def getUser:Future[Users]
  def getUserByName(name:String):Future[Option[User]]
}

/**
  * Application service with multiple injection repository by parameter
  * @param userRep user repository injected
  * @param bankRep bank repository injected
  */
class UserService @Inject()(userRep:UserRepository, bankRep:BankRepository) extends UserServiceApi{

  import entities.Users

  def insertUser(user:User):Future[Int]={
    import entities.Bank
    userRep.saveUser(user)
    bankRep.createBank(Bank("Central"))
  }

  def updateUser(user:User):Future[Int]={
    userRep.update(user)
  }

  def deleteUser(name:String):Future[Int]={
    userRep.deleteUser(name)
  }

  def getUser:Future[Users]={
    userRep.getUsers()
  }

  def getUserByName(name:String):Future[Option[User]]={
    userRep.getUser(name)
  }

}