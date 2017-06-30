package com.lamdevops.annotation.maxlength;

import java.lang.annotation.*;

/**
 * Created by lam.nm on 6/27/2017.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {

    int maxLength();

}
