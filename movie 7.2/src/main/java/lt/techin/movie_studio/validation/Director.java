package lt.techin.movie_studio.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DirectorValidator.class)
public @interface Director {

  String message() default "director field cannot be null. The first letter should be capital followed by the lowercase letters. length range 2-70 characters";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
