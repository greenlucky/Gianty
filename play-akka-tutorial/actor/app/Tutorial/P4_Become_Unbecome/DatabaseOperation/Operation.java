package Tutorial.P4_Become_Unbecome.DatabaseOperation;

import java.util.Optional;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class Operation {

    public static class Connect {};
    public static class DisConnect {};

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
}
