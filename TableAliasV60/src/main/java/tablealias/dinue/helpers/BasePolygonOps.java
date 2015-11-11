package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import tablealias.dto.BufferDto;

/**
 *
 * @author INEGI
 */
public class BasePolygonOps extends DenueBaseOps {

    private String geomName;
    
    public BasePolygonOps(Server server, String geomName ) {
        super(server);
        this.geomName = geomName;
    }

    @Override
    public String getData(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "select gid as id, astext(" + geomName + ") as data from control.mibuffer where gid = ?";
        ResultSetHandler rsh = new BeanHandler(BufferDto.class);
        BufferDto dw = null;
        try {
            Connection conn = ConnectionManager.getConnection(server);
            dw = (BufferDto) qr.query(conn, sql, rsh, new Object[]{id});
            ConnectionManager.closeConnection(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dw != null ? dw.getData() : "";
    }


}
