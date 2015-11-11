package tablealias.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.dto.TematizacionParam;
import tablealias.service.TematizacionJsonService;

@Controller
public class TematizacionJson {
	@Autowired
	TematizacionJsonService tematizacionJsonService;

	@RequestMapping(value = "/tematizacion/Json", method = RequestMethod.POST)
	private @ResponseBody
	Object tematizacionJson(@Valid @RequestBody TematizacionParam tematizacion,
			HttpServletRequest req) throws Exception {
		return tematizacionJsonService.json(tematizacion, req);

	}
}
