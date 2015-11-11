package tablealias.sqlcreators;

import tablealias.utils.Polygon;
import tablealias.utils.SubProjectWhereGenerator;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;

/**
 * 
 * @author INEGI
 */
public class BufferSqlCreator implements SqlCreator {

	private Table table;
	private int recordsPerView;
	private int pageNumber;
	private Polygon polygon;
	private double size;
	private String[] gids;
	private String whereSubProject;

	public BufferSqlCreator(Table table, double size, String[] gids,
			String subProject) {
		this.table = table;
		recordsPerView = 25;// default value
		this.size = size;
		this.gids = gids;
		pageNumber = 1;
		this.whereSubProject = new SubProjectWhereGenerator()
				.getWhereSubProject(subProject, table);
	}

	private String getFieldsSql(Table table) {
		StringBuilder sb = new StringBuilder();
		for (Field f : table.getFields()) {
			if (f.isConsultaDisplay()) {
				sb.append(f.getName()).append(" as ").append(f.getSqlName())
						.append(",");
			}
		}
		return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}

	private String getOrderByFields() {
		StringBuilder sb = new StringBuilder();
		sb.append("order by ");
		for (String s : table.getOrderByFields()) {
			sb.append(s).append(",");
		}
		return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}

	private int calculateOffset() {
		return (pageNumber - 1) * recordsPerView;
	}

	public static boolean isIntNumber(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private String getGids() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		String miC = "'";
		if (isIntNumber(gids[0])) {
			miC = "";
		}
		for (String s : gids) {
			sb.append(miC).append(s).append(miC).append(",");
		}

		return sb.deleteCharAt(sb.lastIndexOf(",")).append(")").toString();
	}

	public String getSql() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select astext(simplify(buffer(").append(table.getGeomName())
				.append(",").append(size).append("),(area(")
				.append(table.getGeomName())
				.append(")/100000000000)))")
				// getFieldsSql(table))
				.append(" ").append("from ").append(table.getSchema())
				.append(".").append(table.getName()).append(" where ")
				.append("gid in ").append(getGids());
		sb.append(" AND ").append(whereSubProject);
		if (table.hasOrderByFields()) {
			sb.append(" ").append(getOrderByFields()).append(" ");
		}
		// .append(" offset ").append(calculateOffset()).append(" limit ").append(recordsPerView);
		return sb.toString();
	}

	public Table getTable() {
		return table;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public void setRecordsPerView(int records) {
		this.recordsPerView = records;
	}

	public void setPageToView(Integer pageNumber) {
		this.pageNumber = pageNumber == null ? 1 : pageNumber;
	}

	public String getNumberOfRecordsSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*)").append(" ").append("from ")
				.append(table.getSchema()).append(".").append(table.getName())
				.append(" where ").append("gid in ").append(getGids());
		sb.append(" AND ").append(whereSubProject);
		return sb.toString();
	}

	public String getTypesOfRecordsSQL() throws Exception {
		return "";// TODO later, not needed now
	}
}
