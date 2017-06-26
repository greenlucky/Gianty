package Tutorial.P3_ActorTool.S3_Become_Unbecome.HotSwap;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/8/2017.
 */
public class HotSwapActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private AbstractActor.Receive angry() {
        return receiveBuilder().matchEquals("Hi", s -> {
            getContext().unbecome();
            logger.info("I'm Angry, i just received {} msg. I'm going unbecome", s);
        }).matchEquals("bar", s -> {
            logger.info("Hi, I'am angry!!! I received msg {}, I will become happy", s);
            getContext().become(happy());
            //sender().tell("Hi, I'am angry!!!", self());
        }).matchAny(s -> {
            logger.info("Hi, I'am angry!!! I received msg {}", s);
            //sender().tell("Hi, I'am angry!!!", self());
        }).build();
    }

    private AbstractActor.Receive happy() {
        return receiveBuilder().matchEquals("Hi", s -> {
            getContext().unbecome();
            logger.info("I'm Happy, i just received {} msg. I'm going become unbecome", s);
        }).matchEquals("foo", s -> {
            logger.info("Hi, I'am happy!!! I received msg {}, I will become angry", s);
            getContext().become(angry());
            //sender().tell("Hi, I'am happy!!!", self());
        }).matchAny(s -> {
            logger.info("Hi, I'am happy!!! I received msg {}", s);
            //sender().tell("Hi, I'am happy!!!", self());
        }).build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("foo", s -> {
            logger.info("I'm default Receive, I received msg {}, I become Angry", s);
            getContext().become(angry());
        }).matchEquals("bar", s -> {
            logger.info("I'm default Receive, I received msg {}, I become Happy", s);
            getContext().become(happy());
        }).matchAny(s -> {
            logger.info("Hi, I'am unbecome!!!");
            getContext().unbecome();
        }).build();
    }
}
