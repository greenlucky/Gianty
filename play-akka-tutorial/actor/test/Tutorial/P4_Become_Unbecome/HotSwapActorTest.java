package Tutorial.P4_Become_Unbecome;

import Tutorial.P4_Become_Unbecome.HotSwap.HotSwapActor;
import Tutorial.P4_Become_Unbecome.HotSwap.SenderHostSwapActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        ActorRef senderHotSwap = system.actorOf(Props.create(SenderHostSwapActor.class), "sender-hot-swap-actor");

        hotSwap.tell("foo",senderHotSwap);
        hotSwap.tell("bar", senderHotSwap);
        hotSwap.tell("foo", senderHotSwap);
        hotSwap.tell("Hi", senderHotSwap);
        hotSwap.tell("foo", senderHotSwap);
        hotSwap.tell("foo", senderHotSwap);
        hotSwap.tell("bar", senderHotSwap);
        hotSwap.tell("Hi", senderHotSwap);

        Thread.sleep(1000);

        system.terminate();
    }

}