package book;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

/**
 * Created by greenlucky on 6/3/17.
 */
public class AcceptanceTest {

    @Test
    public void test() throws Exception {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, testBrowser -> {
           testBrowser.goTo("http://localhost:3333/book");
           assertThat(testBrowser.pageSource(), containsString("Add book"));
        });
    }
}
