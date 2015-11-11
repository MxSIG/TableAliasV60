package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import mx.inegi.dtweb.connection.ConnectionManager;
import mx.inegi.dtweb.connection.DebugerLog;
import tablealias.xmldata.Server;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreaGeoParser;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DenueBaseOps;
import tablealias.dinue.helpers.DenuePK;
import tablealias.dinue.helpers.DenueWhereOps;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;
import tablealias.jdbc.SqlExecutor;
import tablealias.jdbc.fetchers.DinueResultFetcher;
import tablealias.sqlcreators.DinueSqlCreator;
import tablealias.sqlcreators.SqlCreator;
import tablealias.utils.Polygon;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class DinueWrapper implements QueryWorker {

    private Table table;
    private Server server;
    private Polygon polygon;
    private StringBuilder sb;
    private int pageNumber;
    private boolean hasErrors;
    private Actividades actividades;
    private AreasGeo areas;
    private Estratos ests;
    private DinueOptionalParams optionalParams;
    private String where;
    private String extent;
    private final Server srvr;
    private String[] orderFields;
    private final String whereFromParam;
    private String subProject;

    public DinueWrapper(AreasGeo ag, Actividades ac, Estratos est, DinueOptionalParams optParams, 
            Server srvr, String[] orderFields, String whereFromParam, String subProject ) {
        sb = new StringBuilder();
        hasErrors = false;
        pageNumber = 1;
        this.actividades = ac;
        this.areas = ag;
        this.ests = est;
        this.optionalParams = optParams;
        this.srvr = srvr;
        this.orderFields = orderFields;
        this.whereFromParam = whereFromParam;
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

    public Object doQuery() throws SQLException {
        Table[] data = null;
        Iterator i = areas.iterator();
        boolean b = true;
        while (i.hasNext()) {
            AreaGeoParser mia = (AreaGeoParser) i.next();
            if (mia.hasLocalidad() || mia.hasMunicipio()) {
                b = false;
                break;
            }
        }
        String orderby = "order by LOCALIDAD_ID,CLASE_ACTIVIDAD_ID ";
        if (b) {
            orderby = "order by ENTIDAD_ID,CLASE_ACTIVIDAD_ID ";
        }
        Connection conn = ConnectionManager.getConnection(server);
        if (conn != null) {
            SqlCreator sqlMaker = new DinueSqlCreator(table, actividades, areas, ests, optionalParams, orderby, whereFromParam, subProject );
            sqlMaker.setRecordsPerView(50);
            sqlMaker.setPageToView(pageNumber);
            SqlExecutor sqlExecutor = new SqlExecutor(sqlMaker);
            try {
                DebugerLog.log("iniciando get dinue");
                long inicio = System.currentTimeMillis();
                sqlExecutor.setFetcher(new DinueResultFetcher(table));
                data = (Table[]) sqlExecutor.executeQuery(conn);
                where = ((DinueSqlCreator) sqlMaker).getSqlWhereToJson();
                long numero = Long.MIN_VALUE;
                try {
                    numero = Integer.parseInt(where);
                } catch (NumberFormatException e) {
                } finally {
                }
                if (numero == Long.MIN_VALUE) {
                    DenueBaseOps op = new DenueWhereOps(server);
                    DenuePK pk = op.insertWhere(where);
                    if (pk != null) {
                        where = String.valueOf(pk.getId());
                    }
                }
                long fin = System.currentTimeMillis();
                DebugerLog.log("tarde: " + (fin - inicio));
                inicio = System.currentTimeMillis();
                extent = "POLYGON((-118.407653808594 14.5320978164673,-118.407653808594 32.7186546325684,-86.7104034423828 32.7186546325684,-86.7104034423828 14.5320978164673,-118.407653808594 14.5320978164673))"; //excd.getExtent(where,conn, table.getSchema()+"."+table.getName());
                fin = System.currentTimeMillis();
                DebugerLog.log("tarde: " + (fin - inicio));
                hasErrors = false;
            } catch (Exception ex) {
                hasErrors = true;
                sb.append(ex.getMessage());
            }
        }
        ConnectionManager.closeConnection(conn);
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
        server.setDbName(table.getDatabaseName());
        Connection conn = ConnectionManager.getConnection(server);
        if (conn != null) {
            SqlCreator sqlMaker = new DinueSqlCreator(table, actividades, areas, ests, optionalParams, "", whereFromParam, subProject );
            SqlExecutor sqlExecutor = new SqlExecutor(sqlMaker);
            try {
                res = sqlExecutor.getNumberOfRecords(conn);
                hasErrors = false;
            } catch (Exception ex) {
                hasErrors = true;
                sb.append(ex.getMessage());
            }
        }
        ConnectionManager.closeConnection(conn);
        return res;
    }

    /**
     * @return the actividades
     */
    public Actividades getActividades() {
        return actividades;
    }

    /**
     * @return the areas
     */
    public AreasGeo getAreas() {
        return areas;
    }

    /**
     * @return the ests
     */
    public Estratos getEstratos() {
        return ests;
    }

    /**
     * @return the optionalParams
     */
    public Map<String, String> getOptionalParams() {
        return optionalParams.getParams();
    }

    /**
     * @return the where
     */
    public String getWhere() {
        return where.replaceAll("A\\.", "");
        //return where;
    }

    /**
     * @return the extent
     */
    public String getExtent() {
        return extent;
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
