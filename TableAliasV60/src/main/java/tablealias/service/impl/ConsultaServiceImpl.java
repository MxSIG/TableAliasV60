package tablealias.service.impl;

import java.sql.SQLException;

import mx.inegi.dtweb.connection.DebugerLog;

import org.springframework.stereotype.Service;

import tablealias.dto.ConsultaDto;
import tablealias.service.ConsultaService;
import tablealias.sqlworkers.ConsultaWrapper;
import tablealias.utils.Polygon;
import tablealias.utils.ProyectoInvalido;
import tablealias.utils.TablasServidor;
import tablealias.web.result.ConsultaResult;
import tablealias.xmldata.Table;

@Service
public class ConsultaServiceImpl implements ConsultaService {

	private Polygon createPolygon(ConsultaDto dto, Table table) {
		String poligonoURL = dto.getPoligono();
		Polygon polygon = null;
		Double x1 = dto.getX1();
		Double y1 = dto.getY1();
		Double x2 = dto.getX2();
		Double y2 = dto.getY2();
		if (poligonoURL != null) {
			polygon = new Polygon(poligonoURL, table.getGeomName(),
					table.getProy());
		} else {
			polygon = new Polygon(x1, y1, dto.getWidth(), table.getGeomName(),
					table.getProy());
			if (x2 != null && y2 != null) {
				polygon.setX1(x1);
				polygon.setX2(x2);
				polygon.setY1(y1);
				polygon.setY2(y2);
			}
		}
		return polygon;
	}

	private ConsultaResult ejecutaConsulta(ConsultaDto dto,
			TablasServidor tablasServidor, boolean identificables)
			throws ProyectoInvalido, SQLException, Exception {
		Table table = null;
		Polygon polygon = null;
		String table2Search = dto.getTabla();
		String proyecto = dto.getProyName();
		String totalesConsulta = dto.getTotalesConsulta();
		String where = dto.getWhere();
		String gid = dto.getGid();
		Integer pagina = dto.getPagina();

		if (tablasServidor.tableExists(table2Search, proyecto)) {
			table = tablasServidor.getFoundTableClone();
		} else
			throw new Exception("table " + table2Search + " not found.");

		if (totalesConsulta != null && !totalesConsulta.isEmpty()) {
			table.setCampoTotales(totalesConsulta);
			table.setProcesaTotales(true);
		} else {
			table.setProcesaTotales(false);
		}

		polygon = createPolygon(dto, table);
		ConsultaResult result = new ConsultaResult();
		result.setExito(false);

		if (tablasServidor.tableExists(table2Search, proyecto)) {
			// subproject not used in this version of tablealias.
			String subProject = null;
			ConsultaWrapper qw = new ConsultaWrapper(where, gid, subProject,
					identificables);
			qw.setServer(tablasServidor.getFoundServer());
			qw.setTable(table);
			qw.setPolygon(polygon);
			qw.setPageToView(pagina);
			qw.setServerBuffer(tablasServidor.getServerByTable("c100"));
			Table[] data = (Table[]) qw.doQuery();
			if (qw.hasErrors()) {
				DebugerLog.log("Error: " + qw.getErrorMsg());
				result.setErrorMsg(qw.getErrorMsg());
			} else {
				if (data != null && data.length > 0) {
					int totalRecords = ((qw.getPageToView() - 1) * 50)
							+ data.length;
					if (data.length == 50)
						totalRecords = qw.getNumberOfRecords();

					String aliasUsuario = data[0].getAliasUsuario();
					String dataTypes = qw.getTypesOfRecords();

					result.setExito(true);
					result.setTableData(data);
					result.setTabla(table.getAlias());
					result.setTotalFields(totalRecords);
					result.setTypeFields(dataTypes);
					result.setAliasUsuario(aliasUsuario);
					result.setCurrentPage(qw.getPageToView());
					result.setCamposTotales(qw.getTable().getCamposTotales());
					result.setEsTotales(qw.getTable().isProcesaTotales());

				} else {
					String aliasUsuario = qw.getTable().getAliasUsuario();
					result.setAliasUsuario(aliasUsuario);
					result.setTotalFields(0);
					result.setErrorMsg("No records found.");
				}
			}
		}

		return result;
	}

	@Override
	public ConsultaResult doConsulta(ConsultaDto dto,
			TablasServidor tablasServidor) throws Exception {
		return ejecutaConsulta(dto, tablasServidor, false);
	}

	@Override
	public ConsultaResult doConsultaIdentificables(ConsultaDto dto,
			TablasServidor tablasServidor) throws Exception {
		return ejecutaConsulta(dto, tablasServidor, true);
	}
}
