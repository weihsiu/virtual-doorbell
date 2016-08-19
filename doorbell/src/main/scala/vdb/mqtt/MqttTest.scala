package vdb.mqtt

import org.scalajs.dom
import scala.scalajs.js.annotation.JSExport
import Paho._

/**
  * Created by walter
  */
@JSExport
object MqttTest {
  @JSExport
  def main(content: dom.Element): Unit = {
    val client = new MqttClient("broker.hivemq.com", 8000, "/test123", "client123")
    client.onConnectionLost = (e: ErrorResponse) => println(s"connection lost: ${e.errorMessage}")
    client.onMessageArrived = (m: MqttMessage) => println(s"message arrived: ${m.payloadString}")
    client.connect(ConnectOptions(onSuccess = _ => {
      println("connected")
      client.subscribe("/test123")
      val message = new MqttMessage("hello world!")
      message.destinationName = "/test123"
      client.send(message)
    }))
  }
}
