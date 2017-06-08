package Tutorial.P4_Become_Unbecome;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by lam.nm on 6/8/2017.
 */
class HotSwapActorTest {

    private ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("hot-swap");
    }

    @Test
    void createReceive() throws InterruptedException {
        ActorRef hotSwap = system.actorOf(Props.create(HotSwapActor.class), "hot-swap-actor");

        hotSwap.tell("foo", hotSwap);
        hotSwap.tell("foo", hotSwap);
        hotSwap.tell("bar", hotSwap);
        hotSwap.tell("bar", hotSwap);
        hotSwap.tell("Hi", hotSwap);
        hotSwap.tell("foo", hotSwap);

        Thread.sleep(1000);

        system.terminate();
    }

}