package services;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;

import javax.inject.Inject;

/**
 * Created by greenlucky on 6/3/17.
 */
public class DatabaseExecutionContext implements ExecutionContextExecutor {

    private final ExecutionContext executionContext;

    private static final String name = "database.dispatcher";

    @Inject
    public DatabaseExecutionContext(ActorSystem system) {
        this.executionContext = system.dispatchers().lookup(name);
    }

    @Override
    public ExecutionContext prepare() {
        return executionContext.prepare();
    }

    @Override
    public void execute(Runnable command) {
        executionContext.execute(command);
    }

    @Override
    public void reportFailure(Throwable cause) {
        executionContext.reportFailure(cause);
    }


}
