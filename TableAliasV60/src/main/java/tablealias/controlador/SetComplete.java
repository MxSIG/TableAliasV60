package tablealias.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tablealias.delegate.SetCompleteDelegate;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

import com.google.gson.Gson;

/**
 * 
 * @author INEGI
 */
public class SetComplete extends HttpServlet {

	private String campo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		campo = config.getInitParameter("campo");
	}

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
		response.setContentType(HTTPEncodingFormat.getJsonFormatWEncoding());
		response.addHeader("Access-Control-Allow-Origin", "*");
		ServletOutputStream out = out = response.getOutputStream();
		ServletContext context = getServletContext();
		TablasServidor tablasServidor = (TablasServidor) context
				.getAttribute("tablasServidor");
		try {
			String cvegeo = request.getParameter("cvegeo");
			String status = request.getParameter("estatus");
			String table = request.getParameter("tabla");
			if (table.equalsIgnoreCase("manzana")
					|| table.equalsIgnoreCase("manzanas")
					|| table.equalsIgnoreCase("manzana_urb_a")) {
				table = "gnem_geo2";
			}
			if (table.equalsIgnoreCase("ageb")) {
				table = "ageb_urb_a";
			}
			if (cvegeo == null || cvegeo.isEmpty() || status == null
					|| status.isEmpty() || table == null || table.isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"PARAMETROS NO VALIDOS");
			} else {
				tablasServidor.tableExists(table, "mdm6");
				Server server = tablasServidor.getFoundServer();
				Table tabla = tablasServidor.getFoundTable();
				SetCompleteDelegate delegate = new SetCompleteDelegate();
				List<Boolean> outList = new ArrayList<Boolean>();
				String[] cves = cvegeo.split(",");
				for (String cve : cves) {
					outList.add(delegate.updateStatus(status, cve.trim(),
							server, tabla, campo));
				}
				Gson gson = new Gson();
				String salida = gson.toJson(outList);
				RequestHelper rh = new RequestHelper(request);
				if (rh.supportsGzip()) {
					response.addHeader("Content-Encoding", "gzip");
					GZIPOutputStream gzos = new GZIPOutputStream(out);
					gzos.write(salida.getBytes());
					gzos.close();
				} else {
					out.print(salida);
				}
			}
		} catch (IllegalArgumentException iae) {
			Gson gson = new Gson();
			String salida = gson.toJson("Invalid Parameters");
			RequestHelper rh = new RequestHelper(request);
			if (rh.supportsGzip()) {
				response.addHeader("Content-Encoding", "gzip");
				GZIPOutputStream gzos = new GZIPOutputStream(out);
				gzos.write(salida.getBytes());
				gzos.close();
			} else {
				out.print(salida);
			}
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
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		super.doOptions(req, resp);
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
