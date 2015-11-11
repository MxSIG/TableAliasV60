package tablealias.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import tablealias.dto.Buffer;
import tablealias.dto.BufferCE;
import tablealias.xmldata.Server;

/**
 * 
 * @author INEGI
 */
public class BufferDAO {

	private Connection conn;
	private QueryRunner queryRunner;
	private static final String SQL_ID = "SELECT nextval('control.control_seq')";
	private static final String SQL_ISVALID = "SELECT ISVALID('?')";
	private static final String SQL_CURRENT_TIMESTAMP = "SELECT CURRENT_TIMESTAMP";
	private static final String SQL_INSERT_GEOMETRY = "INSERT INTO control.mibuffer VALUES( ?, ?, setsrid( geometryfromtext(?),900913 ) )";
	private static final String SQL_ID_CE = "SELECT nextval('control.control_ce_seq')";
	private static final String SQL_INSERT_GEOMETRY_CE = "INSERT INTO control.mibufferce VALUES( ?, ?, setsrid( geometryfromtext(?),900913 ), ? )";
	private static final String SQL_DELETE_GEOMETRY_CE = "DELETE FROM control.mibufferce WHERE gid=?";

	private Long writeBuffer(Buffer buffer) throws SQLException {
		queryRunner.update(conn, SQL_INSERT_GEOMETRY, buffer.getId(),
				buffer.getDate(), buffer.getGeometry());
		return buffer.getId();
	}

	private Long writeBufferCE(BufferCE buffer) throws SQLException {
		queryRunner.update(conn, SQL_INSERT_GEOMETRY_CE, buffer.getId(),
				buffer.getDate(), buffer.getGeometry(), buffer.getCve_ent());
		return buffer.getId();
	}

	public Long writeGeometry(String geometry, Server server)
			throws SQLException {
		try {
			conn = ConnectionManager.getConnectionW(server);
			queryRunner = new QueryRunner();
			/*
			 * if( !isValidGeometry( geometry ) ){ throw new
			 * IllegalArgumentException(); }
			 */
			Long id = getNextId();
			Date date = getDate();
			Buffer buffer = new Buffer(id, date, geometry);
			Long result = writeBuffer(buffer);
			return result;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (conn != null) {
				ConnectionManager.closeConnection(conn);
			}
		}
	}

	public Long writeGeometryCE(String geometry, Server server, String cve_ent)
			throws SQLException {
		try {
			conn = ConnectionManager.getConnectionW(server);
			queryRunner = new QueryRunner();
			/*
			 * if( !isValidGeometry( geometry ) ){ throw new
			 * IllegalArgumentException(); }
			 */
			Long id = getNextIdCE();
			Date date = getDate();
			BufferCE buffer = new BufferCE(id, date, geometry, cve_ent);
			Long result = writeBufferCE(buffer);
			return result;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (conn != null) {
				ConnectionManager.closeConnection(conn);
			}
		}
	}

	public boolean deleteGeometryCE(String id, Server server)
			throws SQLException {
		try {
			conn = ConnectionManager.getConnectionW(server);
			queryRunner = new QueryRunner();
			boolean result = deleteBufferCE(id);
			return result;
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (conn != null) {
				ConnectionManager.closeConnection(conn);
			}
		}
	}

	private boolean deleteBufferCE(String id) throws SQLException {
		int result = queryRunner.update(conn, SQL_DELETE_GEOMETRY_CE,
				Integer.parseInt(id));
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	private Boolean isValidGeometry(String geometry) throws SQLException {
		BooleanHandler bh = new BooleanHandler();
		Boolean b = queryRunner.query(conn, SQL_ISVALID.replace("?", geometry),
				bh);
		return b;
	}

	private Long getNextId() throws SQLException {
		LongHandler lh = new LongHandler();
		Long id = queryRunner.query(conn, SQL_ID, lh);
		return id;
	}

	private Long getNextIdCE() throws SQLException {
		LongHandler lh = new LongHandler();
		Long id = queryRunner.query(conn, SQL_ID_CE, lh);
		return id;
	}

	private Date getDate() throws SQLException {
		DateHandler dh = new DateHandler();
		Date date = queryRunner.query(conn, SQL_CURRENT_TIMESTAMP, dh);
		return date;
	}

	private class BooleanHandler implements ResultSetHandler<Boolean> {
		public Boolean handle(ResultSet rs) throws SQLException {
			if (!rs.next())
				return null;
			return rs.getBoolean(1);
		}
	}

	private class LongHandler implements ResultSetHandler<Long> {
		public Long handle(ResultSet rs) throws SQLException {
			if (!rs.next())
				return null;
			return rs.getLong(1);
		}
	}

	private class DateHandler implements ResultSetHandler<Date> {
		public Date handle(ResultSet rs) throws SQLException {
			if (!rs.next())
				return null;
			return rs.getDate(1);
		}
	}
}
