package adapters.presenters

class SomeService extends AService {
  def service(): String = "someService"
}


class SomeServiceNamedFoo extends AService {
  def service(): String = "SomeServiceNamedFoo"
}
class SomeServiceNamedBar extends AService {
  def service(): String = "SomeServiceNamedBar"
}

object SingletonService extends AService {
  def service(): String = "singleton"
}

class NonSingletonService extends AService {
  def service(): String = "nonsingleton"
}