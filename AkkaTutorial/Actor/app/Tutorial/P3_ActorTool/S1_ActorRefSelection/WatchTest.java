package Tutorial.P3_ActorTool.S1_ActorRefSelection;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Created by greenlucky on 6/5/17.
 */
public class WatchTest {
    ActorSystem system;

    private LoggingAdapter log;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("watch-actor-selection");
        log = Logging.getLogger(system, this);
    }

    @Test
    public void watchWithCounterTest() throws  Exception {
        ActorRef counter = system.actorOf(Props.create(CounterActor.class), "counter");

        ActorRef watcher = system.actorOf(Props.create(WatcherActor.class), "watcher");

        Thread.sleep(1000);

    }

    @Test
    public void watchWithoutCounterTest() throws  Exception {

        ActorRef watcher = system.actorOf(Props.create(WatcherActor.class), "watcher");

        Thread.sleep(1000);


    }

    @AfterEach
    public void end() throws Exception {
        system.terminate();
    }
}
