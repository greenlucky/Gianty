package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.User;
import play.db.jpa.Transactional;
import repositories.UserRepository;

import java.util.stream.Stream;

/**
 * Created by lamdevops on 6/24/17.
 */
@Singleton
public class UserService {

    private final UserRepository userRepository;


    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User add(User user) {
        return userRepository.insert(user);
    }

    @Transactional
    public Stream<User> list() {
        return userRepository.findAll();
    }
}
