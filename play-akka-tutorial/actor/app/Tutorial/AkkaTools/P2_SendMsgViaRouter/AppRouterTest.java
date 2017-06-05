package Tutorial.AkkaTools.P2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class AppRouterTest {

    private ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create( "router-msg" );
    }

    @Test
    public void testRouter() throws Exception {
        ActorRef router = system.actorOf( Props.create(RouterPool.class), "router");

        router.tell(new WorkerActor.Work(), router);
        router.tell(new WorkerActor.Work(), router);
        router.tell(new WorkerActor.Work(), router);

        Thread.sleep( 100 );

        system.terminate();

    }
}
