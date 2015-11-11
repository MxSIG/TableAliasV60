package tablealias.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import tablealias.dinue.helpers.FiltroAgropeControlador;
import tablealias.dto.AgropeDatos;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class FiltroAgrope extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=ISO-8859-1");
        response.addHeader("Access-Control-Allow-Origin", "*");
        ServletOutputStream out = null;
        TablasServidor tablasServidor = (TablasServidor) getServletContext().getAttribute("tablasServidor");
        Connection conn = null;
        try {
            String filtro = request.getParameter("filtro");
            String filtroId = request.getParameter("filtroId");
            //validar parametros
            if( ( filtro == null || filtro.length() < 1 ) || validate( filtro ) ){
                response.sendError( HttpServletResponse.SC_BAD_REQUEST, "INVALID PARAMETER FORMAT" );
            } else {
                tablasServidor.tableExists("f100", "mdm5");
                Server server = tablasServidor.getFoundServer();
                server.setDbName("bdinfestadistica");
                conn = ConnectionManager.getConnectionW(server);
                FiltroAgropeControlador fac = new FiltroAgropeControlador( conn );
                AgropeDatos ag = new AgropeDatos( fac.escribeFiltro( filtroId, filtro ) );
                RequestHelper rh = new RequestHelper(request);
                String result = new Gson().toJson(ag);
                out = response.getOutputStream();
                if (rh.supportsGzip()) {
                    response.addHeader("Content-Encoding", "gzip");
                    GZIPOutputStream gzipos = new GZIPOutputStream(out);
                    gzipos.write(result.getBytes());
                    gzipos.close();
                } else {
                    out.print(result);
                }
            }
        } catch (Exception e) {
            RegistraErrorWebService.RegistraError(request, e);
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

    private boolean validate(String filtro) {
        Pattern p = Pattern.compile( "(;?\\b\\d\\d\\|[a-zA-z]{2}\\w?\\d{2}(,[a-zA-z]{2}\\w?\\d{2})*)+" );
        Matcher m = p.matcher( filtro );
        return !m.matches();
    }
//01|oi16,pv12;03|oi16,pv12;04|oi16,pv12;06|oi16,pv12;07|oi16,pv12;08|oi16,pv12;09|oi16,pv12;10|oi16,pv12;11|oi16,pv12;12|oi16,pv12,oi16,pv12,per05,oi06,pv04,per07,oi14,pv10,per12,per14,oi17,pv14,per22,oi21,pv16,oi22,pv17;13|oi16,pv12
}
