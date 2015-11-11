/**
 * 
 */
package tablealias.dao;

import java.util.List;

import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
public interface BusquedaDao {

	public List<String> findTypes(Server server, String searchCriteria)
			throws Exception;

}
