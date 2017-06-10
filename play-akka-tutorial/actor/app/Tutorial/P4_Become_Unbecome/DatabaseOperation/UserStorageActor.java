package Tutorial.P4_Become_Unbecome.DatabaseOperation;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class UserStorageActor extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    private AbstractActor.Receive connected() {
        return receiveBuilder().matchEquals(
                Operation.Connect.class, s -> {
                    logger.info("User Storage disconnect to DB");
                    getContext().unbecome();
                }).matchEquals(Operation.class, op -> {
                    logger.info("User storage receive {} to do in user: {}",
                            op.cast(Operation.class).getDbOperation(),
                            op.cast(Operation.class).getUser());
        }).build();
    }

    private AbstractActor.Receive disConnected() {
        return receiveBuilder().matchEquals(
                Operation.Connect.class, s -> {
                    logger.info("User Storage connected to DB");
                    getContext().become(disConnected());
                }).build();
    }

    public UserStorageActor() {
        getContext().become(disConnected());
    }

    @Override
    public Receive createReceive() {
        return null;
    }
}
