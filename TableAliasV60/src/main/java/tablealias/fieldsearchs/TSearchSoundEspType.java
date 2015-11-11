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
public class TSearchSoundEspType  implements SearchType{

    private SearchField searchField;

    public TSearchSoundEspType(SearchField searchField) {
        this.searchField = searchField;
    }

    public String getSqlWhere(String valueToSearch, String dicc) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" @@ to_tsquery('spanish',").append(valueToSearch).append(")");
        return sb.toString();
    }

    public String getSqlWhereWithConvierte(String valueToSearch, String dicc) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" @@ to_tsquery('spanish',")
                .append("(select replace( snsoundesp('")
                .append(valueToSearch).append("')")
                .append(",' ','_')))");
        return sb.toString();
    }

}
