package tablealias.aop;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public aspect GsonWrapperAspect {	
	
	private static final Logger logger = Logger.getLogger(GsonWrapperAspect.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	pointcut toJsonExec(Object obj) : call(String Gson.toJson(Object)) && args(obj);
	
	
	String around(Object obj): toJsonExec(obj){
		String json = null;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return json;
	}

}
