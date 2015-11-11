package tablealias.utils;

import mx.gob.inegi.geografia.sistemas.dtweb.webservices.Registrar;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author INEGI
 */
public class Correo {

    /** Creates a new instance of Correo */
    public Correo() {
    }        
            
    public static void envia(Registrar reg) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("10.1.8.102");
            email.addTo("aaron.villar@inegi.gob.mx");
            //email.addCc("alberto.reyes@inegi.gob.mx");
            email.addCc("brenda.munoz@inegi.org.mx");
            email.addCc("felipe.martin@inegi.org.mx");
            email.addCc("beatriz.lopez@inegi.org.mx");
            //email.addCc("susana.martinez@inegi.gob.mx");
            //email.addCc("victor.zamarripa@inegi.org.mx");
            email.setFrom("mdm.denue@inegi.gob.mx");
            email.setSubject("Error en tablealias");
            String msg = String.format("%nServidor %s, pathInfo %s%nParametros %s ",reg.getNomServidor(), reg.getPagAccesa(), reg.getParametros());
            email.setMsg("Categoria: " + reg.getCategoria() + " , Descripcion " + reg.getDescripcion() + msg + " \n Navegador:" + reg.getExplorador());
            email.send();
            
        } catch (EmailException ex1) {
            ex1.printStackTrace();
        }   
            
    }        
    public static void envia(Registrar reg, String adicional) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("10.1.8.102");
            email.addTo("aaron.villar@inegi.gob.mx");
            //email.addCc("alberto.reyes@inegi.gob.mx");
            email.addCc("brenda.munoz@inegi.org.mx");
            email.addCc("felipe.martin@inegi.org.mx");
            email.addCc("beatriz.lopez@inegi.org.mx");
            email.addCc(adicional); 
            //email.addCc("susana.martinez@inegi.gob.mx");
            //email.addCc("victor.zamarripa@inegi.org.mx");
            email.setFrom("mdm.denue@inegi.gob.mx");
            email.setSubject("Error en tablealias");
            String msg = String.format("%nServidor %s, pathInfo %s%nParametros %s ",reg.getNomServidor(), reg.getPagAccesa(), reg.getParametros());
            email.setMsg("Categoria: " + reg.getCategoria() + " , Descripcion " + reg.getDescripcion() + msg + " \n Navegador:" + reg.getExplorador());
            email.send();
            
        } catch (EmailException ex1) {
            ex1.printStackTrace();
        }   
            
    }            
    public static void envia(Registrar reg, String usuario, String ip) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("10.1.8.102");
            email.addTo("aaron.villar@inegi.gob.mx");
            email.addCc("alberto.reyes@inegi.gob.mx");
            email.addCc("emma.gutierrez@inegi.gob.mx");
            email.addCc("susana.martinez@inegi.gob.mx");
            email.addCc("victor.zamarripa@inegi.org.mx");
            email.setFrom("yan.luevano@inegi.gob.mx");
            email.setSubject("Error en tablealias");
            String msg = String.format("%nServidor %s, pathInfo %s%nParametros %s ",reg.getNomServidor(), reg.getPagAccesa(), reg.getParametros());
            email.setMsg("Categoria: " + reg.getCategoria() + " , Descripcion " + reg.getDescripcion() + msg + " \n Navegador:" + reg.getExplorador() +
                    " \n Usuario :" + usuario);
            email.send();

        } catch (EmailException ex1) {
            ex1.printStackTrace();
        }
    }
    
/*    public static void envia(Registrar reg, String usuario) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName("10.1.8.102");
            email.addTo("aaron.villar@inegi.gob.mx");
            email.addCc("alberto.reyes@inegi.gob.mx");
            email.addCc("emma.gutierrez@inegi.gob.mx");
            email.addCc("susana.martinez@inegi.gob.mx");
            email.addCc("victor.zamarripa@inegi.org.mx");
            email.setFrom("yan.luevano@inegi.gob.mx");
            email.setSubject("Error en tablealias");
            String msg = String.format("%nServidor %s, pathInfo %s%nParametros %s ",reg.getNomServidor(), reg.getPagAccesa(), reg.getParametros());
            email.setMsg("Categoria: " + reg.getCategoria() + " , Descripcion " + reg.getDescripcion() + msg + " \n Navegador:" + reg.getExplorador() +
                    " \n Usuario :" + usuario);
            email.send();

        } catch (EmailException ex1) {
            ex1.printStackTrace();
        }
    }*/
}

