package tablealias.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tablealias.actividadesareas.helpers.SaveQuoteItem;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DenuePK;
import tablealias.dinue.helpers.Estratos;
import tablealias.sqlworkers.ParamConsultaCarritoWrapper;
import tablealias.sqlworkers.ParamConsultaWrapper;
import tablealias.sqlworkers.interfaces.ParamConsultaWrapperInterface;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class ParamConsulta extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(HTTPEncodingFormat.getHTMLFormatWEncoding());
        PrintWriter out = response.getWriter();
        try {
            /*out.write("<html><head><script>function redirect(){"
            + "document.location = 'http://" +request.getLocalAddr()+ "/DENUE_cotizacion/?id=';"
            + "}</script></head><body onload='javascript:redirect();'>cotizando...</body></html> ");
             */

            RequestDispatcher rd = null;
            ServletContext context = getServletContext();

            String areageo = request.getParameter("areageo");
            String actividades = request.getParameter("actividad");
            String estratos = request.getParameter("estratos");
            String sessionstoquote = request.getParameter("sessiontoquote");
            //String orderBy = request.getParameter("order");
            //String[] orderFields = orderBy.split(",");
            boolean res = false;
            ParamConsultaWrapperInterface pcw =  null;
            TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
            String campo=null;
            if (sessionstoquote == null || sessionstoquote.isEmpty()) {
                if (areageo == null || actividades == null || areageo.length() < 1 || actividades.length() < 1) {
                    response.sendError(response.SC_NOT_ACCEPTABLE, "Invalid Parameters");
                    return;
                }
                AreasGeo ag = new AreasGeo(areageo);
                Actividades ac = new Actividades(actividades);
                Estratos est = new Estratos(estratos);
                
                pcw = SaveQuote(tablasServidor, actividades, areageo, estratos, request, ac, ag, est);
                campo="id";
            }else{
                pcw = new ParamConsultaCarritoWrapper(tablasServidor, sessionstoquote);
                campo="idc";
            }
            res = pcw.save();
            if (res) {
                //System.out.println("Redirecionando a cotizacion");
                DenuePK pk = pcw.getDenuePK();
                out.write("<html><head><script>function redirect(){"
                        + "document.location = 'http://www.inegi.org.mx/sistemas/denue/cotizacion.aspx?"+campo+"=" + pk.getId() + "';"
                        + "}</script></head><body onload='javascript:redirect();'><H1>Cotizando...</H1></body></html> ");
                //response.sendRedirect("http://intranetwww.inegi.org.mx/denue/cotizacion.aspx?id="+pk.getId());
            } else {
                if (pcw.getTotales() > 0) {
                    response.sendError(response.SC_BAD_REQUEST, pcw.getErrorMsj());
                    //out.write(pcw.getErrorMsj());
                } else {
                    /*rd = request.getRequestDispatcher("ErrorTotales.jsp");
                    rd.forward(request, response);*/
                    response.sendError(response.SC_BAD_REQUEST, "Error de totales");
                }
            }
        } catch (Exception ex) {
            RegistraErrorWebService.RegistraError(request, ex);
            ex.printStackTrace();
            response.sendError(response.SC_BAD_REQUEST, ex.getMessage());
        } finally {
            out.close();
        }
    }

    private ParamConsultaWrapper SaveQuote(TablasServidor tablasServidor, String actividades, String areageo, String estratos, HttpServletRequest request, Actividades ac, AreasGeo ag, Estratos est) throws SQLException {
        /*ParamConsultaWrapper pcw = new ParamConsultaWrapper(tablasServidor, actividades, areageo, estratos);
        DinueOptionalParams optParams = new DinueOptionalParams(request, tablasServidor.getServerByTable("c100"));
        pcw.setOptParams(optParams);
        pcw.setActividades(ac);
        pcw.setAreasGeo(ag);
        pcw.setEstratos(est);

         */
        SaveQuoteItem sv = new SaveQuoteItem();
        return sv.SaveQuote(tablasServidor, actividades, areageo, estratos, request, ac, ag, est);
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
