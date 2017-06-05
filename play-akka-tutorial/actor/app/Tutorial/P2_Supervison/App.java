package Tutorial.P2_Supervison;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by greenlucky on 6/4/17.
 */
public class App {

    ActorSystem system;
    ActorRef hera;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("supervision");
        hera = system.actorOf(Props.create(HeraActor.class), "hera");
    }

    @Test
    public void resume() throws Exception {
        hera.tell("Resume", hera);
        Thread.sleep(1000);
    }

    @Test
    public void restart() throws Exception {
        hera.tell("Restart", hera);
        Thread.sleep(1000);
    }

    @Test
    public void stop() throws Exception {
        hera.tell("Stop", hera);
        Thread.sleep(1000);
    }

    @AfterEach
    public void end() throws Exception {
        system.terminate();
    }
}
