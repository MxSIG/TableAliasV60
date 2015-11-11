/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tablealias.dao.BufferDAO;
import tablealias.dataformatters.DataFormatter;
import tablealias.dataformatters.FormatterFactory;
import tablealias.utils.ResultFormat;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Server;

/**
 *
 * @author INEGI
 */
public class BorrarBufferCE extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();
        TablasServidor tablasServidor = (TablasServidor) context.getAttribute("tablasServidor");
        try {            
            ResultFormat formatType = ResultFormat.JSON;
            DataFormatter<String> formatter = FormatterFactory.<String>getFormatter(formatType, request);
            response.setContentType(formatter.getContentType());
            boolean result = false;
            Server server = tablasServidor.getFoundServer();
            String id = request.getParameter("idbuffer");
            BufferDAO Buffer = new BufferDAO();
            result = Buffer.deleteGeometryCE(id, server);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", result);
            Gson regresa = new Gson();
            String resultado = regresa.toJson(map);
            out.println(resultado);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
