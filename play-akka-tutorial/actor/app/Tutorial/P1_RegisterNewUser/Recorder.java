package Tutorial.P1_RegisterNewUser;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by greenlucky on 6/4/17.
 */
public class Recorder extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private ActorRef checker;
    private ActorRef storage;
    private Timeout timeout = new Timeout(Duration.create(5, SECONDS));

    public static Props props(ActorRef checker, ActorRef storage) {
        return Props.create(Recorder.class, checker, storage);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof NewUser) {
            checker.tell(new CheckUser(((NewUser) message).getUser()), self());
        } else if (message instanceof WhilteUser)
            storage.tell(new AddUser(((WhilteUser) message).getUser()), self());
        else if (message instanceof BlackUser)
           log.debug("Recorder: " + ((BlackUser) message).getUser().toString() + " in the blacklist");
    }

    public Recorder(ActorRef checker, ActorRef storage) {
        this.checker = checker;
        this.storage = storage;
    }
}
