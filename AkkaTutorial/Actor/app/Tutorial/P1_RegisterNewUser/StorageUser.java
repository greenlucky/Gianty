package Tutorial.P1_RegisterNewUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 6/4/17.
 */
public class StorageUser{
    private List<User> lstUsers;

    public StorageUser() {
        lstUsers = new ArrayList<>();
    }

    public void addUser(User user) {
        lstUsers.add(user);
    }
}
