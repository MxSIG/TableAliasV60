package validation.customconstraint.validator;

import javax.validation.ConstraintValidatorContext;

/**
 * Abstract base class for Dto validators.
 * 
 * @author INEGI
 * 
 * @param <T>
 *            Dto under validation.
 */
abstract class DtoValidator<T> {

	abstract boolean isValidObject(T obj);

	abstract void onValidationFailure(T obj, ConstraintValidatorContext context);

	public boolean validate(T obj, ConstraintValidatorContext context) {
		if (isValidObject(obj)) {
			return true;
		} else {
			onValidationFailure(obj, context);
			return false;
		}
	}

}
