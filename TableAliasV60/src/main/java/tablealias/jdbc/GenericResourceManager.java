/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.inegi.dtweb.connection.Connectable;

/**
 *
 * @author INEGI
 */
public class GenericResourceManager {
    
    private static String JDBC_DRIVER   = "org.postgresql.Driver";
    private static Driver driver = null;
    private static String errorMsg;    
    
    public static Connection getConnection(Connectable conn) throws SQLException {
        if (driver == null) {
            try {
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            } catch (Exception e) {
                //"Failed to initialise JDBC driver");
                e.printStackTrace();
            }
        }        
        return DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPassword());
    }    

    public static void close(Connection conn)
	{
		try {
			if (conn != null) conn.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

    public static void close(PreparedStatement stmt)
	{
		try {
			if (stmt != null) stmt.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

    public static void close(Statement stmt)
	{
		try {
			if (stmt != null) stmt.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}

	public static void close(ResultSet rs)
	{
		try {
			if (rs != null) rs.close();
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}

	}

    /**
     * @return the errorMsg
     */
    public static String getErrorMsg() {
        return errorMsg;
    }


}
