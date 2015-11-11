/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dataformatters;

import javax.servlet.http.HttpServletRequest;
import tablealias.sqlworkers.QueryWorker;

/**
 *
 * @author INEGI
 */
public interface DataFormatter<T> {

    void setQueryWorker(QueryWorker queryWorker);
    T getData() throws Exception;
    boolean hasErrors();
    String getErrorMsg();
    String getContentType();
    void setHttpServletRequest(HttpServletRequest request);

}
