package tablealias.dinue.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import tablealias.delegate.FiltroAgropeDelegado;

/**
 *
 * @author INEGI
 */
public class FiltroAgropeControlador {

    private FiltroAgropeDelegado fad;

    public FiltroAgropeControlador( Connection conn ) throws SQLException {
        fad = new FiltroAgropeDelegado( conn );
    }

    public Long escribeFiltro(String filtroId, String filtro ) throws SQLException {
        return fad.escribeFiltro( filtroId, filtro );
    }

}
