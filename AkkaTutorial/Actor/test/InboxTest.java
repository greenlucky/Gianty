import Counter.Counter;
import akka.actor.*;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by lam.nm on 6/1/2017.
 */
public class InboxTest {

    ActorSystem system = ActorSystem.create( "inbox" );

    @Test
    public void inboxTest() {

        ActorRef counter = system.actorOf( Props.create( Counter.class ) );

        final Inbox inbox = Inbox.create( system );
        inbox.watch( counter);
        counter.tell( PoisonPill.getInstance(), ActorRef.noSender() );

        try {
            assert inbox.receive( Duration.create(1, TimeUnit.SECONDS) ) instanceof Terminated;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
