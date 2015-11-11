/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmldata;

import java.util.Collections;
import java.util.List;

import tablealias.utils.FieldQueryDataCleaner;
import tablealias.utils.comparators.Comparators;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author INEGI
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class Field implements Cloneable {

	private String name;
	@JsonProperty
	private String aliasName;
	@JsonProperty
	private String value;
	private String sqlName;
	private List<FieldFunction> functions;
	private boolean hasFunctions;
	private boolean busquedaDisplay;
	private boolean consultaDisplay;
	private boolean consultaTipo;
	private boolean haspredato;
	private String predato;
	private boolean identificable;

	public Field(String name, String aliasName) {
		this.name = name;
		this.sqlName = FieldQueryDataCleaner.removeAcentos(name.replaceAll(
				"[\\|',\\- ()/]", "_"));
		this.aliasName = aliasName;
		busquedaDisplay = true;
		consultaDisplay = true;
		consultaTipo = false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	@Override
	public boolean equals(Object obj) {
		boolean exito = false;
		if (obj instanceof Field) {
			Field f = (Field) obj;
			exito = this.getName().equalsIgnoreCase(f.getName())
					&& this.getAliasName().equalsIgnoreCase(f.getAliasName());
		}
		return exito;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	protected Object clone() {
		Field obj = null;
		try {
			obj = (Field) super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * @return the sqlName
	 */
	public String getSqlName() {
		return sqlName;
	}

	/**
	 * @return the functions
	 */
	public List<FieldFunction> getFunctions() {
		return functions;
	}

	/**
	 * @return the hasFunctions
	 */
	public boolean hasFunctions() {
		return hasFunctions;
	}

	/**
	 * @param functions
	 *            the functions to set
	 */
	public void setFunctions(List<FieldFunction> functions) {
		this.hasFunctions = functions.size() > 0;
		this.functions = functions;
		if (this.functions.size() > 0)
			Collections
					.sort(this.functions, Comparators.FieldComparators
							.getComparatorByFunctionOrder());
	}

	/**
	 * @return the busquedaDisplay
	 */
	public boolean isBusquedaDisplay() {
		return busquedaDisplay;
	}

	/**
	 * @param busquedaDisplay
	 *            the busquedaDisplay to set
	 */
	public void setBusquedaDisplay(boolean busquedaDisplay) {
		this.busquedaDisplay = busquedaDisplay;
	}

	/**
	 * @return the consultaDisplay
	 */
	public boolean isConsultaDisplay() {
		return consultaDisplay;
	}

	/**
	 * @param consultaDisplay
	 *            the consultaDisplay to set
	 */
	public void setConsultaDisplay(boolean consultaDisplay) {
		this.consultaDisplay = consultaDisplay;
	}

	/**
	 * @return the haspredato
	 */
	public boolean hasPredato() {
		return haspredato;
	}

	/**
	 * @param haspredato
	 *            the haspredato to set
	 */
	public void setHaspredato(boolean haspredato) {
		this.haspredato = haspredato;
	}

	/**
	 * @return the predato
	 */
	public String getPredato() {
		return predato;
	}

	/**
	 * @param predato
	 *            the predato to set
	 */
	public void setPredato(String predato) {
		this.predato = predato;
	}

	public boolean isConsultaTipo() {
		return consultaTipo;
	}

	public void setConsultaTipo(boolean consultaTipo) {
		this.consultaTipo = consultaTipo;
	}

	public void setIdentificable(boolean identificable) {
		this.identificable = identificable;
	}

	public boolean isIdentificable() {
		return identificable;
	}

}
