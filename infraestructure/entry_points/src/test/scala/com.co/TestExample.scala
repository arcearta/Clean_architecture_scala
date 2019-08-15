package com.co

import adapters.presenters.{AService, NonSingletonService, SingletonService}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class UserRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest
  with UserRoutes {

  def singletonOrPrototype {
    import com.google.inject.AbstractModule
    class ScalaModuleSingleton extends AbstractModule {

      override protected def configure() {

        bind(classOf[AService]).toInstance(SingletonService)
      }
    }

    class ScalaModuleNonSingleton extends AbstractModule {

      override protected def configure() {

        bind(classOf[AService]).to(classOf[NonSingletonService])
      }
    }


  }

}