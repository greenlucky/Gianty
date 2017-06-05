package Alarm;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by greenlucky on 5/31/17.
 */
public class Alarm extends AbstractLoggingActor{

    @Override
    public Receive createReceive() {
       return receiveBuilder()
                .match(Activity.class, this::onActivity)
                .match(Disable.class, this::onDisable)
                .match(Enable.class, this::onEnable).build();
    }

    public static class Activity {}

    public static class Password{
        public final String password;
        public Password(String password) {
            this.password = password;
        }
    }
    public static class Disable extends Password{
        public Disable(String password) {
            super(password);
        }
    }
    public static class Enable extends Password{
        public Enable(String password) {
            super(password);
        }
    }

    public static Props props(String password) {
        return Props.create(Alarm.class, password);
    }

    private String password;

    public Alarm(String password) {
        this.password = password;
    }

    private void onDisable(Disable disable) {
        if(password.equals(disable.password)) {
            log().info("Alarm disable");
        }else {
            log().info("Some one failed to disable the alarm");
        }

    }

    private void onEnable(Enable enable) {
        if(password.equals(enable.password)) {
            log().info("Alarm enable");
        } else {
            log().info("Some one failed to enable the alarm");
        }
    }

    private void onActivity(Activity activity) {
        log().warning("oeoeoeooe, alarm alarm!!!");
    }
}
