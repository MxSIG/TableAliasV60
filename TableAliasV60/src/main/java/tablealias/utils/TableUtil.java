package tablealias.utils;

import java.util.ArrayList;
import java.util.List;

import tablealias.xmldata.Field;
import tablealias.xmldata.Resolucion;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

public class TableUtil {

	public static Table[] filtraCamposIdentificables(Table[] tablas) {
		if (tablas != null && tablas.length > 0) {
			Table[] tables = tablas.clone();
			List<Field> removableFields = new ArrayList<Field>();
			for (Table t : tables) {
				TableFields fields = t.getFields();
				for (Field f : fields) {
					if (!f.isIdentificable())
						removableFields.add(f);

				}
				for (Field f : removableFields)
					fields.removeField(f);
			}
			return tables;
		}
		return null;
	}

	public static boolean tablaEstaEntreResolucion(String tabla,
			String resolucion, TablasServidor ts) {
		if (!Validaciones.isFloatNumber(resolucion))
			return false;
		if (!ts.tableExists(tabla))
			return false;
		Table table = ts.getFoundTable();
		Resolucion resolucionTabla = table.getResolucion();
		return resolucionTabla.estaEnRango(Double.parseDouble(resolucion));
	}
}
