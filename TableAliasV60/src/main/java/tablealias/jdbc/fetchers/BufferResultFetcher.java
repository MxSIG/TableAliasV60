/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.jdbc.fetchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tablealias.dto.BufferDto;
import tablealias.sqlworkers.QueryFetcher;

/**
 *
 * @author INEGI
 */
public class BufferResultFetcher implements QueryFetcher {

    public Object fetchResults(ResultSet rs) throws SQLException{
        List<BufferDto> data = new ArrayList<BufferDto>();
        while(rs.next()){
            data.add(new BufferDto(rs.getString(1)));
        }
        return data;
    }
}
