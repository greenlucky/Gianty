package models;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by lamdevops on 7/15/17.
 */
@Entity
public class UserRole extends BaseModel {

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;

    @WhenCreated
    private long createdDate;

    @WhenModified
    private long modifiedDate;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                "user=" + user.getId() +
                ", role=" + role.getName() +
                ", CreatedDate=" + createdDate+
                ", ModifiedDate=" + modifiedDate +
                '}';
    }
}
