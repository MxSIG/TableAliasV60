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
import tablealias.service.ConsultaTotalesService;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.ConsultaTotalesResult;

/**
 * Controller with same functionality as Consulta but is adding needed
 * parameters to get 'Totales'.
 * 
 * @author INEGI
 * 
 */
@Controller
@RequestMapping("/consultaTotales")
public class ConsultaTotales {

	@Autowired
	ConsultaTotalesService service;

	private ServletContext servletContext;

	@CustomResponseWrap
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	CustomResponse lineaTiempo(@Valid @RequestBody ConsultaDto dto,
			final HttpServletRequest request) {
		TablasServidor ts = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		ResponseWrapAspect aspect = ResponseWrapAspect.aspectOf();
		aspect.setResultAwareCallBack(new ResultAware<ConsultaTotalesResult>() {

			@Override
			public void successfulResult(CustomResponse response,
					ConsultaTotalesResult res) {
				request.setAttribute("exito", res.isExito());
				response.addField("tabla", res.getTabla())
						.addField("totales", res.getTableDataTotales())
						.addField("totalFields", res.getTotalFields())
						.addField("typeFields", res.getTypeFields())
						.addField("aliasUsuario", res.getAliasUsuario())
						.addField("currentPage", res.getCurrentPage())
						.addField("camposTotales", res.getCamposTotales())
						.addField("esTotales", res.isEsTotales());
			}
		});
		service.doConsulta(dto, ts);
		return null;
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
