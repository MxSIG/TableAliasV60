package tablealias.delegate;

import java.sql.Connection;
import java.sql.SQLException;
import tablealias.sqlworkers.FiltroAgropeWrapper;

/**
 *
 * @author INEGI
 */
public class FiltroAgropeDelegado {

    private FiltroAgropeWrapper faw;

    public FiltroAgropeDelegado( Connection conn ) throws SQLException {
        FiltroAgropeWrapper.setColorsMap( conn );
        faw = new FiltroAgropeWrapper( conn );
    }

    public Long escribeFiltro(String filtroId, String filtro ) throws SQLException {
        Long rowId = null;
        if( filtroId == null || filtroId.length() < 1 ){
            rowId = faw.escribeFiltro( filtro );
        } else {
            try {
                rowId = faw.actualizaFiltro( filtroId, filtro );
            }catch( SQLException sqle ){
                rowId = faw.escribeFiltro( filtro );
            }
        }
        return rowId;
    }


}
