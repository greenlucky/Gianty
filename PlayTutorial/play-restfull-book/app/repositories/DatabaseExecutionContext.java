package repositories;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;

/**
 * Created by lamdevops on 6/24/17.
 */
public class DatabaseExecutionContext implements ExecutionContextExecutor {

    private final ExecutionContext executionContext;

    private static final String name = "database.dispatcher";

    @Inject
    public DatabaseExecutionContext(ActorSystem actorSystem) {
        this.executionContext = actorSystem.dispatchers().lookup(name);
    }

    @Override
    public void execute(Runnable runnable) {
        executionContext.execute(runnable);
    }

    @Override
    public void reportFailure(Throwable cause) {
        executionContext.reportFailure(cause);
    }

    @Override
    public ExecutionContext prepare() {
        return executionContext.prepare();
    }
}
