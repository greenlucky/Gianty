package model;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class Pong extends UntypedActor implements IPong {

    private final static Logger logger = LoggerFactory.getLogger(Pong.class);
    
    @Override
    public void pong(String msg) {
        sender().tell( "pinged, " + msg, ActorRef.noSender());
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if( !(message instanceof String))
            logger.debug( "Msg is not format of String!!!" );
        else
            if(((String) message).startsWith( "ponged" ))
                pong((String) message);

    }
}
