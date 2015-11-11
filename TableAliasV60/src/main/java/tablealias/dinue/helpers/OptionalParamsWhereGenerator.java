/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.Map;
import mx.inegi.dtweb.connection.DebugerLog;

/**
 *
 * @author INEGI
 */
class OptionalParamsWhereGenerator implements WhereGenerator {

    private final DinueOptionalParams params;

    public OptionalParamsWhereGenerator(DinueOptionalParams params) {
        this.params = params;
    }

    public String getWhere() {
        StringBuilder sql = new StringBuilder();
        for (Map.Entry<String, String> ent : params.getParams().entrySet()) {
            if (ent.getKey().equalsIgnoreCase("CODIGO_POSTAL")) {
                sql.append(ent.getKey()).append(" = '").append(ent.getValue()).append("' and ");
            } else if (ent.getKey().equalsIgnoreCase("ACTIVIDAD_ESPECIFICA")) {
                sql.append(ent.getKey()).append(" = '").append(ent.getValue()).append("' and ");
            } else {                
                sql.append(ent.getKey()).append(" @@ to_tsquery('spanish','")
                //.append("(select replace( convierte('")
                .append(ent.getValue())
                //.append("')")
                //.append(",' ','_')))")
                .append("') and ");
                //sql.append(ent.getKey()).append(" like ").append("'%").append(ent.getValue()).append("%'").append(" and ");
            }
        }
        DebugerLog.log("sql: " + sql);
        sql = new StringBuilder(sql.substring(0, sql.length() - 5));
        return sql.toString();
    }
}
