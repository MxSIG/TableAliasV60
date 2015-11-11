package tablealias.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring filter that adds headers to response as needed by CORS specification.
 * 
 * @author INEGI
 * 
 */
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest _request = (HttpServletRequest) request;
		HttpServletResponse _response = (HttpServletResponse) response;
		if (_request.getMethod().equals("POST")
				|| _request.getMethod().equals("GET")
				|| _request.getMethod().equals("DELETE")
				|| _request.getMethod().equals("PUT")
				|| _request.getMethod().equals("OPTIONS")) {
			// if (originToEcho != null)
			_response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (_request.getHeader("Access-Control-Request-Method") != null) {
			_response.addHeader("Access-Control-Allow-Headers",
					"Origin, Content-Type, Accept, X-Requested-With");
			_response.addHeader("Access-Control-Max-Age", "60");
			// response.addHeader("Access-Control-Allow-Credentials", "true");
			_response.addHeader("Access-Control-Allow-Methods",
					"GET, POST, PUT, DELETE");
		}

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	// public void setOriginMaker(AllowedOriginMaker originMaker) {
	// this.originMaker = originMaker;
	// }

}
