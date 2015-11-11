package tablealias.controlador;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import tablealias.logging.MyLogger;
import tablealias.utils.DataLoader;
import tablealias.utils.ExceptionTables;
import tablealias.utils.TablasServidor;

/**
 * 
 * @author INEGI
 */
public class WebAppListener /* implements ServletContextListener, */implements
		ServletContextAware {

	private ServletContext context = null;
	private DataLoader dataLoader;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
		String webAppPath = context.getRealPath("/WEB-INF")
				+ File.separatorChar;
		// "ruta = " + webAppPath);
		// DataLoader dataLoader = new DataLoader(webAppPath);
		dataLoader.loadData();
		TablasServidor ts = dataLoader.getTablasServidor();
		ts.openConnections();
		ExceptionTables exTables = new ExceptionTables(ts);
		MyLogger logger = new MyLogger(webAppPath);
		context.setAttribute("tablasServidor", ts);
		context.setAttribute("exceptionTables", exTables);
		context.setAttribute("logger", logger);
	}

	@Autowired
	public void setDataLoader(DataLoader dataLoader) {
		this.dataLoader = dataLoader;
	}

	// @PreDestroy
	// public void onDestroy() {
	// JOptionPane.showMessageDialog(null, "Destroying");
	// try {
	// java.sql.Driver mySqlDriver = DriverManager
	// .getDriver("jdbc:postgresql://10.1.33.136:5432/");
	// DriverManager.deregisterDriver(mySqlDriver);
	// } catch (SQLException ex) {
	// JOptionPane.showMessageDialog(null, "error when deregistering jdbc");
	// //logger.info("Could not deregister driver:".concat(ex.getMessage()));
	// }
	// }
}
