package tablealias.web.result;

import tablealias.xmldata.Table;

public class ConsultaTotalesResult implements Result {

	private Table[] tableData;
	private Table[] tableDataTotales;
	private boolean exito;
	private String errorMsg;
	private Integer totalFields;
	private String typeFields;
	private String aliasUsuario;
	private Integer currentPage;
	private String camposTotales;
	private boolean esTotales;
	private String tabla;

	@Override
	public boolean isExito() {
		return exito;
	}

	@Override
	public Object getResult() {
		return tableData;
	}

	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public Table[] getTableData() {
		return tableData;
	}

	public void setTableData(Table[] tableData) {
		this.tableData = tableData;
	}

	public Table[] getTableDataTotales() {
		return tableDataTotales;
	}

	public void setTableDataTotales(Table[] tableDataTotales) {
		this.tableDataTotales = tableDataTotales;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;

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

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getTabla() {
		return tabla;
	}

}
