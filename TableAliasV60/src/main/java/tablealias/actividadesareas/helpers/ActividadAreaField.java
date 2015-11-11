/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.actividadesareas.helpers;

/**
 *
 * @author INEGI
 */
public class ActividadAreaField {
    private final transient String fieldName;
    private final String fieldDisplayName;
    private final transient String value;    
    private final String displayValue;

    public ActividadAreaField(String fieldName, String fieldDisplayName, String value, String displayValue) {
        this.fieldName = fieldName;
        this.fieldDisplayName = fieldDisplayName;
        this.value = value;
        this.displayValue = displayValue;        
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return the fieldDisplayName
     */
    public String getFieldDisplayName() {
        return fieldDisplayName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the displayValue
     */
    public String getDisplayValue() {
        return displayValue;
    }



}
