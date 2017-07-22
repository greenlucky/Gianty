package Tutorial.P3_ActorTool.S2_SendMsgViaRouter;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class WorkerActor extends UntypedActor{

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Work)
            logger.info( "I received Work Message and My ActorRef: {}", message);
    }

    public static class Work{}
}
