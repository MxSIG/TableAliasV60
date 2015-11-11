/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import tablealias.sqlworkers.QueryFetcher;

/**
 *
 * @author INEGI
 */
public class SimpleSqlExecutor {

    private QueryFetcher fetcher;

    public Object executeQuery(Connection conn, String sql) throws Exception {
        Statement cstmt = null;
        ResultSet rs = null;
        try {
            cstmt = conn.createStatement();
            //"consulta: " + sql);
            rs = cstmt.executeQuery(sql);
            if (fetcher == null) {
                throw new NullPointerException("fetcher es nulo, consulta: " + sql);
            }
            return fetcher.fetchResults(rs);
        } finally {
            GenericResourceManager.close(rs);
            GenericResourceManager.close(cstmt);
            //GenericResourceManager.close(conn);
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
