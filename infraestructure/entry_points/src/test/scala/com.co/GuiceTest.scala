package com.co

import com.google.inject.Guice
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Created by arcearta on 03/12/2015.
  */
class GuiceTest extends FlatSpec with BeforeAndAfterAll with Matchers {


  class ScalaModuleTest extends ScalaModule {

    override protected def configure() {

     // bind[AService].to[SingletonService]
      //bind(classOf[AService]).toInstance(SingletonService)


    }
  }


  val injector = Guice.createInjector(new ScalaModuleTest())


  "Calling method of class usingi" should "The call method response the string " in {
    //val logoutResponsePage = SamlLogoutPages.logoutResponsePage("someEncodeResponse", "someLogoutUrl", Some("someRelayState"))

    val service = injector.getInstance(classOf[MyComponent])
    service.callTheService().toString should equal("singleton")
  }

  /*

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
  */
}
