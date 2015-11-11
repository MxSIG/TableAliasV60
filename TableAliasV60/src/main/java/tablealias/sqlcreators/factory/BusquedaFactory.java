package tablealias.sqlcreators.factory;

import tablealias.jdbc.SqlExecutor;
import tablealias.jdbc.fetchers.BusquedaResultFetcher;
import tablealias.sqlcreators.SqlCreator;
import tablealias.sqlcreators.BusquedaExceptionSearchSqlCreator;
import tablealias.sqlcreators.BusquedaSqlCreator;
import tablealias.sqlcreators.BusquedaSqlCreatorLimit;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.utils.ExceptionSearchParser;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class BusquedaFactory {

    public static SqlExecutor getSqlExecutor(Table table, String[] valueToSearch, boolean exceptionSearch, int pageToView, int recordsPerView, ExceptionSearchParser esp, String where, String subProject, String whereTipo ) {
        SqlCreator sqlMaker = null;
        QueryFetcher fetcher = null;
        whereTipo = createWhere( table, whereTipo );
        if (exceptionSearch) {
            sqlMaker = new BusquedaExceptionSearchSqlCreator(table, esp, where, subProject, whereTipo );
            //fetcher = new BusquedaExceptionResultFetcher(table);
            fetcher = new BusquedaResultFetcher(table);
            System.out.println("Es exception");
        } else if ("cNumExt".equalsIgnoreCase(table.getAlias())){
            sqlMaker = new BusquedaSqlCreatorLimit(table, valueToSearch, where, subProject, whereTipo );
            fetcher = new BusquedaResultFetcher(table);
        }else{
            sqlMaker = new BusquedaSqlCreator(table, valueToSearch, where, subProject, whereTipo );
            fetcher = new BusquedaResultFetcher(table);
        }
        sqlMaker.setPageToView(pageToView);
        sqlMaker.setRecordsPerView(recordsPerView);
        SqlExecutor sqlExec = new SqlExecutor(sqlMaker);
        sqlExec.setFetcher(fetcher);
        return sqlExec;
    }

    private static boolean validate( String whereTipo ){
        if( !( whereTipo == null || whereTipo.trim().isEmpty() ) && whereTipo.trim().length() > 3 ){
            return true;
        }
        return false;
    }

//    private static String createWhere(Table table, String where ) {
//        if( validate( where ) ){
//            for (Field field : table.getTypeFields()) {
//                return field.getName() + "='" + where.trim() + "'";
//            }
//        }
//        return "1=1";
//    }
    
    private static String createWhere(Table table, String where ) {
        if( validate( where ) ){
         //   for (Field field : table.getTypeFields()) {
                return "tipo" + "='" + where.trim() + "'";
           // }
        }
		return "1=1";
    }
}
