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
public class TSearchType implements SearchType {

    private SearchField searchField;

    public TSearchType(SearchField searchField) {
        this.searchField = searchField;
    }

    /*private String creaSoundesp(String[] valuesToSearch) {
        StringBuilder sb = new StringBuilder();
        for (String texto : valuesToSearch) {
            String valores[] = texto.split("\\s");            
            sb.append("(select");
            for (String s : valores) {
                if (s.trim().length() > 0) {
                    sb.append(" soundesp('").append(s).append("') || '_' ||");
                }
            }
        }
        if (sb.toString().length() > 0) {
            String res = sb.toString().substring(0, sb.toString().length() - 10);
            return res.concat(")");
        } else {
            return "";
        }

    }*/

    public String getSqlWhere(String valueToSearch, String dicc) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" @@ to_tsquery('").append(dicc).append("',").append(valueToSearch).append(")");
        return sb.toString();
    }

    /*public String getSqlWhereWithConvierte(String valueToSearch) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" @@ to_tsquery('spanish',")
                .append("(select replace( convierte('")
                .append(valueToSearch).append("')")
                .append(",' ','_')))");
        return sb.toString();
    }*/

    public String getSqlWhereWithConvierte(String valueToSearch, String dicc) {
        StringBuilder sb = new StringBuilder();
        sb.append(searchField.getName()).append(" @@ to_tsquery('").append(dicc)
                .append("','")
                .append(valueToSearch).append("')");
        return sb.toString();
    }


}
