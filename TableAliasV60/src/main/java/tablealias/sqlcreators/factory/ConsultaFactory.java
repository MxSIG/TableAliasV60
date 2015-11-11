package tablealias.sqlcreators.factory;

import tablealias.jdbc.SqlExecutor;
import tablealias.jdbc.fetchers.ConsultaIdentificablesResultFetcher;
import tablealias.jdbc.fetchers.ConsultaResultFetcher;
import tablealias.jdbc.fetchers.ConsultaTotalesResultFetcher;
import tablealias.sqlcreators.ConsultaGidSqlCreator;
import tablealias.sqlcreators.ConsultaIdentificablesSqlCreator;
import tablealias.sqlcreators.ConsultaSqlCreator;
import tablealias.sqlcreators.ConsultaTotalesSqlCreator;
import tablealias.sqlcreators.SqlCreator;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.utils.Polygon;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class ConsultaFactory {

	public static SqlExecutor getSqlExecutor(Table table, Polygon polygon,
			String where, String gid, int pageToView, int recordsPerView,
			boolean gidSearch, String subProject, boolean identificables) {
		SqlCreator sqlMaker = null;
		QueryFetcher fetcher = null;
		if (gidSearch) {
			sqlMaker = new ConsultaGidSqlCreator(table, polygon, gid,
					subProject);
			fetcher = new ConsultaResultFetcher(table);

		} else if (table.isProcesaTotales()) {
			sqlMaker = new ConsultaTotalesSqlCreator(table, polygon, where,
					subProject);
			fetcher = new ConsultaTotalesResultFetcher(table);
		} else if (identificables) {
			sqlMaker = new ConsultaIdentificablesSqlCreator(table, polygon,
					where, subProject);
			fetcher = new ConsultaIdentificablesResultFetcher(table);
		} else {
			sqlMaker = new ConsultaSqlCreator(table, polygon, where, subProject);
			fetcher = new ConsultaResultFetcher(table);
		}
		sqlMaker.setPageToView(pageToView);
		sqlMaker.setRecordsPerView(recordsPerView);
		SqlExecutor sqlExec = new SqlExecutor(sqlMaker);
		sqlExec.setFetcher(fetcher);
		return sqlExec;
	}

}
