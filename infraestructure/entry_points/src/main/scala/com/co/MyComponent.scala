package com.co

import adapters.presenters.AService
import com.google.inject.Inject

class MyComponent @Inject()(val service: AService) {
  def callTheService(): String = service.service
}