package cluster.simple

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by lamdevops on 7/8/17.
  */
object SimpleClusterApp extends App{

  def startUp(ports: Seq[String]) : Unit = {
    ports foreach { port =>
      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load("simplecluster"))

      // Create an akka system
      val system = ActorSystem("ClusterSystem", config)
      // Create an actor that handles cluster domain events
      system.actorOf(Props[SimpleClusterListener], name = "clusterListener1")
    }
  }

  startUp(Seq("2551", "2552", "2553" , "0"))

}
