package tablealias.service;

import tablealias.input.LineaTiempoInput;
import tablealias.utils.TablasServidor;
import tablealias.web.result.LineaTiempoResult;

public interface LineaTiempoService {

	LineaTiempoResult consultaLineaTiempo(LineaTiempoInput in, TablasServidor ts)
			throws Exception;

}
