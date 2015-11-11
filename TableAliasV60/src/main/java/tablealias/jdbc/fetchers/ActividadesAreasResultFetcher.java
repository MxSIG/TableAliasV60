/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import tablealias.actividadesareas.helpers.ActividadRowItem;
import tablealias.actividadesareas.helpers.ActividadesAreasDto;
import tablealias.actividadesareas.helpers.AreaColumnItem;
import tablealias.sqlworkers.QueryFetcher;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasResultFetcher implements QueryFetcher {
    private final ActividadRowItem ari;
    private final AreaColumnItem aci;
    private List<ActividadesAreasDto> dtos;

    public ActividadesAreasResultFetcher(ActividadRowItem ari, AreaColumnItem aci, List<ActividadesAreasDto> dtos) {
        this.ari = ari;
        this.aci = aci;
        this.dtos = dtos;
    }

    public Object fetchResults(ResultSet rs) throws SQLException {
        if(rs.next()){
            ActividadesAreasDto dto = new ActividadesAreasDto();
            dto.setActividad(ari);
            dto.setAreageo(aci);
            dto.setTotal(rs.getInt(1));
            dtos.add(dto);
        }
        return null;
    }

}
