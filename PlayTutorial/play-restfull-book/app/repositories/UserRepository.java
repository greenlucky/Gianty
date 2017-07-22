package repositories;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.User;
import play.db.jpa.JPAApi;

/**
 * Created by lamdevops on 6/24/17.
 */
@Singleton
public class UserRepository extends JpaRepositoryImpl<User, Long>{

    @Inject
    public UserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        super(jpaApi, executionContext);
    }
}
