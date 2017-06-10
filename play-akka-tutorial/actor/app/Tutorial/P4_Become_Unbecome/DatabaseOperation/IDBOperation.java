package Tutorial.P4_Become_Unbecome.DatabaseOperation;

/**
 * Created by lam.nm on 6/10/2017.
 */
public interface IDBOperation {
    public void create(User user);

    public void update(User user) throws Exception;

    public User read(String username) throws Exception;

    public void delete(String username) throws Exception;

}
