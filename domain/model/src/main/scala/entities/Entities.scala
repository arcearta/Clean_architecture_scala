package entities

/**
  * Domain object to hold user's login logout information.

  */
//#user-case-classes
final case class User(id:Int,name:String,email:String,mobile:String,password:String)
final case class Users(users: Seq[User])

final case class Bank(name: String, id: Option[Int] = None)
//#user-case-classes