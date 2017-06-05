package Tutorial.P3_ActorTool.S1_ActorRefSelection;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;


/**
 * Created by greenlucky on 6/4/17.
 */
public class WatcherActor extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef counterRef;

    ActorSelection selection = context().actorSelection("/user/counter");

    public WatcherActor() {
        selection.tell(new Identify(null) ,self());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(!(message instanceof ActorIdentity)) return;

        if(((ActorIdentity) message).getRef() != null)
            log.debug("Actor Reference for counter is {}", ((ActorIdentity) message).getRef());
        else log.debug("Actor selection for actor doesn't live :( ");

    }
}
