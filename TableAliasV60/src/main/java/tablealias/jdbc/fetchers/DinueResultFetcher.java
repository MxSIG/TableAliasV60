/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.dtweb.geo.exceptions.DenueCoordsDaoException;
import tablealias.dinue.helpers.TableCoords;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class DinueResultFetcher implements QueryFetcher {

    private Table table;

    public DinueResultFetcher(Table table) {
        this.table = table;
    }

    private void populateData(Table t, ResultSet rs) throws SQLException {
        TableFields fields = t.getFields();
        //Field campillo = null;        
        for (int x = 0; x < fields.size(); x++) {
            Field f = fields.get(x);
            //if(f.isConsultaDisplay())
                f.setValue(rs.getString(x + 1) == null ? "" : rs.getString(x + 1));
        }
    }

    public Object fetchResults(ResultSet rs) throws SQLException {
        List<Table> tableData = new ArrayList<Table>();
        while (rs.next()) {
            Table t = (Table) table.clone();
            //t.getFields().
            populateData(t, rs);
            tableData.add(t);
        }
        /*if (tableData.size() > 0) {
            TableCoords tc = new TableCoords();
            try {
                tc.putCoords(tableData);
            } catch (DenueCoordsDaoException ex) {
                ex.printStackTrace();
            }
        }*/
        return (Table[]) tableData.toArray(new Table[tableData.size()]);
    }
}
