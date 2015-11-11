package tablealias.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.delegate.SetGeometryDelegate;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Server;

import com.google.gson.Gson;

@Controller
@RequestMapping("/SetGeometry")
public class SetGeometry {

	private ServletContext servletContext;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Object doBuffer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletOutputStream out = response.getOutputStream();
		TablasServidor tablasServidor = (TablasServidor) servletContext
				.getAttribute("tablasServidor");
		String salida = null;
		try {
			tablasServidor.tableExists("geometrias", "mdm5");
			Server server = tablasServidor.getFoundServer();

			String geometry = request.getParameter("geometry");
			String cve_ent = request.getParameter("cve_ent");
			if (geometry == null || geometry.isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"PARAMETROS NO VALIDOS");
			} else {
				Long id;
				SetGeometryDelegate delegate = new SetGeometryDelegate();
				if (cve_ent != null) {
					id = delegate.writeGeometryCE(geometry, server, cve_ent);
				} else {
					id = delegate.writeGeometry(geometry, server);
				}
				List<Long> outList = new ArrayList<Long>();
				outList.add(id);
				Gson gson = new Gson();
				salida = gson.toJson(outList);
			}
		} catch (IllegalArgumentException iae) {
			Gson gson = new Gson();
			salida = gson.toJson("Invalid Parameters");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
			}
		}
		return salida;
	}

	@Autowired
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
