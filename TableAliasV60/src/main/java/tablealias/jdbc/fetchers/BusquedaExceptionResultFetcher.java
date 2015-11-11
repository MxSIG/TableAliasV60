/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tablealias.dto.BusquedaExceptionSearchDto;
import tablealias.sqlworkers.QueryFetcher;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class BusquedaExceptionResultFetcher implements QueryFetcher{
    private final Table table;

    public BusquedaExceptionResultFetcher(Table table) {
        this.table = table;
    }


    private void populateData(Table t, ResultSet rs) throws SQLException{
        TableFields fields = t.getFields();
        for(Field f: fields){
            if(f.isBusquedaDisplay())
                f.setValue(rs.getString(f.getSqlName()));
        }
    }

    public Object fetchResults(ResultSet rs) throws SQLException{
        List<BusquedaExceptionSearchDto> tableData = new ArrayList<BusquedaExceptionSearchDto>();
        while (rs.next()) {
            BusquedaExceptionSearchDto dto = new BusquedaExceptionSearchDto();
            dto.setCalle(rs.getString(1));
            dto.setEntidad(rs.getString(2));
            dto.setCalle2(rs.getString(3));
            dto.setEntidad2(rs.getString(4));
            dto.setThe_geom(rs.getString(5));
            dto.setPunto(rs.getString(6));
            tableData.add(dto);
        }
        return (BusquedaExceptionSearchDto[])tableData.toArray(new BusquedaExceptionSearchDto[tableData.size()]);
    }

}
