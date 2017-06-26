package Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation;

import java.util.Optional;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class Operation {

    private DBOperation dbOperation;

    private User user;

    public Operation(DBOperation dbOperation, Optional<User> user) {
        this.dbOperation = dbOperation;
        this.user = (user.isPresent())?user.get(): null;
    }

    public DBOperation getDbOperation() {
        return dbOperation;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "dbOperation=" + dbOperation +
                ", user=" + user +
                '}';
    }
}
