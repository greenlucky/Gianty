package Tutorial.P4_Become_Unbecome.DatabaseOperation;

import Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Created by lam.nm on 6/10/2017.
 */
class UserStorageActorTest {

    private ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("db-operation");
    }

    @Test
    public void TestUserStorage() throws Exception {
        ActorRef userStorage = system.actorOf(Props.create(UserStorageActor.class), "user-storage");
        DBOperation dbOperation = new DBOperation();
        User user = new User("lam", "nguyenlamit86@gmail.com");
        userStorage.tell(Operator.Connect.class, ActorRef.noSender());
        userStorage.tell(new Operation(dbOperation, Optional.of(user)), ActorRef.noSender());

    }
}