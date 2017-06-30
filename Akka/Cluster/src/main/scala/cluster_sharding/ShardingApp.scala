package cluster_sharding

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by lam.nm on 6/30/2017.
  */
object ShardingApp extends App {


  startup(Seq("2551", "2552", "0"))

  def startup(ports: Seq[String]) : Unit = {
    ports foreach { port =>

      //Override the configuration of the port
      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
        withFallback(ConfigFactory.load("sharding"))

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)



    }
  }
}
