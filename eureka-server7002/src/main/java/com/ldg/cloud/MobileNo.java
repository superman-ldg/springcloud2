package com.ldg.cloud;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MobileNoValidator.class}
)

public @interface MobileNo {

    String message() default "手机号格式不正确";

    boolean nullable() default false;

    boolean blankable() default false;

    Class<?>[] groups() default {};

     Class<? extends Payload> [] payload() default {};

}
