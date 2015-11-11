package tablealias.web.result;

import java.util.ArrayList;
import java.util.List;

import tablealias.xmldata.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class IdentificaResult implements Result {

	private String errorMessage;

	@JsonProperty
	List<ResultPerTable> data = new ArrayList<IdentificaResult.ResultPerTable>();

	private static class ResultPerTable {
		private String tabla;
		private Table[] tables;
		private boolean exito;
		private String errorMsg;
		private String aliasUsuario;
		private Integer totalFields;
		private Integer currentPage;

		public void setExito(boolean exito) {
			this.exito = exito;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public void setTables(Table[] tables) {
			this.tables = tables;
		}

		public Table[] getTables() {
			return tables;
		}

		public boolean isExito() {
			return exito;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public String getAliasUsuario() {
			return aliasUsuario;
		}

		public void setAliasUsuario(String aliasUsuario) {
			this.aliasUsuario = aliasUsuario;
		}

		public String getTabla() {
			return tabla;
		}

		public void setTabla(String tableName) {
			this.tabla = tableName;
		}

		public Integer getTotalFields() {
			return totalFields;
		}

		public void setTotalFields(Integer totalFields) {
			this.totalFields = totalFields;
		}

		public Integer getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}
	}

	public boolean isExito() {
		return data.size() > 0;
	}

	public boolean isDefaultTable(String table) {
		if (!isExito())
			return false;
		else
			return data.contains(table);
	}

	public void addResult(String tableName, Table[] tables, boolean exito,
			String error, String aliasUsuario) {
		if (tables != null && tables.length > 0) {
			ResultPerTable r1 = new ResultPerTable();
			r1.setTabla(tableName);
			r1.setTables(tables);
			r1.setErrorMsg(error);
			r1.setExito(exito);
			r1.setAliasUsuario(aliasUsuario);
			data.add(r1);
		}
	}

	public void addResult(String tableName, Table[] tables,
			ConsultaResult result) {
		if (tables != null && tables.length > 0) {
			ResultPerTable r1 = new ResultPerTable();
			r1.setTabla(tableName);
			r1.setTables(tables);
			r1.setErrorMsg(result.getErrorMsg());
			r1.setExito(result.isExito());
			r1.setAliasUsuario(result.getAliasUsuario());
			r1.setCurrentPage(result.getCurrentPage());
			r1.setTotalFields(result.getTotalFields());
			data.add(r1);
		}

	}

	@Override
	public List<ResultPerTable> getResult() {
		return data;
	}

	@Override
	public String getErrorMsg() {
		if (errorMessage != null)
			return errorMessage;
		else
			return "No se encontraron tablas con campos identificables";
	}

	public void setErrorMsg(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
