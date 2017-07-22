package Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld;

import akka.actor.*;

import static Tutorial.P3_ActorTool.S4_FSM_State.HelloWorld.Message.*;

/**
 * Created by lamdevops on 6/17/17.
 */
public class AppHelloWorldFSM {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("hello-world-fsm");
        ActorRef helloWorld = system.actorOf(Props.create(HelloWorldFSM.class), "hello-world");
        ActorRef probe = system.actorOf(Props.create(Probe.class));

        helloWorld.tell(new Hello(probe), ActorRef.noSender());
        helloWorld.tell(new Connect(probe), ActorRef.noSender());
        helloWorld.tell(new Disconnect(probe), ActorRef.noSender());

        Thread.sleep(100);

        system.terminate();
    }
}
