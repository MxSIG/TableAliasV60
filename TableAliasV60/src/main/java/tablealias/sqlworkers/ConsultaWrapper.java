package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.SQLException;

import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.dinue.helpers.BasePolygonOps;
import tablealias.dinue.helpers.DenueBaseOps;
import tablealias.dinue.helpers.DenueWhereOps;
import tablealias.jdbc.SqlExecutor;
import tablealias.sqlcreators.factory.ConsultaFactory;
import tablealias.utils.Polygon;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class ConsultaWrapper implements QueryWorker {

	private Table table;
	private Server server;
	private Polygon polygon;
	private StringBuilder sb;
	private int pageNumber;
	private boolean hasErrors;
	private String where;
	private final String gid;
	boolean gidSearch;
	private Server serverBuffer;
	private String subProject;
	private boolean identificables;

	public ConsultaWrapper(String where, String gid, String subProject,
			boolean identificables) {
		this.identificables = identificables;
		sb = new StringBuilder();
		hasErrors = false;
		pageNumber = 1;
		this.where = (where != null && where.trim().length() > 0) ? where
				: null;
		this.gid = gid;
		gidSearch = gid != null;
		this.subProject = subProject;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void setServer(Server srvr) {
		this.server = srvr;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	public Object doQuery() throws NumberFormatException, SQLException {
		if (polygon != null && polygon.getFPolygon() != null
				&& !polygon.getFPolygon().trim().isEmpty()) {
			try {
				long id = Long.parseLong(polygon.getFPolygon());
				serverBuffer.setDbName("mdm6data");
				BasePolygonOps opts = new BasePolygonOps(serverBuffer,
						table.getGeomName());
				polygon.setPoligono(opts.getData((int) id));
			} catch (NumberFormatException nf) {
				// System.out.println("Fallo conversion de poligono: " +
				// nf.getMessage() );
			} finally {
				// System.out.println("Poligono: " + polygon.getPolygon());
			}
		}
		Table[] data = null;
		Connection conn = null;
		try {
			server.setDbName(table.getDatabaseName());
			conn = ConnectionManager.getConnection(server);
			if (where != null) {
				DenueBaseOps ops = new DenueWhereOps(server);
				if (ops.hasId(where)) {
					where = ops.getData(Integer.parseInt(where));
				}
			}
			SqlExecutor executor = ConsultaFactory.getSqlExecutor(table,
					polygon, where, gid, pageNumber, 50, gidSearch, subProject,
					identificables);
			data = (Table[]) executor.executeQuery(conn);
			hasErrors = false;
		} catch (Exception ex) {
			hasErrors = true;
			sb.append(ex.getMessage());
		} finally {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return data;
	}

	public String getErrorMsg() {
		return sb.toString();
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public void setPageToView(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageToView() {
		return this.pageNumber;
	}

	public int getNumberOfRecords() throws Exception {
		int res = 0;
		Connection conn = null;
		try {
			server.setDbName(table.getDatabaseName());
			conn = ConnectionManager.getConnection(server);
			SqlExecutor executor = ConsultaFactory.getSqlExecutor(table,
					polygon, where, gid, pageNumber, 50, gidSearch, subProject,
					identificables);
			res = executor.getNumberOfRecords(conn);
			hasErrors = false;
		} catch (Exception ex) {
			hasErrors = true;
			sb.append(ex.getMessage());
		} finally {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return res;
	}

	public Table getTable() {
		return table;
	}

	/**
	 * @return the serverBuffer
	 */
	public Server getServerBuffer() {
		return serverBuffer;
	}

	/**
	 * @param serverBuffer
	 *            the serverBuffer to set
	 */
	public void setServerBuffer(Server serverBuffer) {
		this.serverBuffer = serverBuffer;
	}

	public void setValueToSearch(String[] valueToSearch) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public String getTypesOfRecords() {
		return "";// TODO later, not needed now
	}
}
