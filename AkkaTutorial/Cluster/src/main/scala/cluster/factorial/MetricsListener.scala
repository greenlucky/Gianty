package cluster.factorial

import akka.actor.{Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.CurrentClusterState
import akka.cluster.metrics.StandardMetrics.{Cpu, HeapMemory}
import akka.cluster.metrics.{ClusterMetricsChanged, ClusterMetricsExtension, NodeMetrics}

/**
  * Created by lamdevops on 7/8/17.
  */
class MetricsListener extends Actor with ActorLogging {

  val selfAddress = Cluster(context.system).selfAddress
  val extension = ClusterMetricsExtension(context.system)

  override def preStart(): Unit = extension.subscribe(self)

  override def postStop(): Unit = extension.unsubscribe(self)

  override def receive: Receive = {
    case ClusterMetricsChanged(clusterMetrics) =>
      clusterMetrics.filter(_.address == selfAddress).foreach {
        nodeMetrics =>
          logHeap(nodeMetrics)
          logCpu(nodeMetrics)
      }
    case state: CurrentClusterState => // Ignore
  }

  def logHeap(nodeMetrics: NodeMetrics): Unit = nodeMetrics match {
    case HeapMemory(address, timestamp, used, committed, max) => {
      log.info("Used heap: {} MB", used.doubleValue / 1024 / 1024)
    }
    case _ => // No heap info.
  }

  def logCpu(nodeMetrics: NodeMetrics) : Unit = nodeMetrics match {
    case Cpu(address, timestamp, Some(systemLoadAverage), cupCompined, cpuStolen, processors) =>
      log.info("Load: {} ({} processors)", systemLoadAverage, processors)
    case _ => // No cpu info
  }
}
