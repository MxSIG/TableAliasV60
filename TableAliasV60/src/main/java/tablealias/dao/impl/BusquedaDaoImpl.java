/**
 * 
 */
package tablealias.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.stereotype.Repository;

import tablealias.dao.BusquedaDao;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Repository
public class BusquedaDaoImpl implements BusquedaDao {

	private String convierte3(Server server, String str) throws SQLException {
		Connection conn = ConnectionManager.getConnectionW(server);
		String sql = "select convierte3(?)";
		StringHandler sh = new StringHandler();
		QueryRunner qr = new QueryRunner();
		String result = null;
		try {
			result = qr.query(conn, sql, sh, str);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return result;
	}

	@Override
	public List<String> findTypes(Server server, String searchCriteria)
			throws Exception {
		String sql = "SELECT distinct tipo FROM buscador.geolocator";
		String[] criteria = searchCriteria.split(",");

		List<String> result = null;

		if (criteria.length == 1) {
			sql += " where spvector @@ to_tsquery('spanish', ?)";
			String p1 = convierte3(server, criteria[0]);
			System.out.println(sql);
			System.out.println(p1);
			Connection conn = ConnectionManager.getConnectionW(server);
			ListStringHandler lsh = new ListStringHandler();
			QueryRunner qr = new QueryRunner();
			try {
				result = qr.query(conn, sql, lsh, p1);
			} finally {
				ConnectionManager.closeConnection(conn);
			}

		} else if (criteria.length > 1) {

			int index = searchCriteria.indexOf(',');
			int length = searchCriteria.length();
			String str1 = searchCriteria.substring(0, index).trim();
			String str2 = searchCriteria.substring(index + 1, length).trim();
			String p1 = convierte3(server, str1);
			String p2 = convierte3(server, str2);
			sql += " where spvector @@ to_tsquery('spanish', ?)";
			sql += " and spvectorref @@ to_tsquery('spanish', ?)";

			System.out.println(sql);
			System.out.println(p1);
			System.out.println(p2);

			Connection conn = ConnectionManager.getConnectionW(server);
			ListStringHandler lsh = new ListStringHandler();
			QueryRunner qr = new QueryRunner();
			try {
				result = qr.query(conn, sql, lsh, p1, p2);
			} finally {
				ConnectionManager.closeConnection(conn);
			}

		}

		return result;
	}

	private class ListStringHandler implements ResultSetHandler<List<String>> {

		@Override
		public List<String> handle(ResultSet rs) throws SQLException {
			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		}

	}

	private class StringHandler implements ResultSetHandler<String> {

		@Override
		public String handle(ResultSet rs) throws SQLException {
			if (!rs.next()) {
				return null;
			}
			return rs.getString(1);
		}

	}

}
