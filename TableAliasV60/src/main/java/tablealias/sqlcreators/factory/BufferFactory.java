package tablealias.sqlcreators.factory;

import tablealias.jdbc.SqlExecutor;
import tablealias.jdbc.fetchers.BufferResultFetcher;
import tablealias.sqlcreators.BufferCoordsSqlCreator;
import tablealias.sqlcreators.BufferSqlCreator;
import tablealias.sqlcreators.SqlCreator;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class BufferFactory {

    public static SqlExecutor getSqlExecutor(Table table, double size, String[] gids, boolean hasCoords,int pageToView, int recordsPerView, double x1, double x2, String subProject ) {
        SqlCreator sqlMaker = null;
        QueryFetcher fetcher = null;
        if (hasCoords) {
            sqlMaker = new BufferCoordsSqlCreator(table, size, gids, x1, x2, subProject );
            fetcher = new BufferResultFetcher();//BufferCoordsResultFetcher();

        } else {
            sqlMaker = new BufferSqlCreator(table, size, gids, subProject );
            fetcher = new BufferResultFetcher();
        }
        sqlMaker.setPageToView(pageToView);
        sqlMaker.setRecordsPerView(recordsPerView);
        SqlExecutor sqlExec = new SqlExecutor(sqlMaker);
        sqlExec.setFetcher(fetcher);
        return sqlExec;
    }

}
