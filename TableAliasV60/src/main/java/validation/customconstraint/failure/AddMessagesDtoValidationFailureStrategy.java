package validation.customconstraint.failure;

import java.util.Map;

import javax.validation.ConstraintValidatorContext;

/**
 * Failure strategy that adds messages to ConstraintValidatorContext.
 * 
 * @author INEGI
 * 
 * @param <T>
 *            Dto under validation.
 */
public class AddMessagesDtoValidationFailureStrategy<T> implements
		DtoValidationFailureStrategy<T> {

	@Override
	public void onFailure(T obj, ConstraintValidatorContext context,
			Map<String, String> validationMessages) {
		for (Map.Entry<String, String> e : validationMessages.entrySet()) {
			context.buildConstraintViolationWithTemplate(e.getValue())
					.addPropertyNode(e.getKey()).addConstraintViolation();
		}
	}
}
