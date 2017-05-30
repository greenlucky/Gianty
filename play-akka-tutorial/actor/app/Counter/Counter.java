package Counter;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by greenlucky on 5/30/17.
 */
public class Counter extends AbstractLoggingActor{

    public static Props props() {
        return Props.create(Counter.class);
    }

    public static Props props(int initCounter) {
        return Props.create(Counter.class, () -> new Counter(initCounter));
    }
    
    public static class Message {}

    private int counter = 0;

    public Counter() {
        receive(ReceiveBuilder
                .match(Message.class, this::onMessage)
                .build());
    }

    public Counter(int initCounter) {
        this.counter = initCounter;
        receive(ReceiveBuilder
                .match(Message.class, this::onMessage)
                .build());
    }

    private void onMessage(Message message) {
        counter ++;
        log().info("Increased counter {}", counter ++ );
    }


}
