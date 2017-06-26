package repositories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.runAsync;

/**
 * Created by lamdevops on 6/24/17.
 */
public abstract class JpaRepositoryImpl<T, ID extends Serializable> implements JpaRepository<T, ID> {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JpaRepositoryImpl(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public T insert(T t) {
       jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return this.insert(em, t);
        });
       return null;
    }

    @Override
    public T update(T t) {
        return null;
    }

    @Override
    public T findOne(T t) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return this.findOne(id);
    }

    @Override
    public Stream<T> findAll() {
        return null;
    }

    @Override
    public Stream<T> findAll(List list) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return this.listAll(em);
        });
    }

    @Override
    public void delete(T t) {
        runAsync(() -> jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            delete(em, t);
        }), executionContext);
    }

    @Override
    public void delete(ID id) {

    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);

    }

    private <T> T insert(EntityManager em, T t) {
        em.persist(t);
        return t;
    }

    private T update(EntityManager em, T t) {
        em.persist(t);
        return t;
    }

    private T findOne(EntityManager em, ID id) {
        return em.createQuery("select t from " + getGenericClass() + " t where t.id = " + id, getGenericClass()).getSingleResult();
    }

    private Stream<T> listAll(EntityManager em) {

        List<T> results = em.createQuery("select t from " + getGenericClass() + " t", getGenericClass()).getResultList();
        return results.stream();
    }

    private void delete(EntityManager em, T t) {
        em.remove(t);

    }

    private void delete(EntityManager em, ID id) {
        em.createQuery("delete from " + getGenericClass() + " t where t.id = " + id, getGenericClass()).executeUpdate();
    }

    public Class<T> getGenericClass() {
        Class<T> result = null;
        Type type = this.getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type[] fieldArgTypes = pt.getActualTypeArguments();
            result = (Class<T>) fieldArgTypes[0];
        }
        return result;
    }

}
