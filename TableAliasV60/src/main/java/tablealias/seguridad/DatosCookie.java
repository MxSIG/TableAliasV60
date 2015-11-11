/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.seguridad;

/**
 *
 * @author INEGI
 */
import java.io.Serializable;

/**
 *
 * @author INEGI
 */
public class DatosCookie implements Serializable {

    private String sistema;
    private String s;
    private String login;
    private String accDrgna;
    private String nomUsuario;
    private String apeUsuario;
    private String correoUsuario;
    private String direccion;

    public DatosCookie(String valor) {
        String[] campos = valor.split("&");
        for (String campo : campos) {
            String[] dato = campo.split("=");
            if (dato.length > 1) {
                if ("sistema".equals(dato[0])) {
                    sistema = dato[1];
                } else if ("s".equals(dato[0])) {
                    s = dato[1];
                } else if ("login".equals(dato[0])) {
                    login = dato[1];
                } else if ("accDrgna".equals(dato[0])) {
                    accDrgna = dato[1];
                } else if ("nomUsuario".equals(dato[0])) {
                    nomUsuario = dato[1];
                } else if ("apeUsuario".equals(dato[0])) {
                    apeUsuario = dato[1];
                } else if ("correoUsuario".equals(dato[0])) {
                    correoUsuario = dato[1];
                } else if ("direccion".equals(dato[0])) {
                    direccion = dato[1];
                }
            }
        }

    }

    /**
     * @return the sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    /**
     * @return the s
     */
    public String getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(String s) {
        this.s = s;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the accDrgna
     */
    public String getAccDrgna() {
        return accDrgna;
    }

    /**
     * @param accDrgna the accDrgna to set
     */
    public void setAccDrgna(String accDrgna) {
        this.accDrgna = accDrgna;
    }

    /**
     * @return the nomUsuario
     */
    public String getNomUsuario() {
        return nomUsuario;
    }

    /**
     * @param nomUsuario the nomUsuario to set
     */
    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    /**
     * @return the apeUsuario
     */
    public String getApeUsuario() {
        return apeUsuario;
    }

    /**
     * @param apeUsuario the apeUsuario to set
     */
    public void setApeUsuario(String apeUsuario) {
        this.apeUsuario = apeUsuario;
    }

    /**
     * @return the correoUsuario
     */
    public String getCorreoUsuario() {
        return correoUsuario;
    }

    /**
     * @param correoUsuario the correoUsuario to set
     */
    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
