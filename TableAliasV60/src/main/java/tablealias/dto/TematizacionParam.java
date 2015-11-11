package tablealias.dto;

import java.util.List;

import dtweb.temas.dto.EstratificacionData;

public class TematizacionParam {

	String mapa;

	String metodo;

	String muestras;

	String estratos;

	String operacion;

	String proyName;

	String tema;

	List<EstratificacionData> datosEstratos;

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getMuestras() {
		return muestras;
	}

	public void setMuestras(String muestras) {
		this.muestras = muestras;
	}

	public String getEstratos() {
		return estratos;
	}

	public void setEstratos(String estratos) {
		this.estratos = estratos;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getProyName() {
		return proyName;
	}

	public void setProyName(String proyName) {
		this.proyName = proyName;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public List<EstratificacionData> getDatosEstratos() {
		return datosEstratos;
	}

	public void setDatosEstratos(List<EstratificacionData> datosEstratos) {
		this.datosEstratos = datosEstratos;
	}

}
