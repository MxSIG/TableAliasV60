package tablealias.controlador;

import dtweb.temas.dto.DatosTema;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.actividadesareas.helpers.TematizacionTADelegado;
import tablealias.xmldata.Server;
import tablealias.charting.delegate.DelegadoCharting;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class Graficador extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        OutputStream out = response.getOutputStream();
        TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
        Connection conn = null;
        try {
            tablasServidor.tableExists("tematizacion", "mdm5");
            Server server = tablasServidor.getFoundServer();
            conn = ConnectionManager.getConnectionW(server);

            String tema = request.getParameter("tema");
            String colores = request.getParameter("colores");
            String inicio = request.getParameter("inicio");
            String fin = request.getParameter("fin");
            String cuantos = request.getParameter("ocurrencias");
            String tipo = request.getParameter("type");
            String metodo = request.getParameter("metodo");
            String estratos = request.getParameter("estratos");
            String clases = request.getParameter("clases");
            if( metodo == null || metodo.isEmpty() ){
                metodo = "cuantiles";
            }
            if( estratos == null || estratos.isEmpty() ){
                estratos = "9";
            }
            if (tema == null || colores == null || colores.isEmpty() || inicio == null 
                    || inicio.isEmpty() || fin == null || fin.isEmpty() ) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "PARAMETROS NO VALIDOS");
            } else {
                String[] colors = colores.split(",");
                String[] from = inicio.split(",");
                String[] to = fin.split(",");
                String[] count = cuantos.split(",");
                DelegadoCharting dc = new DelegadoCharting();
                response.setContentType("image/png");
                dc.generachart(tema, metodo, estratos, clases, colors, from, to, out, tipo, count,  conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    ConnectionManager.closeConnection(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (out != null) {
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
