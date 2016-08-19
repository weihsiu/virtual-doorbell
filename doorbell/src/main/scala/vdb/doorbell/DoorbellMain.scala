package vdb.doorbell

import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.URIUtils._
import scala.util.Try
import scalatags.JsDom.all._

/**
  * Created by walter
  */
@JSExport
object DoorbellMain {
  @JSExport
  def main(content: dom.Element): Unit = {
    val apiKey = queryVariable("apiKey")
    val sid = queryVariable("sid")
    val container =
      div(`class` := "container")(
        div(`class` := "row")(
          div(`class` := "col-xs-12")(
            div(`class` := "jumbotron")(
              h1("Virtual Doorbell"),
              p("Press the Ring button to ring the virtual doorbell, anywhere in the world."),
              button(`class`:= "btn btn-default", if (apiKey.isEmpty || sid.isEmpty) disabled := "disabled" else (), onclick := (() => ring(apiKey.get, sid.get)))("Ring")
            )
          )
        )
      ).render
    content.appendChild(container)
  }
  def ring(apiKey: String, sid: String): Future[js.Dynamic] = {
    Ajax.post(
      url = "https://android.googleapis.com/gcm/send",
      data = s"""{"registration_ids":["$sid"]}""",
      headers = Map(
        "Content-Type" -> "application/json",
        "Authorization" -> s"key=$apiKey"
      ),
      responseType = "json"
    ).map(r => r.response.asInstanceOf[js.Dynamic])
  }
  def queryVariable(name: String): Option[String] = {
    val query = Try(dom.window.location.search.tail).toOption
    query
      .flatMap(_
        .split('&')
        .map(_.split('='))
        .map(kv => (decodeURIComponent(kv(0)), decodeURIComponent(kv(1))))
        .find(_._1 == name))
      .map(_._2)
  }
}
