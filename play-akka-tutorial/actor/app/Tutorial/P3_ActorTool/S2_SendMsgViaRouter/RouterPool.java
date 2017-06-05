package Tutorial.P3_ActorTool.S2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class RouterPool extends UntypedActor{

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    private List<ActorRef> routees;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof WorkerActor.Work) {
            logger.debug( "I'm a RouterPool and I received a Message...." );
            routees.get(new Random().nextInt(routees.size())).forward(message, getContext());
        }
    }

    /**
     * Adds list sample workers to this router
     */
    public RouterPool() {
        routees = new ArrayList<>();
        routees.add(getContext().actorOf(Props.create(WorkerActor.class)));
        routees.add(getContext().actorOf(Props.create(WorkerActor.class)));
        routees.add(getContext().actorOf(Props.create(WorkerActor.class)));
        routees.add(getContext().actorOf(Props.create(WorkerActor.class)));
        routees.add(getContext().actorOf(Props.create(WorkerActor.class)));
    }
}
