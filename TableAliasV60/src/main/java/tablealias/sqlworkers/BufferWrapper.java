package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import tablealias.dinue.helpers.DenueBaseOps;
import tablealias.dinue.helpers.DenuePK;
import tablealias.dinue.helpers.DenueWhereOps;
import tablealias.dto.BufferDto;
import tablealias.jdbc.SqlExecutor;
import tablealias.sqlcreators.factory.BufferFactory;
import tablealias.utils.Polygon;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class BufferWrapper implements QueryWorker {

    private Table table;
    private Server server;
    private Polygon polygon;
    private StringBuilder sb;
    private int pageNumber;
    private boolean hasErrors;
    private double size;
    private String[] gids;
    private boolean hasCoords;
    private final double x1;
    private final double y1;
    private String subProject;

    public BufferWrapper(double size, String gid, boolean hasCoords, double x1, double y1, String subProject ) {
        sb = new StringBuilder();
        this.size = size;
        gids = gid.split(",");
        hasErrors = false;
        pageNumber = 1;
        this.hasCoords = hasCoords;
        this.x1 = x1;
        this.y1 = y1;
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

    public Object doQuery() throws SQLException{
        List<BufferDto> data = null;
        Connection conn = null;
        try {
            server.setDbName(table.getDatabaseName());
            conn = ConnectionManager.getConnection(server);
            SqlExecutor sqlExecutor = BufferFactory.getSqlExecutor(table, size, gids, hasCoords, pageNumber, 50, x1, y1, subProject);
            data = (List<BufferDto>) sqlExecutor.executeQuery(conn);
            DenueBaseOps op = new DenueWhereOps(server);
            for (BufferDto l : data) {
                DenuePK pk = op.insertBuffer(l.getData(), table.getProy());
                if (pk != null) {
                    l.setId((long) pk.getId());
                    //id = String.valueOf(pk.getId());
                }
            }
            hasErrors = false;
        } catch (Exception ex) {
            hasErrors = true;
            sb.append(ex.getMessage());
        } finally {
            if (conn != null && !conn.isClosed() ) {
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
            SqlExecutor sqlExecutor = BufferFactory.getSqlExecutor(table, size, gids, hasCoords, pageNumber, 50, x1, y1, subProject);
            res = sqlExecutor.getNumberOfRecords(conn);
            hasErrors = false;
        } catch (Exception ex) {
            hasErrors = true;
            sb.append(ex.getMessage());
        } finally {
            if (conn != null && !conn.isClosed() ) {
                try {
                    conn.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return res;
    }

    public Table getTable() {
        return table;
    }

    public void setValueToSearch(String[] valueToSearch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getTypesOfRecords() {
        return "";//TODO later, not needed now
    }
}
