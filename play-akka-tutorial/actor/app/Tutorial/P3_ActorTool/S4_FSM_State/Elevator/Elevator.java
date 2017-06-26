package Tutorial.P3_ActorTool.S4_FSM_State.Elevator;

import akka.actor.AbstractFSMWithStash;
import akka.actor.FSM;
import akka.japi.pf.FSMStateFunctionBuilder;
import com.sun.org.apache.regexp.internal.RE;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.HashSet;
import java.util.Set;

import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.Down.Down;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.NoDirection.NoDirection;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.Open.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.GoingDown.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.GoingUp.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.State;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.Idle.*;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorOperation.Up.Up;
import static Tutorial.P3_ActorTool.S4_FSM_State.Elevator.Elevator.ElevatorProtocol.*;

/**
 * Created by lamdevops on 6/18/17.
 */
public class Elevator {

    public static class ElevatorOperation {

        /**
         * Direction section
         */
        public static interface Direction {
        }

        public static enum Up implements Direction {Up}

        public static enum Down implements Direction {Down}

        public static enum NoDirection implements Direction {NoDirection}


        /**
         * State section
         */
        public static interface State {
        }

        public static enum Idle implements State {Idle}

        public static enum GoingUp implements State {GoingUp}

        public static enum GoingDown implements State {GoingDown}

        public static enum Open implements State {Open}
    }

    public static class Data {
        private ElevatorOperation.Direction direction;
        private int currentFloor = 0;
        private Set<ElevatorProtocol.Request> lstRequest;

        public Data(ElevatorOperation.Direction direction, int currentFloor, Set<ElevatorProtocol.Request> lstRequest) {
            this.direction = direction;
            this.currentFloor = currentFloor;
            this.lstRequest = lstRequest;
        }

        public Data(ElevatorOperation.Direction direction, int currentFloor, Request request) {
            if (lstRequest == null) lstRequest = new HashSet<>();
            this.direction = direction;
            this.currentFloor = currentFloor;
            this.lstRequest.add(request);
        }

        public ElevatorOperation.Direction getDirection() {
            return direction;
        }

        public void setDirection(ElevatorOperation.Direction direction) {
            this.direction = direction;
        }

        public int getCurrentFloor() {
            return currentFloor;
        }

        public void setCurrentFloor(int currentFloor) {
            this.currentFloor = currentFloor;
        }

        public Set<ElevatorProtocol.Request> getLstRequest() {
            return lstRequest;
        }

        public void addRequest(ElevatorProtocol.Request request) {
            this.lstRequest.add(request);
        }
    }

    public static class ElevatorProtocol {
        public static class Request {
            private int floor;

            public Request(int floor) {
                this.floor = floor;
            }

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }
        }

        public static class GetOn extends Request {

            private ElevatorOperation.Direction direction;

            public GetOn(int floor, ElevatorOperation.Direction direction) {
                super(floor);
                this.direction = direction;
            }
        }

        public static class GetOff extends Request {

            public GetOff(int floor) {
                super(floor);
            }
        }

        public static enum GetCurrentFloor {GetCurrentFloor}

        public static enum GetCurrentRequest {GetCurrentRequest}

        public static class ArrivedAtFloor {
            private int floor = 0;

            public ArrivedAtFloor(int floor) {
                this.floor = floor;
            }

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }
        }
    }


    public static class ElevatorFSM extends AbstractFSMWithStash<State, Data> {

        private int currentFloor = 0;

        {
            startWith(Idle, new Data(NoDirection, 0, new HashSet<>()));

            when(Idle,
                    matchEvent(Request.class,
                            (request, data) -> request.getFloor() == data.getCurrentFloor(),
                            (request, data) -> {
                                return goTo(Open);
                            }
                    ).event(Request.class,
                            (request, data) -> request.getFloor() < data.getCurrentFloor(),
                            (request, data) -> {
                                return goTo(GoingDown).using(new Data(Down, request.getFloor(), request));
                            }
                    ).event(Request.class,
                            (request, data) -> request.getFloor() > data.getCurrentFloor(),
                            (request, data) -> {
                                return goTo(GoingUp).using(new Data(Up, request.getFloor(), request));
                            }
                    ));

            when(GoingUp, matchEvent(Request.class, (request, data) -> {

            }));

            when(GoingDown, matchEvent(Request.class, (request, data) -> {

            }));

            whenUnhandled();

            onTransition(
                    matchState(null, Open, () -> {
                        log().info("Opening  the door", nextStateData());
                    }).state(Open, null, () -> {
                        log().info("Closing the door", nextStateData());
                    }));

            initialize();
        }
    }

}
