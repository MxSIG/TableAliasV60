/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import java.sql.Connection;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.Connectable;
import mx.inegi.dtweb.connection.PoolManager;

/**
 *
 * @author INEGI
 */
public class ConnectionManagerOLD {

    public static Connection getConnection(Connectable conn) throws SQLException {
        Connection con = null;
        try {
            int i = 0;
            while (i < 3) {
                con = PoolManager.getConnection(conn);
                if (con != null) {
                    con.setReadOnly(true);
                    break;
                }
                System.out.println("Trying to get a connection: " + i + " time(s)...");
                i++;
            }
            if (con == null) {
                throw new SQLException("Connection timed out after 3 tries on " + conn.getServer() + ":" + conn.getPort() + "/" + conn.getDbName() + " u:" + conn.getUserName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error al obtener la conexion. ", ex);
        }
        return con;
    }

    public static Connection getConnectionW(Connectable conn) throws SQLException {
        Connection con = null;
        try {
            int i = 0;
            while (i < 3) {
                con = PoolManager.getConnectionW(conn);
                if (con != null) {
                    //con.setReadOnly(true);
                    break;
                }
                System.out.println("Retrying get connection 4 writing " + i + "...");
                i++;
            }
            if (con == null) {
                throw new SQLException("Connection time out 3 times 4 writing " + conn.getServer() + ":" + conn.getPort() + "/" + conn.getDbName() + " u:" + conn.getUserName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error al obtener la conexion. ", ex);
        }
        return con;
    }
}
