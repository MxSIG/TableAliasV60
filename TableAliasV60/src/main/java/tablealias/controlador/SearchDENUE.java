package tablealias.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import mx.org.inegi.dtweb.denue.delegate.SearchDelegated;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.ListWrapper;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class SearchDENUE extends HttpServlet {

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

            SearchDelegated delegado = new SearchDelegated(conn);

            String parametro = request.getParameter("criterio");
            String tipo = request.getParameter("tipo");
            if (parametro != null && tipo != null) {
                String respuesta = null;
                List datos = null;
                if ("geo".equalsIgnoreCase(tipo)) {
                    String x1 = request.getParameter("x1");
                    if (x1 != null) {
                        String x2 = request.getParameter("x2");
                        String y1 = request.getParameter("y1");
                        String y2 = request.getParameter("y2");
                        datos = delegado.getGeo(x1, x2, y1, y2);
                    } else {
                        datos = delegado.getGeo(parametro);

                    }
                    Gson gson = new Gson();
                    ListWrapper lw = new ListWrapper("No se encontraron coincidencias");
                    lw.setList(datos);
                    lw.setAliasUsuario("Catálogo de areas geograficas");
                    respuesta = gson.toJson(lw);
                } else {
                    datos = delegado.getActividades(parametro);
                    Gson gson = new Gson();
                    ListWrapper lw = new ListWrapper("No se encontraron coincidencias");
                    lw.setAliasUsuario("Catálogo de actividad económica");
                    lw.setList(datos);
                    respuesta = gson.toJson(lw);
                    //respuesta = gson.toJson(datos);
                }
                String callback = request.getParameter("callback");
                if (callback != null && !callback.isEmpty()) {
                    StringBuilder jsonp = new StringBuilder(callback);
                    jsonp.append("(").append(respuesta).append(")");
                    respuesta = jsonp.toString();
                }
                RequestHelper rh = new RequestHelper(request);
                if (rh.supportsGzip()) {
                    response.setHeader("Content-Encoding", "gzip");
                    out = response.getOutputStream();
                    GZIPOutputStream gzos = new GZIPOutputStream(out);
                    gzos.write(respuesta.getBytes());
                    gzos.close();
                } else {
                    out = response.getOutputStream();
                    out.print(respuesta);
                }
                //out.println(respuesta);//sb.toString());
                //ConnectionManager.closeConnection(conn);
            } else {
                response.sendError(response.SC_BAD_REQUEST, "Invalid Parameters");
            }
        } catch (Exception ex) {
            RegistraErrorWebService.RegistraError(request, ex);
            ex.printStackTrace();
            response.sendError(response.SC_BAD_REQUEST, "PARAMETROS NO VALIDOS");
        } finally {
            if (conn != null){
                try {
                    ConnectionManager.closeConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (out!=null){
                out.close();
            }
        }
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        super.doOptions(req, resp);
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
