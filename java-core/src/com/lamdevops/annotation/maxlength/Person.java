package com.lamdevops.annotation.maxlength;

/**
 * Created by lam.nm on 6/27/2017.
 */
public class Person {

    @MaxLength(maxLength = 10)
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


