package Tutorial.P2_Supervison.Monitoring;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by greenlucky on 6/4/17.
 */
public class AthenaActor extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String) {
            log.debug("Athena received {}", message);
            context().stop(self());
        }
    }
}
