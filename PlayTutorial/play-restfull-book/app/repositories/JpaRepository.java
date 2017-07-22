package repositories;

import com.google.inject.ImplementedBy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by lam.nm on 6/24/2017.
 */
@ImplementedBy(JpaRepositoryImpl.class)
public interface JpaRepository<T, ID extends Serializable> {

    T insert(T t);


    T update(T t);

    T findOne(T t);

    T findOne(ID id);

    Stream<T> findAll();

    Stream<T> findAll(List<ID> ids);

    void delete(T t);

    void delete(ID id);
}
