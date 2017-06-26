package Tutorial.P3_ActorTool.S4_FSM_State.Buncher;

import akka.actor.AbstractFSM;
import akka.japi.pf.UnitMatch;
import scala.concurrent.duration.Duration;

import java.util.Arrays;
import java.util.LinkedList;
import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.State.Active;
import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.State.Idle;
import static Tutorial.P3_ActorTool.S4_FSM_State.Buncher.Message.Uninitialized.*;

/**
 * Created by lam.nm on 6/12/2017.
 */
public class Buncher extends AbstractFSM<State, Data> {

    {
        startWith(Idle, Uninitialized);

        when(Idle,
                matchEvent(SetTarget.class, Uninitialized.getClass(),
                        (setTarget, uninitialized) ->
                            stay().using(new Todo(setTarget.getRef(), new LinkedList<>()))));

        onTransition(
                matchState(Active, Idle, () -> {
                    final UnitMatch<Data> m = UnitMatch.create(
                            matchData(Todo.class,
                                    todo -> todo.getTarget().tell(new Batch(todo.getQueue()), getSelf())));
                }).state(Idle, Active, () -> {
                    log().info("Transition from Idle to Active");
                }));

        when(Active,
                Duration.create(1, "second"),
                matchEvent(Arrays.asList(Flush.class, StateTimeout()),
                        Todo.class,
                        (event, todo) -> goTo(Idle).using(todo.copy(new LinkedList<>()))));

        whenUnhandled(matchEvent(Queue.class, Todo.class,
                (queue, todo)-> goTo(Active).using(todo.addElement(queue.getObj())))
                .anyEvent((event, state) -> {
                    log().warning("received unhandled request {} in state {}/{}", event, stateName(), state);
                    return stay();
                }));

        initialize();
    }
}
