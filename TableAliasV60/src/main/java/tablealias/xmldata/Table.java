package tablealias.xmldata;

import java.util.Iterator;
import java.util.List;

import tablealias.fieldsearchs.iterators.FirstAndRestIterator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author INEGI
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class Table implements Cloneable {

	private String schema;
	private String name;
	private String alias;
	private String databaseName;
	private String server;
	private String geomName;
	private String proy;
	private List<SearchField> searchFields;
	private List<String> orderByFields;
	protected List<String> proyectos;
	private boolean buscable;
	private boolean identificable;
	private boolean buffereable;
	private String aliasUsuario;
	@JsonProperty
	private TableFields fields;
	private Totales totales;
	private boolean procesaTotales;
	private String camposTotales;
	private String campoTotales;
	private SubProjectData subProjectData;
	private Resolucion resolucion;

	public Table(String schema, String name, String alias, String databaseName,
			String server, String geomName, String proy, Totales totales,
			TableFields fields, SubProjectData subProjectData,
			Resolucion resolucion) {
		this.schema = schema;
		this.name = name;
		this.alias = alias;
		this.databaseName = databaseName;
		this.server = server;
		this.geomName = geomName;
		this.proy = proy;
		this.totales = totales;
		this.fields = fields;
		this.subProjectData = subProjectData;
		this.resolucion = resolucion;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void setTableFields(TableFields fields) {
		this.fields = fields;
	}

	public TableFields getFields() {
		return fields;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Table) {
			Table t = (Table) obj;
			return t.getDatabaseName().equalsIgnoreCase(databaseName)
					&& t.getName().equalsIgnoreCase(name)
					&& t.getSchema().equalsIgnoreCase(schema)
					&& t.getServer().equalsIgnoreCase(server);
		}
		return false;
	}

	@Override
	public Object clone() {
		Table obj = null;
		try {
			obj = (Table) super.clone();
			obj.fields = (TableFields) fields.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * @return the searchField
	 */
	public List<SearchField> getSearchFields() {
		return searchFields;
	}

	public String getSqlWhere(String[] valuesToSearch) {
		// valuesToSearch es creado en la clase FieldQueryDataCleaner en el
		// metodo cambia
		Iterator<SearchField> it = new FirstAndRestIterator(searchFields);
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < valuesToSearch.length; x++) {
			if (valuesToSearch[x].length() > 0) {
				if (it.hasNext()) {
					SearchField sf = it.next();
					sb.append(
							sf.getType().getSqlWhere(valuesToSearch[x],
									sf.getDicc())).append(" and ");
				} else {
					// this.name + " no tiene campos de busqueda");
				}
				/*
				 * else{
				 * sb.append(sf.getType().getSqlWhere(valuesToSearch[x])).append
				 * (" and "); }
				 */
			}
		}
		return sb.substring(0, sb.length() - " and ".length());
	}

	public String getSqlWhereWithConvierte(String[] valuesToSearch) {
		// valuesToSearch es creado en la clase FieldQueryDataCleaner en el
		// metodo cambia
		Iterator<SearchField> it = new FirstAndRestIterator(searchFields);
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < valuesToSearch.length; x++) {
			if (valuesToSearch[x].length() > 0) {
				if (it.hasNext()) {
					SearchField sf = it.next();
					sb.append(
							sf.getType().getSqlWhereWithConvierte(
									valuesToSearch[x], sf.getDicc())).append(
							" and ");
				} else {
					// this.name + " no tiene campos de busqueda");
				}
				/*
				 * else{
				 * sb.append(sf.getType().getSqlWhere(valuesToSearch[x])).append
				 * (" and "); }
				 */
			}
		}
		return sb.substring(0, sb.length() - " and ".length());
	}

	/**
	 * @param searchField
	 *            the searchField to set
	 */
	public void setSearchFields(List<SearchField> searchFields) {
		this.searchFields = searchFields;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the orderByFields
	 */
	public List<String> getOrderByFields() {
		return orderByFields;
	}

	public boolean hasOrderByFields() {
		return orderByFields != null && orderByFields.size() > 0;
	}

	/**
	 * @param orderByFields
	 *            the orderByFields to set
	 */
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

	/**
	 * @return the buscable
	 */
	public boolean isBuscable() {
		return buscable;
	}

	/**
	 * @param buscable
	 *            the buscable to set
	 */
	public void setBuscable(boolean buscable) {
		this.buscable = buscable;
	}

	/**
	 * @return the identificable
	 */
	public boolean isIdentificable() {
		return identificable;
	}

	/**
	 * @param identificable
	 *            the identificable to set
	 */
	public void setIdentificable(boolean identificable) {
		this.identificable = identificable;
	}

	/**
	 * @return the aliasUsuario
	 */
	public String getAliasUsuario() {
		return aliasUsuario;
	}

	/**
	 * @param aliasUsuario
	 *            the aliasUsuario to set
	 */
	public void setAliasUsuario(String aliasUsuario) {
		this.aliasUsuario = aliasUsuario;
	}

	/**
	 * @return the proyectos
	 */
	public List<String> getProyectos() {
		return proyectos;
	}

	/**
	 * @param proyectos
	 *            the proyectos to set
	 */
	public void setProyectos(List<String> proyectos) {
		this.proyectos = proyectos;
	}

	/**
	 * @return the buffereable
	 */
	public boolean isBuffereable() {
		return buffereable;
	}

	/**
	 * @param buffereable
	 *            the buffereable to set
	 */
	public void setBuffereable(boolean buffereable) {
		this.buffereable = buffereable;
	}

	public String getGeomName() {
		return geomName;
	}

	public String getProy() {
		return proy;
	}

	/**
	 * @return the totales
	 */
	public Totales getTotales() {
		return totales;
	}

	public boolean hasTotales() {
		if (totales != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the procesaTotales
	 */
	public boolean isProcesaTotales() {
		return procesaTotales;
	}

	/**
	 * @param procesaTotales
	 *            the procesaTotales to set
	 */
	public void setProcesaTotales(boolean procesaTotales) {
		if (totales != null
				&& totales.getGsql().idValidField(this.campoTotales)) {
			this.procesaTotales = procesaTotales;
		} else {
			this.procesaTotales = false;
		}
	}

	/**
	 * @return the campoTotales
	 */
	public String getCampoTotales() {
		return campoTotales;
	}

	/**
	 * @param campoTotales
	 *            the campoTotales to set
	 */
	public void setCampoTotales(String campoTotales) {
		this.campoTotales = campoTotales;
	}

	/**
	 * @return the camposTotales
	 */
	public String getCamposTotales() {
		return camposTotales;
	}

	/**
	 * @param camposTotales
	 *            the camposTotales to set
	 */
	public void setCamposTotales(String camposTotales) {
		this.camposTotales = camposTotales;
	}

	public SubProjectData getSubProjectData() {
		return subProjectData;
	}

	public boolean hasSubProjectData() {
		if (subProjectData == null || subProjectData.getSubProjects() == null
				|| subProjectData.getSubProjects().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<Field> getTypeFields() {
		return fields.getTypeFields();// previendo que pongan mas de uno, por el
										// momento solo debe haber uno
	}

	public boolean hasTypeFields() {
		return !(fields.getTypeFields() == null || fields.getTypeFields()
				.isEmpty());
	}

	public Resolucion getResolucion() {
		return resolucion;
	}

}
