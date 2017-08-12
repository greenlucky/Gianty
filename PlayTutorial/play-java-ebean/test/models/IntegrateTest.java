package models;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by lamdevops on 7/15/17.
 */
public class IntegrateTest extends AbstractTest {

    @Test
    public void addPerson() throws Exception {

        User person = new User("Lam Nguyen Mai");
        person.save();

        Assert.assertEquals(3, person.getId());
        System.out.println(person);

        User actualPerson = person.findByName("Lam Nguyen Mai");

        Assert.assertEquals("Lam Nguyen Mai", actualPerson.getName());
    }

    @Test
    public void addRole() throws Exception {
        Role role1 = new Role("admin");
        role1.save();

        Role role2 = new Role("user");
        role2.save();

        Role actualRole = role1.findById(1L);
        Assert.assertEquals(actualRole, role1);
    }

    @Test
    public void addUserRole() throws Exception {

        User user = new User().findById(1);
        Role roleAdmin = new Role().findById(1);
        Role roleUser = new Role().findById(2);

        UserRole userRole = new UserRole(user, roleAdmin);
        userRole.save();
        System.out.println(userRole.toString());

        userRole.setRole(roleUser);
        userRole.save();
        System.out.println(userRole.toString());
    }
}