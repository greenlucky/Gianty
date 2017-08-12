package models;

import io.ebean.Finder;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.WhenCreated;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

/**
 * Created by lamdevops on 7/15/17.
 */
@Entity
public class User extends BaseModel {

    @Column(length = 50, nullable = false)
    @Constraints.MaxLength(50)
    @Constraints.MinLength(value = 6, message = "Name can not less than 6 characters")
    @NotNull
    private String name;

    @WhenCreated
    private long createDate;

    public User(String name, long createDate) {
        this.name = name;
        this.createDate = createDate;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public static final Finder<Long, User> finder = new Finder<>(User.class);


    public User findById(long id) {
        return finder.byId(id);
    }

    public User findByName(String name) {
        return finder.query().where().eq("name", name).findUnique();
    }

    public List<User> list() {
        return finder.all();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User person = (User) o;

        return id == person.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
