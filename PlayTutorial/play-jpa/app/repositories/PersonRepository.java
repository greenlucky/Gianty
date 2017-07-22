package repositories;

import com.google.inject.ImplementedBy;
import models.Person;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * Created by greenlucky on 6/2/17.
 */
@ImplementedBy(JPAPersonRepository.class)
public interface PersonRepository {

    CompletionStage<Person> add(Person person);

    CompletionStage<Stream<Person>> list();

    Person insert(Person person);
}
