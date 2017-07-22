package repositories;

import com.google.inject.Inject;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import services.UserService;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;

/**
 * Created by lamdevops on 6/24/17.
 */

public class JpaRepositoryTest extends WithApplication {

    private UserRepository userRepository;


    @Before
    public void init() throws Exception {
        userRepository =  app.injector().instanceOf(UserRepository.class);
        Assert.notNull(userRepository);
    }

    @Test
    public void testInsert() throws Exception {

        User user = new User.UserBuilder()
                .setName("Lam")
                .setEmail("nguyenlam@gmail.com")
                .setAddress("521/64 Binh Duong 1, Binh Duong")
                .createBuilder();
        System.out.println(user);
        User result = userRepository.insert(user);
        Stream<User> list = userRepository.findAll();

        System.out.println(list);

    }
}