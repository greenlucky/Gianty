package NonTrustWorthyChild;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.stream.IntStream;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class App {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("non-trust-worthy-child");
        final ActorRef supervisor = system.actorOf(Props.create(Supervior.class), "supervisor");

        IntStream.range(0, 10).forEach( i ->
           supervisor.tell(new NonTrustWorthyChild.Command(), ActorRef.noSender())
        );
    }
}
