/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.sqlworkers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author INEGI
 */
public interface QueryFetcher {

    Object fetchResults(ResultSet rs) throws SQLException;

}
