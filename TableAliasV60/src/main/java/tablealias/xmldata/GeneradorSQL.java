/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.xmldata;

import tablealias.utils.FieldQueryDataCleaner;

/**
 *
 * @author INEGI
 */
public class GeneradorSQL {

    private String[] atributos;
    private String[] aliases;
    private String sql;
    final String patron = "REMPLAZAO";

    public String getSQL(String atributo) {
        int i = 0;
        String salida = null;
        for (String s : atributos) {
            if (s.equalsIgnoreCase(atributo) || aliases[i].equalsIgnoreCase(atributo)) {
                salida = sql.replaceAll(patron, s);
                break;
            }
            i++;
        }
        return salida;
    }

    public GeneradorSQL(String atributos, String aliases, String sql) {
        this.atributos = atributos.split(",");
        this.aliases = aliases.split(",");
        this.sql = sql;
    }

    /**
     * @return the atributos
     */
    public String[] getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(String[] atributos) {
        this.atributos = atributos;
    }

    /**
     * @return the aliases
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * @return the aliases
     */
    public String getAliasesWEB() {
        StringBuilder sb = new StringBuilder();
        for (String aa : aliases) {
            sb.append(aa).append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * @param aliases the aliases to set
     */
    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean idValidField(String campo) {
        boolean salida = false;
        if (campo != null) {
            String lcad = FieldQueryDataCleaner.removeAcentos(campo);
            for (String c : aliases) {
                String lc = FieldQueryDataCleaner.removeAcentos(c);
                if (c.equalsIgnoreCase(campo)) {
                    salida = true;
                    break;
                }
            }
            for (String c : atributos) {
                String lc = FieldQueryDataCleaner.removeAcentos(c);
                if (c.equalsIgnoreCase(campo)) {
                    salida = true;
                    break;
                }
            }
        }
        return salida;
    }
}
