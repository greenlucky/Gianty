package Tutorial.P3_ActorTool.FSM;

/**
 * Created by lam.nm on 6/12/2017.
 */
public final class Queue {
    private final Object obj;

    public Queue(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "obj=" + obj +
                '}';
    }
}
