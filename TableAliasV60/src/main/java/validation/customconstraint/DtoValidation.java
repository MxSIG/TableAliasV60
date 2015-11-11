package validation.customconstraint;

import java.util.Map;

/**
 * Interface for custom Dto validation.
 * 
 * @author INEGI
 * 
 * @param <T>
 *            Dto under validation.
 */
public interface DtoValidation<T> {

	boolean isValidObject(T t);

	void getValidationMessages(T obj, Map<String, String> msgMap);

}
