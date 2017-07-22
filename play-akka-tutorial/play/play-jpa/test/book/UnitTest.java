package book;

import controllers.BookController;
import models.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.data.FormFactory;
import play.data.format.Formatters;
import play.db.jpa.DefaultJPAApi;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.i18n.MessagesApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.Guiceable;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;
import repositories.BookRepository;
import repositories.SBookRepository;

import javax.persistence.EntityManager;
import javax.validation.Validator;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;

/**
 * Created by greenlucky on 6/3/17.
 */
public class UnitTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitTest.class);

    private JPAApi jpaApi;

    @Before
    public void init() throws Exception {

    }

    @Test
    public void checkIndex() {
        BookRepository repository = mock(BookRepository.class);
        SBookRepository srepository = mock(SBookRepository.class);
        FormFactory formFactory = mock(FormFactory.class);
        HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());
        final BookController controller = new BookController(formFactory, repository, srepository, ec);
        final Result result = controller.index();

        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void checkTemplate() throws Exception {
        Content html = views.html.book.render();
        assertThat(html.contentType()).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Add book");
    }

    @Test
    public void checkAddBook() throws Exception {
        MessagesApi messages = mock(MessagesApi.class);
        Validator validator = mock(Validator.class);
        FormFactory formFactory = new FormFactory(messages, new Formatters(messages), validator);

        SBookRepository srepository = mock(SBookRepository.class);
        BookRepository repository = mock(BookRepository.class);
        Book book = new Book.BookBuilder()
                .setId(1L)
                .setName("Play Action")
                .setPrice(10.5)
                .createBook();
        when(repository.add(any(Book.class))).thenReturn(supplyAsync(() -> book));

        final Http.RequestBuilder requestBuilder = new Http.RequestBuilder().method("post").bodyJson(Json.toJson(book));

        final CompletionStage<Result> stage = invokeWithContext(requestBuilder, () -> {
            HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());

            final BookController controller = new BookController(formFactory, repository, srepository, ec);
            return controller.addBook();
        });

        verify(repository).add(book);

        await().atMost(1, TimeUnit.SECONDS).until(() -> {
            assertThat(stage.toCompletableFuture()).isCompletedWithValueMatching(result ->
                result.status() == SEE_OTHER, ""
            );
        });



    }

    @Test
    public void getBook() throws Exception {
        BookRepository repository = mock(BookRepository.class);
        verify(repository).get(1L);
    }


    @Test
    public void removeBook() throws Exception {

        final Http.RequestBuilder requestBuilder1 = new Http.RequestBuilder().method("get").bodyJson(Json.toJson(1));
        final CompletionStage<Result> stage = invokeWithContext(requestBuilder1, () -> {
            FormFactory formFactory = mock(FormFactory.class);
            BookRepository repository = mock(BookRepository.class);
            SBookRepository srepository = mock(SBookRepository.class);
            HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());
            final BookController controller = new BookController(formFactory, repository, srepository, ec);
            return controller.deleteBook(1);
        });

        await().atMost(1, TimeUnit.SECONDS).until(() ->
                assertThat(stage.toCompletableFuture()).isCompletedWithValueMatching(result ->
                        result.status() == SEE_OTHER, ""
                )
        );
        Thread.sleep(100);
    }


    @Test
    public void addBoosTest() throws Exception {

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                JPA.withTransaction(() -> {
                    EntityManager em = JPA.em();
                    Book book = new Book.BookBuilder()
                            .setName("Play Action")
                            .setPrice(10.5)
                            .createBook();
                    em.persist(book);

                    Book actualBook = em.find(Book.class, book.getId());

                    Assert.assertEquals(actualBook, book);
                });
            }
        });

        Thread.sleep(100);

    }

}
