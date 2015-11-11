package tablealias.dao;

import java.sql.Connection;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import org.apache.commons.dbutils.QueryRunner;
import tablealias.xmldata.Table;
/**
 *
 * @author INEGI
 */
public class SetCompleteDAO {
    
    private static final String SQL_UPDATE_STATUS = 
            "UPDATE SCHEME.TABLE SET estatus = ? WHERE cvegeo = ?";

    public Boolean updateStatus( String status, String cvegeo, Server server, Table table, String campo ) throws SQLException{
        Connection conn = null;
        try {
            if( !status.matches( "[ABCDEOabcdeo]" ) || !( table.getName().equalsIgnoreCase( "manzana_urb_a" )
                    || table.getName().equalsIgnoreCase( "frente_mzn_urb_l" ) 
                    || table.getName().equalsIgnoreCase( "ageb_urb_a" ) 
                    || table.getName().equalsIgnoreCase( "gnem_geo2" ) ) ){
                throw new IllegalArgumentException();
            }
            conn = ConnectionManager.getConnectionW( server );
            QueryRunner queryRunner = new QueryRunner();
            String sql = SQL_UPDATE_STATUS.replace( "SCHEME", table.getSchema() );
            sql = sql.replace( "TABLE", table.getName() );
            sql = sql.replace( "estatus", campo );
            Integer result = queryRunner.update( conn, sql, status, cvegeo );
            return result == 1 ? true : false;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if( conn != null ){
                try {
                    ConnectionManager.closeConnection(conn);
                } catch (Exception e) {
                }
            }
        }
    }    
}
