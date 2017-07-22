package PingPong;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class Ping extends UntypedActor implements IPing {

    private final LoggingAdapter log = Logging.getLogger( getContext().system(), this );
    private int counter;

    private ActorRef pong;

    private String msg;

    public static Props props(ActorRef pong) {
        return Props.create( Ping.class, pong );
    }

    public Ping(ActorRef pong) {
        this.pong = pong;
        counter = 0;
    }

    @Override
    public void ping(String msg) {
        pong.tell( "ponged, "  + msg, self());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(!(message instanceof String))
            log.debug( "Msg is invalid format of String!!!" );
        else
            if(((String) message).startsWith( "pinged" )) {
                log.info( "Ponged, {}" , (String) message );
                this.msg = (String) message;
                ping( (String) message );
                counter ++;
                if( counter == 4)
                    getContext().stop( self() );
            }
    }

    public String getMsg() {
        return msg;
    }
}
