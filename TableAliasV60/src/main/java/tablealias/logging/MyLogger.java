/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.logging;

import java.io.File;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 *
 * @author INEGI
 */
public class MyLogger {

    static Logger logger = null;
    public MyLogger(String webAppPath) {
        //BusquedaLogRecord lr = new BusquedaLogRecord();//to load this class first in order to avoid exceptions from appenders defined in configuration file
        //BusquedaLogRecord lr2 = new BusquedaNoExitosaLogRecord();
        //URL  url =Loader.getResource(webAppPath + "WEB-INF\\log-init-file.lfc");
        //"leyendo archivo en " + webAppPath + "WEB-INF\\log-init-file.lfc");
        //PropertyConfigurator.configure(webAppPath + "WEB-INF\\log-init-file.lfc");
        PropertyConfigurator.configure(webAppPath + "log-init-file.lfc");
        /*File f = new File(webAppPath + "log-init-file.lfc");
        "file exists : " + f.exists());*/
    }

    public void Log(BusquedaLogRecord lr){
        logger = Logger.getLogger(lr.getClass());
        logger.debug(lr);
        //"writing " + lr);
    }
}
