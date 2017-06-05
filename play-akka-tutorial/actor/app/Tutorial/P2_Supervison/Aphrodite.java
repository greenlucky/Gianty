package Tutorial.P2_Supervison;

/**
 * Created by greenlucky on 6/4/17.
 */
public class Aphrodite {
    public final static class ResumeException extends Exception {
        public ResumeException() {
        }
    }
    public static class StopException extends Exception {}
    public static class RestartException extends Exception {}
}
