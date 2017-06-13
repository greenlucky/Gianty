package Tutorial.P3_ActorTool.FSM;

import java.util.List;

/**
 * Created by lam.nm on 6/12/2017.
 */
public final class Batch {
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
