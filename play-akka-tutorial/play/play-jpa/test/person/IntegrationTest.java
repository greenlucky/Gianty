package person;

import org.junit.Test;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

/**
 * Created by greenlucky on 6/3/17.
 */
public class IntegrationTest extends WithServer{


    @Test
    public void testInserverThroughUrl() throws Exception {

        // Test using a scoped WSClient to talk the server through a port.
        try(WSClient ws = WS.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url("/").get();
            WSResponse response = stage.toCompletableFuture().get();
            String body = response.getBody();
            assertThat(body, containsString("Add Person"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInServerThroughApp() throws Exception {
        //Test using the internal application available in the server.
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        final String body = contentAsString(result);
        assertThat(body, containsString("Add person"));
    }
}
