package PingPong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class PingPongAppTest {

    ActorSystem system = ActorSystem.create( "ping-pong" );

    @Test
    public void pingPongTest() throws Exception {
        ActorRef pong =  system.actorOf(Props.create(Pong.class));
        TestActorRef<Ping> actorRef = TestActorRef.create( system, Props.create( Ping.class, pong) );
        actorRef.tell("pinged", ActorRef.noSender() );
        Ping ping = actorRef.underlyingActor();
        assertEquals(ping.getMsg(), "pinged");
    }

}