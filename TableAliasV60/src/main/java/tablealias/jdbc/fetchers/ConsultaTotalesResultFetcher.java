/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.xmldata.Columna;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class ConsultaTotalesResultFetcher implements QueryFetcher {

    private Table table;

    public ConsultaTotalesResultFetcher(Table table) {
        this.table = table;
    }
    
    private void populateData(Table t, ResultSet rs) throws SQLException{
        List<Columna> columnas = t.getTotales().getColumnas();
        TableFields fields2 = new TableFields();
        for(Columna f: columnas){
            Field f1 = null;
            if (f.getAlias().equalsIgnoreCase("REMPLAZAO")){
                f1 = new Field(f.getNombre(), t.getCampoTotales());
            }else{
                f1 = new Field(f.getNombre(), f.getAlias());
            }
            fields2.addField(f1);
        }
        t.setTableFields(fields2);
        TableFields fields = t.getFields();
        Field campillo = null;
        //boolean remover = false;
        for(Field f: fields){
            if(f.isConsultaDisplay()){
                if(f.hasPredato())
                    f.setValue(f.getPredato() + rs.getString(f.getSqlName()));
                else
                    if (rs.getString(f.getSqlName())==null){
                        f.setValue("");
                    }else{
                        f.setValue(rs.getString(f.getSqlName()));
                    }
            }
            else if(f.getAliasName().equalsIgnoreCase("Ubicación") || f.getAliasName().equalsIgnoreCase("Ubicacion")){
                campillo = f;
            }
                    

        }
        if(campillo != null)
            fields.removeField(campillo);
    }

    public Object fetchResults(ResultSet rs) throws SQLException{
        List<Table> tableData = new ArrayList<Table>();
        while (rs.next()) {
            Table t = (Table) table.clone();
            populateData(t, rs);
            tableData.add(t);
        }
        return (Table[])tableData.toArray(new Table[tableData.size()]);
    }
}
