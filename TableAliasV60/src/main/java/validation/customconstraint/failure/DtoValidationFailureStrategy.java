package validation.customconstraint.failure;

import java.util.Map;

import javax.validation.ConstraintValidatorContext;

/**
 * Interface that will allow different ways of handling validation failures.
 * 
 * @author INEGI
 * 
 * @param <T>
 *            Dto under validation.
 */
public interface DtoValidationFailureStrategy<T> {
	void onFailure(T obj, ConstraintValidatorContext context,
			Map<String, String> validationMessages);
}
