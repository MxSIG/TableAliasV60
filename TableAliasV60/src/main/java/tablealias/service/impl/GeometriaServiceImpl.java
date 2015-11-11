/**
 * 
 */
package tablealias.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dao.GeometriaDao;
import tablealias.service.GeometriaService;
import tablealias.xmldata.Server;

/**
 * @author INEGI
 *
 */
@Service
public class GeometriaServiceImpl implements GeometriaService {

	@Autowired
	private GeometriaDao dao;

	@Override
	public Object findGeometryByPoint(String sql, String point, Server server)
			throws Exception {
		Object response = dao.findGeometryByPoint(sql, point, server);
		return response;
	}

	@Override
	public Object findGeometryByCvegeo(String sql, String cvegeo, Server server)
			throws Exception {
		Object response = dao.findGeometryByCvegeo(sql, cvegeo, server);
		return response;
	}

}
