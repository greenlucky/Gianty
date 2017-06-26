package Tutorial.P3_ActorTool.S4_FSM_State.Buncher;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lamdevops on 6/17/17.
 */
public class Message {

    public static final class SetTarget {

        private final ActorRef ref;

        public SetTarget(ActorRef ref) {
            this.ref = ref;
        }


        public ActorRef getRef() {
            return ref;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SetTarget setTarget = (SetTarget) o;

            return ref != null ? ref.equals(setTarget.ref) : setTarget.ref == null;
        }

        @Override
        public int hashCode() {
            return ref != null ? ref.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "SetTarget{" +
                    "ref=" + ref +
                    '}';
        }
    }

    public static final class Queue {
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

    public static final class Batch {
        private final List<Object> list;

        public Batch(List<Object> list) {
            this.list = list;
        }

        public List<Object> getList() {
            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Batch batch = (Batch) o;

            return list != null ? list.equals(batch.list) : batch.list == null;
        }

        @Override
        public int hashCode() {
            return list != null ? list.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Batch{" +
                    "list=" + list +
                    '}';
        }
    }

    public interface Data {
    }

    public static final class Todo implements Data{
        private final ActorRef target;
        private final List<Object> queue;

        public Todo(ActorRef target, List<Object> queue) {
            this.target = target;
            this.queue = queue;
        }

        public ActorRef getTarget() {
            return target;
        }

        public List<Object> getQueue() {
            return queue;
        }

        @Override
        public String toString() {
            return "Todo{" +
                    "target=" + target +
                    ", queue=" + queue +
                    '}';
        }

        public Todo addElement(Object element) {
            List<Object> nQueue = new LinkedList<>(queue);
            nQueue.add(element);
            return new Todo(this.target, nQueue);
        }

        public Todo copy(List<Object> queue) {
            return new Todo(this.target, queue);
        }

        public Todo copy(ActorRef target) {
            return new Todo(target, this.queue);
        }
    }



    public static enum State {
        Idle, Active
    }

    public static enum Flush {
        Flush
    }

    public static enum Uninitialized implements Data{
        Uninitialized
    }



    public static class Probe extends UntypedActor {
        private final LoggingAdapter logger = Logging.getLogger( getContext().system(), this );
        @Override
        public void onReceive(Object message) throws Throwable {
            logger.info((String) message);
        }
    }

}
