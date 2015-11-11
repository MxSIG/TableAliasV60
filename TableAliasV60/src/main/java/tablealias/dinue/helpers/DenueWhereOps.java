package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import tablealias.dto.DenueTable;
import tablealias.dto.DenueWhereDto;

/**
 *
 * @author INEGI
 */
public class DenueWhereOps extends DenueBaseOps {

    public DenueWhereOps(Server server) {
        super(server);
    }       

    @Override
    public String getData(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "select the_where as where from control."+DenueTable.getTablaMiWhere()+" where gid = ?";
        ResultSetHandler rsh = new BeanHandler(DenueWhereDto.class);
        DenueWhereDto dw = null;
        try {
            Connection conn = ConnectionManager.getConnection(server);
            dw = (DenueWhereDto) qr.query(conn, sql, rsh, new Object[]{id});
            ConnectionManager.closeConnection(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dw != null ? dw.getWhere() : "";
    }
}
