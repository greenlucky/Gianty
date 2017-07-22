package Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Created by lamdevops on 6/17/17.
 */
public class Message {

    public static enum State {
        Idle, Active
    }

    public static interface Data {

    }

    public static enum EmptyData implements Data {
        EmptyData
    }


    public static interface Operator {

    }

    public static final class Connect implements Operator{
        private final ActorRef actorRef;


        public Connect(ActorRef actorRef) {
            this.actorRef = actorRef;
        }

        public ActorRef getActorRef() {
            return actorRef;
        }
    }

    public static final class Disconnect implements Operator {

        private final ActorRef actorRef;


        public Disconnect(ActorRef actorRef) {
            this.actorRef = actorRef;
        }

        public ActorRef getActorRef() {
            return actorRef;
        }
    }

    public static final class Hello implements Operator {
        private final ActorRef actorRef;


        public Hello(ActorRef actorRef) {
            this.actorRef = actorRef;
        }

        public ActorRef getActorRef() {
            return actorRef;
        }
    }

    public static class Probe extends UntypedActor {

        @Override
        public void onReceive(Object message) throws Throwable {
            System.out.println(message.toString());
        }
    }
}
