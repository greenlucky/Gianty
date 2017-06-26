package Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import static Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.Operator.*;

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
        User user =  new User("lam", "nguyenlamit86@gmail.com");
        userStorage.tell(Connect, ActorRef.noSender());
        userStorage.tell(new Operation(dbOperation,  Optional.of(user)), ActorRef.noSender());
        userStorage.tell(DisConnect, ActorRef.noSender());

        Thread.sleep(100);

        system.terminate();

    }

    /**
     * This example change order of tell Operation before of IOperator connect
     *
     */
    @Test
    public void TestUserStorageWithStash() throws Exception {

        ActorRef userStorage = system.actorOf(Props.create(UserStorageActor.class), "user-storage");
        DBOperation dbOperation = new DBOperation();
        User user =  new User("lam", "nguyenlamit86@gmail.com");
        userStorage.tell(new Operation(dbOperation,  Optional.of(user)), ActorRef.noSender());
        userStorage.tell(Connect, ActorRef.noSender());
        userStorage.tell(DisConnect, ActorRef.noSender());

        Thread.sleep(100);

        system.terminate();
    }

}