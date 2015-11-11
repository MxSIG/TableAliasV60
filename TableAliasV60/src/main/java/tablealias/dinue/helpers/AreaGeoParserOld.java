/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

import tablealias.exceptions.AreaGeoInvalidaException;



/**
 *
 * @author INEGI
 */
public class AreaGeoParserOld implements ParserSqlCreator{
    private String estado;
    private String municipio;
    private String localidad;
    private boolean hasMunicipio;
    private boolean hasLocalidad;

    public AreaGeoParserOld(String value) throws AreaGeoInvalidaException {
        if(value.length() >= 2){
            estado = value.substring(0, 2);
        }
        if(value.length() >= 5){
            municipio = value.substring(2, 5);
            hasMunicipio = true;
        }
        if(value.length() == 9){
            localidad = value.substring(5, 9);
            hasLocalidad = true;
        }
        isValid();
    }

    private void isValid() throws AreaGeoInvalidaException{
        boolean exito = true;
        if(estado == null || (hasMunicipio && municipio.length() != 3)
                || (hasLocalidad && localidad.length() != 4))
            exito = false;
        /*if(!exito)
            throw new AreaGeoInvalidaException(this);*/
    }

    @Override
    public String toString() {
        return String.format("estado = %s municipio = %s localidad = %s",
                estado,municipio,localidad);
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the hasMunicipio
     */
    public boolean hasMunicipio() {
        return hasMunicipio;
    }

    /**
     * @return the hasLocalidad
     */
    public boolean hasLocalidad() {
        return hasLocalidad;
    }

    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("ENTIDAD_ID='").append(estado).append("' ");
        if(hasMunicipio)
            sb.append(" and ").append("MUNICIPIO_ID='").append(municipio).append("'");
        if(hasLocalidad)
            sb.append(" and ").append("LOCALIDAD_ID='").append(localidad).append("'");
        return sb.toString();
    }



}
