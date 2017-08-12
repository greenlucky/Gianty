package models;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.*;
import play.Application;
import play.ApplicationLoader;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;
import play.test.WithApplication;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by lamdevops on 7/15/17.
 */
public class AbstractTest extends WithApplication{

    @Before
    public void init() throws  Exception {


        User user = new User("John Cry");
        user.save();

        user = new User("Marry Obama");
        user.save();

        List<User> users = user.list();

        Assert.assertEquals(2, users.size());

        Role role1 = new Role("admin");
        role1.save();

        Role role2 = new Role("user");
        role2.save();

        List<Role> roles = role1.list();

        Assert.assertEquals(2, roles.size());
    }


}
