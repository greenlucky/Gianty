package cluster.frontend_backend

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, RootActorPath}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.typesafe.config.ConfigFactory
import commons.{Add, BackendRegistration}

/**
  * Created by lamdevops on 6/29/17.
  */
class Backend extends Actor with ActorLogging{

  val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberUp])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = {
    case Add(num1, num2) =>
      log.info(s"I'am a backend with path: ${self} and I received add operation.")
    case MemberUp(member) =>
      if(member.hasRole("frontend")) {
        context.actorSelection(RootActorPath(member.address)/"user"/"frontend")!
        BackendRegistration
      }
  }
}

object Backend {
  def initiate(port: Int) = {
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.load().getConfig("Backend"))

    val system = ActorSystem("ClusterSystem", config)

    val Backend = system.actorOf(Props[Backend], name = "cluster.frontend_backend.Backend")
  }
}