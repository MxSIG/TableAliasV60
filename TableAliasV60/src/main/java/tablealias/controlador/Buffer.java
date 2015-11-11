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
import tablealias.sqlworkers.BufferWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.ResultFormat;
import tablealias.utils.SubProjectReader;
import tablealias.utils.TablasServidor;

/**
 * 
 * @author INEGI
 */
public class Buffer extends HttpServlet {

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
		// response.setContentType("text/html;charset=UTF-8");
		ServletOutputStream out = null;
		String table2Search = null;
		try {
			RequestDispatcher rd = null;
			ServletContext context = request.getSession().getServletContext();
			// DataLoader dl = new DataLoader(context.getRealPath("/"));
			double size = Double.MIN_VALUE;
			String gid = null;
			double x1 = Double.MIN_VALUE;
			double y1 = Double.MIN_VALUE;
			if (request.getParameter("x1") != null) {
				x1 = Double.parseDouble(request.getParameter("x1"));
			}
			if (request.getParameter("y1") != null) {
				y1 = Double.parseDouble(request.getParameter("y1"));
			}
			if (request.getParameter("size") != null) {
				size = Double.parseDouble(request.getParameter("size"));
				// size = (size / 101301.2);
			}
			boolean hasCoords = x1 != Double.MIN_VALUE
					&& y1 != Double.MIN_VALUE;
			if (request.getParameter("gid") != null) {
				gid = request.getParameter("gid");
			}
			if (request.getParameter("tabla") != null) {
				table2Search = request.getParameter("tabla");
			}
			if (table2Search != null && (size != Double.MIN_VALUE)
					&& gid != null) {
				TablasServidor tablasServidor = (TablasServidor) context
						.getAttribute("tablasServidor");
				// Integer pagina =
				// Integer.parseInt(request.getParameter("pagina"));
				ResultFormat formatType = ResultFormat.BUFFER;
				if (tablasServidor.tableExists(table2Search)) {
					String subProject = SubProjectReader.getSubProjectString(
							tablasServidor.getFoundTable(), request);
					QueryWorker qw = new BufferWrapper(size, gid, hasCoords,
							x1, y1, subProject);
					qw.setServer(tablasServidor.getFoundServer());
					qw.setTable(tablasServidor.getFoundTable());
					qw.setPolygon(null);
					// qw.setPageToView(pagina);
					DataFormatter<String> formatter = FormatterFactory
							.<String> getFormatter(formatType, request);
					formatter.setQueryWorker(qw);
					rd = request.getRequestDispatcher("Resultados.jsp");
					response.setContentType(formatter.getContentType());
					// response.setHeader("Content-Encoding", "gzip");
					RequestHelper rh = new RequestHelper(request);
					// if (rh.supportsGzip()) {
					// response.setHeader("Content-Encoding", "gzip");
					// out = response.getOutputStream();
					// GZIPOutputStream gzos = new GZIPOutputStream(out);
					// String result = formatter.getData();
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

}
