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
public class ConsultaResultFetcher implements QueryFetcher {

	private Table table;

	public ConsultaResultFetcher(Table table) {
		this.table = table;
	}

	private void populateData(Table t, ResultSet rs) throws SQLException {
		TableFields fields = t.getFields();
		Field campillo = null;
		// boolean remover = false;
		for (Field f : fields) {
			if (f.isConsultaDisplay()) {
				if (f.hasFunctions()){
					String alias = f.getAliasName().replaceAll(" ", "");
					f.setValue(rs.getString(alias));
				} else if (f.hasPredato())
					f.setValue(f.getPredato() + rs.getString(f.getSqlName()));
				else if (rs.getString(f.getSqlName()) == null) {
					f.setValue("");
				} else {
					f.setValue(rs.getString(f.getSqlName()));
				}
			} else if (f.getAliasName().equalsIgnoreCase("Ubicación")
					|| f.getAliasName().equalsIgnoreCase("Ubicacion")) {
				campillo = f;
			}
		}
		if (campillo != null)
			fields.removeField(campillo);
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
