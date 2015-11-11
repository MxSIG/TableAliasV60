/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.fieldsearchs;

import tablealias.xmldata.SearchField;

/**
 *
 * @author INEGI
 */
public class NumericType implements SearchType{

    private SearchField searchField;

    public NumericType(SearchField searchField) {
        this.searchField = searchField;
    }
   
    public String getSqlWhere(String valueToSearch, String dicc) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" = ").append(valueToSearch);
        return sb.toString();
    }

    public String getSqlWhereWithConvierte(String valueToSearch, String dicc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
