package NonTrustWorthyChild;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class NonTrustWorthyChild extends AbstractLoggingActor {

    public static class Command {
    }

    private long messages = 0L;

    {
        receive( ReceiveBuilder.match( Command.class, this::onCommand ).build() );
    }

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
