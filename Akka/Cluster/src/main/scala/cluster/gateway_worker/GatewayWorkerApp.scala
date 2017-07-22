package cluster.gateway_worker

import akka.actor.ActorSystem
import commons.Order

/**
  * Created by lamdevops on 7/1/17.
  */
object GatewayWorkerApp extends App {

  val system = ActorSystem("clusterGatewayWorkerApp")

  // start gateway
  Gateway.initiate()

  // start worker 1st
  Worker.initiate(2552)

  // start worker 2nd
  Worker.initiate(2560)

  // start worker 3rd
  Worker.initiate(2561)

  Thread.sleep(10000)

  // send order to gateway
  Gateway.getGateway ! Order("Hello worker")
}
