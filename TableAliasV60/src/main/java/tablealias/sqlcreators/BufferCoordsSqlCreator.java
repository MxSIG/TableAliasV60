package tablealias.sqlcreators;

import tablealias.utils.Polygon;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class BufferCoordsSqlCreator implements SqlCreator{

    private Table table;
    private int recordsPerView;
    private int pageNumber;
    private Polygon polygon;
    private double size;
    private String[] gids;
    private final double x1;
    private final double x2;
    private String whereSubProject;

    public BufferCoordsSqlCreator(Table table, double size, String[] gids, double x1, double x2, String subProject ) {
        this.table = table;
        recordsPerView = 25;//default value
        this.size = size;
        this.gids = gids;
        pageNumber = 1;
        this.x1 = x1;
        this.x2 = x2;
        this.whereSubProject = new SubProjectWhereGenerator().getWhereSubProject( subProject, table );
    }

    private String getFieldsSql(Table table) {
        StringBuilder sb = new StringBuilder();
        for (Field f : table.getFields()) {
            if(f.isConsultaDisplay())
                sb.append(f.getName()).append(" as ").append(f.getSqlName()).append(",");
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private String getOrderByFields(){
        StringBuilder sb = new StringBuilder();
        sb.append("order by ");
        for(String s: table.getOrderByFields()){
            sb.append(s).append(",");
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    private int calculateOffset() {
        return (pageNumber - 1) * recordsPerView;
    }

    private String getGids(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for(String s: gids){
            sb.append(s).append(",");
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).append(")").toString();
    }

    public String getSql() throws Exception {
        StringBuilder sb = new StringBuilder();        
        sb.append("select astext(st_buffer(geomfromtext('POINT(").append(x1).append(" ").append(x2)
                .append(")'), ").append(size).append("))");
        return sb.toString();
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
         sb.append("select 1");
        return sb.toString();
    }

    public String getTypesOfRecordsSQL() throws Exception {
        return "";//TODO later, not needed now
    }

}
