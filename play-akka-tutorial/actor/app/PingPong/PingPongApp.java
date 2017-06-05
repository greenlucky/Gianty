package PingPong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class PingPongApp {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create( "ping-pong" );
        ActorRef pong = system.actorOf( Props.create( Pong.class ) );

        ActorRef ping = system.actorOf( Props.create( Ping.class, pong ) );
        ping.tell( "pinged, hello everyone" , ping);

    }


}
