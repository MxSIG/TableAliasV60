/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.encabezado;

import java.io.Serializable;

/**
 *
 * @author INEGI
 */
public class Encabezado implements Serializable {

    private String encabezadoSistema;
    private String pieSistema;
    private String cintillo;
    private String franjaIzquierda;
    private String tituloSubtitulo;

    /**
     * @return the encabezadoSistema
     */
    public String getEncabezadoSistema() {
        return encabezadoSistema;
    }

    /**
     * @param encabezadoSistema the encabezadoSistema to set
     */
    public void setEncabezadoSistema(String encabezadoSistema) {
        this.encabezadoSistema = encabezadoSistema;
    }

    /**
     * @return the pieSistema
     */
    public String getPieSistema() {
        return pieSistema;
    }

    /**
     * @param pieSistema the pieSistema to set
     */
    public void setPieSistema(String pieSistema) {
        this.pieSistema = pieSistema;
    }

    /**
     * @return the cintillo
     */
    public String getCintillo() {
        return cintillo;
    }

    /**
     * @param cintillo the cintillo to set
     */
    public void setCintillo(String cintillo) {
        this.cintillo = cintillo;
    }

    /**
     * @return the franjaIzquierda
     */
    public String getFranjaIzquierda() {
        return franjaIzquierda;
    }

    /**
     * @param franjaIzquierda the franjaIzquierda to set
     */
    public void setFranjaIzquierda(String franjaIzquierda) {
        this.franjaIzquierda = franjaIzquierda;
    }

    /**
     * @return the tituloSubtitulo
     */
    public String getTituloSubtitulo() {
        return tituloSubtitulo;
    }

    /**
     * @param tituloSubtitulo the tituloSubtitulo to set
     */
    public void setTituloSubtitulo(String tituloSubtitulo) {
        this.tituloSubtitulo = tituloSubtitulo;
    }
}
