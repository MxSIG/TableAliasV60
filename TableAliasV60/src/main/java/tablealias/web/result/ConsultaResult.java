package tablealias.web.result;

import tablealias.xmldata.Table;

public class ConsultaResult implements Result {

	private String tabla;
	private Table[] tableData;
	private Integer totalFields;
	private String typeFields;
	private String aliasUsuario;
	private Integer currentPage;
	private String camposTotales;
	private boolean esTotales;
	private boolean exito;
	private String errorMsg;

	public Table[] getTableData() {
		return tableData;
	}

	public void setTableData(Table[] tableData) {
		this.tableData = tableData;
	}

	public Integer getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(Integer totalFields) {
		this.totalFields = totalFields;
	}

	public String getTypeFields() {
		return typeFields;
	}

	public void setTypeFields(String typeFields) {
		this.typeFields = typeFields;
	}

	public String getAliasUsuario() {
		return aliasUsuario;
	}

	public void setAliasUsuario(String aliasUsuario) {
		this.aliasUsuario = aliasUsuario;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getCamposTotales() {
		return camposTotales;
	}

	public void setCamposTotales(String camposTotales) {
		this.camposTotales = camposTotales;
	}

	public boolean isEsTotales() {
		return esTotales;
	}

	public void setEsTotales(boolean esTotales) {
		this.esTotales = esTotales;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	// @Override
	public Object getResult() {
		return tableData;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

}
