package person;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

/**
 * Created by greenlucky on 6/3/17.
 */
public class AcceptanceTest {

    //check if the welcome page is being show
    @Test
    public void test() throws Exception{
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertThat(browser.pageSource(), containsString("Add person"));
        });
    }
}
