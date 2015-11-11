package tablealias.sqlworkers;

import java.sql.Connection;
import java.util.List;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.dto.Estados;
import tablealias.jdbc.SimpleSqlExecutor;
import tablealias.jdbc.fetchers.EdoCiudadEdosFueraResultFetcher;
import tablealias.jdbc.fetchers.EdoCiudadEdosResultFetcher;
import tablealias.jdbc.fetchers.EdoCiudadLocUrbResultFetcher;
import tablealias.utils.EdoCiudadData;
import tablealias.utils.Polygon;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class EdoCiudadWrapper {//implements QueryWorker {

    /*private Table table;
    private Server server;
    private StringBuilder sb;
    private Polygon polygon;
    private int pageNumber;
    private boolean hasErrors;*/
    private EdoCiudadData data;

    public EdoCiudadWrapper() {
        data = new EdoCiudadData();
    }

    /*public void setTable(Table table) {
        this.table = table;
    //List<SearchField> fields = table.getSearchFields();

    }

    public void setServer(Server srvr) {
        this.server = srvr;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }*/

    private void getEdosFuera(TablasServidor tablas) throws Exception{
        SimpleSqlExecutor sse = new SimpleSqlExecutor();
        if (tablas.tableExists("c100")) {//mge43
            Table t = tablas.getFoundTable();
            Connection conn = ConnectionManager.getConnection(tablas.getFoundServer());
            List<Estados> edos = data.getEstados();
            StringBuilder sb = new StringBuilder();
            sb.append(" in (");
            for(Estados edo: edos){
                sb.append("'").append(edo.getCve_ent()).append("'").append(",");
            }

            String query1 = "select cve_ent,nom_ent,astext(envelope(" + t.getGeomName() + ")) as geom from " + t.getSchema() + "." + t.getName();
            if (edos.size()>0){
                sb.deleteCharAt(sb.lastIndexOf(",")).append(")");
                query1 = query1 + " where not cve_ent "+ sb.toString();
            }
            query1 = query1 + " order by cve_ent";
                    
            //query1);
            sse.setFetcher(new EdoCiudadEdosFueraResultFetcher(data));
            sse.executeQuery(conn, query1);
            ConnectionManager.closeConnection(conn);
        }
    }

    private void getEdos(Polygon polygon, TablasServidor tablas) throws Exception{
        SimpleSqlExecutor sse = new SimpleSqlExecutor();
        if (tablas.tableExists("c100")) {//mge43
            Table t = tablas.getFoundTable();
            Connection conn = ConnectionManager.getConnection(tablas.getFoundServer());
            String query1 = "select cve_ent,nom_ent,astext(envelope(" + t.getGeomName() + ")) as geom from " + t.getSchema() + "." + t.getName()
                    + " where (" + t.getGeomName() + " && " +
                    polygon.getPolygon() + ") " +
                    /*"setsrid(makeBox2d(" +
                "makepoint(" + polygon.getX1() + ", " + polygon.getY1() + ")" +
                ",makepoint(" + polygon.getX2() + " ," + polygon.getY2() + ")),4326)) "+
                     */
                " and st_intersects(" + t.getGeomName() + " , " +
                polygon.getPolygon() +
                /*"setsrid(makeBox2d(" +
                "makepoint(" + polygon.getX1() + ", " + polygon.getY1() + ")" +
                ",makepoint(" + polygon.getX2() + " ," + polygon.getY2() + ")),4326)
                 */
                 " ) "+
                " order by cve_ent";
            sse.setFetcher(new EdoCiudadEdosResultFetcher(data));
            sse.executeQuery(conn, query1);
            ConnectionManager.closeConnection(conn);
        }
    }

    private void getLocUrbanas(Polygon polygon, TablasServidor tablas) throws Exception{
        SimpleSqlExecutor sse = new SimpleSqlExecutor();
        if (tablas.tableExists("c102")) {//locurbanas
            Table t = tablas.getFoundTable();
            Connection conn = ConnectionManager.getConnection(tablas.getFoundServer());
            String query2 = "select cve_loc,nom_loc,cve_mun,cve_ent,astext(envelope(" + t.getGeomName() + ")) as geom from " + t.getSchema() + "." + t.getName()
                    + " where (" + t.getGeomName() + " && " +
                    polygon.getPolygon() + ") " +
                    /*"setsrid(makeBox2d(" +
                "makepoint(" + polygon.getX1() + ", " + polygon.getY1() + ")" +
                ",makepoint(" + polygon.getX2() + " ," + polygon.getY2() + ")),4326)) "+
                     */
                " and st_intersects(" + t.getGeomName() + " , " +
                polygon.getPolygon() +
                /*"setsrid(makeBox2d(" +
                "makepoint(" + polygon.getX1() + ", " + polygon.getY1() + ")" +
                ",makepoint(" + polygon.getX2() + " ," + polygon.getY2() + ")),4326)
                 */
                 " ) "+
                "order by tamanio desc LIMIT 15";
            sse.setFetcher(new EdoCiudadLocUrbResultFetcher(data));
            sse.executeQuery(conn, query2);
            ConnectionManager.closeConnection(conn);
        }
    }


    public EdoCiudadData getData(Polygon polygon, TablasServidor tablas) throws Exception {
        getEdos(polygon, tablas);
        getEdosFuera(tablas);
        getLocUrbanas(polygon, tablas);
        return data;
    }

    /*public Object doQuery() {
        Table[] data = null;
        server.setDbName(table.getDatabaseName());
        Connection conn = ConnectionManager.getConnection(server);
        if (conn != null) {
            SqlCreator sqlMaker = new EdoCiudadSqlCreator(table, polygon);
            sqlMaker.setPageToView(1);
            sqlMaker.setRecordsPerView(15);
            SqlExecutor sqlExecutor = new SqlExecutor(sqlMaker);
            try {
                //sqlExecutor.setFetcher(new EdoCiudadEdosResultFetcher());
                data = (Table[]) sqlExecutor.executeQuery(conn);
                hasErrors = false;
            } catch (Exception ex) {
                hasErrors = true;
                sb.append(ex.getMessage());
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
        server.setDbName(table.getDatabaseName());
        Connection conn = ConnectionManager.getConnection(server);
        if (conn != null) {
            SqlCreator sqlMaker = new EdoCiudadSqlCreator(table, polygon);
            SqlExecutor sqlExecutor = new SqlExecutor(sqlMaker);
            try {
                res = sqlExecutor.getNumberOfRecords(conn);
                hasErrors = false;
            } catch (Exception ex) {
                hasErrors = true;
                sb.append(ex.getMessage());
            }
        }
        return res;
    }*/
}
