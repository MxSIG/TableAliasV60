package tablealias.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class IdentificaDto {

	@NotNull(message = "no puede ser nulo")
	@Pattern(regexp = "^(?:\\w+,?)+$", message = "mal formato.")
	private String tablas;
	private Integer pagina;
	private Double x1;
	// private Double x2;
	private Double y1;
	// private Double y2;
	private Double width;
	private String proyName;
	private String where;
	private String tipo;
	private String resolution;

	public void setTablas(String tablas) {
		this.tablas = tablas;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Double getX1() {
		return x1;
	}

	public void setX1(Double x1) {
		this.x1 = x1;
	}

	public Double getY1() {
		return y1;
	}

	public void setY1(Double y1) {
		this.y1 = y1;
	}

	// public Double getX2() {
	// return x2;
	// }
	//
	// public void setX2(Double x2) {
	// this.x2 = x2;
	// }

	// public Double getY2() {
	// return y2;
	// }
	//
	// public void setY2(Double y2) {
	// this.y2 = y2;
	// }

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getProyName() {
		return proyName;
	}

	public void setProyName(String proyName) {
		this.proyName = proyName;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String[] getTablas() {
		return tablas.split(",");
	}

	public String getResolution() {
		return resolution;
	}

}
