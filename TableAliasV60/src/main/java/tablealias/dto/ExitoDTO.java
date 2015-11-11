/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class ExitoDTO {
    private String operacion;
    private String estatus;
    private String tema;
    private String mapa;
    private String Especial = "";

    /**
     * @return the operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the tema
     */
    public String getTema() {
        return tema;
    }

    /**
     * @param tema the tema to set
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * @return the mapa
     */
    public String getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    /**
     * @return the Especial
     */
    public String getEspecial() {
        return Especial;
    }

    /**
     * @param Especial the Especial to set
     */
    public void setEspecial(String Especial) {
        this.Especial = Especial;
    }
    

}
