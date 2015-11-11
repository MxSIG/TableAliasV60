/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class BusquedaExceptionSearchDto {

    private String calle;
    private String entidad;
    private String calle2;
    private String entidad2;
    private String the_geom;
    private String punto;

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the entidad
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the calle2
     */
    public String getCalle2() {
        return calle2;
    }

    /**
     * @param calle2 the calle2 to set
     */
    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    /**
     * @return the entidad2
     */
    public String getEntidad2() {
        return entidad2;
    }

    /**
     * @param entidad2 the entidad2 to set
     */
    public void setEntidad2(String entidad2) {
        this.entidad2 = entidad2;
    }

    /**
     * @return the the_geom
     */
    public String getThe_geom() {
        return the_geom;
    }

    /**
     * @param the_geom the the_geom to set
     */
    public void setThe_geom(String the_geom) {
        this.the_geom = the_geom;
    }

    /**
     * @return the punto
     */
    public String getPunto() {
        return punto;
    }

    /**
     * @param punto the punto to set
     */
    public void setPunto(String punto) {
        this.punto = punto;
    }

}
