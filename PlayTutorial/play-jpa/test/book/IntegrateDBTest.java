package book;

import akka.actor.ActorSystem;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import models.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.ApplicationLoader;
import play.Environment;
import play.db.DBApi;
import play.db.DefaultDBApi;
import play.db.jpa.DefaultJPAApi;
import play.db.jpa.JPAApi;
import play.db.jpa.JPAModule;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.inject.guice.Guiceable;
import play.test.Helpers;
import repositories.BookRepository;

import static play.test.Helpers.running;

/**
 * Created by lamdevops on 7/13/17.
 */
public class IntegrateDBTest {

    @Inject
    Application application;

    JPAApi jpaApi;

    private BookRepository bookRepository;

    @Before
    public void setup() {
        Module testModule = new AbstractModule() {
            @Override
            public void configure() {
                bind(ActorSystem.class).toInstance(ActorSystem.create("integrateTest"));
                bind(DBApi.class).to(DefaultDBApi.class);
            }
        };

        GuiceApplicationBuilder builder = new GuiceApplicationLoader()
                .builder(new ApplicationLoader.Context(Environment.simple()))
                .load(new JPAModule());
        Guice.createInjector(builder.applicationModule()).injectMembers(this);

        Helpers.start(application);

        jpaApi = application.injector().instanceOf(JPAApi.class);
        bookRepository = application.injector().instanceOf(BookRepository.class);

    }

    @After
    public void teardown() {
        Helpers.stop(application);
    }

    @Test
    public void createUser() {
        running(application, () -> {
                Book book = new Book.BookBuilder()
                        .setName("Play Action")
                        .setPrice(10.5)
                        .createBook();
                bookRepository.add(book);
                System.out.println(book);
            });
    }
}
