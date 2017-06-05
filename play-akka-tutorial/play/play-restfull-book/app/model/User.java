package model;


import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by greenlucky on 6/3/17.
 */
public class User{

    @MongoId
    @MongoObjectId
    private Long id;

    private String email;

    private String name;

    private long birthday;

    private String address;

    public User() {
    }

    public User(Long id, String email, String name, long birthday, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class UserBuilder {
        private Long id;

        private String email;

        private String name;

        private long birthday;

        private String address;

        public UserBuilder() {
        }

        public UserBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setBirthday(long birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public User createBuilder() {
            return new User(id, email, name, birthday, address);
        }

        @Override
        public String toString() {
            return "UserBuilder{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", birthday=" + birthday +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
