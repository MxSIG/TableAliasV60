/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tablealias.sqlworkers.EdoCiudadWrapper;
import tablealias.utils.EdoCiudadData;
import tablealias.utils.HTTPEncodingFormat;
import tablealias.utils.Polygon;
import tablealias.utils.RegistraErrorWebService;
import tablealias.utils.RequestHelper;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class EdoCiudad extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        ServletOutputStream out = null;
        try {
            RequestDispatcher rd = null;
            ServletContext context = getServletContext();
            double x1 = Double.MIN_VALUE;
            double y1 = Double.MIN_VALUE;
            double x2 = Double.MIN_VALUE;
            double y2 = Double.MIN_VALUE;
            if (request.getParameter("x1") != null) {
                x1 = Double.parseDouble(request.getParameter("x1"));
            }
            if (request.getParameter("y1") != null) {
                y1 = Double.parseDouble(request.getParameter("y1"));
            }
            if (request.getParameter("x2") != null) {
                x2 = Double.parseDouble(request.getParameter("x2"));
            }
            if (request.getParameter("y2") != null) {
                y2 = Double.parseDouble(request.getParameter("y2"));
            }
            if (x1 != Double.MIN_VALUE && y1 != Double.MIN_VALUE && x2 != Double.MIN_VALUE && y2 != Double.MIN_VALUE) {

                //String table2Search = request.getParameter("tabla").trim();
                TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
                //ResultFormat formatType = ResultFormat.JSON;
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType(HTTPEncodingFormat.getJsonFormatWEncoding());
                Polygon polygon = new Polygon();
                polygon.setX1(x1);
                polygon.setY1(y1);
                polygon.setX2(x2);
                polygon.setY2(y2);
                EdoCiudadWrapper ecw = new EdoCiudadWrapper();
                EdoCiudadData data = ecw.getData(polygon, tablasServidor);
                Gson gson = new Gson();
                String res = null;
                if (data.hasData()) {
                    data.setTotalFields();
                    res = gson.toJson(data);
                } else {
                    res = String.format("\"totalFields\":\"%d\",%n", 0);
                }
                RequestHelper rh = new RequestHelper(request);
                if (rh.supportsGzip()) {
                    response.setHeader("Content-Encoding", "gzip");
                    out = response.getOutputStream();
                    GZIPOutputStream gzos = new GZIPOutputStream(out);
                    gzos.write(res.getBytes());
                    gzos.close();

                } else {
                    out = response.getOutputStream();
                    out.print(res);
                }
                /* if (tablasServidor.tableExists("c100")) {

                Polygon polygon = new Polygon();
                polygon.setX1(x1);
                polygon.setY1(y1);
                polygon.setX2(x2);
                polygon.setY2(y2);
                QueryWorker qw = new EdoCiudadWrapper();
                qw.setServer(tablasServidor.getFoundServer());
                qw.setTable(tablasServidor.getFoundTable());
                qw.setPolygon(polygon);
                qw.setPageToView(1);
                DataFormatter<String> formatter = FormatterFactory.<String>getFormatter(formatType);
                formatter.setQueryWorker(qw);
                rd = request.getRequestDispatcher("Resultados.jsp");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType(formatter.getContentType());
                out.print(formatter.getData());
                if (formatter.hasErrors()) {
                request.setAttribute("error", formatter.getErrorMsg().replaceAll("\n", "<br>"));
                rd.forward(request, response);
                }
                } else {
                //"No se ha encontrado la tabla " + "c100");
                }*/
            } else {
                rd = request.getRequestDispatcher("Resultados.jsp");
                request.setAttribute("error", "Parametros no validos");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            RegistraErrorWebService.RegistraError(request, ex);
            ex.printStackTrace();
        } finally {
            out.close();
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
