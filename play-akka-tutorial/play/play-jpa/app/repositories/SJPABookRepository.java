package repositories;

import models.Book;
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
public class SJPABookRepository implements SBookRepository{

    private final JPAApi jpaApi;

    @Inject
    public SJPABookRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Book add(Book book) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            em.getTransaction().begin();
            //insert(em, book);
            em.persist(book);
            em.getTransaction().commit();
            em.close();
        });
        return book;
    }

    @Override
    public Stream<Book> list() {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return list(em);
        });
        return null;
    }

    @Override
    public Boolean delete(long id) {
        return null;
    }

    private  <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Book insert(EntityManager em, Book book) {
        em.persist(book);
        return book;
    }

    private boolean remove(EntityManager em, long id) {
        try {
            em.remove(id);
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
