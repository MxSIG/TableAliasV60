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
import tablealias.input.BusquedaInput;
import tablealias.service.BusquedaService;
import tablealias.utils.ExceptionTables;
import tablealias.utils.TablasServidor;
import tablealias.web.ResponseFactory.CustomResponse;
import tablealias.web.result.WithSummaryResult;

@Controller
@RequestMapping("/busqueda")
public class Busqueda {

	@Autowired
	private BusquedaService service;

	@Autowired
	ServletContext context;

	@CustomResponseWrap
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody CustomResponse doBusqueda(
			@Valid @RequestBody BusquedaInput input,
			final HttpServletRequest request) {
		TablasServidor ts = (TablasServidor) context
				.getAttribute("tablasServidor");
		ExceptionTables exTables = (ExceptionTables) context
				.getAttribute("exceptionTables");
		ResponseWrapAspect aspect = ResponseWrapAspect.aspectOf();
		aspect.setResultAwareCallBack(new ResultAware<WithSummaryResult>() {

			@Override
			public void successfulResult(CustomResponse response,
					WithSummaryResult result) {
				request.setAttribute("exito", result.isExito());
				response.addField("tabla", result.getTabla())
						.addField("totalFields", result.getTotalFields())
						.addField("typeFields", result.getTypeFields())
						.addField("aliasUsuario", result.getAliasUsuario())
						.addField("currentPage", result.getCurrentPage())
						.addField("camposTotales", result.getCamposTotales())
						.addField("esTotales", result.isEsTotales())
						.addField("types", result.getTypes());

			}
		});
		service.doBusqueda(input, ts, exTables);
		return null;
	}

}
