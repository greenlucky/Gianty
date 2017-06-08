package Tutorial.P4_Become_Unbecome;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/8/2017.
 */
public class HotSwapActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    private AbstractActor.Receive angry;
    private AbstractActor.Receive happy;


    public HotSwapActor() {
        angry = receiveBuilder()
                .matchEquals("foo", s -> {
                    getSender().tell("I'm already angry?", getSelf());
                })
                .matchEquals("bar", s -> {
                    getContext().become(happy);
                }).build();

        happy = receiveBuilder().matchEquals("bar", s -> {
            getSender().tell("I'm already happy :-)", getSelf());
        }).matchEquals("foo", s -> {
            getContext().become(angry);
        }).build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("foo", s -> {
            getContext().become(angry);
        }).matchEquals("bar", s -> {
            getContext().become(happy);
        }).matchAny(s -> {
            getContext().unbecome();
            logger.info(getContext().toString());
        }).build();
    }
}
