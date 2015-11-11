/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import mx.gob.inegi.geografia.sistemas.dtweb.webservices.RegistraError;
import mx.gob.inegi.geografia.sistemas.dtweb.webservices.Registrar;
import tablealias.logging.BusquedaLogRecord;
import tablealias.logging.BusquedaNoExitosaLogRecord;
import tablealias.logging.MyLogger;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class StatisticianFilter implements Filter {

    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public StatisticianFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("StatisticianFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
        for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
        String name = (String)en.nextElement();
        String values[] = request.getParameterValues(name);
        int n = values.length;
        StringBuffer buf = new StringBuffer();
        buf.append(name);
        buf.append("=");
        for(int i=0; i < n; i++) {
        buf.append(values[i]);
        if (i < n-1)
        buf.append(",");
        }
        log(buf.toString());
        }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("StatisticianFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed.
	/*
        for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
        String name = (String)en.nextElement();
        Object value = request.getAttribute(name);
        log("attribute: " + name + "=" + value.toString());

        }
         */

        // For example, a filter might append something to the response.
	/*
        PrintWriter respOut = new PrintWriter(response.getWriter());
        respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    private String getCoords(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (request.getParameter("x1") != null) {
            sb.append(request.getParameter("x1")).append(",");
        }
        if (request.getParameter("y1") != null) {
            sb.append(request.getParameter("y1")).append(",");
        }
        if (request.getParameter("x2") != null) {
            sb.append(request.getParameter("x2")).append(",");
        }
        if (request.getParameter("y2") != null) {
            sb.append(request.getParameter("y2"));
        }
        if(sb.lastIndexOf(",") != -1)
            sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.length() > 2 ? sb.toString() : null;
    }

    public String getServlet(String uri){
        int idx = uri.lastIndexOf("/");
        if(idx != -1)
          return uri.substring(idx+1);
        return null;
        /*Pattern regex = Pattern.compile("/(.+?)\\.do", Pattern.DOTALL);
	Matcher regexMatcher = regex.matcher(uritmp);
        if(regexMatcher.find())
            return regexMatcher.group(1);
        return null;*/
    }

    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String sesionId = httpRequest.getSession().getId();
        long ini = System.currentTimeMillis();
        chain.doFilter(request, response);
        long fin = System.currentTimeMillis();
        long tiempo = fin - ini;        
        Boolean exito = null;
        if (request.getAttribute("exito") != null) {
            exito = (Boolean) request.getAttribute("exito");
        }
        if (exito != null) {            
            String ip = request.getRemoteAddr();
            String coordenadas = getCoords(httpRequest);
            //String sesionId = httpRequest.getSession().getId();
            String criteria = null;
            String table = request.getParameter("tabla");// == null ? null : request.getParameter("tabla");
            criteria = request.getParameter("searchCriteria"); //== null ? null :request.getParameter("searchCriteria");
            String gid = request.getParameter("gid");
            BusquedaLogRecord lr = null;
            if (exito) {
                lr = new BusquedaLogRecord();
            } else {
                lr = new BusquedaNoExitosaLogRecord();
                //RegistraError.Registra(getRegistrar(httpRequest, table));
            }
            lr.setCoordenadas(coordenadas);
            lr.setCriterio(criteria);
            lr.setIdSesion(sesionId);
            lr.setIp(ip);
            lr.setGid(gid);
            lr.setProcessingTime(tiempo);
            lr.setTabla(table);            
            lr.setServlet(getServlet(httpRequest.getRequestURI()));
            MyLogger logger = (MyLogger)httpRequest.getSession().getServletContext().getAttribute("logger");
            //MyLogger logger = new MyLogger(httpRequest.getSession().getServletContext().getRealPath("/"));
            //File f = new File(httpRequest.getSession().getServletContext().getRealPath("/") + "WEB-INF\\log-init-file.lfc");
            //f.exists());
            logger.Log(lr);
        }
        /*else
            //"no se hallo exito");*/
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     */
    public void destroy() {
    }

    /**
     * Init method for this filter 
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("StatisticianFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("StatisticianFilter()");
        }
        StringBuffer sb = new StringBuffer("StatisticianFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
