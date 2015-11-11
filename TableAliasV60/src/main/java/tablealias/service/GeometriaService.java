/**
 * 
 */
package tablealias.service;

import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
public interface GeometriaService {

	public Object findGeometryByPoint(String sql, String point, Server server)
			throws Exception;

	public Object findGeometryByCvegeo(String sql, String cvegeo, Server server)
			throws Exception;

}
