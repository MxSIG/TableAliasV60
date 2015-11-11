/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.logging;
/**
 *
 * @author INEGI
 * clase que representa cuando la busqueda fue exitosa
 */
public class BusquedaLogRecord {

    protected String ip;
    protected String coordenadas;
    protected String idSesion;
    protected String criterio;
    protected String tabla;
    protected String gid;
    protected String servlet;
    protected long processingTime;    

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the coordenadas
     */
    public String getCoordenadas() {
        return coordenadas;
    }

    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * @return the idSesion
     */
    public String getIdSesion() {
        return idSesion;
    }

    /**
     * @param idSesion the idSesion to set
     */
    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    /**
     * @return the criterio
     */
    public String getCriterio() {
        return criterio;
    }

    /**
     * @param criterio the criterio to set
     */
    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    /**
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * @return the milliseconds
     */
    public long getProcessingTime() {
        return processingTime;
    }

    /**
     * @param milliseconds the milliseconds to set
     */
    public void setProcessingTime(long milliseconds) {
        this.processingTime = milliseconds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ip).append(":")
                .append("\"").append(coordenadas).append("\"").append(":")
                .append(idSesion).append(":")
                .append("\"").append(criterio).append("\"").append(":")
                .append(tabla).append(":")
                .append(gid).append(":")
                .append(servlet).append(":")
                .append(processingTime);
        return sb.toString();
    }

    /**
     * @return the gid
     */
    public String getGid() {
        return gid;
    }

    /**
     * @param gid the gid to set
     */
    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * @return the servlet
     */
    public String getServlet() {
        return servlet;
    }

    /**
     * @param servlet the servlet to set
     */
    public void setServlet(String servlet) {
        this.servlet = servlet;
    }
}
