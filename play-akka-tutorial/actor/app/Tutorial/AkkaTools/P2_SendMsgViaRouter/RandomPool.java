package Tutorial.AkkaTools.P2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class RandomPool {

    ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("random-router");
    }

    @Test
    public void testRandomPool() throws  Exception {
        ActorRef routerPool = system.actorOf(
                FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "random-router-pool");

        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);
        routerPool.tell(new WorkerActor.Work(), routerPool);

        Thread.sleep(100);

        system.terminate();
    }
}
