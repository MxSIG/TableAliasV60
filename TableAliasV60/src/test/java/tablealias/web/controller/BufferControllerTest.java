package tablealias.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import org.junit.Test;
import org.springframework.http.MediaType;

public class BufferControllerTest extends BaseControllerTest {

    @Test
    public void createBuffer() throws Exception {
	MediaType JSONP = new MediaType("application", "javascript",
		Charset.forName("ISO-8859-1"));
	mockMvc.perform(
		post("/buffer").param("gid", "14072").param("proyName", "mdm5")
			.param("size", "0").param("tabla", "f9041"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(JSONP));
    }

    @Test
    public void createBufferGZipRequest() throws Exception {
	MediaType JSONP = new MediaType("application", "javascript",
		Charset.forName("ISO-8859-1"));
	mockMvc.perform(
		post("/buffer").param("gid", "14072").param("proyName", "mdm5")
			.param("size", "0").param("tabla", "f9041").header("Accept-Encoding", "gzip"))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(JSONP));
    }

}
