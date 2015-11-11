package tablealias.input;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusquedaInput {

	@NotNull(message = "no puede ser nulo")
	String tabla;
	@NotNull(message = "no puede ser nulo")
	Integer pagina;
	@NotNull(message = "no puede ser nulo")
	String searchCriteria;
	String where;
	String whereTipo;
	String proyName;

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getWhereTipo() {
		return whereTipo;
	}

	public void setWhereTipo(String whereTipo) {
		this.whereTipo = whereTipo;
	}

	public String getProyName() {
		return proyName;
	}

	public void setProyName(String proyecto) {
		this.proyName = proyecto;
	}

}
