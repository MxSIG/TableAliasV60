package validation.customconstraint.validator.strategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import validation.customconstraint.DtoValidation;

/**
 * Composite validation strategy that will be successful if at least one
 * validation is successful.
 * 
 * @author INEGI
 * 
 * @param <T>
 */
public class DisjunctionCompositeValidationStrategy<T> extends
		CompositeValidationStrategyWithDefaultFailureStrategy<T> {

	private List<DtoValidation<T>> unsuccesfulValidations = null;

	// private List<DtoValidation<T>> succesfulValidations = null;

	@Override
	public boolean validate(List<DtoValidation<T>> validations, T obj) {
		unsuccesfulValidations = new ArrayList<DtoValidation<T>>();
		boolean atLeastOneValidationSucceded = false;
		for (DtoValidation<T> validation : validations) {
			if (!validation.isValidObject(obj))
				unsuccesfulValidations.add(validation);
			else
				atLeastOneValidationSucceded = true;
		}
		return atLeastOneValidationSucceded;
	}

	@Override
	public void onValidationFailure(T obj, List<DtoValidation<T>> validations,
			ConstraintValidatorContext context) {
		for (DtoValidation<T> validation : unsuccesfulValidations) {
			Map<String, String> messages = new LinkedHashMap<String, String>();
			validation.getValidationMessages(obj, messages);
			failureStrategy.onFailure(obj, context, messages);
		}
	}

}
