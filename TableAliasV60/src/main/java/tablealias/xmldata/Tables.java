/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.xmldata;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author INEGI
 */
public class Tables {
    private List<Table> tables = new LinkedList<Table>();
    
    public void addTable(Table t){
        tables.add(t);
    }
    
    public List<Table> getTables(){
        return tables;
    }
    
}
