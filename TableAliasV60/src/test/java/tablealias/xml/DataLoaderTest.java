package tablealias.xml;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.inegi.dtweb.connection.ConnectionManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import tablealias.utils.DataLoader;
import tablealias.utils.TablasServidor;
import tablealias.xmldata.Field;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

import com.dbexplorer.management.SchemaData;

public class DataLoaderTest {

	private static XmlWebApplicationContext context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		String[] contexts = new String[] {
				"classpath:/META-INF/spring/applicationContext.xml",
				"file:src/main/webapp/WEB-INF/spring/web-context.xml" };
		context = new XmlWebApplicationContext();
		context.setConfigLocations(contexts);
		context.setServletContext(new MockServletContext("/webapp",
				new FileSystemResourceLoader()));
		context.refresh();

	}

	@Test
	public void loadData() throws Exception {
		DataLoader loader = context.getBean(DataLoader.class);
		TablasServidor ts = loader.getTablasServidor();
		assertNotNull(ts);
		String servidorAlias = "servidorsisec2014";
		List<Table> tables = ts.getTablesByServerAlias("servidorsisec2014");
		Server server = ts.getServer(servidorAlias);
		Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
		if (tables != null) {
			for (Table table : tables) {
				result.put(table.getName(), new ArrayList<String>());
				Connection connection = null;
				try {
					connection = ConnectionManager.getConnection(server);
					DatabaseMetaData md = connection.getMetaData();
					SchemaData sd = new SchemaData(table.getSchema(), md);
					com.dbexplorer.management.Table table2 = sd.getTable(table
							.getName());
					if (table2 != null) {
						for (Field xmlField : table.getFields()) {
							if (!table2.containsField(xmlField.getName())) {
								List<String> tableErrors = result.get(table
										.getName());
								String msg = String.format(
										"xmlField %s not found in table %s%n",
										xmlField.getName(), table.getSchema()
												+ "." + table2.getTableName());
								tableErrors.add(msg);
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
			System.out.println("**************************");
			for (Map.Entry<String, List<String>> e : result.entrySet()) {
				String table = e.getKey();
				List<String> errors = e.getValue();
				if (errors.size() > 0) {
					System.out.println("Table " + table);
					for (String s : errors)
						System.out.printf("\t%s%n", s);
				}
				// else
				// System.out.printf("\t%s%n", "is ok.");

			}
		} else
			System.out.printf("Servidor alias %s not found.%n", servidorAlias);
	}
}
