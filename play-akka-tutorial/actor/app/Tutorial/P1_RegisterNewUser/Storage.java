package Tutorial.P1_RegisterNewUser;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 6/4/17.
 */
public class Storage extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private List<User> lstUser;

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof AddUser) {
            log.debug("Storage: add " + ((AddUser) message).getUser().toString() + " to storage");
            lstUser.add(((AddUser) message).getUser());
        }
    }

    public Storage() {
        this.lstUser = new ArrayList();
    }
}
