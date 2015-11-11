/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class LocUrbanas {

    private String cve_loc;
    private String nom_loc;
    private String cve_mun;
    private String cve_ent;
    private String geom;

    /**
     * @return the cve_loc
     */
    public String getCve_loc() {
        return cve_loc;
    }

    /**
     * @param cve_loc the cve_loc to set
     */
    public void setCve_loc(String cve_loc) {
        this.cve_loc = cve_loc;
    }

    /**
     * @return the nom_loc
     */
    public String getNom_loc() {
        return nom_loc;
    }

    /**
     * @param nom_loc the nom_loc to set
     */
    public void setNom_loc(String nom_loc) {
        this.nom_loc = nom_loc;
    }

    /**
     * @return the cve_mun
     */
    public String getCve_mun() {
        return cve_mun;
    }

    /**
     * @param cve_mun the cve_mun to set
     */
    public void setCve_mun(String cve_mun) {
        this.cve_mun = cve_mun;
    }

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
