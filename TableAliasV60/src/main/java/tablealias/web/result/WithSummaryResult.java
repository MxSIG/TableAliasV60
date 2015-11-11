package tablealias.web.result;

import java.util.List;

public class WithSummaryResult implements Result {

	private String tabla;
	private Object result;
	private Integer totalFields;
	private String typeFields;
	private String aliasUsuario;
	private Integer currentPage;
	private String camposTotales;
	private boolean esTotales;
	private boolean exito;
	private String errorMsg;
	private List<String> types;

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
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

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
}
