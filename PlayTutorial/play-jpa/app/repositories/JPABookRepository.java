package repositories;

import models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.jpa.JPAApi;
import services.DatabaseExecutionContext;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Created by greenlucky on 6/3/17.
 */
public class JPABookRepository implements BookRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(JPABookRepository.class);
    
    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPABookRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Book> add(Book book) {
        LOGGER.info("Install book: [{}]", book);
        return supplyAsync(() -> wrap(em -> insert(em, book)), executionContext);
    }

    @Override
    public CompletionStage<Book> get(long bookId) {
        return supplyAsync(() -> wrap(em -> get(em, bookId)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Book>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage<Boolean> delete(long id) {
        return supplyAsync(() -> wrap(em -> remove(em, id)), executionContext);
    }

    private  <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Book insert(EntityManager em, Book book) {
        jpaApi.withTransaction(() -> {
            em.persist(book);
        });

        return book;
    }

    private Book get(EntityManager em, long id) {
        Book book = em.find(Book.class, id);
        return book;
    }

    private boolean remove(EntityManager em, long id) {
        try {
            em.createQuery("delete from Book b where b.id =" + id).executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean remove(EntityManager em, Book book) {
        try {
            em.remove(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Stream<Book> list(EntityManager em) {
        List<Book> lstBook = em.createQuery("select b from Book b", Book.class).getResultList();
        return lstBook.stream();
    }
}
