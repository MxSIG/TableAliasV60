package tablealias.service;

import tablealias.dto.IdentificaDto;
import tablealias.utils.TablasServidor;
import tablealias.web.result.IdentificaResult;

public interface IdentificaService {

	IdentificaResult identifica(IdentificaDto dto, TablasServidor ts)
			throws Exception;

}
