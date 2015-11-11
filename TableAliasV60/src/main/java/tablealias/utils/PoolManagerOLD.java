package tablealias.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mx.inegi.dtweb.connection.Connectable;
import mx.inegi.dtweb.connection.DebugerLog;

import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author INEGI
 */
public class PoolManagerOLD {

	private static Map<String, BasicDataSource> dataSources;
	private static Map<String, BasicDataSource> dataSourcesW;

	public static Connection getConnection(Connectable conn)
			throws SQLException {
		String url = conn.getURL();
		if (dataSources == null) {
			DebugerLog.log("creado DS's container");
			dataSources = new HashMap<String, BasicDataSource>();
		}
		if (conn.getDbName() != null) {// si hay tablas para el servidor
			if (!dataSources.containsKey(url)) {
				System.out
						.println("Building datastore for " + conn.getDbName());
				BasicDataSource ds = new BasicDataSource();
				ds.setDriverClassName(conn.getDriverClassName());
				ds.setUrl(url);
				ds.setUsername(conn.getUserName());
				ds.setPassword(conn.getPassword());
				ds.setMaxActive(50);
				ds.setMaxIdle(8);
				ds.setMinIdle(3);
				ds.setTestOnBorrow(true);
				ds.setValidationQuery(conn.getValidationQuery());
				ds.setRemoveAbandonedTimeout(60);
				ds.setRemoveAbandoned(true);
				ds.setDefaultReadOnly(true);
				// ds.setLoginTimeout(1);
				// ds.setValidationQueryTimeout(1);
				// ds.getMaxActive());
				// ds.getMaxIdle());
				dataSources.put(url, ds);
			}
			try {
				DebugerLog.log("Connection active: "
						+ dataSources.get(url).getNumActive() + " idle: "
						+ dataSources.get(url).getNumIdle());
				return dataSources.get(url).getConnection();
			} catch (SQLException e) {
				throw new SQLException(conn.getServer() + " " + conn.getPort()
						+ " " + conn.getDbName() + " :: " + e.getMessage());
			}
		}
		return null;
	}

	public static Connection getConnectionW(Connectable conn)
			throws SQLException {
		String url = conn.getURL();
		if (dataSourcesW == null) {
			DebugerLog.log("creado DS's container 4 wrintig");
			dataSourcesW = new HashMap<String, BasicDataSource>();
		}
		if (conn.getDbName() != null) {// si hay tablas para el servidor
			if (!dataSourcesW.containsKey(url)) {
				System.out.println("Building datastore for " + conn.getDbName()
						+ " with writing privileges");
				BasicDataSource ds = new BasicDataSource();
				ds.setDriverClassName(conn.getDriverClassName());
				ds.setUrl(url);
				ds.setUsername(conn.getUserName());
				ds.setPassword(conn.getPassword());
				ds.setMaxActive(50);
				ds.setMaxIdle(8);
				ds.setMinIdle(3);
				ds.setTestOnBorrow(true);
				ds.setValidationQuery(conn.getValidationQuery());
				ds.setRemoveAbandonedTimeout(60);
				ds.setRemoveAbandoned(true);
				ds.setDefaultReadOnly(false);
				// ds.setValidationQueryTimeout(1);
				// ds.setLoginTimeout(1);
				// ds.getMaxActive());
				// ds.getMaxIdle());
				dataSourcesW.put(url, ds);
			}
			try {
				DebugerLog.log("Connection active: "
						+ dataSources.get(url).getNumActive() + " idle: "
						+ dataSources.get(url).getNumIdle() + " 4 writing");
				return dataSourcesW.get(url).getConnection();
			} catch (SQLException e) {
				throw new SQLException(conn.getServer() + " " + conn.getPort()
						+ " " + conn.getDbName() + " :: " + e.getMessage());
			}
		}
		return null;
	}
}
