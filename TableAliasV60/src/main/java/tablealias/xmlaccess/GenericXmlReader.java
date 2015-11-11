package tablealias.xmlaccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tablealias.xmldata.Columna;
import tablealias.xmldata.Field;
import tablealias.xmldata.FieldFunction;
import tablealias.xmldata.Resolucion;
import tablealias.xmldata.SearchField;
import tablealias.xmldata.SubProjectData;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;
import tablealias.xmldata.Totales;
import tablealias.xmldata.dto.SubProject;

/**
 * 
 * @author INEGI this class substitutes aliasreader
 */
public class GenericXmlReader<T> extends AbstractXmlReader<T> {

	private List<T> data;

	public GenericXmlReader(File xmlFile) {
		super(xmlFile);
	}

	private void getFunctions(Element el, List<FieldFunction> lista) {
		NodeList nl = el.getElementsByTagName("function");// will get as much
															// functions as
															// defined in
															// aliasData.xml in
															// Some field at a
															// given moment
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el2 = (Element) nl.item(i);
				String fxName = getTextValue(el2, "functionname");
				String order = el2.getAttribute("order");
				FieldFunction ff = new FieldFunction(fxName, order);
				lista.add(ff);
			}
		}
	}

	private Field getField(Element el) {
		String name = getTextValue(el, "nombre");
		String alias = getTextValue(el, "alias");
		String busquedaDisplay = el.getAttribute("busquedaDisplay");
		String consultaDisplay = el.getAttribute("consultaDisplay");
		String consultaTipo = el.getAttribute("consultaTipo");
		String preDato = el.getAttribute("predato");
		String identificable = el.getAttribute("identificable");

		// if (Boolean.valueOf(consultaDisplay) &&
		// alias.equalsIgnoreCase("Ubicacion") == false) {
		Field field = new Field(name, alias);
		if (!preDato.equalsIgnoreCase("")) {
			field.setHaspredato(true);
			field.setPredato(preDato);
		}
		if (busquedaDisplay.equalsIgnoreCase("true")
				|| busquedaDisplay.equalsIgnoreCase("false")) {
			field.setBusquedaDisplay(Boolean.valueOf(busquedaDisplay)
					.booleanValue());
		}
		if (consultaDisplay.equalsIgnoreCase("true")
				|| consultaDisplay.equalsIgnoreCase("false")) {
			field.setConsultaDisplay(Boolean.valueOf(consultaDisplay)
					.booleanValue());
		}
		if (consultaTipo.equalsIgnoreCase("true")
				|| consultaTipo.equalsIgnoreCase("false")) {
			field.setConsultaTipo(Boolean.valueOf(consultaTipo));
		}
		if (identificable.equalsIgnoreCase("true")
				|| identificable.equalsIgnoreCase("false")) {
			field.setIdentificable(Boolean.valueOf(identificable));
		}

		LinkedList<FieldFunction> lista = new LinkedList<FieldFunction>();
		getFunctions(el, lista);
		field.setFunctions(lista);
		// lista.size());
		return field;
	}

	private SearchField getSearchField(Element el) {
		String searchFieldName = getTextValue(el, "nombre");
		String searchType = el.getAttribute("tipo");
		String searchDicc = el.getAttribute("dicc");
		String searchCFunction = el.getAttribute("cfunction");
		SearchField sf = new SearchField(searchFieldName, searchType,
				searchDicc, searchCFunction);
		return sf;
	}

	private T getTable(Element el) {
		String schema = getTextValue(el, "esquema");
		String name = getTextValue(el, "nombre");
		String alias = getTextValue(el, "alias");
		String servidor = getTextValue(el, "servidor");
		String dbName = getTextValue(el, "database");
		String geomName = getTextValue(el, "nombre_geometria");
		String proy = getTextValue(el, "numero_proyeccion");
		String resolucionMinValue = getTextValue(el,
				"identifica_resolucion_min");
		String resolucionMaxValue = getTextValue(el,
				"identifica_resolucion_max");
		TableFields tf = new TableFields();
		Totales totales = getTotales(el);
		SubProjectData subProjectData = getSubProjectData(el);
		Table table = new Table(schema, name, alias, dbName, servidor,
				geomName, proy, totales, tf, subProjectData, new Resolucion(
						resolucionMinValue, resolucionMaxValue));
		if (totales != null)
			table.setCamposTotales(totales.getCamposTotalesWEB());
		NodeList nl = el.getElementsByTagName("campo");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el2 = (Element) nl.item(i);
				Field f = getField(el2);
				if (f != null) {
					tf.addField(f);
				}
			}
		}
		List<SearchField> fields = new ArrayList<SearchField>();
		NodeList nl2 = el.getElementsByTagName("campoBusqueda");
		if (nl2 != null && nl2.getLength() > 0) {
			for (int i = 0; i < nl2.getLength(); i++) {
				Element el2 = (Element) nl2.item(i);
				SearchField sf = getSearchField(el2);
				fields.add(sf);
			}
		}
		table.setSearchFields(fields);
		List<String> orderByFields = new ArrayList<String>();
		NodeList nl3 = el.getElementsByTagName("field");
		if (nl3 != null && nl3.getLength() > 0) {
			for (int i = 0; i < nl3.getLength(); i++) {
				Element el2 = (Element) nl3.item(i);
				// String sf = getOrderByField(el2);
				String sf = el2.getFirstChild().getNodeValue();
				orderByFields.add(sf);
			}
		}
		table.setOrderByFields(orderByFields);
		return (T) table;
	}

	private Totales getTotales(Element el) {
		Totales totales = null;
		if (el.getElementsByTagName("totales") != null
				&& el.getElementsByTagName("totales").getLength() > 0) {
			totales = new Totales();
			// NodeList nl = el.getElementsByTagName("totales");
			// nl = ((Element)nl).getElementsByTagName("columnas");
			NodeList nl = el.getElementsByTagName("columna");
			List<Columna> columnas = null;
			if (nl != null && nl.getLength() > 0) {
				columnas = new LinkedList<Columna>();
				for (int i = 0; i < nl.getLength(); i++) {
					Columna c = new Columna();
					Element el2 = (Element) nl.item(i);
					c.setAlias(getTextValue(el2, "alias"));
					c.setNombre(getTextValue(el2, "nombre"));
					columnas.add(c);
				}
			}
			/*
			 * nl = el.getElementsByTagName("totales"); Element ee =
			 * (Element)nl;
			 */
			totales.setColumnas(columnas);
			nl = el.getElementsByTagName("generador");
			Element ee = (Element) nl.item(0);
			totales.setGsql(ee.getAttribute("camposAgrupamiento"),
					ee.getAttribute("alias"), getTextValue(el, "generador"));
		}
		return totales;
	}

	private void getTableAttributes(Table table, Element el) {
		String buscable = el.getAttribute("buscable");
		String proyectos = el.getAttribute("proyectos").toLowerCase();
		if (!proyectos.equals("")) {
			String[] proys = proyectos.split(",");
			List<String> datos = Arrays.<String> asList(proys);
			table.setProyectos(datos);
		}
		if (buscable.equalsIgnoreCase("true")
				|| buscable.equalsIgnoreCase("false")) {
			table.setBuscable(Boolean.parseBoolean(buscable));
		}
		String identificable = el.getAttribute("identificable");
		if (identificable.equalsIgnoreCase("true")
				|| identificable.equalsIgnoreCase("false")) {
			table.setIdentificable(Boolean.parseBoolean(identificable));
		}
		String buffereable = el.getAttribute("buffer");
		if (buffereable.equalsIgnoreCase("true")
				|| buffereable.equalsIgnoreCase("false")) {
			table.setBuffereable(Boolean.parseBoolean(buffereable));
		}
		table.setAliasUsuario(el.getAttribute("aliasusuario"));
	}

	@Override
	protected List<T> readDocumentStructure() {
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("tabla");
		data = new ArrayList<T>();
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				// "buscable = " + buscable);
				Table table = (Table) getTable(el);
				getTableAttributes(table, el);
				data.add((T) table);
			}
		}
		return data;
	}

	private SubProjectData getSubProjectData(Element el) {
		SubProjectData spd = null;
		if (el.getElementsByTagName("subproyectos") != null
				&& el.getElementsByTagName("subproyectos").getLength() > 0) {
			NodeList nodeList = el.getElementsByTagName("subproyecto");
			List<SubProject> subProjects = null;
			if (nodeList != null && nodeList.getLength() > 0) {
				subProjects = new LinkedList<SubProject>();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);
					String name = getTextValue(element, "subproyecto-nombre");
					String field = getTextValue(element, "subproyecto-columna");
					String value = getTextValue(element,
							"subproyecto-valor-columna");
					SubProject subProject = new SubProject(name, field, value);
					subProjects.add(subProject);
				}
			}
			String varName = getTextValue(el, "subproyecto-var");
			spd = new SubProjectData(varName, subProjects);
		}
		return spd;
	}

}
