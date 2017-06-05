package Tutorial.P2_Supervison.Monitoring;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by greenlucky on 6/4/17.
 */
public class AresActor extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef athena;

    @Override
    public void preStart()  {
        context().watch(athena);
    }

    @Override
    public void postStop() {
        log.debug("Ares postStop....");
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Terminated)
            context().stop(self());
    }

    public static Props props (ActorRef athena) {
        return Props.create(AresActor.class, athena);
    }

    public AresActor(ActorRef athena) {
        this.athena = athena;
    }
}
