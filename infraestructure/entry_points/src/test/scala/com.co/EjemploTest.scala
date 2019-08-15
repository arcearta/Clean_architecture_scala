package com.co

import org.scalatest.{ BeforeAndAfterAll, FlatSpec, Matchers }

/**
  * Created by arcearta on 03/12/2015.
  */
class SamlLogoutPagesTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  "Generete logoutResponsePage with relayState" should "be contain the relayState attribute" in {
    //val logoutResponsePage = SamlLogoutPages.logoutResponsePage("someEncodeResponse", "someLogoutUrl", Some("someRelayState"))

    val logoutResponsePage = "someRelayState"

    logoutResponsePage.toString should include("someRelayState")
    logoutResponsePage.toString should include("SAMLResponse")
    logoutResponsePage.toString should include("RelayState")
  }

  "Generete logoutResponsePage with out relayState" should "not be contain the relayState attribute" in {
    //val logoutResponsePage = SamlLogoutPages.logoutResponsePage("someEncodeResponse", "someLogoutUrl", None)
    val logoutResponsePage = "someLogoutUrl"

    logoutResponsePage.toString should include("someLogoutUrl")
    logoutResponsePage.toString should include("SAMLResponse")
    logoutResponsePage.toString should not include ("RelayState")
  }
}
