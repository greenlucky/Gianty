package person;

import controllers.PersonController;
import models.Person;
import repositories.PersonRepository;
import org.junit.Test;
import play.data.FormFactory;
import play.data.format.Formatters;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;

import javax.validation.Validator;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.invokeWithContext;


/**
 * Created by greenlucky on 6/3/17.
 */
public class UnitTest {

    @Test
    public void checkIndex() {
        PersonRepository repository = mock(PersonRepository.class);
        FormFactory formFactory = mock(FormFactory.class);
        HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());
        final PersonController controller = new PersonController(formFactory, repository, ec);
        final Result result = controller.index();

        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void checkTemplate() throws Exception {
        Content html = views.html.index.render();
        assertThat(html.contentType()).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Add Person");
        assertThat(contentAsString(html)).contains("Add person!");
    }

    @Test
    public void checkAddPerson() throws Exception {

        //mock out the form factory inputs class
        MessagesApi messagesApi = mock(MessagesApi.class);
        Validator validator = mock(Validator.class);
        FormFactory formFactory = new FormFactory(messagesApi, new Formatters(messagesApi), validator);

        PersonRepository repository = mock(PersonRepository.class);
        Person person = new Person.PersonBuilder()
                .setId(1L)
                .setName("Lam DevOps")
                .createPerson();

        System.out.println(person.toString());

        when(repository.add(any())).thenReturn(supplyAsync(() -> person));

        final Http.RequestBuilder requestBuilder = new Http.RequestBuilder().method("post").bodyJson(Json.toJson(person));

        final CompletionStage<Result> stage = invokeWithContext(requestBuilder, () -> {
            HttpExecutionContext ec = new HttpExecutionContext(ForkJoinPool.commonPool());

            final PersonController controller = new PersonController(formFactory, repository, ec);
            return controller.addPerson();
        });

        // Test completed result
        await().atMost(1, SECONDS).until(() ->
                assertThat(stage.toCompletableFuture()).isCompletedWithValueMatching(result ->
                        result.status() == SEE_OTHER, "Should redirect after operation"
                )
        );
    }

    @Test
    public void addPerson() throws Exception {
        PersonRepository repository = mock(PersonRepository.class);
        Person person = new Person.PersonBuilder()
                .setId(1L)
                .setName("Lam DevOps")
                .createPerson();
        person = repository.insert(person);
        CompletionStage<Stream<Person>> result = repository.list();
        System.out.println(person);

        Thread.sleep(100);

    }
}
