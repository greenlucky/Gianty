package models;

import io.ebean.annotation.NotNull;

import javax.persistence.Entity;

/**
 * Created by lamdevops on 7/15/17.
 */
@Entity
public class RoleType extends BaseModel{

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleType{" +
                "name='" + name + '\'' +
                '}';
    }
}
