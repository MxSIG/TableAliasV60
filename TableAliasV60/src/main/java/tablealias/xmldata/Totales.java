/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmldata;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author INEGI
 */
public class Totales {
    private List<Columna> columnas = new LinkedList<Columna>();
    private GeneradorSQL gsql = null;

    /**
     * @return the columnas
     */
    public List<Columna> getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(List<Columna> columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the gsql
     */
    public GeneradorSQL getGsql() {
        return gsql;
    }

    /**
     * @param gsql the gsql to set
     */
    public void setGsql(String atributos, String alias, String sql) {
        this.gsql = new GeneradorSQL(atributos, alias, sql);
    }

    public String[] getCamposTotales(){
        return gsql.getAliases();
    }

    public String getCamposTotalesWEB(){
        return gsql.getAliasesWEB();
    }

    public String[] getAtributos(){
        return gsql.getAtributos();
    }

}
