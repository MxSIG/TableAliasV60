package tablealias.sqlcreators;

import tablealias.utils.ExceptionSearchParser;
import tablealias.utils.Polygon;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Field;
import tablealias.xmldata.FieldFunction;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class BusquedaExceptionSearchSqlCreator implements SqlCreator {

	private Table table;
	private int recordsPerView;
	private int pageNumber;
	private String[] valueToSearch;
	private final ExceptionSearchParser esp;
	private final String where;
	private String whereSubProject;
	private String whereTipo;

	public BusquedaExceptionSearchSqlCreator(Table table,
			ExceptionSearchParser esp, String where, String subProject,
			String whereTipo) {
		this.table = table;
		recordsPerView = 25;// default value
		pageNumber = 1;
		this.esp = esp;
		this.where = where;
		this.whereSubProject = new SubProjectWhereGenerator()
				.getWhereSubProject(subProject, table);
		this.whereTipo = whereTipo;
	}

	private int calculateOffset() {
		return (pageNumber - 1) * recordsPerView;
	}

	private String getFieldSqlWithFunctions(Field f) {
		StringBuilder sb = new StringBuilder();
		for (FieldFunction ff : f.getFunctions()) {
			sb.append(ff.getName()).append("(");
		}
		sb.append(f.getName());
		sb.append(String.format("%0" + f.getFunctions().size() + "d", 0)
				.replace("0", ")"));
		return sb.toString();
	}

	private String getOrderByFields() {
		StringBuilder sb = new StringBuilder();
		sb.append("order by ");
		for (String s : table.getOrderByFields()) {
			sb.append(s).append(",");
		}
		return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}

	private String getFieldsSql(Table table) {
		StringBuilder sb = new StringBuilder();
		for (Field f : table.getFields()) {
			if (!f.hasFunctions())
				sb.append(f.getName()).append(" as ").append(f.getName())
						.append(",");
			else {
				if (f.isBusquedaDisplay())
					sb.append(getFieldSqlWithFunctions(f)).append(" as ")
							.append(f.getName()).append(",");
			}
		}
		return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}

	private String[] setParams(String valueToSearch) {
		String[] datos = valueToSearch.split(",");
		String[] params = null;
		if (datos.length > 1) {
			params = new String[2];
		} else {
			params = new String[1];
		}
		params[0] = datos[0];
		if (datos.length > 1) {
			params[1] = datos[1];
			for (int i = 2; i < datos.length; i++) {
				params[1] = params[1] + " " + datos[i];
			}
		}
		return params;
	}

	public String getSql() throws Exception {
		if (table.getSearchFields() == null)
			throw new Exception("tabla " + table.getName()
					+ " has null searchField");
		StringBuilder sb = new StringBuilder();
		// esp.setDatosCalle1(setParams(esp.getCalle1()));
		// esp.setDatosCalle2(setParams(esp.getCalle2()));
		sb.append(
				"select a.nomvial as calle, a.entmunloc  as entmun, b.nomvial as calle2, b.entmunloc as entmun2,")
				.append("astext(envelope(st_buffer(st_intersection(a.")
				.append(table.getGeomName()).append(",b.")
				.append(table.getGeomName())
				.append("),'0.0002'))) as the_geom,")
				.append("astext(st_pointonsurface(st_intersection(a.")
				.append(table.getGeomName()).append(",b.")
				.append(table.getGeomName()).append("))) as punto from")
				.append("(select gid, nomvial, entmunloc, ")
				.append(table.getGeomName()).append(" from ")
				.append(table.getSchema()).append(".").append(table.getName())
				.append(" where ")
				.append(table.getSqlWhereWithConvierte(esp.getDatosCalle1()))
				.append(") a,").append("(select gid, nomvial, entmunloc, ")
				.append(table.getGeomName()).append(" from ")
				.append(table.getSchema()).append(".").append(table.getName())
				.append(" where ")
				.append(table.getSqlWhereWithConvierte(esp.getDatosCalle2()))
				.append(") b ").append("where a.").append(table.getGeomName())
				.append(" && b.").append(table.getGeomName())
				.append(" and st_intersects(a.").append(table.getGeomName())
				.append(", b.").append(table.getGeomName()).append(")");
		/*
		 * .append(table.getSchema()) .append(".") .append(table.getName())
		 * .append(" where ") .append(table.getSqlWhere(valueToSearch));
		 * if(table.hasOrderByFields())
		 * sb.append(" ").append(getOrderByFields()).append(" ");
		 */
		/*
		 * sb.append(" offset "); sb.append(calculateOffset());
		 * sb.append(" limit ").append(recordsPerView);
		 */
		// Aqui falta usar el nuevo where!
		return sb.toString();
	}

	public Table getTable() {
		return table;
	}

	public Polygon getPolygon() {
		return null;
	}

	public void setRecordsPerView(int records) {
		this.recordsPerView = records;
	}

	public void setPageToView(Integer pageNumber) {
		this.pageNumber = pageNumber == null ? 1 : pageNumber;
	}

	public String getNumberOfRecordsSql() {
		StringBuilder sb = new StringBuilder();
		// esp.setDatosCalle1(setParams(esp.getCalle1()));
		// esp.setDatosCalle2(setParams(esp.getCalle2()));
		sb.append("select count(*) from")
				.append("(select gid, nomvial, entmunloc, ")
				.append(table.getGeomName()).append(" from ")
				.append(table.getSchema()).append(".").append(table.getName())
				.append(" where ")
				.append(table.getSqlWhereWithConvierte(esp.getDatosCalle1()))
				.append(") a,").append("(select gid, nomvial, entmunloc, ")
				.append(table.getGeomName()).append(" from ")
				.append(table.getSchema()).append(".").append(table.getName())
				.append(" where ")
				.append(table.getSqlWhereWithConvierte(esp.getDatosCalle2()))
				.append(") b ").append("where a.").append(table.getGeomName())
				.append(" && b.").append(table.getGeomName())
				.append(" and st_intersects(a.").append(table.getGeomName())
				.append(", b.").append(table.getGeomName()).append(")");
		return sb.toString();
	}

	public String getTypesOfRecordsSQL() throws Exception {
		return "";// TODO later, not needed now
	}

}
