package vdb.service

import org.scalajs.dom.Event
import org.scalajs.dom.experimental.NotificationOptions
import org.scalajs.dom.experimental.serviceworkers.{ClientType, ExtendableEvent, ServiceWorkerGlobalScope, WindowClient}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport
import vdb.service.ServiceWorkers._

/**
  * Created by walter
  */
@JSExport
object ServiceMain {
  @JSExport
  def main(): Unit = {
    val self = global.self.asInstanceOf[ServiceWorkerGlobalScope]
    println("in service main!")
    self.addEventListener("install", (e: Event) => {
      self.skipWaiting()
      println(s"installed $e")
    })
    self.addEventListener("activate", (e: Event) => {
      println(s"activated $e")
    })
    self.addEventListener("push", (e: ExtendableEvent) => {
      println(s"pushed $e")
      e.waitUntil(self.registration.showNotification("doorbell rang", NotificationOptions(body = "world", tag = "my tag")))
    })
    self.addEventListener("notificationclick", (e: NotificationEvent) => {
      println(s"notification clicked $e")
      e.notification.close()
      e.waitUntil(
        self.clients.matchAll(ClientQueryOptions(`type` = ClientType.window)).toFuture.map(cs =>
          cs.find(_.url.contains("ringer.html")).foreach(_.asInstanceOf[WindowClient].focus())
        ).toJSPromise
      )
    })
  }
}
