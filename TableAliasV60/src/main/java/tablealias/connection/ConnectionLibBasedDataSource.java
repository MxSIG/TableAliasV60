package tablealias.connection;

//import org.springframework.jdbc.datasource.AbstractDataSource;

/**
 * DataSource that depends on connectionLib to provide a DataSource.
 * 
 * @author INEGI
 * 
 */
/*public class ConnectionLibBasedDataSource extends AbstractDataSource {

 @Autowired
 private ServletContext context;

 @Override
 public Connection getConnection() throws SQLException {
 return getConnectionFromAlias();
 }

 private Connection getConnectionFromAlias() throws SQLException {
 TableAliasInfo tableAlias = TableAliasContextHolder.getTableAliasInfo();
 Assert.notNull(tableAlias.getName(), "tableAlias cannot be null.");
 TablasServidor ts = (TablasServidor) context
 .getAttribute("tablasServidor");
 if (ts.tableExists(tableAlias.getName())) {
 Server foundServer = ts.getFoundServer();
 if (tableAlias.isWritableConnection())
 return ConnectionManager.getConnectionW(foundServer);
 else
 return ConnectionManager.getConnection(foundServer);
 } else
 throw new SQLException("table " + tableAlias + " not found.");
 }

 @Override
 public Connection getConnection(String username, String password)
 throws SQLException {
 return getConnectionFromAlias();
 }

 }*/
