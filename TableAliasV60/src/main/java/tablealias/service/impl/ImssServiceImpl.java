/**
 * 
 */
package tablealias.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dao.ImssDao;
import tablealias.dto.FrenteManzana;
import tablealias.dto.NumeroExterior;
import tablealias.service.ImssService;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Service
public class ImssServiceImpl implements ImssService {

	@Autowired
	private ImssDao dao;

	@Override
	public Object georeferencing(String point, Server server) throws Exception {
		FrenteManzana fm = dao.findFrenteManzana(point, server);

		NumeroExterior ne = null;
		if (fm != null) {
			String puntoEnVialidad = fm.getPunto();
			ne = dao.findNumeroExterior(puntoEnVialidad, server);
		} else {
			return null;
		}

		if (ne != null) {
			return ne;
		} else {
			return fm;
		}

	}

}
