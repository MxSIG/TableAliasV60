package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import org.apache.commons.dbutils.QueryRunner;
import tablealias.dto.DenueTable;
import tablealias.utils.Validaciones;

/**
 *
 * @author INEGI
 */
public abstract class DenueBaseOps {

    protected final Server server;

    public DenueBaseOps(Server server) {
        this.server = server;
        this.server.setDbName("mdm6data");
    }

    public boolean hasId(String where){
        return Validaciones.isNumber(where);
    }

    public DenuePK insertWhere(String where) throws SQLException {
        QueryRunner qr = new QueryRunner();
        DenuePK dto = getDenuePK();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        String sql = "INSERT INTO control."+DenueTable.getTablaMiWhere()+"(gid, fechahora, the_where) VALUES(" + dto.getId() + ", '" + date + "', '" + where.replaceAll("'", "''") + "')";
        Connection conn= ConnectionManager.getConnectionW(server);
        qr.update(conn, sql);
        ConnectionManager.closeConnection(conn);
        return dto;
    }
    
    public DenuePK insertParamConsulta(String where) throws SQLException {
        QueryRunner qr = new QueryRunner();
        DenuePK dto = getDenuePK();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        String sql = "INSERT INTO control."+DenueTable.getTablaMiWhere()+"(gid, fechahora, the_where) VALUES(" + dto.getId() + ", '" + date + "', '" + where.replaceAll("'", "''") + "')";
        Connection conn= ConnectionManager.getConnectionW(server);
        qr.update(conn, sql);
        ConnectionManager.closeConnection(conn);
        return dto;
    }



     public DenuePK insertBuffer(String geom, String proy ) throws SQLException {
        QueryRunner qr = new QueryRunner();
        DenuePK dto = getDenuePK();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        String sql = "INSERT INTO control.mibuffer(gid, fechahora, the_geom) VALUES(" + dto.getId() + ", '" + date + "', setsrid(geomfromtext('" + geom + "')," + proy + "))";
        Connection conn= ConnectionManager.getConnectionW(server);
        qr.update(conn, sql);
        ConnectionManager.closeConnection(conn);
        return dto;
    }

    protected DenuePK getDenuePK() {
        DenuePK dto = null;
        try {
            Connection conn= ConnectionManager.getConnectionW(server);
            PreparedStatement stmt = conn.prepareStatement("SELECT nextval('control.control_seq')");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dto = new DenuePK();
                dto.setId(new Integer(rs.getInt(1)));
                rs.close();
                stmt.close();
                ConnectionManager.closeConnection(conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    abstract public String getData(int id);
}
