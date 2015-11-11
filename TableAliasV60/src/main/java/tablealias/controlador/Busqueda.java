package tablealias.controlador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tablealias.dataformatters.DataFormatter;
import tablealias.dataformatters.FormatterFactory;
import tablealias.delegate.DelegateCPV2010Variables;
import tablealias.sqlworkers.BusquedaWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.DomicilioValidator;
import tablealias.utils.ExceptionSearchParser;
import tablealias.utils.ExceptionTables;
import tablealias.utils.FieldQueryDataCleaner;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.ResultFormat;
import tablealias.utils.SubProjectReader;
import tablealias.utils.TablasServidor;

/**
 * 
 * @author INEGI
 */
public class Busqueda extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
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
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = null;
		String table2Search = null;
		try {
			RequestDispatcher rd = null;
			ServletContext context = getServletContext();

			final String PROY_PARAM = context
					.getInitParameter("proyname.param");
			String where = null;
			String whereTipo = null;
			if (request.getParameter("tabla") != null
					&& request.getParameter("pagina") != null
					&& request.getParameter("searchCriteria") != null) {
				table2Search = request.getParameter("tabla").replaceAll(
						"['\"]", "");
				String proyecto = null;
				if (request.getParameter(PROY_PARAM) != null) {
					proyecto = request.getParameter(PROY_PARAM);
					if (proyecto.trim().length() == 0) {
						proyecto = null;
					}
				}
				String valueToSearch = request.getParameter("searchCriteria")
						.trim().replaceAll("['\"]", "");
				;
				// "criteria  " + valueToSearch);
				Integer pagina = Integer.parseInt(request
						.getParameter("pagina"));
				if (request.getParameter("where") != null) {
					where = request.getParameter("where");
				}
				whereTipo = request.getParameter("whereTipo");
				TablasServidor tablasServidor = (TablasServidor) context
						.getAttribute("tablasServidor");
				ResultFormat formatType = ResultFormat.JSON;
				boolean exceptionSearch = false;
				if (tablasServidor.tableExists(table2Search, proyecto)) {
					if (proyecto != null
							&& table2Search.equalsIgnoreCase("geolocator")) {
						if (tablasServidor.isProyectoValido(proyecto)) {
							tablasServidor
									.setGeolocatorTableFromProyecto(proyecto);
						}
					}
					valueToSearch = FieldQueryDataCleaner
							.removeAcentos(valueToSearch);
					ExceptionSearchParser esp = null;
					ExceptionTables exTables = (ExceptionTables) context
							.getAttribute("exceptionTables");
					if (exTables.isExceptionTable(table2Search)) {
						// "busqueda = " + valueToSearch);
						esp = new ExceptionSearchParser(valueToSearch);
						esp.setExceptionToken("--");
						exceptionSearch = !esp.isBusquedaNormal();
						if (exceptionSearch) {
							table2Search = "geocalles";
							tablasServidor.tableExists(table2Search);
							if (tablasServidor.tableExists(table2Search
									+ proyecto, proyecto)) {
								table2Search = table2Search + proyecto;
							} else {
								tablasServidor.tableExists(table2Search);
							}
						}
					}
					String[] datos = null;
					if (!exceptionSearch) {
						datos = new String[] { valueToSearch };//
					}
					String subProject = SubProjectReader.getSubProjectString(
							tablasServidor.getFoundTable(), request);
					QueryWorker qw = new BusquedaWrapper(datos, exTables, esp,
							where, subProject, whereTipo);
					qw.setServer(tablasServidor.getFoundServer());
					qw.setTable(tablasServidor.getFoundTable());
					qw.setPageToView(pagina);
					DataFormatter<String> formatter = FormatterFactory
							.<String> getFormatter(formatType, request);
					formatter.setQueryWorker(qw);
					String result = formatter.getData();
					String result1 = null;
					DomicilioValidator dmv = new DomicilioValidator();
					int totalIndicadores = 0;
					if (("mdm6".equalsIgnoreCase(proyecto) || "scince"
							.equalsIgnoreCase(proyecto))
							&& (table2Search.equalsIgnoreCase("geolocator")
									&& qw.getNumberOfRecords() == 0 && !exceptionSearch)) {
						try {
							if (tablasServidor.tableExists("indicadores",
									"scince")) {
								DelegateCPV2010Variables cpdel = new DelegateCPV2010Variables(
										tablasServidor.getFoundServer());
								result1 = cpdel.searchData(valueToSearch,
										tablasServidor.getFoundTableClone());
								totalIndicadores = cpdel.getCuantos();
							}
						} catch (Exception e) {
							System.out
									.println("Error al utilizar indicador con corte geografico -- "
											+ valueToSearch);
							e.printStackTrace();
						}
					}
					// System.out.println("testing: " + result1 +
					// " totalIndicadores: " + totalIndicadores);
					if ((result1 != null && !result1.isEmpty())
							|| totalIndicadores > 0) {
						result = result1;
					}
					if (table2Search.equalsIgnoreCase("geolocator")
							&& totalIndicadores == 0
							&& qw.getNumberOfRecords() == 0 && !exceptionSearch
							&& dmv.isDomicilioKind(valueToSearch)) {
						table2Search = "cNumExt";
						datos = new String[] { valueToSearch };//
						qw.setValueToSearch(datos);
						tablasServidor.tableExists(table2Search);
						qw.setTable(tablasServidor.getFoundTable());
						result = formatter.getData();
						if (qw.getNumberOfRecords() == 0) {
							table2Search = "cNumExtRur";
							datos = new String[] { valueToSearch };//
							qw.setValueToSearch(datos);
							tablasServidor.tableExists(table2Search);
							qw.setTable(tablasServidor.getFoundTable());
							result = formatter.getData();
						}
					}
					rd = request.getRequestDispatcher("Resultados.jsp");
					// response.addHeader("Access-Control-Allow-Origin", "*");
					response.setContentType(formatter.getContentType());
					RequestHelper rh = new RequestHelper(request);

					// comentar esta parte
					// if (rh.supportsGzip()) {
					//
					// response.setHeader("Content-Encoding", "gzip");
					// out = response.getOutputStream();
					// GZIPOutputStream gzos = new GZIPOutputStream(out);
					//
					// if (formatter.hasErrors()) {
					// gzos.write(formatter.getErrorMsg().getBytes());
					// } else {
					// gzos.write(result.getBytes());
					// }
					// gzos.close();
					// } else {
					out = response.getOutputStream();
					out.print(formatter.getData());
					// }
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
		// response.addHeader("Access-Control-Allow-Origin", "*");
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
		// response.addHeader("Access-Control-Allow-Origin", "*");
		processRequest(request, response);
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
