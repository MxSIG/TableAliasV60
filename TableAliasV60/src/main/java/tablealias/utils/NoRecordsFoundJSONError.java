/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.utils;

/**
 *
 * @author INEGI
 */
public class NoRecordsFoundJSONError {

    private int totalFields;
    private String aliasUsuario;

    public NoRecordsFoundJSONError(String aliasUsuario) {
        totalFields = 0;
        this.aliasUsuario = aliasUsuario;
    }



}
