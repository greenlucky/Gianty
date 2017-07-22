package controllers;

import models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repositories.PersonRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class PersonController extends Controller {

    private final FormFactory formFactory;
    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Inject
    public PersonController(FormFactory formFactory, PersonRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> addPerson() {
        Person person = formFactory.form(Person.class).bindFromRequest().get();

        LOGGER.info("Add person: [{}]", person.toString());
        return personRepository.add(person).thenApplyAsync(p -> {
            return ok(toJson(p));
        });
    }

    public CompletionStage<Result> getPersons() {
        return personRepository.list().thenApplyAsync(p -> {
            return ok(toJson(p.collect(Collectors.toList())));
        });
    }

}
