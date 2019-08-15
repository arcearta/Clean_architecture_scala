package adapters.presenters

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[SomeOtherService])
trait AService {
  def service: String
}
