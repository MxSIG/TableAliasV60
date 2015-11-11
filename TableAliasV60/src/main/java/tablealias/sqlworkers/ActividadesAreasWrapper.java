package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.inegi.dtweb.connection.ConnectionManager;
import mx.inegi.dtweb.connection.DebugerLog;
import tablealias.xmldata.Server;
import tablealias.actividadesareas.helpers.ActividadAreaField;
import tablealias.actividadesareas.helpers.ActividadRowItem;
import tablealias.actividadesareas.helpers.ActividadesAreasDto;
import tablealias.actividadesareas.helpers.ActividadesAreasGrid;
import tablealias.actividadesareas.helpers.AreaColumnItem;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;
import tablealias.jdbc.SimpleSqlExecutor;
import tablealias.jdbc.fetchers.ActividadesAreasResultFetcher;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasWrapper {

    private ActividadesAreasGrid grid;
    private Table table;
    private Server server;
    private boolean hasErrors;
    private StringBuilder sb;
    private List<ActividadesAreasDto> dtos;
    private final DinueOptionalParams optParams;
    private final Estratos estratos;
    private String subProject;

    public ActividadesAreasWrapper(ActividadesAreasGrid grid, DinueOptionalParams optParams, Estratos ests, String subProject ) {
        this.grid = grid;
        dtos = new ArrayList<ActividadesAreasDto>();
        sb = new StringBuilder();
        this.optParams = optParams;
        this.estratos = ests;
        this.subProject = subProject;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setServer(Server srvr) {
        this.server = srvr;
    }

    private void executeSql(Connection conn) throws Exception {
        SimpleSqlExecutor sse = new SimpleSqlExecutor();
        Map<ActividadRowItem, List<AreaColumnItem>> data = grid.getGrid();
        for (Map.Entry<ActividadRowItem, List<AreaColumnItem>> ent : data.entrySet()) {
            ActividadRowItem ari = ent.getKey();
            List<AreaColumnItem> cols = ent.getValue();//always same list
            iterateAreasgeo(cols, ari, sse, conn);
        }
    }

    private void iterateAreasgeo(List<AreaColumnItem> cols, ActividadRowItem ari, SimpleSqlExecutor sse, Connection conn) throws Exception {
        for (AreaColumnItem aci : cols) {
            StringBuilder sql = new StringBuilder();
            sql.append("select count(*) from ").append(table.getSchema()).append(".");
            sql.append(table.getName()).append(" where ");
            //if (!((String)ari.getParamValue()).equals("'99'")) {
            if (ari.getParamValue().equals("'30'")) {
                //sb.append("'").append("31','32','33").append("',");
                //sql.append(f.getKey()).append(" in (").append("'31','32','33'").append(")  and ");
                sql.append(ari.getSqlField()).append(" in (").append("'31','32','33')");
            } else if (ari.getParamValue().equals("'47'")) {
                //sb.append("'").append("48','49").append("',");
                //sql.append(f.getKey()).append(" in (").append("'48','49'").append(")  and ");
                sql.append(ari.getSqlField()).append(" in (").append("'48','49')");
            } else {
                sql.append(ari.getSqlField()).append("=").append(ari.getParamValue());
            }
            sql.append("  and ");
            //}
            Map<String, ActividadAreaField> fields = aci.getFields();

            for (Map.Entry<String, ActividadAreaField> f : fields.entrySet()) {
                if (!((String) f.getValue().getValue()).equals("'00'")) {
                    sql.append(f.getKey()).append("=").append(f.getValue().getValue()).append("  and ");
                }
            }
            if (optParams != null && !optParams.isEmpty()) {
                sql.append(optParams.getSql()).append("  and ");
            }
            if (estratos != null && !estratos.isEmpty()) {
                String a = estratos.getSql();
                if (a != null) {
                    sql.append(a).append("  and ");
                }
            }
            String query = sql.substring(0, sql.length() - 6);
            String whereSubProject = new SubProjectWhereGenerator().getWhereSubProject( subProject, table );
            query = query + " AND " + whereSubProject;
            DebugerLog.log("la query es: " + query);
            sse.setFetcher(new ActividadesAreasResultFetcher(ari, aci, dtos));
            sse.executeQuery(conn, query);
        }
    }

    public List<ActividadesAreasDto> doQuery() throws SQLException {
        server.setDbName(table.getDatabaseName());
        Connection conn = ConnectionManager.getConnection(server);
        if (conn != null) {
            try {
                executeSql(conn);
                hasErrors = false;
            } catch (Exception ex) {
                hasErrors = true;
                sb.append(ex.getMessage());
            }
        }
        ConnectionManager.closeConnection(conn);
        return dtos;
    }

    public String getErrorMsg() {
        return sb.toString();
    }

    public boolean hasErrors() {
        return hasErrors;
    }
}
