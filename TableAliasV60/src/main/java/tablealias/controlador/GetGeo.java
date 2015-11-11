package tablealias.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import mx.org.inegi.dtweb.denue.delegate.GetGeoDelegated;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class GetGeo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(HTTPEncodingFormat.getJsonFormatWEncoding());
        response.addHeader("Access-Control-Allow-Origin", "*");
        //PrintWriter out = response.getWriter();
        ServletOutputStream out = null;
        ServletContext context = getServletContext();
        TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
        Connection conn = null;
        try {
            tablasServidor.tableExists("c500", "denue");
            Server server = tablasServidor.getFoundServer();
            conn = ConnectionManager.getConnection(server);

            String clave = request.getParameter("clave");
            GetGeoDelegated geo = new GetGeoDelegated(conn);
            Gson gson = new Gson();
            String salida = gson.toJson(geo.getSubArbol(clave));
            RequestHelper rh = new RequestHelper(request);
            if (rh.supportsGzip()) {
                response.setHeader("Content-Encoding", "gzip");
                out = response.getOutputStream();
                GZIPOutputStream gzos = new GZIPOutputStream(out);
                gzos.write(salida.getBytes());
                gzos.close();
            } else {
                out = response.getOutputStream();
                out.print(salida);
            }
            //out.print(salida);
        } catch (Exception e) {
            RegistraErrorWebService.RegistraError(request, e);
            e.printStackTrace();
        } finally {
            if (conn != null){
                try {
                    ConnectionManager.closeConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        super.doOptions(req, resp);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
