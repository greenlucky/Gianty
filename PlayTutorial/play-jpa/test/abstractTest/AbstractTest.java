package abstractTest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.db.jpa.JPAApi;
import play.test.FakeApplication;
import play.test.Helpers;

import static org.mockito.Mockito.mock;

/**
 * Created by lamdevops on 7/12/17.
 */
public class AbstractTest {

    protected JPAApi jpaApi;

    @BeforeClass
    public void initClass() throws Exception {
        jpaApi = mock(JPAApi.class);


    }
}
