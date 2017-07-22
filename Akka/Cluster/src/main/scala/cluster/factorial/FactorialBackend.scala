package cluster.factorial

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.annotation.tailrec
import scala.concurrent.Future
import akka.pattern.pipe
import com.typesafe.config.ConfigFactory

/**
  * Created by lamdevops on 7/8/17.
  */
class FactorialBackend extends Actor with ActorLogging {

  import context.dispatcher

  override def receive: Receive = {
    case (n: Int) =>
      (Future(factorial(n)) map { result => (n, result) }).pipeTo(sender())
  }

  def factorial(n: Int): Unit = {
    @tailrec def factorialAcc(acc: BigInt, n: Int): BigInt = {
      if (n <= 1) acc
      else factorialAcc(acc * n, n - 1)
    }

    factorialAcc(BigInt(1), n)
  }
}

object FactorialBackend {
  def main(args: Array[String]): Unit = {
    val port = 0
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
      .withFallback(ConfigFactory.parseString("akka.cluster.roles=[backend]"))
      .withFallback(ConfigFactory.load("factorial"))

    val system = ActorSystem("ClusterSystem", config)
    system.actorOf(Props[FactorialBackend], name = "factorialBackend")

    system.actorOf(Props[MetricsListener], name = "metricsListener")
  }
}
