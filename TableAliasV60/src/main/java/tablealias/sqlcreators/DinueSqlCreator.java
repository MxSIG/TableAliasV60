package tablealias.sqlcreators;

import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.DinueOptionalParams;
import tablealias.dinue.helpers.Estratos;
import tablealias.utils.DenueWhereCreator;
import tablealias.utils.Polygon;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Field;
import tablealias.xmldata.FieldFunction;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class DinueSqlCreator implements SqlCreator{

    private Table table;
    private int recordsPerView;
    private int pageNumber;
    private Polygon polygon;
    private String sqlWhere;
    private String sqlWhereToJson;
    private String orderby;
    private String whereFromParam;

    public DinueSqlCreator(Table table, Actividades actividades, AreasGeo areas, Estratos ests, DinueOptionalParams optionalParams, String orderby, String whereFromParam, String subProject ) {
        this.table = table;
        recordsPerView = 50;//default value
        pageNumber = 1;
        String where = DenueWhereCreator.getWhere(areas, ests, actividades, optionalParams);
        sqlWhereToJson = where;
        sqlWhere = where;
        if (whereFromParam != null) {
            sqlWhereToJson = whereFromParam.trim().length() > 0 ? whereFromParam : null;
        }
        sqlWhere = sqlWhere + " AND " + new SubProjectWhereGenerator().getWhereSubProject( subProject, table );
        this.orderby = orderby;
    }

    private String getFieldSqlWithFunctions(Field f){
        StringBuilder sb = new StringBuilder();
        for(FieldFunction ff: f.getFunctions()){
            sb.append(ff.getName()).append("(");
        }
        sb.append(f.getName());
        sb.append(String.format("%0" + f.getFunctions().size() +"d", 0).replace("0", ")"));
        return sb.toString();
    }

    private String getFieldsSql(Table table) {
        StringBuilder sb = new StringBuilder();
        for (Field f : table.getFields()) {
            if(!f.hasFunctions())
                sb.append(f.getName()).append(",");//.append(" as ").append(f.getSqlName()).append(",");
            else{
                if(f.isBusquedaDisplay())
                    sb.append(getFieldSqlWithFunctions(f)).append(" as ").append(f.getSqlName()).append(",");
            }
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private int calculateOffset() {
        return (pageNumber - 1) * recordsPerView;
    }

    public String getSql() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(getFieldsSql(table)).append(" from ").append(table.getSchema());
        sql.append(".").append(table.getName());
        if (sqlWhere != null && !sqlWhere.trim().isEmpty()) {
            sql.append(" where ");
        }
        sql.append(sqlWhere).append(" ").append(orderby).append(" offset ");
        sql.append(calculateOffset()).append(" limit ").append(recordsPerView);
        return sql.toString();
    }

    public Table getTable() {
        return table;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setRecordsPerView(int records) {
        this.recordsPerView = records;
    }

    public void setPageToView(Integer pageNumber) {
        this.pageNumber = pageNumber == null ? 1 : pageNumber;
    }

    public String getNumberOfRecordsSql() {
        StringBuilder sql = new StringBuilder();
        if (sqlWhere != null && !sqlWhere.trim().isEmpty()) {
            sql.append("select ").append("count(*)").append(" ").append("from ");
            sql.append(table.getSchema()).append(".").append(table.getName());
            if (sqlWhere != null && !sqlWhere.trim().isEmpty()) {
                sql.append(" where ");
            }
            if (whereFromParam != null) {
                sql.append(whereFromParam).append(" ");
            } else {
                sql.append(sqlWhere).append(" ");
            }
        } else {
            sql.append("4374600");
        }
        return sql.toString();
    }

    /**
     * @return the sqlWhereToJson
     */
    public String getSqlWhereToJson() {
        return sqlWhereToJson;
    }

    public String getTypesOfRecordsSQL() throws Exception {
        return "";//TODO later, not needed now
    }



}
