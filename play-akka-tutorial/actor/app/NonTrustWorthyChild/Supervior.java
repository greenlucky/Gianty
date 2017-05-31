package NonTrustWorthyChild;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class Supervior extends AbstractLoggingActor {

    final ActorRef child = getContext().actorOf( NonTrustWorthyChild.props(), "child" );

    {
        receive(
                ReceiveBuilder
                        .matchAny( command -> child.forward( command, getContext() ) )
                        .build()
        );
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10,
                Duration.create( 10, TimeUnit.SECONDS ),
                DeciderBuilder.match( RuntimeException.class, ex -> stop() ).build()
        );
    }

    private SupervisorStrategy.Directive stop() {
        return SupervisorStrategy.stop();
    }

    public static Props props() {
        return Props.create(Supervior.class);
    }
}
