package tablealias.web.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/spring/web-context.xml",
	"classpath:/META-INF/spring/applicationContext.xml" })
// @TestExecutionListeners({ DependencyInjectionTestExecutionListener.class})
public class BaseControllerTest {

    protected MockMvc mockMvc;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init() {
	mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//	request = new MockHttpServletRequest();
//	response = new MockHttpServletResponse();
    }

}
