/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.actividadesareas.helpers;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasDto {

    private ActividadRowItem actividad;
    private AreaColumnItem areageo;
    private int total;

    /**
     * @return the actividad
     */
    public ActividadRowItem getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(ActividadRowItem actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the areageo
     */
    public AreaColumnItem getAreageo() {
        return areageo;
    }

    /**
     * @param areageo the areageo to set
     */
    public void setAreageo(AreaColumnItem areageo) {
        this.areageo = areageo;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }



}
