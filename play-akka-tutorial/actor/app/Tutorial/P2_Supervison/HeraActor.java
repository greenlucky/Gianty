package Tutorial.P2_Supervison;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;
import static akka.actor.SupervisorStrategy.escalate;

/**
 * Created by greenlucky on 6/4/17.
 */
public class HeraActor extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef childRef;

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10, Duration.create("1 second"),
                DeciderBuilder.match(Aphrodite.ResumeException.class, e -> resume())
                        .match(Aphrodite.RestartException.class, e -> restart())
                        .match(Aphrodite.StopException.class, e -> stop())
                .matchAny(o -> escalate()).build()
        );
    }

    @Override
    public void preStart() throws InterruptedException {
        childRef = context().actorOf(Props.create(AphroditeActor.class), "aphrodite");
        Thread.sleep(100);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String) {
            log.debug("Hera received {}", message);
            childRef.tell(message, self());
            Thread.sleep(100);
        }
    }
}
