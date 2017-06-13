package Tutorial.P3_ActorTool.FSM;

import akka.actor.ActorRef;

/**
 * Created by lam.nm on 6/12/2017.
 */
public final class SetTarget {
    private final ActorRef ref;


    public SetTarget(ActorRef ref) {
        this.ref = ref;
    }

    public ActorRef getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "SetTarget{" +
                "ref=" + ref +
                '}';
    }
}
