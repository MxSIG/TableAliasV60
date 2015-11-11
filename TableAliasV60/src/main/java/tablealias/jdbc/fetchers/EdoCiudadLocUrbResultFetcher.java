/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tablealias.dto.LocUrbanas;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.utils.EdoCiudadData;

/**
 *
 * @author INEGI
 */
public class EdoCiudadLocUrbResultFetcher implements  QueryFetcher{

    private EdoCiudadData dataToFill;

    public EdoCiudadLocUrbResultFetcher(EdoCiudadData dataToFill) {
        this.dataToFill = dataToFill;
    }


    public Object fetchResults(ResultSet rs) throws SQLException {
        List<LocUrbanas> urbs = dataToFill.getLocUrbanas();
        //cve_loc,nom_loc,cve_mun,cve_ent,astext(envelope(the_geom)) as geom
        while(rs.next()){
            LocUrbanas lu = new LocUrbanas();
            lu.setCve_loc(rs.getString("cve_loc"));
            lu.setNom_loc(rs.getString("nom_loc"));
            lu.setCve_mun(rs.getString("cve_mun"));
            lu.setCve_ent(rs.getString("cve_ent"));
            lu.setGeom(rs.getString("geom"));
            urbs.add(lu);
        }
        return null;
    }

}
