/**
 * 
 */
package tablealias.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.service.ImssService;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Controller
@RequestMapping("/imss")
public class ImssController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ImssService service;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CustomResponse process(@RequestParam String point,
			final HttpServletRequest request) {
		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		Server server = ts.getFoundServer();
		CustomResponse response;
		try {
			Object result = service.georeferencing(point, server);
			if (result == null) {
				String error = "Domicilio fuera de los límites de la manzana.";
				response = ResponseFactory.unsuccessfulResponse(error);
			} else {
				response = ResponseFactory.successfulResponse(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			String error = "Internal Server Error!";
			response = ResponseFactory.unsuccessfulResponse(error);
		}
		return response;
	}

}
