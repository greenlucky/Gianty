package Tutorial.P1_RegisterNewUser;

/**
 * Created by greenlucky on 6/4/17.
 */
public class UserMsg {

    private User user;

    public UserMsg(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "user=" + user +
                '}';
    }
}
