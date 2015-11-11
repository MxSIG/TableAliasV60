/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.fieldsearchs;


/**
 *
 * @author INEGI
 */
public interface SearchType {    
    
    String getSqlWhere(String valueToSearch, String dicc);

    String getSqlWhereWithConvierte(String valueToSearch, String dicc);

}
