package Tutorial.P3_ActorTool.S2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import akka.routing.RoundRobinPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class RouterPoolTest {

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

        fnTellTest(routerPool);
    }

    /**
     * Gets round router pool directly configuration
     *
     * @throws Exception
     */
    @Test
    public void testRoundRobinPool() throws Exception {
        ActorRef roundRouterPool = system.actorOf(
                FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "round-router-pool");
        fnTellTest(roundRouterPool);
    }

    /**
     * Hard code configuration in local code
     * @throws Exception
     */
    @Test
    public void testRoundRobinRoolLocal() throws Exception {
        ActorRef roundRouterPool = system.actorOf(new RoundRobinPool(5)
                .props(Props.create(WorkerActor.class)),
                "round-robin-pool-local");
        fnTellTest(roundRouterPool);
    }

    @Test
    public void testBalancingPool() throws Exception {
        ActorRef balancingRouter = system.actorOf(FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "balancing-router-pool");

        fnTellTest(balancingRouter);
    }

    @Test
    public void testBalancingDispatcherPool() throws Exception {
        ActorRef balancingRouter = system.actorOf(FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "balancing-router-dispatcher-pool");

        fnTellTest(balancingRouter);
    }

    public void fnTellTest(ActorRef roundRouterPool) throws InterruptedException {

        int i = 0;
        while (i < 30) {
            roundRouterPool.tell(new WorkerActor.Work(), roundRouterPool);
            i++;
        }

        Thread.sleep(100);

        system.terminate();
    }
}
