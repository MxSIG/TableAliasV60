/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class BusquedaResultFetcher implements QueryFetcher {

    private Table table;

    public BusquedaResultFetcher(Table table) {
        this.table = table;
    }


    private void populateData(Table t, ResultSet rs) throws SQLException{
        TableFields fields = t.getFields();
        for(int x = 0; x < fields.size(); x++){
        //for(Field f: fields){
            Field f = fields.get(x);
            if(f.isBusquedaDisplay()){
                if(f.hasPredato())
                    f.setValue(f.getPredato() + rs.getString(x+1));
                else
                    f.setValue(rs.getString(x+1));
            }
        }
    }

    public Object fetchResults(ResultSet rs) throws SQLException{
        List<Table> tableData = new ArrayList<Table>();
        System.out.println("Sacando datos");
        while (rs.next()) {
            System.out.print("11");
            Table t = (Table) table.clone();
            populateData(t, rs);
            tableData.add(t);
        }
        return (Table[])tableData.toArray(new Table[tableData.size()]);
    }
}
