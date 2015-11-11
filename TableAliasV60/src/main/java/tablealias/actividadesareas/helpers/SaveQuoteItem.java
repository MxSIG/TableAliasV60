/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.actividadesareas.helpers;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;
import tablealias.sqlworkers.ParamConsultaWrapper;
import tablealias.utils.TablasServidor;

/**
 *
 * @author INEGI
 */
public class SaveQuoteItem {

    public ParamConsultaWrapper SaveQuote(TablasServidor tablasServidor, String actividades, String areageo, String estratos, HttpServletRequest request, Actividades ac, AreasGeo ag, Estratos est) throws SQLException {
        ParamConsultaWrapper pcw = new ParamConsultaWrapper(tablasServidor, actividades, areageo, estratos);
        DinueOptionalParams optParams = new DinueOptionalParams(request, tablasServidor.getServerByTable("c100"));
        pcw.setOptParams(optParams);
        pcw.setActividades(ac);
        pcw.setAreasGeo(ag);
        pcw.setEstratos(est);
        return pcw;
    }
}
