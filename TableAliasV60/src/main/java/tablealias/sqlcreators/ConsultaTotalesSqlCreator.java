package tablealias.sqlcreators;

import tablealias.dto.FilteredTable;
import tablealias.utils.Polygon;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Field;
import tablealias.xmldata.FieldFunction;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 * 
 */
public class ConsultaTotalesSqlCreator implements SqlCreator {

    private Table table;
    private int recordsPerView;
    private int pageNumber;
    private Polygon polygon;
    private final String where;
    private String whereSubProject;

    public ConsultaTotalesSqlCreator(Table table, Polygon polygon, String where, String subProject ) {
        this.table = table;
        this.polygon = polygon;
        recordsPerView = 25;//default value
        pageNumber = 1;
        this.where = where;
        this.whereSubProject = new SubProjectWhereGenerator().getWhereSubProject( subProject, table );
    }

    private String getFieldSqlWithFunctions(Field f) {
        StringBuilder sb = new StringBuilder();
        for (FieldFunction ff : f.getFunctions()) {
            sb.append(ff.getName()).append("(");
        }
        sb.append(f.getName());
        sb.append(String.format("%0" + f.getFunctions().size() + "d", 0).replace("0", ")"));
        return sb.toString();
    }

    private String getOrderByFields() {
        StringBuilder sb = new StringBuilder();
        sb.append("order by ");
        for (String s : table.getOrderByFields()) {
            sb.append(s).append(",");
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private String getFieldsSql(Table table) {
        StringBuilder sb = new StringBuilder();
        for (Field f : table.getFields()) {
            if (!f.hasFunctions()) {
                sb.append(f.getName()).append(" as ").append(f.getSqlName()).append(",");
            } else {
                if (f.isConsultaDisplay()) {
                    sb.append(getFieldSqlWithFunctions(f)).append(" as ").append(f.getSqlName()).append(",");
                }
            }
            /*if(f.isConsultaDisplay())
            sb.append(f.getName()).append(" as ").append(f.getSqlName()).append(",");*/
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private int calculateOffset() {
        return (pageNumber - 1) * recordsPerView;
    }

    public String getSql() throws Exception {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        /*sb.append("select ").append(getFieldsSql(table))
        .append(" ").append("from ").append(table.getSchema())
        .append(".").append(table.getName()).append(" where ")
        .append("((").append( table.getGeomName() ).append(" && ").append(polygon.getPolygon()).append( ") ")
        .append(" and (st_intersects(").append( table.getGeomName() ).append(" , " ).append(polygon.getPolygon()).append( "))) ");
        if(where != null && FilteredTable.isTableFiltered(table.getName())){
        if(where.trim().length() > 0 && !where.equalsIgnoreCase("true"))
        sb.append(" and ").append(where);
        }
        if(table.hasOrderByFields())
        sb.append(" ").append(getOrderByFields()).append(" ");
        sb.append(" offset ").append(calculateOffset()).append(" limit ").append(recordsPerView);
         *
         */
        sb.append(table.getTotales().getGsql().getSQL(table.getCampoTotales()).replace("LIMITADOR", " offset " + calculateOffset() + " limit " + recordsPerView));


        sb1.append(" where ((").append(table.getGeomName()).append(" && ").append(polygon.getPolygon()).append(") ").
                append(" and (st_intersects(").append(table.getGeomName()).append(" , ").append(polygon.getPolygon()).append("))) ");
        if (where != null && FilteredTable.isTableFiltered(table.getName())) {
            if (where.trim().length() > 0 && !where.equalsIgnoreCase("true")) {
                sb1.append(" and ").append(where).append(" ");
            }
        }
        sb1.append( " AND " ).append( whereSubProject );
        return sb.toString().replaceAll("MIWHERE", sb1.toString());
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
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("select ").append("count(*) ").append("from ").append(table.getSchema()).append(".").append(table.getName()).append(" where ").append("((").append(table.getGeomName()).append(" && ").append(polygon.getPolygon()).append(") ").append(" and (st_intersects(").append(table.getGeomName()).append(" , ").append(polygon.getPolygon()).append("))) ");
        if (where != null && FilteredTable.isTableFiltered(table.getName())) {
        if (where.trim().length() > 0 && !where.equalsIgnoreCase("true")) {
        sb.append(" and ").append(where);
        }
        }
        return sb.toString();
         */
        sb.append("select count(*) from (");
        sb.append(table.getTotales().getGsql().getSQL(table.getCampoTotales()).replace("LIMITADOR", ""));


        sb1.append(" where ((").append(table.getGeomName()).append(" && ").append(polygon.getPolygon()).append(") ").
                append(" and (st_intersects(").append(table.getGeomName()).append(" , ").append(polygon.getPolygon()).append("))) ");
        if (where != null && where.trim().length() > 0 && !where.equalsIgnoreCase("true")) {
            sb1.append(" and ").append(where).append(" ");
        }
        sb1.append( " AND " ).append( whereSubProject );
        String salida = sb.toString().replaceAll("MIWHERE", sb1.toString()) + ") a";
        return salida;
    }

    public String getTypesOfRecordsSQL() throws Exception {
        return "";//TODO later, not needed now
    }
}
