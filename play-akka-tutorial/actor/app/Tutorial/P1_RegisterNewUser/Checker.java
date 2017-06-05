package Tutorial.P1_RegisterNewUser;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.List;

/**
 * Created by greenlucky on 6/4/17.
 */
public class Checker extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private List<User> blackList;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof CheckUser) {
            if (blackList.contains(((CheckUser) message).getUser())) {
                log.debug("Checker: " + ((CheckUser) message).getUser().toString() + " in the black list");
                sender().tell(new BlackUser(((CheckUser) message).getUser()), self());
            } else {
                log.debug("Checker: " + ((CheckUser) message).getUser().toString() + " not in the black list");
                sender().tell(new WhilteUser(((CheckUser) message).getUser()), self());
            }
        }
    }

    public static Props props(List<User> blackList) {
        return Props.create(Checker.class, blackList);
    }

    public Checker(List<User> blackList) {
        this.blackList = blackList;
    }


}
