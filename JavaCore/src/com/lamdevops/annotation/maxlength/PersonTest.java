package com.lamdevops.annotation.maxlength;

import org.junit.Test;

/**
 * Created by lam.nm on 6/27/2017.
 */
public class PersonTest {

    @Test
    public void testPerson() throws  Exception {

        MaxLengthHandler parser = new MaxLengthHandler();
        Person person = new Person();
        person.setName("Nguyen Mai Lam");
        parser.handle(person);

    }
}
