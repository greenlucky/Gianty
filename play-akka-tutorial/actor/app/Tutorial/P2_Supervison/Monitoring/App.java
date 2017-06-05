package Tutorial.P2_Supervison.Monitoring;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by greenlucky on 6/4/17.
 */
public class App {
    ActorSystem system;
    ActorRef athena;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("monitoring");
        athena = system.actorOf(Props.create(AthenaActor.class), "athena");
    }

    @Test
    public void monitoring() throws Exception {
        ActorRef ares = system.actorOf(Props.create(AresActor.class, athena), "ares");
        athena.tell("Hi athena", ares);
    }

}
