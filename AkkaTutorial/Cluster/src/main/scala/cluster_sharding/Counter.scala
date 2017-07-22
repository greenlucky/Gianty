package cluster_sharding

import akka.actor.{ActorLogging, Props}
import akka.cluster.sharding.ShardRegion
import akka.persistence.PersistentActor
import cluster_sharding.Counter.CounterChanged

import scala.concurrent.duration._

/**
  * Created by lamdevops on 7/8/17.
  */
class Counter extends PersistentActor with ActorLogging {
  import Counter._
  context.setReceiveTimeout(120.seconds)

  var count = 0;
  
  override def persistenceId: String = self.path.parent.name + "_" + self.path.name

  def updateCounter(evt: CounterChanged): Unit = {
    count += evt.delta
  }

  override def receiveRecover: Receive =  {
    case evt : CounterChanged => updateCounter(evt)
  }

  override def receiveCommand: Receive = {
    case Increment =>
      log.info(s"Couter path: ${self} received increment Command")
      persist(CounterChanged(+1))(updateCounter)
    case Decrement =>
      log.info(s"Counter path: ${self} received decrement Command")
      persist(CounterChanged(-1))(updateCounter)
    case Get =>
      log.info(s"Counter path: ${self} received get Command")
      log.info(s"Count = ${count}")
      sender() ! count
    case Stop =>
      log.info(s"Counter path ${self} received stop Command")
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

  val shardName: String = "cluster"

  case class CounterMessage(id: Long, command: Command)

  val idExtractor: ShardRegion.ExtractEntityId = {
    case CounterMessage(id, msg) => (id.toString, msg)
  }

  val shardResolver: ShardRegion.ExtractShardId = {
    case CounterMessage(id, msg) => (id % 12).toString
  }

  def props() = Props[Counter]

}
