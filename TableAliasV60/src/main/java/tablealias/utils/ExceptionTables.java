package tablealias.utils;

import java.util.HashMap;
import java.util.Map;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class ExceptionTables {

    private Map<String, Table> exceptions;

    private void setExceptionTables(TablasServidor ts) {
        String[] tablas = {"geolocator", "geocalles", "calles", "geocallesdenue"};
        for (String tabla : tablas) {
            if (ts.tableExists(tabla)) {
                exceptions.put(ts.getFoundTable().getName(), ts.getFoundTable());
            }
        }
        /*if(ts.tableExists("geolocator")){
        exceptions.put(ts.getFoundTable().getName(), ts.getFoundTable());
        }
        if(ts.tableExists("geocalles")){
        exceptions.put(ts.getFoundTable().getName(), ts.getFoundTable());
        }
        if(ts.tableExists("calles")){
        exceptions.put(ts.getFoundTable().getName(), ts.getFoundTable());
        }*/
    }

    public ExceptionTables(TablasServidor tablasServidor) {
        exceptions = new HashMap<String, Table>();
        setExceptionTables(tablasServidor);
    }

    public boolean isExceptionTable(String nameOrAlias) {
        for (Map.Entry<String, Table> entry : exceptions.entrySet()) {
            Table t = entry.getValue();
            if (t.getAlias().equalsIgnoreCase(nameOrAlias) || t.getName().equalsIgnoreCase(nameOrAlias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the exceptions
     */
    public Map<String, Table> getExceptions() {
        return exceptions;
    }
}
