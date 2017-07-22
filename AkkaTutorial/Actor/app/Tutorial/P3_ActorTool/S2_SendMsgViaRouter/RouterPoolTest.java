package Tutorial.P3_ActorTool.S2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scala.Array;
import scala.concurrent.duration.FiniteDuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
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


    /**
     * ------------------------------------------------------
     * Balancing Pool
     * ------------------------------------------------------
     */
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

    /**
     * Router via smallest mail box pool
     * @throws Exception
     */
    @Test
    public void testSmallestMailBoxPool() throws Exception {

        ActorRef smallestMaiboxRouter = system.actorOf(FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "smallest-mailbox-router");
        fnTellTest(smallestMaiboxRouter);

    }


    /**
     * --------------------------------------------------------------
     * Broad Cast Pool
     * --------------------------------------------------------------
     */

    //Directly
    @Test
    public void testBroadCastDirectly() throws Exception {

        ActorRef router14 = system.actorOf(new BroadcastPool(5).props(Props.create(WorkerActor.class)),
                "router14");

        fnTellTest(router14);
    }

    //Via configuration file
    @Test
    public void testBroadCastViaConfig() throws Exception {
        ActorRef router15 = system.actorOf(
                FromConfig.getInstance().props(Props.create(WorkerActor.class)),
                "router15");

        fnTellTest(router15);
    }

    /**
     * Broadcast group
     */

    @Test
    public void testBroadCastGroupViaConfig() throws InterruptedException {
        ActorRef router16 = system.actorOf(FromConfig.getInstance().props(), "router16");

        fnTellTest(router16);
    }

    @Test
    public void testBroadCastGroupDirectly() throws InterruptedException {
        List<String> paths = Arrays.asList(
                "/user/wokers/w1",
                "/user/wokers/w2",
                "/user/wokers/w3"
        );
        ActorRef router16 = system.actorOf(new BroadcastGroup(paths).props(), "router16");

        fnTellTest(router16);
    }


    /**
     * ---------------------------------------------------------------------
     * Scatter Gather
     * ---------------------------------------------------------------------
     */

    @Test
    public void testScatterGatherViaConfig() throws Exception {
        ActorRef router17 = system.actorOf(
                FromConfig
                        .getInstance()
                        .props(Props.create(WorkerActor.class)),
                "router17");
        fnTellTest(router17);
    }

    @Test
    public void testScatterGatherDirectly() throws Exception {

        FiniteDuration within = FiniteDuration.create(10, TimeUnit.SECONDS);
        ActorRef router18 = system.actorOf(
                new ScatterGatherFirstCompletedPool(5, within).props(Props.create(WorkerActor.class)),
                "router18");

        fnTellTest(router18);
    }

    /**
     * Scatter Gather Group
     */

    @Test
    public void testScatterGatherGroupViaConfig() throws Exception {
        ActorRef router19 = system.actorOf(
                FromConfig.getInstance().props(),
                "router19");
        fnTellTest(router19);
    }

    @Test
    public void testScatterGatherGroupDirectly() throws InterruptedException {
        List<String> paths = Arrays.asList(
                "/user/workers/w1",
                "/user/workers/w2",
                "/user/workers/w3"
        );

        FiniteDuration within = FiniteDuration.create(10, TimeUnit.SECONDS);

        ActorRef router20 = system.actorOf(
                new ScatterGatherFirstCompletedGroup(paths, within).props(),
                "router20");

        fnTellTest(router20);
    }


    public void fnTellTest(ActorRef roundRouterPool) throws InterruptedException {

        int i = 0;
        while (i < 10) {
            roundRouterPool.tell(new WorkerActor.Work(), roundRouterPool);
            i++;
        }

        Thread.sleep(100);

        system.terminate();
    }
}
