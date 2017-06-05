package book;

import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static org.asynchttpclient.util.HttpConstants.Methods.GET;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

/**
 * Created by greenlucky on 6/3/17.
 */
public class IntegrationTest extends WithServer{

    @Test
    public void testInServerThroughUrl() throws Exception {
        try ( WSClient ws = WS.newClient(this.testServer.port())){
            CompletionStage<WSResponse> stage = ws.url("/book").get();
            WSResponse response = stage.toCompletableFuture().get();
            String body = response.getBody();
            assertThat(body, containsString("Add book"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInServerThroughApp() throws Exception {

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/book");

        Result result = route(app, request);

        final String body = contentAsString(result);
        assertThat(body, containsString("Add book"));

    }

}
