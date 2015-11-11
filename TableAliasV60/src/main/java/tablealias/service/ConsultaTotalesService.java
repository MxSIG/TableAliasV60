package tablealias.service;

import tablealias.dto.ConsultaDto;
import tablealias.utils.TablasServidor;
import tablealias.web.result.ConsultaTotalesResult;

public interface ConsultaTotalesService {

	ConsultaTotalesResult doConsulta(ConsultaDto dto, TablasServidor ts)
			throws Exception;

}
