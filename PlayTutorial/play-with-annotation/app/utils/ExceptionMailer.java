package utils;

/**
 * Created by lamdevops on 7/22/17.
 */
public class ExceptionMailer {
    public static void send(Throwable e) {
        System.out.println(String.format("Sending email containing exception ", e));
    }
}
