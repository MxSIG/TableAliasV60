package tablealias.mapper;

/**
 * Class meant to be used by ConnectionLibBasedDataSource.
 * 
 * @author INEGI
 * 
 */
public class TableAliasContextHolder {

	public static class TableAliasInfo {
		String name;
		boolean writableConnection;

		public TableAliasInfo(String name) {
			super();
			this.name = name;
			this.writableConnection = true;
		}

		public void setWritableConnection(boolean writableConnection) {
			this.writableConnection = writableConnection;
		}

		public String getName() {
			return name;
		}

		public boolean isWritableConnection() {
			return writableConnection;
		}
	}

	private static ThreadLocal<TableAliasInfo> name = new ThreadLocal<TableAliasInfo>();

	public static TableAliasInfo getTableAliasInfo() {
		return name.get();
	}

	public static void setTableAlias(TableAliasInfo tableAliasInfo) {
		name.set(tableAliasInfo);
	}

	public static void resetInfo() {
		name.remove();
	}

}
