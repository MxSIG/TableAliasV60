package tablealias.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

public class BaseServlet {

    private ServletContext servletContext;
    private HttpServletResponse response;
    private ServletWebRequest webRequest;

    public ServletContext getServletContext() {
	return servletContext;
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
	this.servletContext = servletContext;
    }

    public HttpServletResponse getResponse() {
	return null;
    }

    //
    // public void setResponse(HttpServletResponse response) {
    // this.response = response;
    // }

    public HttpServletRequest getCurrentRequest() {
	ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder
		.currentRequestAttributes();
	return ra.getRequest();
    }

    public ServletWebRequest getWebRequest() {
	return webRequest;
    }

}
