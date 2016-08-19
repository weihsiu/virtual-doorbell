package vdb.ringer

import org.scalajs.dom
import org.scalajs.dom.experimental.serviceworkers._
import org.scalajs.dom.experimental.push._
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import scalatags.JsDom.all._
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Dynamic
import scala.util.Try

/**
  * Created by walter
  */
@JSExport
object RingerMain {
  @JSExport
  def main(content: dom.Element): Unit = {
    val apiKey = "AIzaSyCGQ6g2-HCjdZ1ho5QxX4lCQIAUDIk5eBY"
    val qrCode = div().render
    val url = textarea().render
    val container =
      div(`class` := "container")(
        div(`class` := "row")(
          div(`class` := "col-xs-12")(
            div(`class` := "jumbotron")(
              h1("Virtual Ringer"),
              p("Share the following doorbell url with someone."),
              qrCode,
              br,
              url
            )
          )
        )
      ).render
    content.appendChild(container)
    registerService("service-fastopt.js")
        .flatMap(_.pushManager.subscribe(PushSubscriptionOptions(userVisibleOnly = true)).toFuture)
        .foreach { (s: PushSubscription) =>
          val u = makeUrl(apiKey, sid(s.endpoint))
          Dynamic.newInstance(global.QRCode)(qrCode, u)
          url.textContent = u
        }
  }
  def registerService(url: String): Future[ServiceWorkerRegistration] =
    Future.fromTry(Try(dom.window.navigator.serviceWorker)).flatMap(_.register(url).toFuture)
  def sid(endpoint: String): String = {
    val Sid = ".+/send/(.+)".r
    val Sid(sid) = endpoint
    sid
  }
  def makeUrl(apiKey: String, sid: String): String = {
    val l = dom.window.location
    val path = l.pathname.replace("ringer", "doorbell")
    s"${l.protocol}//${l.host}/$path?apiKey=$apiKey&sid=$sid"
  }
}
