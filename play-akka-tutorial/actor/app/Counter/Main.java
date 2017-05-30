package Counter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by greenlucky on 5/30/17.
 */
public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("lamdevops");
        //create by Props in constructor of Counter.class
        ActorRef counter1 = system.actorOf(Counter.props(5), "counter");
        //create directly
        //ActorRef counter2 = system.actorOf(Props.create(Counter.class), "counter-directly");

        //create actor with parameter
        //ActorRef counter3 = system.actorOf(Counter.props(2), "counter-with-param");

        counter1.tell(new Counter.Message(), ActorRef.noSender());
    }
}
