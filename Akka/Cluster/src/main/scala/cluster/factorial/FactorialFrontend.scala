package cluster.factorial

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, ReceiveTimeout}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import akka.cluster.Cluster

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by lamdevops on 7/8/17.
  */
class FactorialFrontend(upToN: Int, repeat: Boolean) extends Actor with ActorLogging {

  val backend = context.actorOf(FromConfig.props(),
    name = "factorialBackendRouter")

  override def preStart(): Unit = {
    sendJobs()
    if (repeat) {
      context.setReceiveTimeout(10.seconds)
    }
  }

  def sendJobs(): Unit = {
    log.info(s"Starting batch of factorials up to [${upToN}]")
    1 to upToN foreach {
      backend ! _
    }
  }

  override def receive: Receive = {
    case (n: Int, factorial: BigInt) =>
      if (n == upToN) {
        log.debug(s"${n} != ${factorial}")
        if (repeat) sendJobs()
        else context.stop(self)
      }
    case ReceiveTimeout =>
      log.info("Timeout")
      sendJobs()
  }
}

object FactorialFrontend {
  def main(args: Array[String]): Unit = {
    val upToN = 200

    val config = ConfigFactory.parseString("akka.cluster.roles=[frontend]")
      .withFallback(ConfigFactory.load("factorial"))

    val system = ActorSystem("ClusterSystem", config)
    system.log.info("Factorials will start when 2 backend members in the cluster.")

    Cluster(system) registerOnMemberUp {
      system.actorOf(Props(classOf[FactorialFrontend], upToN, true),
        name = "factorialFrontend")
    }

    Cluster(system).registerOnMemberRemoved {
      system.registerOnTermination(System.exit(0))

      system.terminate()

      new Thread {
        override def run(): Unit = {
          if (Try(Await.ready(system.whenTerminated, 10.seconds)).isFailure)
            System.exit(-1)
        }
      }.start()
    }
  }
}
