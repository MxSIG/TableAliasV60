package tablealias.delegate;

import java.sql.SQLException;
import tablealias.xmldata.Server;
import tablealias.dao.BufferDAO;

/**
 *
 * @author INEGI
 */
public class SetGeometryDelegate {

    public Long writeGeometry( String geometry, Server server ) throws SQLException{
        BufferDAO bufferDAO = new BufferDAO();
        return bufferDAO.writeGeometry( geometry, server );
    }

    public Long writeGeometryCE(String geometry, Server server, String cve_ent) throws SQLException {
        BufferDAO bufferDAO = new BufferDAO();
        return bufferDAO.writeGeometryCE( geometry, server,cve_ent );
    }
}
