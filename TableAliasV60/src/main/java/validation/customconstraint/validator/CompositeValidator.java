package validation.customconstraint.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import validation.customconstraint.DtoValidation;
import validation.customconstraint.validator.strategy.CompositeValidationStrategy;
import validation.customconstraint.validator.strategy.DisjunctionCompositeValidationStrategy;

/**
 * Validator subclass that handles one or more validations.
 * 
 * @author INEGI
 * 
 * @param <T>
 * @see DtoValidator
 */
public class CompositeValidator<T> extends DtoValidator<T> {

	private List<DtoValidation<T>> validations = new ArrayList<DtoValidation<T>>();

	// default strategy
	private CompositeValidationStrategy<T> validationStrategy = new DisjunctionCompositeValidationStrategy<T>();

	@Override
	boolean isValidObject(T obj) {
		return validationStrategy.validate(validations, obj);
	}

	public boolean addValidator(DtoValidation<T> validator) {
		return validations.add(validator);
	}

	public void setValidationStrategy(
			CompositeValidationStrategy<T> validationStrategy) {
		this.validationStrategy = validationStrategy;
	}

	@Override
	void onValidationFailure(T obj, ConstraintValidatorContext context) {
		validationStrategy.onValidationFailure(obj, validations, context);
	}

}
