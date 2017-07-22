package cluster.gateway_worker

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}
import com.typesafe.config.ConfigFactory
import commons.{Order, WorkerRegistration}

/**
  * Created by lamdevops on 7/1/17.
  */
class Gateway extends Actor with ActorLogging {

  var workers = IndexedSeq.empty[ActorRef]

  override def receive: Receive = {
    case Order if workers.isEmpty =>
      log.info("Service unavailable, cluster doesn't have worker node.")
    case order: Order =>
      log.info("{}", workers.size)
      log.info("Worker: I'll sent order to worker node to handle it.")
      workers(workers.size-1) forward order
    case WorkerRegistration if !(workers.contains(sender())) =>
      log.info("Worker registration {}", sender())
      workers = workers :+ sender()
      context watch (sender())
    case Terminated(a) =>
      workers = workers.filterNot(_ == a)
  }
}

object Gateway {

  private var _gateway: ActorRef = _

  def initiate() = {
    val config = ConfigFactory.load("gatewayworker").getConfig("Gateway")

    val system = ActorSystem("ClusterSystem", config)

    _gateway = system.actorOf(Props[Gateway], name = "gateway")

  }

  def getGateway = _gateway

}
