package tablealias.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.inegi.dtweb.connection.ConnectionManager;
import mx.inegi.dtweb.connection.DebugerLog;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import tablealias.dto.FieldType;
import tablealias.sqlcreators.SqlCreator;
import tablealias.sqlworkers.QueryFetcher;

/**
 *
 * @author INEGI
 */
public class SqlExecutor {

    private SqlCreator sqlCreator;
    private QueryFetcher fetcher;
    private Connection conn;

    public SqlExecutor(SqlCreator sqlCreator) {
        this.sqlCreator = sqlCreator;
    }

    public Object executeQuery(Connection conn) throws Exception {
        Statement cstmt = null;
        ResultSet rs = null;
        try {
            this.conn = conn;
            String sql = sqlCreator.getSql();
            DebugerLog.log("consulta: " + sql);
            cstmt = conn.createStatement();
            try {
                rs = cstmt.executeQuery(sql);
            } catch ( SQLException sqle ) {
                if( sqle.getErrorCode() == 933 ){//patch for Oracle
                    sql = sql.substring( 0, sql.indexOf( "offset" ) );
                    rs = cstmt.executeQuery( sql );
                } else {
                    throw sqle;
                }
            }
            if (fetcher == null) {
                throw new NullPointerException("fetcher es nulo, consulta: " + sql);
            }
            return fetcher.fetchResults(rs);
        } finally {
            GenericResourceManager.close(rs);
            GenericResourceManager.close(cstmt);
            ConnectionManager.closeConnection(conn);
            //conn.close();
            //GenericResourceManager.close(conn);
        }
        //return (Table[])tableData.toArray(new Table[tableData.size()]);
    }

    public int getNumberOfRecords(Connection conn) throws Exception {
        Statement cstmt = null;
        ResultSet rs = null;
        int total = 0;
        try {
            cstmt = conn.createStatement();
            String sql = sqlCreator.getNumberOfRecordsSql();
            //"consulta: " + sql);
            if (!sql.startsWith("select")) {
                total = Integer.parseInt(sql);
            } else {
                rs = cstmt.executeQuery(sql);
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } finally {
            GenericResourceManager.close(rs);
            GenericResourceManager.close(cstmt);
            GenericResourceManager.close(conn);
        }
        return total;
    }
    
    public List< FieldType > getTypesOfRecords(Connection conn) throws Exception {
        List< FieldType > typesOfRecords = null;
        try {
            this.conn = conn;
            String sql = sqlCreator.getTypesOfRecordsSQL();
            if( sql != null ){
                DebugerLog.log("consulta: " + sql);
                QueryRunner qr = new QueryRunner();
                ResultSetHandler rsh = new BeanListHandler(FieldType.class);
                typesOfRecords = (List< FieldType>) qr.query(conn, sql, rsh);
            }
            if( typesOfRecords == null || typesOfRecords.isEmpty() ){
                typesOfRecords = new ArrayList< FieldType>();
                FieldType fieldType = new FieldType("");
                typesOfRecords.add(fieldType);
            }
            return typesOfRecords;
        } finally {
            ConnectionManager.closeConnection(conn);
        }
    }

    /**
     * @return the fetcher
     */
    public QueryFetcher getFetcher() {
        return fetcher;
    }

    /**
     * @param fetcher the fetcher to set
     */
    public void setFetcher(QueryFetcher fetcher) {
        this.fetcher = fetcher;
    }
}
