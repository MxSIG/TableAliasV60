/**
 * 
 */
package tablealias.service;

import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
public interface ImssService {

	public Object georeferencing(String point, Server server)
			throws Exception;

}
