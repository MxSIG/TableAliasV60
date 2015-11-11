package tablealias.delegate;

import java.sql.SQLException;
import tablealias.xmldata.Server;
import tablealias.dao.SetCompleteDAO;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class SetCompleteDelegate {

    public Boolean updateStatus(String estatus, String cvegeo, Server server, Table table, String campo ) throws SQLException {
        SetCompleteDAO setCompleteDAO = new SetCompleteDAO();
        return setCompleteDAO.updateStatus( estatus, cvegeo, server, table, campo );
    }
    
}
