/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.actividadesareas.helpers;

/**
 *
 * @author INEGI
 */
public class ActividadRowItem {

    private transient String sqlField;
    private transient String sqlValue;
    private String field;
    private String value;
    //private String valueFromDb;
    

    /**
     * @return the field
     */
    public String getSqlField() {
        return sqlField;
    }

    /**
     * @param field the field to set
     */
    public void setSqlField(String field) {
        this.sqlField = field;
    }

    public void setGsonData(String field, String value){
        this.field = field;
        if (value.equals("00")) value="Todas";
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getParamValue() {
        return sqlValue;
    }


    /**
     * @return the value
     */
    public void setParamValue(String s) {
        sqlValue = s;
    }

    /**
     * @param value the value to set
     */
    public void setSqlValue(String value) {
        this.sqlValue = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ActividadRowItem) {
            ActividadRowItem tmp = (ActividadRowItem) obj;
            return (tmp.getSqlField().equalsIgnoreCase(this.sqlField) && tmp.getParamValue().equalsIgnoreCase(this.sqlValue));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return sqlField.hashCode() + sqlValue.hashCode();
    }


}
