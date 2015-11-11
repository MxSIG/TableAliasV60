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
 */
public class BusquedaSqlCreator implements SqlCreator{

    private Table table;
    private int recordsPerView;
    private int pageNumber;
    private String[] valueToSearch;
    private final String where;
    private String whereSubProject;
    private String whereTipo;

    public BusquedaSqlCreator(Table table,String[] valueToSearch, String where, String subProject, String whereTipo ) {
        this.table = table;
        recordsPerView = 25;//default value
        pageNumber = 1;
        this.valueToSearch = valueToSearch;
        this.where = where;
        this.whereSubProject = new SubProjectWhereGenerator().getWhereSubProject( subProject, table );
        this.whereTipo = whereTipo;
    }

    private int calculateOffset() {
        return (pageNumber - 1) * recordsPerView;
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
                sb.append(f.getName()).append(" as ").append(f.getSqlName()).append(",");
            else{
                if(f.isBusquedaDisplay())
                    sb.append(getFieldSqlWithFunctions(f)).append(" as ").append(f.getSqlName()).append(",");
            }            
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
    

    public String getSql() throws Exception{
        if(table.getSearchFields() == null)
            throw new Exception("tabla " + table.getName() + " has null searchField");
        StringBuilder sb = new StringBuilder();
        //String[] params = setParams();
        String[] params = valueToSearch;
        sb.append("select ").append(getFieldsSql(table)).append(" ")
                .append("from ")
                .append(table.getSchema())
                .append(".")
                .append(table.getName())
                .append(" where ")
                .append(table.getSqlWhereWithConvierte(params));
        sb.append( " AND " ).append( whereSubProject );
                if(where != null && FilteredTable.isTableFiltered(table.getName()))
                    sb.append(" and ").append(where);
                sb.append( " and " ).append( whereTipo );
                if(table.hasOrderByFields())
                    sb.append(" ").append(getOrderByFields()).append(" ");
                sb.append(" offset ");
                sb.append(calculateOffset());
                sb.append(" limit ").append(recordsPerView);
        return sb.toString();
    }

    private String[] setParams() {
        String[] datos = valueToSearch[0].split(",");
        String[] params = null;
        if (datos.length > 1) {
            params = new String[2];
        } else {
            params = new String[1];
        }
        params[0] = datos[0];
        if (datos.length > 1) {
            params[1] = datos[1];
            for (int i = 2; i < datos.length; i++) {
                params[1] = params[1] + " " + datos[i];
            }
        }
        return params;
    }

    public Table getTable() {
        return table;
    }

    public Polygon getPolygon() {
        return null;
    }

    public void setRecordsPerView(int records) {
        this.recordsPerView = records;
    }

    public void setPageToView(Integer pageNumber) {
        this.pageNumber = pageNumber == null ? 1 : pageNumber;
    }

    public String getNumberOfRecordsSql() {
        StringBuilder sb = new StringBuilder();
        //String[] params = setParams();
        String[] params = valueToSearch;
        sb.append("select ").append("count(*) ")
                .append("from ")
                .append(table.getSchema())
                .append(".")
                .append(table.getName())
                .append(" where ")
                .append(table.getSqlWhereWithConvierte(params));
        sb.append( " AND ").append( whereSubProject );
        if(where != null && FilteredTable.isTableFiltered(table.getName()))
            sb.append(" and ").append(where);
        sb.append( " and " ).append( whereTipo );
        return sb.toString();
    }
    
    public String getTypesOfRecordsSQL() throws Exception {
        if( !table.hasTypeFields() ) return null;
        String[] params = valueToSearch;
        String fields = "";
        for( Field f : table.getTypeFields() ){
            fields = fields.concat( f.getName() );
            break;//just the first one will be used
        }
        StringBuilder sb = new StringBuilder();
        sb.append( "select distinct( " ).append( fields );
        sb.append( " ) as type ").append("from ").append(table.getSchema()).append(".").append(table.getName());
        sb.append(" where ").append(table.getSqlWhereWithConvierte(params));
        sb.append( " AND ").append( whereSubProject );
        if(where != null && FilteredTable.isTableFiltered(table.getName()))
            sb.append(" and ").append(where);
        sb.append( " and " ).append( whereTipo );
        sb.append(" order by ").append( fields );
        return sb.toString();
    }
    

}
