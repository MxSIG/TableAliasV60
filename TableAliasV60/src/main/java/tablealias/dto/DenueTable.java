/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class DenueTable {

    private static final String esquema = "denue012011";
    private static final String tabla = "establecimientos";
    private static final String tablaCache = "cache_actividadesareas";
    private static final String tablaMiWhere = "miwhere2011";

    /**
     * @return the esquema
     */
    public static String getEsquema() {
        return esquema;
    }

    /**
     * @return the tabla
     */
    public static String getTabla() {
        return tabla;
    }

    /**
     * @return the tablaCache
     */
    public static String getTablaCache() {
        return tablaCache;
    }

    /**
     * @return the tablaMiWhere
     */
    public static String getTablaMiWhere() {
        return tablaMiWhere;
    }
}
