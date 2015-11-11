package tablealias.aop;

import org.apache.log4j.Logger;

import tablealias.aop.helper.CustomResponseWrap;
import tablealias.aop.helper.ResultAware;
import tablealias.web.ResponseFactory;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.Result;

public aspect ResponseWrapAspect percflow(controllerExec()){

	Logger logger = Logger.getLogger(ResponseWrapAspect.class);
	
	private CustomResponse finalResponse;
	
	private ResultAware resultAware;

	pointcut serviceCall(): call( Result+ tablealias.service.*.*(..)) ;

	pointcut controllerExec(): execution(@CustomResponseWrap CustomResponse tablealias.web.controller.*.*(..));
	
	pointcut methodReturningResult(): cflow(controllerExec()) && serviceCall() && !cflowbelow(serviceCall()) && !within(ResponseWrapAspect+);

	declare soft : Exception : serviceCall();

	after() returning(Result res):  methodReturningResult() {
		if (res.isExito()) {
			logger.debug("result " +  res + " is ok.");
			finalResponse = ResponseFactory.successfulResponse(res.getResult());
			if(resultAware != null){
				logger.debug("invoking ResultAware");
				resultAware.successfulResult(finalResponse, res);
			}
			else
				logger.debug("ResultAware is null!!");
		} else {
			finalResponse = ResponseFactory.unsuccessfulResponse(res
					.getErrorMsg());
		}
	}

	/**
	 * Code that will be invoked upon successful result from service.
	 * @param resultAware
	 */
	public <T extends Result> void setResultAwareCallBack(ResultAware<T> resultAware) {
		this.resultAware = resultAware;		
	}

	CustomResponse around(): controllerExec() {
		try {
			proceed();
		} catch (Exception e) {
			String msg = e.getCause() == null ? e.getMessage() : e.getCause()
					.getMessage();
			return ResponseFactory.unsuccessfulResponse(msg);
		}
		return finalResponse;
	}
}
