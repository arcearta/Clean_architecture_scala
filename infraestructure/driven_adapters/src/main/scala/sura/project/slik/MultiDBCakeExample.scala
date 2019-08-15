package sura.project.slik

import slick.dbio.{DBIOAction, Effect, NoStream}
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.{H2Profile, SQLiteProfile}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


/** Run Slick code with multiple drivers using the Cake pattern. */
object MultiDBCakeExample extends App {

  def run(dao: DAO, db: Database) = {
    import scala.concurrent.Future
    import scala.util.{Failure, Success, Try}


    println("Running test against " + dao.profile)
    val dbio = for {
       _ <- dao.create

      // Creating our default picture
      defaultPic <- dao.insert(Picture("http://pics/default")): DBIOAction[Picture, NoStream, Effect.All]
      _ = println("- Inserted picture: " + defaultPic)


      u1 <- dao.insert(DUser(Some(1), "Pedro", "Ramirez", "Pedro Ramirez", 20, true))
      _ = println("- Inserted user: " + u1)
      u2 <- dao.insert(DUser(Some(2), "Juan", "Cazas", "Juan Cazas", 30, false))
      _ = println("- Inserted user: " + u2)
      u3 <- dao.users.result
      _ = println("- List users: " + u3.toList)

     pictures <- dao.pictures.result
      _ = println("- All pictures: " + pictures)
      users <- dao.users.result
      _ = println("- All users: " + users)


    } yield {

      println("imprimiendo nombres")
      users.toList.map( l =>  println("Result:  " + l.fullName) )
     // Void
    }

   // db.run {  dao.create }

    val datos: Future[List[DUser]] = db.run { dao.users.to[List].result }
    //Await.result(datos, Duration.Inf)

    Try(Await.result(datos,  Duration.Inf)) match {
      case Success(res) => println("###respuesta ") ; println(res)
      case Failure(e) => println("##### se genero un error: " + e.getMessage)
    }




   /* val query = for {
      s <- dao.create
      r <- dao.users.result
    } yield r
    val future = for {
      r <- db.run(query)
    } yield r
    val rows = Await.result(future, Duration.Inf)
    rows.foreach{ row =>
      println(row)
    }*/


/*

    db.run({
      var q2 = for {
        c <- dao.users
      } yield c.name;

      println("Lista de usuaruios:")
      println(q2)

    })*/



    //se cierra la conexion
    db.run(dbio.withPinnedSession)

    /* val dbio2 = for {

      _ <- dao.create
      u3 <- dao.users.result
      _ = println("- List users: " + u3.toList)
    }*/

   // db.run(dbio2.withPinnedSession)*/

  // val db2 = Database.forConfig("h2mem1")

    //val query = dao.users.map(p => p)
   // db2.run(query.result)


  }

  try {
    val f = {
      val h2db = Database.forConfig("h2")

      run(new DAO(H2Profile), h2db).andThen { case _ => h2db.close }
    }.flatMap { _ =>

      val sqlitedb = Database.forConfig("sqlite")
      run(new DAO(SQLiteProfile), sqlitedb).andThen { case _ => sqlitedb.close }
    }

    Await.result(f, Duration.Inf)

    println("Imprimiento registro")

    /*val db2 = Database.forConfig("h2")
    val dao2 = new DAO(H2Profile)

    val query = for {
      //s <- dao2.create
      r <- dao2.users.result
    } yield r
    val future = for {
      r <- db2.run(query)
    } yield r
    val rows = Await.result(future, Duration.Inf)
    rows.foreach{ row =>
      println("Imprimiento registro")
      println(row)
    }*/



  } finally DriverManagerUtil.unloadDrivers

}
