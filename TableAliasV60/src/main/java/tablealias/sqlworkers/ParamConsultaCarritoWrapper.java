package tablealias.sqlworkers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.inegi.dtweb.connection.ConnectionManager;
import tablealias.xmldata.Server;
import org.apache.commons.dbutils.QueryRunner;
import tablealias.dinue.helpers.DenuePK;
import tablealias.sqlworkers.interfaces.ParamConsultaWrapperInterface;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class ParamConsultaCarritoWrapper implements ParamConsultaWrapperInterface {

    private final Server oracleServer;
    private String items;
    private Integer[] claves;
    DenuePK denuePK = null;
    String errorMsg = null;

    public ParamConsultaCarritoWrapper(TablasServidor tablas, String items) {
        this.oracleServer = tablas.getServer("oracledenuepro"); // desarrollo : oracledenuedes ;  produccion: oracledenuepro
        this.items = items;
    }

    private DenuePK getDenuePK(Connection conn) {
        DenuePK denueDto = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT CARRITO_SEQ.nextval from dual");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                denueDto = new DenuePK();
                denueDto.setId(new Integer(rs.getInt(1)));
                rs.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return denueDto;
    }

    private void generaCarrito(Connection conn, Integer[] claves, DenuePK denueDto) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "UPDATE DINUE_DGG.param_consulta SET NUMERO_CARRITO = ? where "
                + "ID_CONSULTA in (aki)";

        StringBuilder p = new StringBuilder();
        for (Integer i : claves) {
            p.append(i).append(",");
        }
        p.delete(p.length() - 1, p.length());
        sql = sql.replaceAll("aki", p.toString());

        Object[] params = {denueDto.getId()};
        qr.update(conn, sql, params);
    }

    public boolean save() throws Exception {
        boolean exito = false;
        Connection oracleConn = ConnectionManager.getConnectionW(oracleServer);
        if (isValid()) {
            if (oracleConn != null) {
                denuePK = getDenuePK(oracleConn);
                generaCarrito(oracleConn, claves, denuePK);
                exito = true;
            } else {
                errorMsg = "No se pudo establecer conexion a oracle " + oracleServer.getURL();
            }
        } else {
            exito = false;
        }
        ConnectionManager.closeConnection(oracleConn);
        return exito;

    }

    private boolean isValid() {
        boolean salida = false;
        if (items != null) {
            String[] pClaves = items.split(",");
            if (pClaves.length > 0) {
                claves = new Integer[pClaves.length];
                int i = 0;
                for (String s : pClaves) {
                    claves[i++] = Integer.parseInt(s);
                }
                salida = true;
            }
        }
        return salida;
    }

    public DenuePK getDenuePK() {
        return denuePK;
    }

    public Integer getTotales() throws SQLException {
        return 0;
    }

    public String getErrorMsj() {
        return errorMsg;
    }
}
