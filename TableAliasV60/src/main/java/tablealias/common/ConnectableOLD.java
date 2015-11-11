/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.common;

/**
 *
 * @author INEGI
 */
public interface ConnectableOLD{

    String getServer();
    String getPort();
    String getDbName();
    String getUserName();
    String getPassword();
    String getDriverClassName();
    String getValidationQuery();
    String getURL();

}
