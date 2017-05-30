import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage, WebSocketRequest}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future

/**
  * Created by greenlucky on 5/28/17.
  */
object WebSocketClientFlow extends App{

    implicit val system = ActorSystem("lam-system")
    implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system)
      .withInputBuffer(
        initialSize = 64,
        maxSize = 64))
    import  system.dispatcher
    implicit val log: LoggingAdapter = Logging(system, this.getClass)

    val incoming: Sink[Message, Future[Done]] =
      Sink.foreach[Message] {
        case message: TextMessage.Strict =>
          printf(message.text)
        case message: BinaryMessage.Strict =>
          printf(message.data.toString())

      }

    val size: Int = 5900
    val shit = Array.fill[Byte](size)(1)
    val outgoing = Source.zipN(ByteString(shit))

    val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest("ws://echo.websocket.org"))

   val (upgradeResponse, closed) =
      outgoing
        .viaMat(webSocketFlow)(Keep.right) // keep the materialized Future[WebSocketUpgradeResponse]
        .toMat(incoming)(Keep.both) // also keep the Future[Done]
        .run()

    // just like a regular http request we can access response status which is available via upgrade.response.status
    // status code 101 (Switching Protocols) indicates that server support WebSockets
    val connected = upgradeResponse.flatMap { upgrade =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
        Future.successful(Done)
      } else {
        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
      }
    }

    // in a real application you would not side effect here
    connected.onComplete(println)
    closed.foreach(_ => println("closed"))
}
