package tablealias.dto;

import validation.customconstraint.consultadto.ValidConsultaDto;

/**
 * Dto class that represents all expected request parameters for controller
 * Consulta.
 * 
 * @author INEGI
 * @see ValidConsultaDto
 * 
 */
@ValidConsultaDto
public class ConsultaDto {

	private String tabla;

	private String poligono;
	private String gid;
	private String totalesConsulta;

	private Double x1;

	private Double x2;

	private Double y1;
	private Double y2;

	private Double width;

	private String where;
	private Integer pagina;
	private String proyName;
	private String tipo;

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getPoligono() {
		return poligono;
	}

	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getTotalesConsulta() {
		return totalesConsulta;
	}

	public void setTotalesConsulta(String totalesConsulta) {
		this.totalesConsulta = totalesConsulta;
	}

	public Double getX1() {
		if (this.x1 == null) {
			this.x1 = Double.MIN_VALUE;
		}

		return x1;
	}

	public void setX1(Double x1) {
		this.x1 = x1;
	}

	public Double getX2() {
		return x2;
	}

	public void setX2(Double x2) {
		this.x2 = x2;
	}

	public Double getY1() {
		if (this.y1 == null) {

			this.y1 = Double.MIN_VALUE;

		}
		return y1;
	}

	public void setY1(Double y1) {
		this.y1 = y1;
	}

	public Double getY2() {
		return y2;
	}

	public void setY2(Double y2) {
		this.y2 = y2;
	}

	public Double getWidth() {
		if (width == null) {

			this.width = Double.MIN_VALUE;
		}
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public String getProyName() {
		return proyName;
	}

	public void setProyName(String proyName) {
		this.proyName = proyName;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
