/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tablealias.dto.Estados;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.utils.EdoCiudadData;

/**
 *
 * @author INEGI
 */
public class EdoCiudadEdosResultFetcher implements  QueryFetcher{
    private EdoCiudadData dataToFill;

    public EdoCiudadEdosResultFetcher(EdoCiudadData dataToFill) {
        this.dataToFill = dataToFill;
    }


    public Object fetchResults(ResultSet rs) throws SQLException {
        List<Estados> edos = dataToFill.getEstados();
        while(rs.next()){
            Estados edo = new Estados();
            edo.setCve_ent(rs.getString("cve_ent"));
            edo.setNom_ent(rs.getString("nom_ent"));
            edo.setGeom(rs.getString("geom"));
            edos.add(edo);
        }
        return null;
    }

}
