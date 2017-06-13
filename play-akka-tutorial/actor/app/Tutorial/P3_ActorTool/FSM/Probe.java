package Tutorial.P3_ActorTool.FSM;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/12/2017.
 */
public class Probe extends UntypedActor{
    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );
    @Override
    public void onReceive(Object message) throws Throwable {
        logger.info((String) message);
    }
}
