package Alarm;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by lam.nm on 5/31/2017.
 */
public class AlarmActor extends UntypedActor {

    private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );

    private String password;

    public AlarmActor(String password) {
        this.password = password;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Alarm.Activity)
            onActivity( ((Alarm.Activity) message) );
        else if (message instanceof Alarm.Disable)
            onDisable( ((Alarm.Disable) message) );
        else onEnable( (Alarm.Enable) message );
    }

    private void onEnable(Alarm.Enable message) {
        if (password.equals( message.password )) {
            logger.info( "Alarm enable" );
        } else {
            logger.info( "Some one failed to enable the alarm" );
        }

    }

    private void onDisable(Alarm.Disable disable) {
        if (password.equals( disable.password )) {
            logger.info( "Alarm disable" );
        } else {
            logger.info( "Some one failed to disable the alarm" );
        }
    }

    private void onActivity(Alarm.Activity activity) {
        logger.info( "Oeoeoeoeoe, alarm alarm!!!" );
    }

    public static Props props(String password) {
        return Props.create( AlarmActor.class, password );
    }
}
