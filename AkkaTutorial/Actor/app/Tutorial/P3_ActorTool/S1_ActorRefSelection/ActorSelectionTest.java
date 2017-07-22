package Tutorial.P3_ActorTool.S1_ActorRefSelection;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by greenlucky on 6/4/17.
 */
public class ActorSelectionTest {


    ActorSystem system;

    private LoggingAdapter log;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("actor-path");
        log = Logging.getLogger(system, this);
    }

    @Test
    public void actorSelection() throws Exception {

        ActorRef counter1 = system.actorOf(Props.create(CounterActor.class), "Counter");

        log.debug("Actor Reference for counter1: {}", counter1);

        ActorSelection counterSelection1 = system.actorSelection("counter");

        log.debug("Actor Selection for counter1: {}", counterSelection1);

        counter1.tell(PoisonPill.getInstance(), counter1);

        Thread.sleep(100);

        ActorRef counter2 = system.actorOf(Props.create(CounterActor.class), "counter");

        log.debug("Actor Reference for counter2: {}", counter2);

        ActorSelection counterSelection2 = system.actorSelection("counter");

        log.debug("Actor Selection for counter2 is: {}", counterSelection2);

        Thread.sleep(1000);

        system.terminate();
    }
}
