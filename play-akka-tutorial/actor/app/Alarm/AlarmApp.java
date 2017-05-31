package Alarm;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class AlarmApp {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("non-trust-worthy-child");
        final ActorRef alarm = system.actorOf(AlarmActor.props("123456"), "alarm-actor");
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("dog"), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("123456"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("cat"), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("123456"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
    }
}
