/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tablealias.actividadesareas.helpers.ActividadAreaField;
import tablealias.actividadesareas.helpers.AreaColumnItem;
import tablealias.exceptions.AreaGeoInvalidaException;

/**
 *
 * @author INEGI
 */
public class AreaGeoParser implements ParserSqlCreator {

    private String estado;
    private String municipio;
    private String localidad;
    private boolean hasMunicipio;
    private boolean hasLocalidad;

    public AreaGeoParser(String value) throws AreaGeoInvalidaException {
        Pattern regex = Pattern.compile("\\b(\\d{2})(\\d{3})(\\d{4})\\b|\\b(\\d{2})(\\d{3})\\b|\\b(\\d{2})\\b", Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = regex.matcher(value);
        if (matcher.matches()) {
            if (matcher.group(1) == null && matcher.group(4) == null) {
                estado = matcher.group(6);
            } else if (matcher.group(1) == null && matcher.group(6) == null) {
                estado = matcher.group(4);
                municipio = matcher.group(5);
                hasMunicipio = true;
            } else if (matcher.group(4) == null && matcher.group(6) == null) {
                estado = matcher.group(1);
                municipio = matcher.group(2);
                localidad = matcher.group(3);
                hasMunicipio = true;
                hasLocalidad = true;
            }
        } else {
            throw new AreaGeoInvalidaException(this);
        }
    }

    public boolean isValid() {
        int edo = Integer.parseInt(estado);
        return edo >= 0 && edo <= 32;
    }

    @Override
    public String toString() {
        return String.format("estado = %s municipio = %s localidad = %s",
                estado, municipio, localidad);
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
        if (hasMunicipio) {
            sb.append(" and ").append("MUNICIPIO_ID='").append(municipio).append("'");
        }
        if (hasLocalidad) {
            sb.append(" and ").append("LOCALIDAD_ID='").append(localidad).append("'");
        }
        return sb.toString();
    }

    /**
     * @return the colItem
     */
    public AreaColumnItem getColumnItem() {
        String field = null;
        String val = null;

        Map<String, ActividadAreaField> fields = new LinkedHashMap<String, ActividadAreaField>();
        //Map<String,ActividadAreaField> gsonfield = new LinkedHashMap<String, ActividadAreaField>();

        field = "ENTIDAD_ID";
        val = "'" + estado + "'";
        AreaColumnItem aci = new AreaColumnItem();

        fields.put(field, new ActividadAreaField(field, "ENTIDAD", val, estado));
        aci.setGsonData("ENTIDAD", estado);
        //gsonfield.put(field, new ActividadAreaField(field, "ENTIDAD", val, estado));

        if (hasMunicipio) {
            fields.remove(field);
            field = "MUNICIPIO_ID";
            //val = "'" + municipio + "'";
            val = "'" + estado + municipio + "'";
            fields.put(field, new ActividadAreaField(field, "MUNICIPIO", val, estado + municipio));
            //gsonfield.put(field, new ActividadAreaField(field, "MUNICIPIO", val, estado + municipio));
            aci.setGsonData("MUNICIPIO", estado + municipio);
        }
        if (hasLocalidad) {
            fields.remove(field);
            field = "LOCALIDAD_ID";
            //val = "'" + localidad + "'";
            val = "'" + estado + municipio + localidad + "'";
            fields.put(field, new ActividadAreaField(field, "LOCALIDAD", val, estado + municipio + localidad));
            //gsonfield.put(field, new ActividadAreaField(field, "LOCALIDAD", val, estado + municipio + localidad));
            aci.setGsonData("LOCALIDAD", estado + municipio + localidad);
        }
        aci.setFields(fields);
        //aci.setGsonField(gsonfield);
        return aci;
    }
}
