package nosi.core.validator.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import nosi.core.validator.MessageValidator;
import nosi.core.validator.NegativeValidator;

/**
 * emerson
 * 26/07/2019
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NegativeValidator.class)
@Documented
public @interface Negative {
	
	String message() default MessageValidator.MESSAGE_NEGATIVE;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
 