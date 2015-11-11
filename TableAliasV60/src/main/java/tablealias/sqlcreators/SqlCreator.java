package tablealias.sqlcreators;

import tablealias.utils.Polygon;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public interface SqlCreator {

    void setRecordsPerView(int records);
    void setPageToView(Integer pageNumber);
    String getNumberOfRecordsSql();
    String getSql() throws Exception;
    String getTypesOfRecordsSQL() throws Exception;
    Table getTable();
    Polygon getPolygon();

}
