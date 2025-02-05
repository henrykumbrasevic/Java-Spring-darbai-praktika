package lt.techin.movie_studio.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DirectorValidator implements ConstraintValidator<Director, String> {

  @Override
  public boolean isValid(String director, ConstraintValidatorContext constraintValidatorContext) {
    return director != null && director.matches("^[A-Z][a-z]+$") && director.length() > 2 && director.length() < 50;
  }
}
