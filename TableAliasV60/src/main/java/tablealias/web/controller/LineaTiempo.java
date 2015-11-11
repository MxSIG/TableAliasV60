package tablealias.web.controller;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.aop.helper.CustomResponseWrap;
import tablealias.input.LineaTiempoInput;
import tablealias.service.LineaTiempoService;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.LineaTiempoResult;

@Controller
@RequestMapping("/lineatiempo")
public class LineaTiempo {

	@Autowired
	LineaTiempoService service;

	private ServletContext servletContext;

	@CustomResponseWrap
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	CustomResponse lineaTiempo(@Valid @RequestBody LineaTiempoInput in) {
		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		LineaTiempoResult result = service.consultaLineaTiempo(in, ts);
		return null;
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
