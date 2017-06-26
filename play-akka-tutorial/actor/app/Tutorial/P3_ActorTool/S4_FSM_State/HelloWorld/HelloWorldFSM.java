package Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld;

import akka.actor.AbstractFSM;
import akka.actor.AbstractFSMWithStash;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;

import static Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld.Message.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld.Message.State.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld.Message.EmptyData.*;

/**
 * Created by lamdevops on 6/17/17.
 */
public class HelloWorldFSM extends AbstractFSMWithStash<State, Data> {

    {
        startWith(Idle, EmptyData);

        when(Idle,
                matchEvent(Connect.class, Message.EmptyData.class, (e, d) -> {
                    log().info("Connect to DB");
                    unstash();
                    return goTo(Active).using(d);
                }).anyEvent((event, state) -> {
                            log().info("Waiting connect to DB");
                            stash();
                            return stay().using(EmptyData);
                        }
                ));
        when(Active, Duration.create(1, "second"),
                matchEvent(Disconnect.class,
                        Message.EmptyData.class, (e, d) -> {
                            log().info("Disconnect to DB");
                            return goTo(Idle).using(d);
                        }).event(Hello.class, (e, d) -> {
                    log().info("Tell msg connect to Probe");
                    e.getActorRef().tell("Hello Probe", ActorRef.noSender());
                    return stay();
                }));

        initialize();
    }


}

