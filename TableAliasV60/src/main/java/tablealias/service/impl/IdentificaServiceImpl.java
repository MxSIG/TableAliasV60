package tablealias.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablealias.dto.ConsultaDto;
import tablealias.dto.IdentificaDto;
import tablealias.service.ConsultaService;
import tablealias.service.IdentificaService;
import tablealias.utils.TablasServidor;
import tablealias.utils.TableUtil;
import tablealias.web.result.ConsultaResult;
import tablealias.web.result.IdentificaResult;
import tablealias.xmldata.Table;

@Service
public class IdentificaServiceImpl implements IdentificaService {

	@Autowired
	private ConsultaService consultaService;

	private List<String> defaultLayers;

	private DozerBeanMapper mapper;

	@Override
	public IdentificaResult identifica(IdentificaDto dto, TablasServidor ts)
			throws Exception {
		IdentificaResult identificaResult = new IdentificaResult();
		String[] tablas = addDefaultTables(dto.getTablas());
		boolean nonDefaultAdded = false;
		boolean notWithinResolution = true;
		for (String tabla : tablas) {
			if (TableUtil.tablaEstaEntreResolucion(tabla, dto.getResolution(),
					ts) || defaultLayers.contains(tabla)) {
				notWithinResolution = false;
				ConsultaDto consulta = mapper.map(dto, ConsultaDto.class);
				consulta.setTabla(tabla);
				ConsultaResult result = consultaService
						.doConsultaIdentificables(consulta, ts);
				Table[] tables = TableUtil.filtraCamposIdentificables(result
						.getTableData());
				if (defaultLayers.contains(tabla)) {
					identificaResult.addResult(tabla, tables, result);
				} else {
					if (!nonDefaultAdded && tables != null && tables.length > 0) {
						identificaResult.addResult(tabla, tables, result);
						nonDefaultAdded = true;
					}
				}
			}
		}
		if (notWithinResolution)
			identificaResult.setErrorMsg("Resolucion no encontrada");
		return identificaResult;
	}

	private String[] addDefaultTables(String[] tablas) {
		List<String> sentTables = new ArrayList<String>(Arrays.asList(tablas));
		List<String> result = new ArrayList<String>(Arrays.asList(tablas));
		for (String table : defaultLayers) {
			// boolean defaultLayerExists = false;
			for (String sentTable : sentTables) {
				if (sentTable.equalsIgnoreCase(table)) {
					result.remove(sentTable);
					// defaultLayerExists = true;
					break;
				}
			}
			// if (!defaultLayerExists)
			// sentTables.add(table);
		}
		result.addAll(defaultLayers);
		return result.toArray(new String[result.size()]);
	}

	@Autowired
	public void setMapper(DozerBeanMapper mapper) {
		this.mapper = mapper;
	}

	@Resource(name = "defaultLayers")
	public void setDefaultLayers(List<String> defaultLayers) {
		this.defaultLayers = defaultLayers;
	}

}
