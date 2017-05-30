package Alarm;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by greenlucky on 5/31/17.
 */
public class AlarmTest {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("lamdevops");
        ActorRef alarm = system.actorOf(Alarm.props("76543218"), "alarm");

        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("dogs"), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("76543218"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("cat"), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("76543218"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
    }

}