/*
 * Parametreador.java
 *
 * Created on 4 de diciembre de 2007, 05:34 PM
 */
package tablealias.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import tablealias.dinue.helpers.Md5Digest;

/**
 *
 * @author INEGI
 */
public class Parametreador {

    /** Creates a new instance of Parametreador */
    public Parametreador() {
    }

    public String dameNombreParametro(String parametro, Enumeration listaParametros) {
        String resultado = "", nombre = "";
        while (listaParametros.hasMoreElements()) {
            nombre = (String) listaParametros.nextElement();
            if (parametro.equalsIgnoreCase(nombre)) {
                resultado = nombre;
                break;
            }
        }
        return resultado;
    }

    public HashMap<String, String> dameParametros(HttpServletRequest request, String encoding) {
        Enumeration listaParametros = request.getParameterNames();
        HashMap<String, String> resultado = new HashMap();
        String nombre = "", valor = "";
        StringBuilder sb = new StringBuilder();
        while (listaParametros.hasMoreElements()) {
            nombre = (String) listaParametros.nextElement();
            try {
                valor = URLEncoder.encode(request.getParameter(nombre), encoding);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            //valor = request.getParameter(nombre);
            resultado.put(nombre, valor);
            sb.append(" ");
            sb.append(nombre);
            sb.append(" :");
            sb.append(valor);
        }
        //System.out.println(sb.toString());
        return resultado;
    }

    public String getDigestedParams(HttpServletRequest request, String encoding) throws NoSuchAlgorithmException {
        Enumeration listaParametros = request.getParameterNames();
        String nombre = "", valor = "";
        StringBuilder sb = new StringBuilder();
        while (listaParametros.hasMoreElements()) {
            nombre = (String) listaParametros.nextElement();
            if (nombre.equalsIgnoreCase("estratos")
                    || nombre.equalsIgnoreCase("actividad")
                    || nombre.equalsIgnoreCase("vars")
                    || nombre.equalsIgnoreCase("areageo")
                    || nombre.equalsIgnoreCase("aespecifica")) {
                try {
                    if (nombre.equalsIgnoreCase("vars")) {
                        String[] x = request.getParameter(nombre).toLowerCase().split(",");
                        StringBuilder sb1 = new StringBuilder();
                        for (String a : x) {
                            sb1.append(a).append(" ");
                        }
                        //System.out.println("parametro: " + nombre + " valor: " + sb1.toString().trim().toLowerCase());
                        valor = URLEncoder.encode(sb1.toString().trim().toLowerCase(), encoding);
                    } else if (nombre.equalsIgnoreCase("aespecifica")) {
                        if (!"0".equalsIgnoreCase(request.getParameter(nombre))){
                            valor = URLEncoder.encode(request.getParameter(nombre), encoding);
                        }
                    }else{
                        //System.out.println("parametro: " + nombre + " valor: " + request.getParameter(nombre));
                        valor = URLEncoder.encode(request.getParameter(nombre), encoding);
                    }


                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                sb.append(nombre);
                sb.append(valor);
            }
        }
        //System.out.println(sb.toString());
        return Md5Digest.digest(sb.toString());
    }
}
