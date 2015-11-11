package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.dinue.helpers.DenueBaseOps;
import tablealias.dinue.helpers.DenueWhereOps;
import tablealias.dto.FieldType;
import tablealias.jdbc.SqlExecutor;
import tablealias.sqlcreators.factory.BusquedaFactory;
import tablealias.sqlworkers.helpers.TSearchConvierte;
import tablealias.utils.ExceptionSearchParser;
import tablealias.utils.ExceptionTables;
import tablealias.utils.Polygon;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class BusquedaWrapper implements QueryWorker {

	private Table table;
	private int rows;
	private Server server;
	private StringBuilder sb;
	private int pageNumber;
	private boolean hasErrors;
	private String[] valueToSearch;
	private ExceptionTables exTables;
	private boolean ExceptionSearch;
	private ExceptionSearchParser esp;
	private String where;
	private String subProject;
	private String whereTipo;

	// if exception search is true it means that valuetosearch contains --
	public BusquedaWrapper(String[] valueToSearch, ExceptionTables exTables,
			ExceptionSearchParser esp, String where, String subProject,
			String whereTipo) {
		sb = new StringBuilder();
		hasErrors = false;
		pageNumber = 1;
		this.valueToSearch = valueToSearch;
		this.exTables = exTables;
		this.ExceptionSearch = esp == null ? false : !esp.isBusquedaNormal();
		this.esp = esp;
		// if (where != null){
		this.where = (where != null && where.trim().length() > 0) ? where
				: null;
		// }
		this.subProject = subProject;
		this.whereTipo = whereTipo;
	}

	public void setTable(Table table) {
		this.table = table;
		// List<SearchField> fields = table.getSearchFields();

	}

	public void setServer(Server srvr) {
		this.server = srvr;
	}

	public void setPolygon(Polygon polygon) {
	}

	public Object doQuery() throws NumberFormatException, SQLException {
		Object data = null;
		Connection conn = null;
		try {
			server.setDbName(table.getDatabaseName());
			conn = ConnectionManager.getConnection(server);
			if (where != null) {
				DenueBaseOps ops = new DenueWhereOps(server);
				if (ops.hasId(where)) {
					System.out.println("AKi no debo entrar");
					where = ops.getData(Integer.parseInt(where));
				}
			}
			setValueToSearch(convierteTSearch(valueToSearch, esp,
					ExceptionSearch, conn));
			// cambio
			SqlExecutor sqlExecutor = BusquedaFactory.getSqlExecutor(table,
					valueToSearch, ExceptionSearch, pageNumber, 10, esp,
					where, subProject, whereTipo);
			data = (Table[]) sqlExecutor.executeQuery(conn);
			rows = ((Table[]) data).length;
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

	private String[] setParams(String[] vts) {
		String[] datos = vts[0].split(",");
		String[] params = null;
		if (datos.length > 1) {
			params = new String[2];
		} else {
			params = new String[1];
		}
		params[0] = datos[0];
		if (datos.length > 1) {
			params[1] = datos[1];
			for (int i = 2; i < datos.length; i++) {
				params[1] = params[1] + " " + datos[i];
			}
		}
		return params;
	}

	private String[] convierteTSearch(String[] valueToSearch,
			ExceptionSearchParser esp, boolean b, Connection conn)
			throws SQLException {
		try {
			TSearchConvierte ts = new TSearchConvierte();
			String cfunction = null;
			if (table.getSearchFields() == null
					|| table.getSearchFields().isEmpty()) {
				cfunction = "convierte";
			} else {
				cfunction = table.getSearchFields().get(0).getCfunction();
			}
			if (b) {
				String[] parametro = new String[] { esp.getCalle1() };
				esp.setDatosCalle1(ts.processString(conn, setParams(parametro),
						cfunction));
				parametro = new String[] { esp.getCalle2() };
				esp.setDatosCalle2(ts.processString(conn, setParams(parametro),
						cfunction));
				return valueToSearch;
			} else {
				valueToSearch = setParams(valueToSearch);
				return ts.processString(conn, valueToSearch, cfunction);
			}
		} finally {
		}
		// return valueToSearch;
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
			SqlExecutor sqlExecutor = BusquedaFactory.getSqlExecutor(table,
					valueToSearch, ExceptionSearch, pageNumber, 10, esp,
					where, subProject, whereTipo);
			if (table.getAlias().startsWith("cNumExt")) {
				res = rows;
			} else {
				res = sqlExecutor.getNumberOfRecords(conn);
			}
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
	 * @param valueToSearch
	 *            the valueToSearch to set
	 */
	public void setValueToSearch(String[] valueToSearch) {
		this.valueToSearch = valueToSearch;
	}

	public String getTypesOfRecords() throws Exception {
		String types = "";
		Connection conn = null;
		try {
			server.setDbName(table.getDatabaseName());
			conn = ConnectionManager.getConnection(server);
			SqlExecutor sqlExecutor = BusquedaFactory.getSqlExecutor(table,
					valueToSearch, ExceptionSearch, pageNumber, 10, esp,
					where, subProject, whereTipo);
			List<FieldType> typesOfRecords = sqlExecutor
					.getTypesOfRecords(conn);
			for (FieldType ft : typesOfRecords) {
				types += ft + ", ";
			}
			types = types.substring(0, types.length() - ", ".length());
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
		return types;
	}
}
