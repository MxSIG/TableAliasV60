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

/**
 *
 * @author INEGI
 */
public class BufferCoordsResultFetcher implements QueryFetcher {

    public Object fetchResults(ResultSet rs) throws SQLException{
        String result = null;
        if(rs.next()){
            result = rs.getString(1);
        }
        List<String> data = new ArrayList<String>();
        data.add(result);
        return data;
    }

}
