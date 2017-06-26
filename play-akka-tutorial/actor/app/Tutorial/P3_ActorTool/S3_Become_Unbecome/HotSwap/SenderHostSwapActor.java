package Tutorial.P3_ActorTool.S3_Become_Unbecome.HotSwap;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class SenderHostSwapActor extends UntypedActor{

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String)
            logger.info("I'm Sender Host Swap: {}", message);
    }
}
