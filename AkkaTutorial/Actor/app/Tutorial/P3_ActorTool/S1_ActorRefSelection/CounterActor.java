package Tutorial.P3_ActorTool.S1_ActorRefSelection;

import akka.actor.UntypedActor;

/**
 * Created by greenlucky on 6/4/17.
 */
public class CounterActor extends UntypedActor {

    private int counter = 0;

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof Counter.Inc)
            counter += ((Counter.Inc) message).getX();
        else if(message instanceof Counter.Dec)
            counter -= ((Counter.Dec) message).getX();
    }

    public static class Counter {
        public final class Inc {
            private int x;

            public Inc(int x) {
                this.x = x;
            }

            public int getX() {
                return x;
            }
        }

        public final class Dec {
            private int x;

            public Dec(int x) {
                this.x = x;
            }

            public int getX() {
                return x;
            }
        }
    }
}



