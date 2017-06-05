package person;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by greenlucky on 6/3/17.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UnitTest.class,
        IntegrationTest.class,
        AcceptanceTest.class
})
public class TestSuite {
}
