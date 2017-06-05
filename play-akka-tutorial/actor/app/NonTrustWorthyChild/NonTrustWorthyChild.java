package NonTrustWorthyChild;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class NonTrustWorthyChild extends AbstractLoggingActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match( Command.class, this::onCommand ).build();
    }

    public static class Command {
    }

    private long messages = 0L;


    private void onCommand(Command command) {
        messages++;
        if (messages % 4 == 0)
            throw new RuntimeException("Oh, no, I got four command, can't handle more!!!");
        else
            log().info("Got a command " + messages);
    }

    public static Props props() {
        return Props.create(NonTrustWorthyChild.class);
    }
}
