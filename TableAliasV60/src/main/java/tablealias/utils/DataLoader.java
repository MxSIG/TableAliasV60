package tablealias.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.core.io.Resource;

import tablealias.xmlaccess.AbstractXmlReader;
import tablealias.xmlaccess.AliasDataConfigReader;
import tablealias.xmlaccess.GenericXmlReader;
import tablealias.xmlaccess.ServersReader;
import tablealias.xmldata.Document;
import tablealias.xmldata.Server;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class DataLoader {

    private List<Table> tables;
    private List<Server> servers;
    // private ConfigOptions options;
    // private String servletPath;
    private Set<String> proyectos;
    private TablasServidor tablasServidor;
    private Resource aliasFileName;
    private Resource serversFileName;
    

    public DataLoader() {
        tablasServidor = new TablasServidor();
        proyectos = new TreeSet<String>();
        tables = new ArrayList<Table>();        
    }

    public void loadData() {
        readTables();
        readServers();
        for (Server s : servers) {
            if (!tablasServidor.tableServerPool.containsKey(s)) {
                tablasServidor.tableServerPool.put(s, new ArrayList<Table>());
            }
            for (Table t : tables) {
                for (String proy : t.getProyectos()) {
                    proyectos.add(proy);
                }
                if (t.getServer().equalsIgnoreCase(s.getAlias())) {
                    s.setDbName(t.getDatabaseName());
                    List<Table> tablas = tablasServidor.tableServerPool.get(s);
                    tablas.add(t);
                }
            }
        }
        StringBuilder sb = new StringBuilder( "Proyecto(s): " );
        for( String proy : proyectos ) sb.append( proy ).append( ", " );
        System.out.println( sb.toString() );
        tablasServidor.setProyectos(proyectos);
    }

	private void readTables() {
        File xml = null;
	try {
	    xml = aliasFileName.getFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	String path = xml.getParent() + File.separatorChar;
        AbstractXmlReader<Document> filesReader = new AliasDataConfigReader<Document>(xml, path);
        List<Document> files = filesReader.getData();
        for (Document f : files) {
            List<Table> tmp = new ArrayList<Table>();
            AbstractXmlReader<Table> fileData = new GenericXmlReader<Table>(f.getFile());
            tmp = fileData.getData();
            tables.addAll(tmp);
        }
    }

    private void readServers() {
        File xml = null;
	try {
	    xml = serversFileName.getFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}
        AbstractXmlReader<Server> fileData = new ServersReader<Server>(xml);
        servers = fileData.getData();
    }
    
    /***
     * Reloads data from xml files for tables and servers respectively
     */
    public void reloadData() {
        loadData();
    }

    /**
     * @return the tablasServidor
     */
    public TablasServidor getTablasServidor() {
        return tablasServidor;
    }

    /**
     * @return the proyectos
     */
    public Set<String> getProyectos() {
        return proyectos;
    }

    // public void setOptions(ConfigOptions options) {
    // this.options = options;
    // }

    public void setAliasFile(Resource aliasFileName) {
        this.aliasFileName = aliasFileName;
    }

    public void setServersFile(Resource serversFileName) {
        this.serversFileName = serversFileName;
    }
    
}
