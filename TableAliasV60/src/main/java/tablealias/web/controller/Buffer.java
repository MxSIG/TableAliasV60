package tablealias.web.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tablealias.dataformatters.DataFormatter;
import tablealias.dataformatters.FormatterFactory;
import tablealias.sqlworkers.BufferWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.ResultFormat;
import tablealias.utils.SubProjectReader;
import tablealias.utils.TablasServidor;

@Controller
@RequestMapping("/buffer")
public class Buffer {

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Object doBuffer(@RequestParam(value = "x1", required = false) String _x1,
			@RequestParam(value = "y1", required = false) String _y1,
			@RequestParam(value = "size", required = false) String _size,
			@RequestParam(value = "gid", required = false) String _gid,
			@RequestParam(value = "tabla", required = false) String _tabla,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream out = null;
		String table2Search = null;
		Object finalResult = null;
		try {
			double size = Double.MIN_VALUE;
			String gid = null;
			double x1 = Double.MIN_VALUE;
			double y1 = Double.MIN_VALUE;
			if (_x1 != null) {
				x1 = Double.parseDouble(_x1);
			}
			if (_y1 != null) {
				y1 = Double.parseDouble(_y1);
			}
			if (_size != null) {
				size = Double.parseDouble(_size);
				// size = (size / 101301.2);
			}
			boolean hasCoords = x1 != Double.MIN_VALUE
					&& y1 != Double.MIN_VALUE;
			if (_gid != null) {
				gid = _gid;
			}
			if (_tabla != null) {
				table2Search = _tabla;
			}
			if (table2Search != null && (size != Double.MIN_VALUE)
					&& gid != null) {
				TablasServidor tablasServidor = (TablasServidor) servletContext
						.getAttribute("tablasServidor");
				ResultFormat formatType = ResultFormat.BUFFER;
				if (tablasServidor.tableExists(table2Search)) {
					String subProject = SubProjectReader.getSubProjectString(
							tablasServidor.getFoundTable(), request);
					QueryWorker qw = new BufferWrapper(size, gid, hasCoords,
							x1, y1, subProject);
					qw.setServer(tablasServidor.getFoundServer());
					qw.setTable(tablasServidor.getFoundTable());
					qw.setPolygon(null);
					DataFormatter<String> formatter = FormatterFactory
							.<String> getFormatter(formatType, request);
					formatter.setQueryWorker(qw);
					finalResult = formatter.getData();
				} else {
					request.setAttribute("exito", false);
				}
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Parametros no validos");
			}
		} catch (Exception ex) {
			RegistraErrorWebService.RegistraError(request, ex);
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					ex.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return finalResult;

	}

}
