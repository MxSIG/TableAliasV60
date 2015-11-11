package tablealias.xmldata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author INEGI
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class TableFields implements Iterable<Field>, Cloneable {

	@JsonProperty
	private List<Field> fields;

	public TableFields() {
		fields = new ArrayList<Field>();
	}

	public void clearFields() {
		fields.clear();
	}

	public Field get(int idx) {
		return fields.get(idx);
	}

	public int size() {
		return fields.size();
	}

	public boolean addField(Field f) {
		return fields.add(f);
	}

	public boolean addField(String name, String aliasName) {
		return fields.add(new Field(name, aliasName));
	}

	public boolean removeField(Field f) {
		return fields.remove(f);
	}

	public boolean removeField(String name, String aliasName) {
		boolean exito = false;
		Field f = getField(name);
		if (f != null) {
			exito = removeField(f);
		}
		return exito;
	}

	private Field getField(String name) {
		Field field = null;
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(name)) {
				field = f;
				break;
			}
		}
		return field;
	}

	public Field getFieldByName(String name) {
		return getField(name);
	}

	public Field getFieldByAlias(String name) {
		Field field = null;
		for (Field f : fields) {
			if (f.getAliasName().equalsIgnoreCase(name)) {
				field = f;
				break;
			}
		}
		return field;
	}

	public List<Field> getTypeFields() {
		List<Field> typeFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.isConsultaTipo()) {
				typeFields.add(field);
			}
		}
		return typeFields;
	}

	public Iterator<Field> iterator() {
		return fields.iterator();
	}

	@Override
	protected Object clone() {
		TableFields obj = null;
		try {
			obj = (TableFields) super.clone();
			List<Field> newFields = new ArrayList<Field>(fields.size());

			for (Field f : fields) {
				Field fi = (Field) f.clone();
				newFields.add(fi);
			}
			// obj.clearFields();
			/*
			 * for(Field f: newFields) obj.addField(f);
			 */
			obj.fields = newFields;

		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public List<Field> getFields() {
		return fields;
	}

}
