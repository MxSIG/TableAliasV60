package tablealias.service;

import tablealias.dto.ConsultaDto;
import tablealias.utils.TablasServidor;
import tablealias.web.result.ConsultaResult;

public interface ConsultaService {

	ConsultaResult doConsulta(ConsultaDto dto, TablasServidor tablasServidor)
			throws Exception;

	ConsultaResult doConsultaIdentificables(ConsultaDto dto,
			TablasServidor tablasServidor) throws Exception;

}
