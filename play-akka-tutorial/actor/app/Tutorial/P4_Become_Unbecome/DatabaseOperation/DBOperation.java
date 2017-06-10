package Tutorial.P4_Become_Unbecome.DatabaseOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lam.nm on 6/10/2017.
 */
public class DBOperation implements IDBOperation{

    private List<User> lstUser;

    public DBOperation() {
        this.lstUser = new ArrayList<>();
    }

    @Override
    public void create(User user) {
        lstUser.add(user);
    }

    @Override
    public void update(User user) throws Exception {
        if(lstUser.contains(user)) {
             int userIndex = lstUser.indexOf(user);
             lstUser.set(userIndex, user);
        } else {
            throw new Exception("User " + user.toString() + " not found!!!");
        }
    }

    @Override
    public User read(String username) throws Exception {
        if(lstUser.contains(username)) {
            int userIndex = lstUser.indexOf(username);
            return lstUser.get(userIndex);
        } else {
            throw new Exception("User " + username + " not found!!!");
        }
    }

    @Override
    public void delete(String username) throws Exception {
        if(lstUser.contains(username)) {
            int userIndex = lstUser.indexOf(username);
            lstUser.remove(userIndex);
        } else {
            throw new Exception("User " + username + " not found!!!");
        }
    }
}
