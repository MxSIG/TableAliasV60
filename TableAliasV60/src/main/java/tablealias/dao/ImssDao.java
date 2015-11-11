/**
 * 
 */
package tablealias.dao;

import tablealias.dto.FrenteManzana;
import tablealias.dto.NumeroExterior;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
public interface ImssDao {

	public FrenteManzana findFrenteManzana(String point, Server server)
			throws Exception;

	public NumeroExterior findNumeroExterior(String point, Server server)
			throws Exception;

}
