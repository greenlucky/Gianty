package cluster_sharding

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.cluster.sharding.ClusterSharding
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random

/**
  * Created by lamdevops on 7/8/17.
  */
class Frontend extends Actor with ActorLogging{
  import Frontend._

  val counterRegion: ActorRef = ClusterSharding(context.system).shardRegion(Counter.shardName)

  context.system.scheduler.schedule(3.seconds, 3.seconds, self, Tick(Inc))

  context.system.scheduler.schedule(6.seconds, 6.seconds, self, Tick(Dec))

  context.system.scheduler.schedule(10.seconds, 10.seconds, self, Tick(Get))

  override def receive: Receive = {
    case Tick(Inc) =>
      log.info(s"Frontend: Send Increment message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Increment)
    case Tick(Dec) =>
      log.info(s"Frontend: Send Decrement message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Decrement)
    case Tick(Get) =>
      log.info(s"Frontend: send Get message to shard region.")
      counterRegion ! Counter.CounterMessage(getId, Counter.Get)
  }

  def getId = Random.nextInt(4)
}

object Frontend {
  sealed trait Op
  case object Inc extends Op
  case object Dec extends Op
  case object Get extends Op

  case class Tick(op:Op)
}
