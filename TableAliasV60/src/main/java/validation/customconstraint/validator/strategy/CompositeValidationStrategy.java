package validation.customconstraint.validator.strategy;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import validation.customconstraint.DtoValidation;

/**
 * Strategy interface that will allow to have different types of validations
 * when using CompositeValidator.
 * 
 * @author INEGI
 * 
 */
public interface CompositeValidationStrategy<T> {
	boolean validate(List<DtoValidation<T>> validations, T obj);

	void onValidationFailure(T obj, List<DtoValidation<T>> validations,
			ConstraintValidatorContext context);

}
