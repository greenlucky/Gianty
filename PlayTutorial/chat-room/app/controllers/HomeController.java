package controllers;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Pair;
import akka.japi.pf.PFBuilder;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.F;
import play.mvc.*;

import javax.inject.Inject;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final Flow<String, String, NotUsed> userFlow;

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer mat) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        LoggingAdapter logging = Logging.getLogger(actorSystem.eventStream(), logger.getName());

        Source<String, Sink<String, NotUsed>> source = MergeHub.of(String.class)
                .log("source", logging)
                .recoverWithRetries(-1, new PFBuilder().match(Throwable.class, e -> Source.empty()).build());

        Sink<String, Source<String, NotUsed>> sink = BroadcastHub.of(String.class);

        Pair<Sink<String, NotUsed>, Source<String, NotUsed>>  sinkSourcePair = source.toMat(sink, Keep.both()).run(mat);
        Sink<String, NotUsed> chatSink = sinkSourcePair.first();
        Source<String, NotUsed> chatSource = sinkSourcePair.second();
        this.userFlow = Flow.fromSinkAndSource(chatSink, chatSource).log("userFlow", logging);
    }

    public Result index() {
        Http.Request request = request();
        String url = routes.HomeController.chat().webSocketURL(request);
        return Results.ok(views.html.index.render(url));
    }

    public WebSocket chat() {
        return WebSocket.Text.acceptOrResult(request -> {
            if(sameOriginCheck(request))
                return CompletableFuture.completedFuture(F.Either.Right(userFlow));
            else
                return CompletableFuture.completedFuture(F.Either.Left(forbidden()));
        });
    }

    private boolean sameOriginCheck(Http.RequestHeader request) {
        String[] origins  = request.headers().get("Origin");
        if(origins.length > 1)
            //more than origin found
            return false;
        String origin = origins[0];
        return originMatches(origin);
    }

    private boolean originMatches(String origin) {
        if(origin == null) return false;

        try {
            URL url = new URL(origin);
            return url.getHost().equals("localhost")
                    && (url.getPort() == 9000 || url.getPort() == 19001);
        } catch (Exception e) {
            return false;
        }
    }

}
