package repositories;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lam.nm on 6/24/2017.
 */
public interface JpaRepository<T, ID extends Serializable> {

    T insert(T t);

    T update(T t);

    T findOne(T t);

    T findOne(ID id);

    List<T> findAll();

    List<T> findAll(List<ID> ids);

    void delete(T t);

    void delete(ID id);
}
