package tablealias.utils;


/**
 * 
 * @author INEGI
 */
public class ConfigOptions {

    private String serversFileName;
    private String aliasFileName;
    private String stopWordsFileName;

    // InitialContext context;

    // public ConfigOptions() {
    // try {
    // context = new InitialContext();
    // } catch (NamingException ex) {
    // ex.printStackTrace();
    // }
    // }

    public String getServersFileName() {
	return serversFileName;
    }

    public String getAliasFileName() {
	return aliasFileName;
    }

    public String getStopWordsFileName() {
	return stopWordsFileName;
    }

    // @Value("#{webConfig['ServersDataFileName']}")
    //@Value("${ServersDataFileName}")
    public void setServersFileName(String serversFileName) {
	this.serversFileName = serversFileName;
    }

    // @Value("#{webConfig['AliasDataFileName']}")
    //@Value("${AliasDataFileName}")
    public void setAliasFileName(String aliasFileName) {
	this.aliasFileName = aliasFileName;
    }

    // @Value("#{webConfig['StopWordsFileName']}")
    //@Value("${StopWordsFileName}")
    public void setStopWordsFileName(String stopWordsFileName) {
	this.stopWordsFileName = stopWordsFileName;
    }
    
}
