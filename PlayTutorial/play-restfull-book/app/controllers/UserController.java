package controllers;


import com.google.inject.Inject;
import model.User;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.UserRepository;
import services.UserService;

/**
 * Created by lamdevops on 6/24/17.
 */
public class UserController extends Controller{

    private final UserRepository userRepository;

    @Inject
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Result addUser() {
        User user = new User.UserBuilder()
                .setName("Lam")
                .setEmail("nguyenlam@gmail.com")
                .setAddress("521/64 Binh Duong 1, Binh Duong")
                .createBuilder();
        userRepository.insert(user);
        return ok(user.toString());
    }
}
