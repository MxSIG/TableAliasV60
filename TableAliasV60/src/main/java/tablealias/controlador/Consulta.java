package tablealias.controlador;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tablealias.dataformatters.DataFormatter;
import tablealias.dataformatters.FormatterFactory;
import tablealias.sqlworkers.ConsultaWrapper;
import tablealias.utils.Polygon;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.ResultFormat;
import tablealias.utils.SubProjectReader;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class Consulta extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// response.setContentType("text/html;charset=UTF-8");
		ServletOutputStream out = null;
		String table2Search = null;
		try {
			RequestDispatcher rd = null;
			ServletContext context = getServletContext();
			final String PROY_PARAM = context
					.getInitParameter("proyname.param");
			String where = null;
			String gid = null;
			// DataLoader dl = new DataLoader(context.getRealPath("/"));
			double x1 = Double.MIN_VALUE;
			double y1 = Double.MIN_VALUE;
			double x2 = Double.MIN_VALUE;
			double y2 = Double.MIN_VALUE;
			double res = Double.MIN_VALUE;
			String poligonoURL = request.getParameter("poligono");
			boolean parametros = true;

			String totalesConsulta = request.getParameter("totalesConsulta");
			// HttpSession sesion = request.getSession();
			String proyecto = null;
			if (request.getParameter(PROY_PARAM) != null) {
				proyecto = request.getParameter(PROY_PARAM);
				if (proyecto.trim().length() == 0) {
					proyecto = null;
				}
			}
			if (request.getParameter("gid") != null) {
				gid = request.getParameter("gid");
			}
			if (request.getParameter("x1") != null) {
				x1 = Double.parseDouble(request.getParameter("x1"));
			}
			if (request.getParameter("y1") != null) {
				y1 = Double.parseDouble(request.getParameter("y1"));
			}
			if (request.getParameter("x2") != null) {
				x2 = Double.parseDouble(request.getParameter("x2"));
			}
			if (request.getParameter("y2") != null) {
				y2 = Double.parseDouble(request.getParameter("y2"));
			}
			if (request.getParameter("width") != null) {
				res = Double.parseDouble(request.getParameter("width"));
			}
			if (request.getParameter("where") != null) {
				where = request.getParameter("where");
			}
			// if (request.getParameter("tabla") != null
			// && (
			// ((x1 != Double.MIN_VALUE)
			// && request.getParameter("pagina") != null
			// && (y1 != Double.MIN_VALUE) && (res != Double.MIN_VALUE))
			// || (poligonoURL != null) || (gid != null)
			// ))
			if (request.getParameter("tabla") != null
					&& (((x1 != Double.MIN_VALUE)
							&& request.getParameter("pagina") != null
							&& (y1 != Double.MIN_VALUE) && (res != Double.MIN_VALUE)))) {
				parametros = true;
			}
			if (request.getParameter("tabla") != null
					&& (((res != Double.MIN_VALUE)) || (poligonoURL != null) || (gid != null))) {
				parametros = true;
			}
			System.out.println("parametros " + parametros);
			System.out.println("---" + y1 + "--" + y2);
			if (parametros == true) {
				table2Search = request.getParameter("tabla").trim();
				TablasServidor tablasServidor = (TablasServidor) context
						.getAttribute("tablasServidor");
				Integer pagina = 1;
				try {
					pagina = Integer.parseInt(request.getParameter("pagina"));
				} catch (Exception e) {
					pagina = 1;
				}
				ResultFormat formatType = ResultFormat.JSON;

				Table table = null;
				Polygon polygon = null;

				if (tablasServidor.tableExists(table2Search, proyecto)) {
					table = tablasServidor.getFoundTableClone();
				}

				if (totalesConsulta != null && !totalesConsulta.isEmpty()) {
					table.setCampoTotales(totalesConsulta);
					table.setProcesaTotales(true);
				} else {
					table.setProcesaTotales(false);
				}

				if (poligonoURL != null) {
					polygon = new Polygon(poligonoURL, table.getGeomName(),
							table.getProy());
				} else {
					polygon = new Polygon(x1, y1, res, table.getGeomName(),
							table.getProy());
					if (x2 != Double.MIN_VALUE && y2 != Double.MIN_VALUE) {
						polygon.setX1(x1);
						polygon.setX2(x2);
						polygon.setY1(y1);
						polygon.setY2(y2);
					}
				}

				if (tablasServidor.tableExists(table2Search, proyecto)) {
					String subProject = SubProjectReader.getSubProjectString(
							tablasServidor.getFoundTable(), request);
					ConsultaWrapper qw = new ConsultaWrapper(where, gid,
							subProject, false);
					qw.setServer(tablasServidor.getFoundServer());
					qw.setTable(table); // tablasServidor.getFoundTable()
					qw.setPolygon(polygon);
					qw.setPageToView(pagina);
					qw.setServerBuffer(tablasServidor.getServerByTable("c100"));
					DataFormatter<String> formatter = FormatterFactory
							.<String> getFormatter(formatType, request);
					formatter.setQueryWorker(qw);
					rd = request.getRequestDispatcher("Resultados.jsp");
					response.addHeader("Access-Control-Allow-Origin", "*");
					response.setContentType(formatter.getContentType());
					RequestHelper rh = new RequestHelper(request);
					if (rh.supportsGzip()) {
						response.setHeader("Content-Encoding", "gzip");
						out = response.getOutputStream();
						GZIPOutputStream gzos = new GZIPOutputStream(out);
						String result = formatter.getData();
						result = corrigeNegativosSCINCE(proyecto, result);
						// System.out.println("result: " + result);
						// "Salida " + result);
						if (formatter.hasErrors()) {
							gzos.write(formatter.getErrorMsg().getBytes());
						} else {
							gzos.write(result.getBytes());
						}
						gzos.close();
					} else {
						out = response.getOutputStream();
						out.print(formatter.getData());
					}
					/*
					 * out.print(formatter.getData()); if
					 * (formatter.hasErrors()) { request.setAttribute("error",
					 * formatter.getErrorMsg().replaceAll("\n", "<br>"));
					 * rd.forward(request, response); }
					 */
				} else {
					request.setAttribute("exito", false);
					// "No se ha encontrado la tabla " + table2Search);
				}
			} else {
				/*
				 * rd = request.getRequestDispatcher("Resultados.jsp");
				 * request.setAttribute("error", "Parametros no validos");
				 * rd.forward(request, response);
				 */
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
	}

	private String corrigeNegativosSCINCE(String proyecto, String result) {
		if ("scince".equalsIgnoreCase(proyecto)) {
			// System.out.println("Corrigiendo... SCINCE");
			result = result.replaceAll("-6",
					"Datos reservados por confidencialidad");
			result = result.replaceAll("-9", "No especificado");
			result = result.replaceAll("-8", "No aplica");
			result = result.replaceAll("-6.00",
					"Datos reservados por confidencialidad");
			result = result.replaceAll("-9.00", "No especificado");
			result = result.replaceAll("-8.00", "No aplica");
		}

		return result;
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		super.doOptions(req, resp);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
