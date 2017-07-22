package cluster.simple

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._

/**
  * Created by lamdevops on 7/8/17.
  */
class SimpleClusterListener extends Actor with ActorLogging{

  val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember]
    )
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = {
    case MemberUp(member) =>
      log.info(s"Member is Up: ${member.address}")
    case UnreachableMember(member) =>
      log.info(s"Member detected as unreachable: ${member}")
    case MemberRemoved(member, previousStatus) =>
      log.info(s"Member is Removed: ${member} atfter ${previousStatus}")
    case _: MemberEvent => cluster.unsubscribe(self)

  }
}
