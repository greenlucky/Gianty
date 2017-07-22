package cluster.frontend_backend

import akka.actor.ActorSystem
import commons.Add
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, MILLISECONDS}

/**
  * Created by lamdevops on 6/29/17.
  */
object ClusterApp extends App {

  val system = ActorSystem("clusterApp")
  Frontend.initiate()

  Backend.initiate(2552)

  Backend.initiate(2560)

  Backend.initiate(2561)

  Thread.sleep(10000)

  system.scheduler.schedule(
    Duration.create(5000, MILLISECONDS),
    Duration.create(10000, MILLISECONDS),
    Frontend.getFrontend, Add(2, 4)
  )
}
