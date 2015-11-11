package tablealias.sqlworkers.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author INEGI
 */
public class TSearchConvierte {

    public String[] processString(Connection conn, String[] valueToSearch, String cfunction ) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        ResultSet rs = null;
        String s[] = new String[valueToSearch.length];


        try {
            String sql = "select ";
            int i = 0;
            for (String p : valueToSearch) {
                sql = sql + " " + cfunction + "(?)" + " as texto" + i + ", ";
                i++;
            }
            sql = sql.substring(0, sql.length() - 2);
            //System.out.println("SQL: " + sql);
            stmt = conn.prepareStatement(sql);
            i = 1;
            for (String p : valueToSearch) {
                stmt.setString(i++, p);
            }
            rset = stmt.executeQuery();

            if (rset.next()) {
                i = 0;
                for (String p : valueToSearch) {
                    s[i] = rset.getString("texto" + i);
                    i++;
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rset != null) {
                rset.close();
            }
        }
        return s;
    }

    public String processString(Connection conn, String valueToSearch, String cfunction ) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rset = null;
        ResultSet rs = null;
        String s = null;


        try {
            String sql = "select ";
            sql = sql + " replace(" + cfunction + "(?),' ','_') as texto ";
            //System.out.println("SQL: " + sql);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, valueToSearch.replaceAll("'", " "));
            rset = stmt.executeQuery();

            if (rset.next()) {
                s=rset.getString("texto");
            }
            //System.out.println("s: " + s);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rset != null) {
                rset.close();
            }
        }
        return s;
    }
}
