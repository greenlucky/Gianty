package Tutorial.P3_ActorTool.S4_FSM_State.UserStorage;

import Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.Operation;
import akka.actor.AbstractFSMWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import static Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.Data.EmptyData;
import static Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.State.Connected;
import static Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.State.Disconnected;


/**
 * Created by lamdevops on 6/11/17.
 */
public class UserStorageFSM extends AbstractFSMWithStash<State, Data> {

    public static enum Operator {
        Connect, Disconnect
    }

    public UserStorageFSM() {
    }

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    {
        // 1. define start with
        startWith(Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.State.Disconnected, EmptyData);

        // 2. define state
        when(Disconnected,
                matchEventEquals(Operator.Connect, Data.class,
                        (event, state) -> {
                            logger.info("UserStorage Connected to DB");
                            unstash();
                            return goTo(Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.State.Connected).using(EmptyData);
                        }).anyEvent((event, state) -> {
                    logger.info("UserStorage any Event");
                    stash();
                    return stay().using(EmptyData);
                }));
        when(Connected,
                matchEventEquals(Operator.Disconnect,(event, state) -> {
                            logger.info("UserStorage disconnected from DB {}", event);
                            return goTo(Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.State.Disconnected).using(EmptyData);
                        }).event(Operation.class, (event, state) -> {
                            logger.info("UserStorage receive {} operation to do in user: {}", event.getDbOperation(), event.getUser());
                    return stay().using(EmptyData);
                }));

        initialize();

    }
}

