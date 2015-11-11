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
public class ConsultaIdentificablesResultFetcher implements QueryFetcher {

	private Table table;

	public ConsultaIdentificablesResultFetcher(Table table) {
		this.table = table;
	}

	private void populateData(Table t, ResultSet rs) throws SQLException {
		TableFields fields = t.getFields();
		Field campillo = null;
		for (Field f : fields) {
			if (f.isIdentificable())
				f.setValue(rs.getString(f.getSqlName()));
		}
	}

	public Object fetchResults(ResultSet rs) throws SQLException {
		List<Table> tableData = new ArrayList<Table>();
		while (rs.next()) {
			Table t = (Table) table.clone();
			populateData(t, rs);
			tableData.add(t);
		}
		return (Table[]) tableData.toArray(new Table[tableData.size()]);
	}
}
