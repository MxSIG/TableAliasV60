/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class Estados {

    private String cve_ent;
    private String nom_ent;
    private String geom;

    /**
     * @return the cve_ent
     */
    public String getCve_ent() {
        return cve_ent;
    }

    /**
     * @param cve_ent the cve_ent to set
     */
    public void setCve_ent(String cve_ent) {
        this.cve_ent = cve_ent;
    }

    /**
     * @return the nom_ent
     */
    public String getNom_ent() {
        return nom_ent;
    }

    /**
     * @param nom_ent the nom_ent to set
     */
    public void setNom_ent(String nom_ent) {
        this.nom_ent = nom_ent;
    }

    /**
     * @return the geom
     */
    public String getGeom() {
        return geom;
    }

    /**
     * @param geom the geom to set
     */
    public void setGeom(String geom) {
        this.geom = geom;
    }


}
