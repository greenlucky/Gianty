package cluster.gateway_worker

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, RootActorPath}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.MemberUp
import com.typesafe.config.ConfigFactory
import commons.{Order, WorkerRegistration}

/**
  * Created by lamdevops on 7/1/17.
  */
class Worker extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberUp])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = {
    case order: Order =>
      log.info(s"I'm Worker with path: ${self} and I received order: ${order}" )
    case MemberUp(member) =>
      log.info("I'm Worker, I MemberUp...{}", member.getRoles)
      if (member.hasRole("gateway"))
        context.actorSelection(RootActorPath(member.address) / "user" / "gateway") ! WorkerRegistration
    case _ =>
      log.info("Can not receive order")
  }
}

object Worker {
  def initiate (port: Int ) = {
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.load("gatewayworker").getConfig("Worker"))
    val system = ActorSystem("ClusterSystem", config)

    val Worker = system.actorOf(Props[Worker], name = "cluster.Worker")

  }
}
