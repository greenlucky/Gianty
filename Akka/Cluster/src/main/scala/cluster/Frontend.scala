package cluster

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}
import commons.{Add, BackendRegistration}
import com.typesafe.config.ConfigFactory

import scala.util.Random

/**
  * Created by lamdevops on 6/29/17.
  */
class Frontend extends Actor with ActorLogging {

  var backends = IndexedSeq.empty[ActorRef]

  override def receive = {
    case Add if backends.isEmpty =>
      log.info("Service unavailable, cluster doesn't have backend node.")
    case addOp: Add =>
      log.info("{}", backends.size)
      log.info("Frontend: I'll forward add operation to backend node to handle it.")
      backends(Random.nextInt(backends.size)) forward addOp
    case BackendRegistration if !(backends.contains(sender())) =>
      log.info("Backend registration {}", sender())
      backends = backends :+ sender()
      context watch (sender())
    case Terminated(a) =>
      backends = backends.filterNot(_ == a)
  }
}

object Frontend {

  private var _frontend : ActorRef = _

  def initiate() = {
    val config = ConfigFactory.load().getConfig("Frontend")

    val  system = ActorSystem("ClusterSystem", config)

    _frontend = system.actorOf(Props[Frontend], name = "frontend")
  }

  def getFrontend = _frontend

}
