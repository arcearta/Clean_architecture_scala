package repository

import com.google.inject.{AbstractModule, Guice}
import entities.Bank
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSuite}
import usecases.BankRepository

class BankRepositoryTest extends FunSuite with BeforeAndAfter with ScalaFutures {

  import scala.concurrent.ExecutionContext

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      bind(classOf[BankRepository]).to(classOf[BankRepositoryBDImpl])
    }
  })

  //private val bankService = injector.getInstance(classOf[Routes])
  private val backrepo = injector.getInstance(classOf[BankRepository])

  before {

  }

  test("Add new banks") {

    import scala.concurrent.Await
    import scala.concurrent.duration.Duration
    Await.result(backrepo.ddl.map(println), Duration.Inf)

    var response = backrepo.createBank(Bank("Colpatria bank"))
    whenReady(response) { bankId =>
      assert(bankId === 1)
    }

    response = backrepo.createBank(Bank("Bancolombia bank"))
    whenReady(response) { bankId =>
      assert(bankId === 2)
    }

    response = backrepo.createBank(Bank("BBVA bank"))
    whenReady(response) { bankId =>
      assert(bankId === 3)
    }
  }

  test("Update  Colpatria bank  ") {

    val response = backrepo.updateBank(Bank("Colpatria Bank2", Some(1)))
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Delete Colpatria bank") {
    val response = backrepo.deleteBank(1)
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Get bank list") {
    val bankList = backrepo.getAllBanks()
    whenReady(bankList) { result =>
      assert(result === List(Bank("Bancolombia bank", Some(2)), Bank("BBVA bank", Some(3))))
    }
  }
}
