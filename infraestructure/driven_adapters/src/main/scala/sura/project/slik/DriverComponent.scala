package sura.project.slik

import slick.jdbc.JdbcProfile

/** The slice of the cake which provides the Slick driver */
trait DriverComponent {
  val profile: JdbcProfile
}
