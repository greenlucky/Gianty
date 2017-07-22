package repositories;

import com.google.inject.ImplementedBy;
import models.Book;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * Created by greenlucky on 6/3/17.
 */
@ImplementedBy(JPABookRepository.class)
public interface BookRepository {

    CompletionStage<Book> add(Book book);

    CompletionStage<Book> get(long bookId);

    CompletionStage<Stream<Book>> list();

    CompletionStage<Boolean> delete(long id);
}
