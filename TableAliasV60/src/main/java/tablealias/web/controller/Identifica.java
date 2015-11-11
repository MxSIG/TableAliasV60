package tablealias.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.aop.helper.CustomResponseWrap;
import tablealias.dto.IdentificaDto;
import tablealias.service.IdentificaService;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.IdentificaResult;

@Controller
@RequestMapping("/identifica")
public class Identifica {

	private ServletContext servletContext;

	@Autowired
	private IdentificaService service;

	@CustomResponseWrap
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	CustomResponse process(@Valid @RequestBody IdentificaDto dto,
			HttpServletRequest request) {
		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		IdentificaResult result = service.identifica(dto, ts);
		return null;
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
