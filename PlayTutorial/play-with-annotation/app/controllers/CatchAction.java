package controllers;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.ExceptionMailer;

import java.util.concurrent.CompletionStage;

/**
 * Created by lamdevops on 7/22/17.
 */
public class CatchAction extends Action.Simple{

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        try {
            System.out.println("Catch action");
            return delegate.call(ctx);
        }catch (Throwable e) {
            ExceptionMailer.send(e);
            throw new RuntimeException(e);
        }
    }
}
