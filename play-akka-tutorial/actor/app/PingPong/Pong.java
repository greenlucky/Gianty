package PingPong;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class Pong extends UntypedActor implements IPong {

    private final static Logger logger = LoggerFactory.getLogger( Pong.class);
    private final LoggingAdapter log = Logging.getLogger( getContext().system(), this );
    private String msg;
    
    @Override
    public void pong(String msg) {
        sender().tell( "pinged, " + msg, self());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if( !(message instanceof String))
            log.debug( "Msg is not format of String!!!" );
        else
            if(((String) message).startsWith( "ponged" )) {
                log.info( "Pinged, {}" , (String) message );
                this.msg = (String) message;
                pong( (String) message );
            }

    }

    public String getMsg() {
        return msg;
    }
}
