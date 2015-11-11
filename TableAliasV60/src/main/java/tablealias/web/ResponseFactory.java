package tablealias.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class ResponseFactory {

	static public class CustomResponse {

		@JsonProperty
		private Response response = new Response();

		@JsonProperty
		private Map<String, Object> data = new LinkedHashMap<String, Object>();

		{
			data.put("value", null);
		}

		public CustomResponse(Object response) {
			modifyField("value", response);
		}

		public CustomResponse() {
		}

		private void modifyField(String field, Object value) {
			if (data.containsKey(field))
				data.put(field, value);
		}

		@SuppressWarnings("unused")
		private Object removeField(String field, Object value) {
			if (data.containsKey(field))
				return data.remove(field);
			return null;
		}

		public CustomResponse addField(String name, Object val) {
			data.put(name, val);
			return this;
		}

		public void setResponseSuccess(boolean success) {
			response.setSuccess(success);
		}

		public void setResponseMessage(List<String> message) {
			response.setMessage(message);
		}

		@Override
		public String toString() {
			return "success is " + response.isSuccess() + ", data = "
					+ data.size();
		}

	}

	public static CustomResponse successfulResponse(Object object) {
		CustomResponse i = new ResponseFactory.CustomResponse(object);
		i.setResponseSuccess(true);
		return i;
	}

	public static CustomResponse unsuccessfulResponse(List<String> errors) {
		CustomResponse i = new ResponseFactory.CustomResponse();
		i.setResponseMessage(errors);
		i.setResponseSuccess(false);
		return i;
	}

	public static CustomResponse unsuccessfulResponse(String error) {
		List<String> errors = new ArrayList<String>();
		errors.add(error);
		return unsuccessfulResponse(errors);
	}
}
