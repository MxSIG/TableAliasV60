package tablealias.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dao.BusquedaDao;
import tablealias.input.BusquedaInput;
import tablealias.service.BusquedaService;
import tablealias.sqlworkers.BusquedaWrapper;
import tablealias.sqlworkers.QueryWorker;
import tablealias.utils.DomicilioValidator;
import tablealias.utils.ExceptionSearchParser;
import tablealias.utils.ExceptionTables;
import tablealias.utils.FieldQueryDataCleaner;
import tablealias.utils.TablasServidor;
import tablealias.web.result.ResultUtil;
import tablealias.web.result.WithSummaryResult;

@Service
public class BusquedaServiceImpl implements BusquedaService {

	private static final Logger logger = Logger
			.getLogger(BusquedaServiceImpl.class);

	@Autowired
	private BusquedaDao dao;

	@Override
	public WithSummaryResult doBusqueda(BusquedaInput input,
			TablasServidor tablasServidor, ExceptionTables exTables)
			throws Exception {
		WithSummaryResult result = new WithSummaryResult();
		String table2Search = input.getTabla().replaceAll("['\"]", "");
		String proyecto = input.getProyName();
		String valueToSearch = input.getSearchCriteria().trim()
				.replaceAll("['\"]", "");
		Integer pagina = input.getPagina();
		String where = input.getWhere();
		String whereTipo = input.getWhereTipo();

		boolean exceptionSearch = false;
		if (tablasServidor.tableExists(table2Search, proyecto)) {
			if (proyecto != null && table2Search.equalsIgnoreCase("geolocator")) {
				if (tablasServidor.isProyectoValido(proyecto)) {
					tablasServidor.setGeolocatorTableFromProyecto(proyecto);
				}
			}
			valueToSearch = FieldQueryDataCleaner.removeAcentos(valueToSearch);
			ExceptionSearchParser esp = null;
			if (exTables.isExceptionTable(table2Search)) {
				esp = new ExceptionSearchParser(valueToSearch);
				esp.setExceptionToken("--");
				exceptionSearch = !esp.isBusquedaNormal();
				if (exceptionSearch) {
					table2Search = "geocalles";
					tablasServidor.tableExists(table2Search);
					if (tablasServidor.tableExists(table2Search + proyecto,
							proyecto)) {
						table2Search = table2Search + proyecto;
					} else {
						tablasServidor.tableExists(table2Search);
					}
				}
			}
			String[] datos = null;
			if (!exceptionSearch) {
				datos = new String[] { valueToSearch };
			}
			String subProject = null;// SubProjectReader.getSubProjectString(tablasServidor.getFoundTable(),
										// request);
			QueryWorker qw = new BusquedaWrapper(datos, exTables, esp, where,
					subProject, whereTipo);
			qw.setServer(tablasServidor.getFoundServer());
			qw.setTable(tablasServidor.getFoundTable());
			qw.setPageToView(pagina);
			ResultUtil.doQuery(qw, result);

			DomicilioValidator dmv = new DomicilioValidator();
			int totalIndicadores = 0;

			if (table2Search.equalsIgnoreCase("geolocator")
					&& totalIndicadores == 0 && qw.getNumberOfRecords() == 0
					&& !exceptionSearch && dmv.isDomicilioKind(valueToSearch)) {
				table2Search = "cNumExt";
				datos = new String[] { valueToSearch };
				qw.setValueToSearch(datos);
				tablasServidor.tableExists(table2Search);
				qw.setTable(tablasServidor.getFoundTable());
				ResultUtil.doQuery(qw, result);
				if (qw.getNumberOfRecords() == 0) {
					table2Search = "cNumExtRur";
					datos = new String[] { valueToSearch };
					qw.setValueToSearch(datos);
					tablasServidor.tableExists(table2Search);
					qw.setTable(tablasServidor.getFoundTable());
					ResultUtil.doQuery(qw, result);
				}
			}

			List<String> types = dao.findTypes(tablasServidor.getFoundServer(),
					input.getSearchCriteria());

			result.setTypes(types);

			return result;
		} else {
			result.setExito(false);
			result.setErrorMsg("table " + table2Search + "does not exists.");
			return result;
		}

	}

}
