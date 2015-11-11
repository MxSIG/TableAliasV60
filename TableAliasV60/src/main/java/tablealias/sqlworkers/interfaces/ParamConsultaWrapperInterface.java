/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.sqlworkers.interfaces;

import java.sql.SQLException;
import tablealias.dinue.helpers.DenuePK;

/**
 *
 * @author INEGI
 */
public interface ParamConsultaWrapperInterface {
    public DenuePK getDenuePK();
    public boolean save() throws Exception;
    public Integer getTotales() throws SQLException;
    public String getErrorMsj() ;
}
