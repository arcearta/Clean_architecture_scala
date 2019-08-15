package wiring


import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

/**
  * Guice's wiring module.
  * */

class MyModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    import adapters.presenters.{AService, SomeOtherService, UserActorRepositoryImp}
    import repository.BankRepositoryBDImpl
    import usecases.{BankRepository, UserRepository}

    bind[AService].to[SomeOtherService]
   // bind[AService].to[SingletonService].sing //llamado a una clase
   //bind(classOf[AService]).toInstance(SingletonService)

    //bind[UserServiceApi].to[UserService]

    /** Binding repositorio de usuarios en memoria*/
   // bind[UserRepository].to[UserRepositoryMemImp]//.in[Singleton] //en memoria

    /** Binding repositorio de usuarios con actor*/
    bind[UserRepository].to[UserActorRepositoryImp]//.in[Singleton]  //con Actores

    /** Binding repositorio de BD*/
    bind(classOf[BankRepository]).to(classOf[BankRepositoryBDImpl])
   // bind[UserRepository].to[UserRepositoryBDImp]//.in[Singleton]  //con BD

    //bind(classOf[UserRepository]).toInstance(usr)
    //bind[JdbcProfile].to[usr]//.in[Singleton]  //con BD



    //bind(classOf[PageViewRepository]).to(classOf[PageViewRepositoryMemoryImpl])
    //bind(classOf[UserRepository]).to(classOf[UserRepositoryImp])
    //bind[CreditCardPaymentService]
    //bind[Bar[Foo]].to[FooBarImpl]
    //bind[PaymentService].annotatedWith(Names.named("paypal")).to[CreditCardPaymentService]
  }
}
/*
class MyPrivateModule extends PrivateModule with ScalaPrivateModule {
  def configure(): Unit = {
    bind[Foo].to[RealFoo]
    expose[Foo]

    install(new TransactionalBarModule())
    expose[Bar].annotatedWith[Transactional]

    bind[SomeImplementationDetail]
    install(new MoreImplementationDetailsModule())
  }
}*/