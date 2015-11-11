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

import tablealias.aop.ResponseWrapAspect;
import tablealias.aop.helper.CustomResponseWrap;
import tablealias.aop.helper.ResultAware;
import tablealias.dto.ConsultaDto;
import tablealias.service.ConsultaService;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.ConsultaResult;

@Controller
@RequestMapping("/consulta")
public class Consulta {

	@Autowired
	ConsultaService consultaService;
	private ServletContext servletContext;

	@CustomResponseWrap
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	CustomResponse doConsulta(@Valid @RequestBody ConsultaDto dto,
			final HttpServletRequest request) {
		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		ResponseWrapAspect aspect = ResponseWrapAspect.aspectOf();
		aspect.setResultAwareCallBack(new ResultAware<ConsultaResult>() {

			@Override
			public void successfulResult(CustomResponse response,
					ConsultaResult result) {
				request.setAttribute("exito", result.isExito());
				response.addField("tabla", result.getTabla())
						.addField("totalFields", result.getTotalFields())
						.addField("typeFields", result.getTypeFields())
						.addField("aliasUsuario", result.getAliasUsuario())
						.addField("currentPage", result.getCurrentPage())
						.addField("camposTotales", result.getCamposTotales())
						.addField("esTotales", result.isEsTotales());

			}
		});
		consultaService.doConsulta(dto, ts);
		return null;
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
