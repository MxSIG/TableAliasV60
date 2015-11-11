/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import tablealias.seguridad.DatosCookie;
import mx.gob.inegi.geografia.sistemas.dtweb.webservices.RegistraError;
import mx.gob.inegi.geografia.sistemas.dtweb.webservices.Registrar;

/**
 *
 * @author INEGI
 */
public class RegistraErrorWebService {

    private static DatosCookie datosCookie = null;

    private static Registrar getRegistrar(HttpServletRequest req, Exception ex) {
        Registrar reg = new Registrar();
        reg.setTipoRegistro("internet");
        reg.setCveAplicacion(RegistraError.cveAplicacion);
        reg.setIISError("0");
        //TablasServidor tablasServidor = (TablasServidor) req.getSession().getServletContext().getAttribute("tablasServidor");
        //if (tablasServidor.tableExists(table)) {
        reg.setNomServidor(req.getLocalName());//max 10 chars
        /*} else {
        reg.setNomServidor("couldnt find server");
        }*/
        String pathInfo = req.getPathInfo() == null ? req.getServletPath() : req.getPathInfo();
        reg.setPagAccesa(pathInfo);
        reg.setPagError("");
        Parametreador p = new Parametreador();
        HashMap<String, String> params = p.dameParametros(req, "ISO-8859-1");
        reg.setParametros(params.toString());
        reg.setCategoria(ex.getClass().getName());
        reg.setDescripcion(ex.getMessage());
        reg.setCOMError("0");
        reg.setIpRequest(req.getRemoteHost());
        if (req.getHeader("TUA") != null) {
            reg.setExplorador(req.getHeader("TUA"));
        } else {
            reg.setExplorador(req.getHeader("User-Agent"));
        }
        reg.setUsuario("REMOTE_USER");

        return reg;
    }

    public static void RegistraError(HttpServletRequest req, Exception ex) {
        Registrar reg = getRegistrar(req, ex);
        RegistraError.Registra(reg);
        if (datosCookie != null) {
            Correo.envia(reg, datosCookie.getLogin());
        } else {
            Correo.envia(reg);
        }
    }

    public static void RegistraError(HttpServletRequest req, Exception ex, String adicional) {
        Registrar reg = getRegistrar(req, ex);
        RegistraError.Registra(reg);
        if (datosCookie != null) {
            Correo.envia(reg, adicional);//datosCookie.getLogin()
        } else {
            Correo.envia(reg, adicional);
        }
    }

    private void sacaUsuario(HttpServletRequest request) {
        String nombreCookie = "InfoUsuarioDenue";
        //Lee todos los headers tipo cookie
        String cook = null;
        Enumeration e = request.getHeaders("cookie");
        if (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            //Buscando la cookie de autenticacion
            String[] cookies = s.split(";");
            for (int i = 0; i < cookies.length; i++) {
                String c = cookies[i].trim();
                if (c.startsWith(nombreCookie)) {
                    cook = c;
                }
            }
        }
        if (cook != null) {
            nombreCookie = "InfoUsuario";
            //Lee todos los headers tipo cookie
            e = request.getHeaders("cookie");
            if (e.hasMoreElements()) {
                String s = (String) e.nextElement();
                //Buscando la cookie de autenticacion
                String[] cookies = s.split(";");
                for (int i = 0; i < cookies.length; i++) {
                    String c = cookies[i].trim();
                    if (c.startsWith(nombreCookie)) {
                        cook = c;
                    }
                }
            }
        }
        //Si existe la cookie continuar el proceso
        if (cook != null) {
            //System.out.println("Cookie: "+cookie);
            //Obteniendo el valor de la cookie
            String valor = cook.substring(nombreCookie.length() + 1);
            //System.out.println("Valor: "+valor);
            datosCookie = new DatosCookie(valor);
        }
    }
}
