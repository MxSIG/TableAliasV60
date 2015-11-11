package tablealias.sqlworkers;

import tablealias.xmldata.Server;
import tablealias.utils.Polygon;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public interface QueryWorker {

    void setTable(Table table);
    Table getTable();
    void setServer(Server srvr);
    void setPolygon(Polygon polygon);
    void setPageToView(int pageNumber);
    int getPageToView();

    boolean hasErrors();
    String getErrorMsg();
    
    Object doQuery() throws Exception;
    int getNumberOfRecords() throws Exception;
    String getTypesOfRecords() throws Exception;
    
    void setValueToSearch(String[] valueToSearch);

}
