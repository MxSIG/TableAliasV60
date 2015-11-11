package tablealias.aop.helper;

import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.Result;

/**
 * Interface that 'signals' when a Result is successful
 * 
 * @see Result
 * 
 * @author INEGI
 * 
 * @param <T>
 *            The result implementation.
 * 
 */
public interface ResultAware<T extends Result> {

	void successfulResult(CustomResponse response, T result);

}
