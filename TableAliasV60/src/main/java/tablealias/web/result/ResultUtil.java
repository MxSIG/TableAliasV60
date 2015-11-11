package tablealias.web.result;

import tablealias.sqlworkers.QueryWorker;
import tablealias.xmldata.Table;

/**
 * Utility class for 'Result' types that need to have some extra info besides
 * query result.
 * 
 * @author INEGI
 * 
 */
public class ResultUtil {

	public static void doQuery(QueryWorker qw, WithSummaryResult result)
			throws Exception {
		String aliasUsuario = null;
		String camposTotales = null;
		boolean isProcesaTotales = false;
		String dataTypes = null;
		Integer currentPage = null;
		Integer totalRecords = null;
		Table[] data = (Table[]) qw.doQuery();
		if (qw.hasErrors()) {
			result.setErrorMsg(qw.getErrorMsg());
			result.setExito(false);
		} else {
			if (data != null && data.length > 0) {
				result.setExito(true);
				totalRecords = ((qw.getPageToView() - 1) * 50) + data.length;
				if (data.length == 50)
					totalRecords = qw.getNumberOfRecords();
				aliasUsuario = data[0].getAliasUsuario();
				dataTypes = qw.getTypesOfRecords();
				currentPage = qw.getPageToView();
				camposTotales = qw.getTable().getCamposTotales();
				isProcesaTotales = qw.getTable().isProcesaTotales();
			} else {
				aliasUsuario = qw.getTable().getAliasUsuario();
				result.setErrorMsg("No records found.");
			}
			result.setAliasUsuario(aliasUsuario);
			result.setCurrentPage(currentPage);
			result.setCamposTotales(camposTotales);
			result.setEsTotales(isProcesaTotales);
			result.setResult(data);
			result.setTotalFields(totalRecords);
			result.setTabla(qw.getTable().getAlias());
			result.setTypeFields(dataTypes);
		}
	}

}
