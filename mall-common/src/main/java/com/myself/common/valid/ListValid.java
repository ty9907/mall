package com.myself.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { ListValidContraintValidtator.class})
public @interface ListValid {

    /**
     * @return the error message template
     */
    String message() default "{com.myself.common.valid.ListValid.message}";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] vals() default { };
}
