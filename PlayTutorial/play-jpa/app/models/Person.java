package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by greenlucky on 6/2/17.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    public Person() {
    }

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }



    public static class PersonBuilder {
        private long id;
        private String name;
        public PersonBuilder(){

        }

        public PersonBuilder(long id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public PersonBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Person createPerson() {
            return new Person(id, name);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
