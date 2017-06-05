package Tutorial.AkkaTools.P2_SendMsgViaRouter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.List;
import java.util.Random;

/**
 * Created by lam.nm on 6/5/2017.
 */
public class RouterGroup extends UntypedActor {

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    private List<String> routees;

    @Override
    public void onReceive(Object message) throws Throwable {

        if( message instanceof WorkerActor.Work) {
            logger.info("I'm a router and I received a Message....");
            getContext().actorSelection(routees.get(new Random().nextInt(routees.size())))
                    .forward(message, getContext());
        }


    }

    public static Props props(List<String> routees) {
        return Props.create( RouterGroup.class, routees );
    }

    public RouterGroup(List<String> routees) {
        this.routees = routees;
    }
}
