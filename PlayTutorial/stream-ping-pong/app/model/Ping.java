package model;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class Ping extends UntypedActor implements IPing{

    private final static Logger logger = LoggerFactory.getLogger(Ping.class);

    private ActorRef pong;

    public static Props props(ActorRef pong) {
        return Props.create( Ping.class, pong );
    }

    public Ping(ActorRef pong) {
        this.pong = pong;
    }

    @Override
    public void ping(String msg) {
        pong.tell( "ponged, "  + msg, ActorRef.noSender());
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if(!(message instanceof String))
            logger.debug( "Msg is invalid format of String!!!" );
        else
            if(((String) message).startsWith( "pinged" ))
                ping( (String) message );
    }


}
