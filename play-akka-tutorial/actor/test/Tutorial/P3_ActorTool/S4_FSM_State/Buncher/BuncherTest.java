package Tutorial.P3_ActorTool.S4_FSM_State.Buncher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.Flush.*;

/**
 * Created by lam.nm on 6/12/2017.
 */
class BuncherTest {

    private LoggingAdapter logger;

    static ActorSystem system;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create("BuncherTest");
        Logging.getLogger(system, BuncherTest.class);

    }

    @Test
    public void testBuncher() throws Exception {
        final ActorRef buncher = system.actorOf(Props.create(Buncher.class), "buncher");
        final ActorRef probe = system.actorOf(Props.create(Probe.class), "probe");

        buncher.tell(new SetTarget(probe), probe);
        buncher.tell(new Queue(42), probe);
        buncher.tell(new Queue(43), probe);
        LinkedList<Object> lstObj1 = new LinkedList<>();
        lstObj1.add(42);
        lstObj1.add(43);
        Assert.assertEquals(lstObj1.size(), 2);

        buncher.tell(new Queue(44), probe);
        buncher.tell(Flush, probe);
        buncher.tell(new Queue(45), probe);
        LinkedList<Object> lstObj2 = new LinkedList<>();
        lstObj2.add(44);
        Assert.assertEquals(lstObj2.size(), 1);
        LinkedList<Object> lstObj3 = new LinkedList<>();
        lstObj3.add(45);
        Assert.assertEquals(lstObj3.size(), 1);
        Thread.sleep(5000);
        system.terminate();
    }


}