package tablealias.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tablealias.dto.BuscableIdentificableDto;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.ListWrapper;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.TablasServidor;

import com.google.gson.Gson;

/**
 * 
 * @author INEGI
 */
public class BuscablesIdentificables extends HttpServlet {

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
		PrintWriter out = response.getWriter();
		final String PROY_PARAM = getServletContext().getInitParameter(
				"proyname.param");
		try {
			RequestDispatcher rd = null;
			if (request.getParameter(PROY_PARAM) != null) {
				ServletContext context = getServletContext();
				TablasServidor tablasServidor = (TablasServidor) context
						.getAttribute("tablasServidor");
				String proyecto = request.getParameter(PROY_PARAM);
				List<BuscableIdentificableDto> data = tablasServidor
						.getBuscablesIdentificables(proyecto);
				ListWrapper lw = new ListWrapper("Proyecto invalido");
				lw.setList(data);
				Gson gson = new Gson();
				out.print(gson.toJson(lw));
			} else {
				/*
				 * rd = request.getRequestDispatcher("Resultados.jsp");
				 * request.setAttribute("error", "Parametros no validos");
				 * rd.forward(request, response);
				 */
				response.sendError(response.SC_BAD_REQUEST,
						"Parametros no validos");
			}
		} catch (Exception ex) {
			RegistraErrorWebService.RegistraError(request, ex);
			ex.printStackTrace();
			response.sendError(response.SC_BAD_REQUEST, ex.getMessage());
		} finally {
			out.close();
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
