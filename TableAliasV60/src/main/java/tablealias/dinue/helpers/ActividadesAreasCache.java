package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.ConnectionManager;
import mx.inegi.dtweb.connection.DebugerLog;
import tablealias.xmldata.Server;
import tablealias.dto.DenueTable;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasCache {

    public void createCache(String key, String info, Server server) throws SQLException{
        DebugerLog.log("Cacheando...");
        String sql = "insert into "+DenueTable.getEsquema()+"."+DenueTable.getTablaCache()+" (llave, info) values (?,?);";
        server.setDbName("mdm6data");
        Connection conn = ConnectionManager.getConnectionW(server);
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, key);
        st.setString(2, info);
        st.execute();
        ConnectionManager.closeConnection(conn);
    }

    public String getCache(String key, Server server) throws SQLException{
        String sql = "select info from "+DenueTable.getEsquema()+"."+DenueTable.getTablaCache()+"  where llave = ?;";
        server.setDbName("mdm6data");
        Connection conn = ConnectionManager.getConnection(server);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, key);
        ResultSet rs =  ps.executeQuery();
        String resutado = null;
        if (rs.next()){
            resutado = rs.getString(1);
        }
        ConnectionManager.closeConnection(conn);
        return resutado;
    }

}
