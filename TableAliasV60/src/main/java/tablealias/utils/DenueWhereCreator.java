/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;

/**
 *
 * @author INEGI
 */
public class DenueWhereCreator {

    public static String getWhere(AreasGeo areasGeo, Estratos estratos, Actividades actividades, DinueOptionalParams optParams) {
        StringBuilder sql = new StringBuilder();
        String a = actividades.getSql();
        if (a != null && a.length() > 2) {
            sql.append(a);
        }
        a = areasGeo.getSql();
        if (a != null && a.length() > 2) {
            if (sql.length() > 1) {
                sql.append(" and ");
            }
            sql.append(a);
        }
        a = estratos.getSql();
        if (a != null && a.length() > 2) {
            if (sql.length() > 1) {
                sql.append(" and ");
            }
            sql.append(a);
        }
        if (!optParams.isEmpty()) {
            if (sql.length() > 1) {
                sql.append(" and ");
            }
            sql.append(optParams.getSql());
        }
        return sql.toString();
    }
}
