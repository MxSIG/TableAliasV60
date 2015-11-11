package tablealias.controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tablealias.xmldata.Server;
import tablealias.dataformatters.DataFormatter;
import tablealias.dataformatters.FormatterFactory;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.Estratos;
import tablealias.sqlworkers.DinueWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.ResultFormat;
import tablealias.utils.SubProjectReader;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class GetDinue extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream out = null;
        String table2Search = null;
        try {
            RequestDispatcher rd = null;
            ServletContext context = getServletContext();
            //ThDinueDelegate dinueDelegate = new ThDinueDelegate();
            String areageo = request.getParameter("areageo");
            String actividades = request.getParameter("actividad");
            String estratos = request.getParameter("estratos");
            String orderBy = request.getParameter("order");
            String[] orderFields = null;
            if (orderBy != null) {
                orderFields = orderBy.split(",");
            }
            if (areageo == null || actividades == null || areageo.length() < 1 || actividades.length() < 1) {
                response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Invalid Parameters");
                return;
            }
            AreasGeo ag = new AreasGeo(areageo);
            Actividades ac = new Actividades(actividades);
            Estratos est = new Estratos(estratos);
            int pagina = Integer.parseInt(request.getParameter("pagina"));
            table2Search = request.getParameter("tabla").trim();
            TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
            String where = null;
            if (request.getParameter("where") != null) {
                where = request.getParameter("where");
            }
            ResultFormat formatType = ResultFormat.DINUE;
            if (tablasServidor.tableExists(table2Search)) {
                String subProject = SubProjectReader.getSubProjectString( tablasServidor.getFoundTable(), request );
                Server srvr = tablasServidor.getServerByTable("c100");
                DinueOptionalParams optParams = new DinueOptionalParams(request, srvr);
                QueryWorker qw = new DinueWrapper(ag, ac, est, optParams, srvr, orderFields, where, subProject );
                qw.setServer(tablasServidor.getFoundServer());
                qw.setTable(tablasServidor.getFoundTable());
                qw.setPageToView(pagina);
                DataFormatter<String> formatter = FormatterFactory.<String>getFormatter(formatType, request);
                formatter.setQueryWorker(qw);
                rd = request.getRequestDispatcher("Resultados.jsp");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType(formatter.getContentType());
                RequestHelper rh = new RequestHelper(request);
                if (rh.supportsGzip()) {
                    response.setHeader("Content-Encoding", "gzip");
                    out = response.getOutputStream();
                    GZIPOutputStream gzos = new GZIPOutputStream(out);
                    String result = result = formatter.getData();
                    /*
                    ActividadesAreasCache cache = new ActividadesAreasCache();
                    Parametreador par = new Parametreador();
                    String llave = par.getDigestedParams(request, "iso-8859-1");
                    result = cache.getCache(llave, srvr);
                    if (result == null || result.length()<5){
                    result = formatter.getData();
                    cache.createCache(llave, result, srvr);
                    }*/
                    //"Salida " + result);
                    if (formatter.hasErrors()) {
                        gzos.write(formatter.getErrorMsg().getBytes());
                    } else {
                        gzos.write(result.getBytes());
                    }
                    gzos.close();
                } else {
                    out = response.getOutputStream();
                    out.print(formatter.getData());
                }
                /*out.print(formatter.getData());
                if (formatter.hasErrors()) {
                request.setAttribute("error", formatter.getErrorMsg().replaceAll("\n", "<br>"));
                rd.forward(request, response);
                }*/
            } else {
                request.setAttribute("exito", false);
                //"No se ha encontrado la tabla " + table2Search);
            }
            /*ThDinue[] datos = dinueDelegate.getData(areageo, actividades, estratos, optParams.getParams(), pagina);
            List<ThDinue> datosList = dinueDelegate.getDataAsList(datos);
            ListWrapper lw = new ListWrapper();
            lw.setList(datosList);
            }*/
        } catch (Exception e1) {
            RegistraErrorWebService.RegistraError(request, e1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = sdf.format(new Date(System.currentTimeMillis()));
            System.out.println("Error en : " + date + " desde el equipo: " + request.getRemoteAddr());
            e1.printStackTrace();
        } finally {
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
