/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class FilteredTable {
    private static String[] tables = new String[]{"establecimientos_r","establecimientos","mzaspendientes","idfmanzanas"};

    public static boolean isTableFiltered(String table){
        boolean isT = false;
        for(String t:tables){
            if (t.equalsIgnoreCase(table)){
                isT = true;
                break;
            }
        }
        return isT;
    }
}
