package tablealias.sqlcreators;

import tablealias.utils.Polygon;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 * 
 */
public class ConsultaIdentificablesSqlCreator extends ConsultaSqlCreator {

	public ConsultaIdentificablesSqlCreator(Table table, Polygon polygon,
			String where, String subProject) {
		super(table, polygon, where, subProject);
	}

	@Override
	protected String getFieldsSql(Table table) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Field f : table.getFields()) {
			if (f.isIdentificable())
				sb.append(f.getName()).append(" as ").append(f.getSqlName())
						.append(",");
			else {
				if (f.hasFunctions() && f.isConsultaDisplay())
					sb.append(getFieldSqlWithFunctions(f)).append(" as ")
							.append(f.getSqlName()).append(",");
			}
		}
		if (sb.length() > 0)
			return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
		else
			throw new Exception("No hay campos identificables");
	}

}
