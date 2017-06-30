package cluster_sharding

import akka.actor.{ActorLogging, Props}
import akka.cluster.sharding.ShardRegion
import akka.persistence.PersistentActor
import scala.concurrent.duration._


/**
  * Created by lam.nm on 6/30/2017.
  */
class Counter extends PersistentActor with ActorLogging {
  import Counter._

  var count = 0

  context.setReceiveTimeout(120.seconds)

  override def persistenceId: String = self.path.parent.name + "-" + self.path.name

  def updateState(evt: CounterChanged): Unit =
    count += evt.delta

  override def receiveRecover: Receive = {
    case evt : CounterChanged => updateState(evt)
  }

  override def receiveCommand: Receive = {
    case Increment =>
      log.info(s"Counter with path: ${self} received Increment Command")
    case Decrement =>
      log.info(s"Counter with path: ${self} received Descrement Command")
    case Get =>
      log.info(s"Counter with path: ${self} received Get Command")
      log.info(s"Count = ${count}")
      sender() ! count
    case Stop =>
      context.stop(self)
  }

}

object Counter {

  trait Command

  case object Increment extends Command

  case object Decrement extends Command

  case object Get extends Command

  case object Stop extends Command

  trait Event

  case class CounterChanged(delta: Int) extends Event

  // Sharding Name
  val shardName: String = "Counter"

  // outside world if he want to send message to sharding should use this message
  case class CounterMessage(id: Long, cmd: Command)

  // id extractor
  val idExtractor: ShardRegion.ExtractEntityId = {
    case CounterMessage(id, msg) => (id.toString, msg)
  }

  // shard resolve
  val shardResolver : ShardRegion.ExtractShardId = {
    case CounterMessage(id, msg) => (id % 12).toString
  }

  def props() = Props[Counter]

}
