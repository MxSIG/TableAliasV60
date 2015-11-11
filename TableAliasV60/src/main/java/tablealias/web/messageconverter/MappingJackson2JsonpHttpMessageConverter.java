package tablealias.web.messageconverter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MappingJackson2JsonpHttpMessageConverter extends
	MappingJackson2HttpMessageConverter {

    private static final String DEFAULT_CALLBACK_PARAMETER = "callback";

    private static final Charset charset = Charset.forName("ISO-8859-1");

    private static final List<MediaType> SUPPORTED_MEDIA_TYPES = new ArrayList<MediaType>() {
	{
	    add(new MediaType("application", "x-javascript", charset));
	    add(new MediaType("application", "javascript", charset));
	    add(new MediaType("text", "javascript", charset));
	}
    };

    public MappingJackson2JsonpHttpMessageConverter() {
	setSupportedMediaTypes(SUPPORTED_MEDIA_TYPES);
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
	    throws IOException, HttpMessageNotWritableException {
	JsonGenerator jsonGenerator = getJsonGenerator(outputMessage);

	try {
	    String callbackParam = getRequestParam(DEFAULT_CALLBACK_PARAMETER);

	    if (callbackParam == null || callbackParam.isEmpty()) {
		callbackParam = DEFAULT_CALLBACK_PARAMETER;
	    }

	    jsonGenerator.writeRaw(callbackParam);
	    jsonGenerator.writeRaw(" (");
	    getObjectMapper().writeValue(jsonGenerator, object);
	    jsonGenerator.writeRaw(");");
	    jsonGenerator.flush();
	} catch (JsonProcessingException ex) {
	    throw new HttpMessageNotWritableException("Could not write JSON:"
		    + ex.getMessage(), ex);
	}
    }

    private JsonGenerator getJsonGenerator(HttpOutputMessage outputMessage)
	    throws IOException {
	JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders()
		.getContentType());
	return getObjectMapper().getJsonFactory().createJsonGenerator(
		outputMessage.getBody(), encoding);
    }

    /**
     * Returns given parameter from servlet request.
     * 
     * @param paramName
     *            Name of the param
     */
    private String getRequestParam(String paramName) {
	return getServletRequest().getParameter(paramName);
    }

    /**
     * Returns current servlet request.
     */
    private HttpServletRequest getServletRequest() {
	return ((ServletRequestAttributes) RequestContextHolder
		.currentRequestAttributes()).getRequest();
    }
}
