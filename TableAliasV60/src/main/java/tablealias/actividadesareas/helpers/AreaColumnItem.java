/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.actividadesareas.helpers;

import java.util.Map;

/**
 *
 * @author INEGI
 */
public class AreaColumnItem {

    private String field;
    private String value;

    private transient Map<String,ActividadAreaField> sqlFields;

    public void setFields(Map<String,ActividadAreaField> fields) {
        this.sqlFields = fields;
    }
   
    public void setGsonData(String field, String value){
        this.field = field;
        if (value.equals("99") || value.equals("00")) value="Todas";
        this.value = value;
    }

    /*@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof AreaColumnItem) {
            AreaColumnItem tmp = (AreaColumnItem) obj;
            return (tmp.getField().equalsIgnoreCase(this.field) && tmp.getValue().equalsIgnoreCase(this.value));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return field.hashCode() + value.hashCode();
    }*/

    /**
     * @return the fields
     */
    public Map<String, ActividadAreaField> getFields() {
        return sqlFields;
    }

   
}
