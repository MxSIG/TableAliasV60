package tablealias.service;

import tablealias.input.BusquedaInput;
import tablealias.utils.ExceptionTables;
import tablealias.utils.TablasServidor;
import tablealias.web.result.WithSummaryResult;

public interface BusquedaService {

	WithSummaryResult doBusqueda(BusquedaInput input,
			TablasServidor tablasServidor, ExceptionTables exTables)
			throws Exception;

}
