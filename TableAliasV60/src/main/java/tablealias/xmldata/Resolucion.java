package tablealias.xmldata;

import tablealias.utils.Validaciones;

/**
 * Dependiendo de la resolucion dada, se identificaran distintos elementos.
 * 
 * @author INEGI
 * 
 */
public class Resolucion {

	Double minValue;
	Double maxValue;

	public Resolucion(Double minValue, Double maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Resolucion(String minimumValue, String maximumValue) {
		minValue = validDouble(minimumValue);
		maxValue = validDouble(maximumValue);
	}

	private Double validDouble(String value) {
		if (value != null && value.trim().length() > 0)
			if (Validaciones.isFloatNumber(value))
				return Double.parseDouble(value);
		return null;
	}

	public boolean estaEnRango(Double resolucion) {
		if (minValue != null && maxValue != null)
			if (resolucion >= minValue && resolucion <= maxValue)
				return true;
		return false;
	}

	public Double getMinValue() {
		return minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

}
