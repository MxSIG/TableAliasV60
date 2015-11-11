package tablealias.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tablealias.delegate.SetGeometryDelegate;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Server;

import com.google.gson.Gson;

/**
 * 
 * @author INEGI
 */
public class SetGeometry extends HttpServlet {

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
		ServletOutputStream out = response.getOutputStream();
		ServletContext context = getServletContext();
		TablasServidor tablasServidor = (TablasServidor) context
				.getAttribute("tablasServidor");
		try {
			tablasServidor.tableExists("geometrias", "mdm6");
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
				String salida = gson.toJson(outList);
				// RequestHelper rh = new RequestHelper(request);
				// if (rh.supportsGzip()) {
				// response.addHeader("Content-Encoding", "gzip");
				// GZIPOutputStream gzos = new GZIPOutputStream(out);
				// gzos.write(salida.getBytes());
				// gzos.close();
				// } else {
				out.print(salida);
				// }
			}
		} catch (IllegalArgumentException iae) {
			Gson gson = new Gson();
			String salida = gson.toJson("Invalid Parameters");
			RequestHelper rh = new RequestHelper(request);
			// if (rh.supportsGzip()) {
			// response.addHeader("Content-Encoding", "gzip");
			// GZIPOutputStream gzos = new GZIPOutputStream(out);
			// gzos.write(salida.getBytes());
			// gzos.close();
			// } else {
			out.print(salida);
			// }
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
