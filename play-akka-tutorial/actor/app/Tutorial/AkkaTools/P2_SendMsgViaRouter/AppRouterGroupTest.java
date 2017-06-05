package Tutorial.AkkaTools.P2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class AppRouterGroupTest {

    private ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create( "router-msg" );
    }

    @Test
    public void routerGroupTest() throws Exception {
        system.actorOf(Props.create(WorkerActor.class), "w1");
        system.actorOf(Props.create(WorkerActor.class), "w2");
        system.actorOf(Props.create(WorkerActor.class), "w3");
        system.actorOf(Props.create(WorkerActor.class), "w4");
        system.actorOf(Props.create(WorkerActor.class), "w5");

        List<String> workers = Arrays.asList(
                "/user/w1",
                "/user/w2",
                "/user/w3",
                "/user/w4",
                "/user/w5");

        ActorRef routerGroup = system.actorOf(Props.create(RouterGroup.class, workers), "worker-group");

        routerGroup.tell(new WorkerActor.Work(), routerGroup);
        routerGroup.tell(new WorkerActor.Work(), routerGroup);

        Thread.sleep(100);

        system.terminate();
    }
}
