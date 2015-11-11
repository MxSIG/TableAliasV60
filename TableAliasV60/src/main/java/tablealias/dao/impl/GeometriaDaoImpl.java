/**
 * 
 */
package tablealias.dao.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.springframework.stereotype.Repository;

import tablealias.dao.GeometriaDao;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Repository
public class GeometriaDaoImpl implements GeometriaDao {

	@Override
	public Object findGeometryByPoint(String sql, String point, Server server)
			throws Exception {
		Connection conn = ConnectionManager.getConnectionW(server);
		MapHandler mh = new MapHandler();
		QueryRunner qr = new QueryRunner();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response = qr.query(conn, sql, mh, point);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return response;
	}

	@Override
	public Object findGeometryByCvegeo(String sql, String cvegeo, Server server)
			throws Exception {
		Connection conn = ConnectionManager.getConnectionW(server);
		MapHandler mh = new MapHandler();
		QueryRunner qr = new QueryRunner();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response = qr.query(conn, sql, mh, cvegeo);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return response;
	}

}
