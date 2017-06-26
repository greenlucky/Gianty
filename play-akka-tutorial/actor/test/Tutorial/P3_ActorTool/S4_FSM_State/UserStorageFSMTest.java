package Tutorial.P3_ActorTool.S4_FSM_State;

import Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.DBOperation;
import Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.Operation;
import Tutorial.P3_ActorTool.S3_Become_Unbecome.DatabaseOperation.User;
import Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.UserStorageFSM;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static Tutorial.P3_ActorTool.S4_FSM_State.UserStorage.UserStorageFSM.Operator.*;



/**
 * Created by lamdevops on 6/11/17.
 */
class UserStorageFSMTest {

    private ActorSystem system;

    @BeforeEach
    public void init() throws Exception {
        system = ActorSystem.create("fsm-state");
    }

    @Test
    public void fmsTest() throws Exception {
        ActorRef userStorage = system.actorOf(Props.create(UserStorageFSM.class), "user-storage-fsm");

        User user =  new User("lam", "nguyenlamit86@gmail.com");

        userStorage.tell(Connect, ActorRef.noSender());
        userStorage.tell(new Operation(new DBOperation(), Optional.of(user)), ActorRef.noSender());
        userStorage.tell(Disconnect, ActorRef.noSender());

        Thread.sleep(1000);

        system.terminate();
    }

}