package tablealias.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

public class ActividatesAreasControllerTest extends BaseControllerTest {

    MockHttpServletResponse response = new MockHttpServletResponse();
    @Test
    public void testHandleLogin() throws Exception {	
	MediaType JSONP = new MediaType("application", "javascript",
		Charset.forName("ISO-8859-1"));
	mockMvc.perform(
		get(
			"/actividadesAreas/{areageo}/{actividad}/{saveitem}/{estratos}/{tabla}",
			"areageo", "actividad", "saveitem", "estratos",
			"pestab").contentType(MediaType.APPLICATION_JSON)
			.content("{}").accept(JSONP))
		.andExpect(status().isOk());

	/*
	 * .andExpect( request() .sessionAttribute(
	 * LoginController.ACCOUNT_ATTRIBUTE, this.account))
	 * .andExpect(redirectedUrl("/index.htm"));
	 */
    }

    @Test
    public void testTestMe() throws Exception {
	MediaType JSONP = new MediaType("application", "javascript",
		Charset.forName("ISO-8859-1"));
	mockMvc.perform(get("/actividadesAreas/testme")).andExpect(
		status().isOk());
    }
}
