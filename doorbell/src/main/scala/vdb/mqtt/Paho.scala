package vdb.mqtt

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSName

/**
  * Created by walter
  */
object Paho {
  @js.native
  @JSName("Paho.MQTT.Message")
  class MqttMessage(payload: String) extends js.Object {
    var destinationName: String = js.native
    var duplicate: Boolean = js.native
    val payloadString: String = js.native
    var qos: Int = js.native
    var retained: Boolean = js.native
  }
  @js.native
  trait ErrorResponse extends js.Object {
    val errorCode: String = js.native
    val errorMessage: String = js.native
  }
  @js.native
  trait ConnectOptions extends js.Object {
    val onSuccess: js.Function1[js.Any, Unit] = js.native
  }
  object ConnectOptions {
    def apply(onSuccess: js.Any => Unit): ConnectOptions = js.Dynamic.literal(onSuccess = onSuccess).asInstanceOf[ConnectOptions]
  }
  @js.native
  trait SubscribeOptions extends js.Object {
    val qos: Int = js.native
    val invocationContext: js.Any = js.native
  }
  object SubscribeOptions {
    def apply(qos: Int, invocationContext: js.Any): SubscribeOptions = js.Dynamic.literal(qos = qos, invocationContext = invocationContext).asInstanceOf[SubscribeOptions]
  }
  @js.native
  @JSName("Paho.MQTT.Client")
  class MqttClient(val host: String = js.native, val port: Int = js.native, val path: String = js.native, val clientId: String = js.native) extends js.Object {
    var onConnectionLost: js.Function1[ErrorResponse, Unit] = js.native
    var onMessageArrived: js.Function1[MqttMessage, Unit] = js.native
    def connect(connectOptions: ConnectOptions): Unit = js.native
    def disconnect(): Unit = js.native
    def send(message: MqttMessage): Unit = js.native
    def subscribe(filter: String, subscribeOptions: UndefOr[SubscribeOptions] = js.undefined): Unit = js.native
  }
}
