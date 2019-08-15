package connection

import slick.jdbc.JdbcProfile
import slick.basic._

trait DBComponent {
  val database = DatabaseConfig.forConfig[JdbcProfile]("h2_dc")
  val driver = database.profile
}
