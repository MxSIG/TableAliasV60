package tablealias.service;

import javax.servlet.http.HttpServletRequest;

import tablealias.dto.TematizacionParam;

public interface TematizacionJsonService {

	public Object json(TematizacionParam tematizacion, HttpServletRequest req);

}
