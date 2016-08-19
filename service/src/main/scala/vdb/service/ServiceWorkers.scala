package vdb.service

import org.scalajs.dom.experimental.{Notification, NotificationOptions}
import org.scalajs.dom.experimental.serviceworkers.{ClientQueryOptions, ClientType, ExtendableEvent, ServiceWorkerRegistration}
import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.annotation.JSName

/**
  * Created by walter
  */
object ServiceWorkers {
  @js.native
  class NotificationEvent extends ExtendableEvent {
    val notification: Notification = js.native
  }
  object ClientQueryOptions {
    def apply(includeUncontrolled: Boolean = false, `type`: ClientType = ClientType.all): ClientQueryOptions = Dynamic.literal(includeUncontrolled = includeUncontrolled).asInstanceOf[ClientQueryOptions]
  }
  implicit class RichServiceWorkerRegistration(registration: ServiceWorkerRegistration) {
    def showNotification(title: String, options: NotificationOptions): js.Promise[NotificationEvent] =
      registration.asInstanceOf[Dynamic].showNotification(title, options).asInstanceOf[js.Promise[NotificationEvent]]
  }
}
