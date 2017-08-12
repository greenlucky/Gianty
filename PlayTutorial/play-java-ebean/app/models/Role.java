package models;

import io.ebean.Finder;
import io.ebean.annotation.NotNull;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by lamdevops on 7/15/17.
 */
@Entity
public class Role extends BaseModel {

    @Constraints.MaxLength(30)
    @NotNull
    private String name;

    @NotNull
    @ManyToOne
    private RoleType roleType;


    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    private Finder<Long, Role> finder = new Finder<>(Role.class);

    public Role findById(long id) {
        return finder.byId(id);
    }

    public Role findByName(String name) {
        return finder.query().where().eq("name", name).findUnique();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return id == role.id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleType=" + roleType +
                ", finder=" + finder +
                '}';
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public List<Role> list() {
        return finder.all();
    }
}
