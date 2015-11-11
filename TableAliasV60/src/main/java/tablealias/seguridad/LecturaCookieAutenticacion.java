/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.seguridad;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author INEGI
 */
public class LecturaCookieAutenticacion {

    public DatosCookie leeCookieAutenticacion(HttpServletRequest request){
        DatosCookie datosCookie=null;
        String nombreCookie="InfoUsuarioDenue";
        String cookie=null;
        //Lee todos los headers tipo cookie
        Enumeration e=request.getHeaders("cookie");
        if(e.hasMoreElements()){
            String s=(String)e.nextElement();
            //Buscando la cookie de autenticacion
            String[] cookies=s.split(";");
            for (int i = 0; i < cookies.length; i++) {
                String c = cookies[i].trim();
                if(c.startsWith(nombreCookie)){
                    cookie=c;
                }
            }
        }
        //Si existe la cookie continuar el proceso
        if(cookie!=null){
            //System.out.println("Cookie: "+cookie);
            //Obteniendo el valor de la cookie
            String valor=cookie.substring(nombreCookie.length()+1);
            //System.out.println("Valor: "+valor);
            datosCookie=new DatosCookie(valor);
        }
        return datosCookie;
    }

}
