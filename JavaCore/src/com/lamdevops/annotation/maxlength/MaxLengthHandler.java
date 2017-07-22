package com.lamdevops.annotation.maxlength;

import java.lang.reflect.Field;

/**
 * Created by lam.nm on 6/27/2017.
 */
public class MaxLengthHandler {
    public void handle(Object ob) throws Exception {
        Field[] fields = ob.getClass().getFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(MaxLength.class)) {
                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                int ml = maxLength.maxLength();
                System.out.println("Max length is : " + ml);
                if (ml < field.get(ob).toString().length()) {
                    throw new Exception("Max length of " + field.getName() + " cannot over " + ml + " characters");
                }
            }
        }
    }
}
