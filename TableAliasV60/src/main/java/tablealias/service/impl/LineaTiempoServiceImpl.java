package tablealias.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dao.LineaTiempoDao;
import tablealias.dto.LineaTiempoDto;
import tablealias.input.LineaTiempoInput;
import tablealias.service.LineaTiempoService;
import tablealias.utils.TablasServidor;
import tablealias.web.result.LineaTiempoResult;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

@Service
public class LineaTiempoServiceImpl implements LineaTiempoService {

	@Autowired
	private LineaTiempoDao dao;

	private List<LineaTiempoDto> procesaTabla(LineaTiempoInput in, Table table,
			TablasServidor ts) throws SQLException {
		Server server = ts.getFoundServer();
		String _table = table.getSchema() + "." + table.getName();
		return dao.getLineaTiempo(_table, server, in.getExtent());
	}

	@Override
	public LineaTiempoResult consultaLineaTiempo(LineaTiempoInput in,
			TablasServidor ts) throws Exception {
		LineaTiempoResult lineaResult = new LineaTiempoResult();
		String proyecto = in.getProyName();

		String[] tablas = in.getTablas();
		Map<String, List<LineaTiempoDto>> result = new HashMap<String, List<LineaTiempoDto>>();
		for (String tabla : tablas) {
			Table table = null;
			if (ts.tableExists(tabla, proyecto)) {
				table = ts.getFoundTableClone();
			} else
				throw new Exception("table " + tabla + " not found.");
			List<LineaTiempoDto> data = procesaTabla(in, table, ts);
			if (data != null) {
				result.put(tabla, data);
			}
		}
		if (result.size() > 0) {
			lineaResult.setExito(true);
			lineaResult.setResult(result);
		} else {
			lineaResult.setErrorMsg("No se encontraron datos");
		}
		return lineaResult;
	}

}
