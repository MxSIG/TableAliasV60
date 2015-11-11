package tablealias.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dto.ConsultaDto;
import tablealias.service.ConsultaService;
import tablealias.service.ConsultaTotalesService;
import tablealias.utils.TablasServidor;
import tablealias.utils.TableUtil;
import tablealias.web.result.ConsultaResult;
import tablealias.web.result.ConsultaTotalesResult;
import tablealias.xmldata.Table;
import tablealias.xmldata.Totales;

@Service
public class ConsultaTotalesServiceImpl implements ConsultaTotalesService {

	@Autowired
	private ConsultaService consultaService;

	@Override
	public ConsultaTotalesResult doConsulta(ConsultaDto dto, TablasServidor ts)
			throws Exception {
		ConsultaTotalesResult result = new ConsultaTotalesResult();
		String totalesConsulta = dto.getTotalesConsulta();
		dto.setTotalesConsulta(null);
		ConsultaResult consultaResult = consultaService
				.doConsultaIdentificables(dto, ts);
		Table[] tableData = consultaResult.getTableData();
		result.setTotalFields(consultaResult.getTotalFields());
		result.setTabla(consultaResult.getTabla());
		result.setTypeFields(consultaResult.getTypeFields());
		result.setAliasUsuario(consultaResult.getAliasUsuario());
		result.setCurrentPage(consultaResult.getCurrentPage());
		result.setCamposTotales(consultaResult.getCamposTotales());
		result.setEsTotales(consultaResult.isEsTotales());
		Table[] tables = TableUtil.filtraCamposIdentificables(tableData);
		result.setTableData(tables);
		result.setExito(consultaResult.isExito());
		result.setErrorMsg(consultaResult.getErrorMsg());
		if (ts.tableExists(dto.getTabla(), dto.getProyName())) {
			Table foundTable = ts.getFoundTable();
			Totales totales = foundTable.getTotales();
			if (totales != null && totales.getColumnas() != null
					&& totales.getColumnas().size() > 0) {
				dto.setTotalesConsulta(totalesConsulta);
				ConsultaResult consultaResultTotales = consultaService
						.doConsultaIdentificables(dto, ts);
				Table[] tableDataTotales = consultaResultTotales.getTableData();
				// Table[] tablesTotales = TableUtil
				// .filtraCamposIdentificables(tableDataTotales);
				result.setTableDataTotales(tableDataTotales);
			}
		}
		return result;

	}
}
