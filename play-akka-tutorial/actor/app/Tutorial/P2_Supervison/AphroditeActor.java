package Tutorial.P2_Supervison;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

/**
 * Created by greenlucky on 6/4/17.
 */
public class AphroditeActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        log.debug("Aphrodite preStart hook....");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.debug("Aphrodite preRestart hook....");
        super.preRestart(reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.debug("Aphrodite postRestart hook...");
        super.postRestart(reason);
    }

    @Override
    public void postStop() {
        log.debug("Aphrodite postStop....");
    }

    @Override
    public void onReceive(Object message) throws Throwable {

        if (!(message instanceof String)) return;

        switch ((String) message) {
            case "Resume": throw new Aphrodite.ResumeException();
            case "Stop" : throw new Aphrodite.StopException();
            case "Restart" : throw new Aphrodite.RestartException();
            default: throw new Exception();
        }
    }
}
