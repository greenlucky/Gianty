package Tutorial.P1_RegisterNewUser;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 6/4/17.
 */
public class TalkToActor {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("talk-to-actor");

        //black list user
        List<User> blackList = Arrays.asList(
                new User("lam", "lam@gmail.com"));

        ActorRef checker = system.actorOf(Checker.props(blackList), "checker");

        ActorRef storage = system.actorOf(Props.create(Storage.class), "storage");

        ActorRef recorder = system.actorOf(Recorder.props(checker, storage), "recoder");

        recorder.tell(new NewUser(new User("lam", "lam@gmail.com")), recorder);
        recorder.tell(new NewUser(new User("men", "men@gmail.com")), recorder);
        recorder.tell(new NewUser(new User("em tu", "emtu@gmail.com")), recorder);
        recorder.tell(new NewUser(new User("thao", "thao@gmail.com")), recorder);

        Thread.sleep(100);

        system.terminate();

    }
}

