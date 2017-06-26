package Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.Operator.*;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class UserStorageActor extends AbstractActorWithStash{

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private AbstractActor.Receive connected() {
        return receiveBuilder().matchEquals(
                    DisConnect, s -> {
                    logger.info("User Storage disconnect to DB");
                    getContext().unbecome();
                }).match(Operation.class, op -> {
            logger.info("User storage receive {} to do in user: {}",
                    op.getDbOperation(),
                    op.getUser());
        }).build();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(
                Connect, s -> {
                    logger.info("User Storage connected to DB");
                    getContext().become(connected());
                }).build();
    }
}
