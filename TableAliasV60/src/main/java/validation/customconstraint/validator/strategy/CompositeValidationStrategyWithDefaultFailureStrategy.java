package validation.customconstraint.validator.strategy;

import validation.customconstraint.failure.AddMessagesDtoValidationFailureStrategy;
import validation.customconstraint.failure.DtoValidationFailureStrategy;

/**
 * Abstract class for composite validations with default failure implementation
 * set to {@link AddMessagesDtoValidationFailureStrategy}.
 * 
 * @author INEGI
 * 
 * @param <T>
 */
public abstract class CompositeValidationStrategyWithDefaultFailureStrategy<T>
		implements CompositeValidationStrategy<T> {

	protected DtoValidationFailureStrategy<T> failureStrategy = new AddMessagesDtoValidationFailureStrategy<T>();

	/**
	 * Setter for providing a different DtoValidationFailureStrategy in case
	 * default behavior is not enough.
	 * 
	 * @param failureStrategy
	 */
	public void setDtoValidationWithFailureStrategy(
			DtoValidationFailureStrategy<T> failureStrategy) {
		this.failureStrategy = failureStrategy;
	}

}
