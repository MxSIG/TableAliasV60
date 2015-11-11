/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmldata;

import mx.inegi.dtweb.connection.Connectable;
//import tablealias.common.Connectable;

/**
 *
 * @author INEGI
 */
public class ServerOLD implements Connectable{

    private String alias;
    private String ip;
    private String puerto;
    private String userName;
    private String password;
    private String dbName;
    private String url;
    private String driverClass;
    protected String validationQuery;

    public ServerOLD(String alias, String ip, String puerto, String userName,
            String password, String url, String driverClass, String validationQuery) {
        this.alias = alias;
        this.ip = ip;
        this.puerto = puerto;
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.driverClass = driverClass;
        this.validationQuery = validationQuery;
    }

    

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public String getServer() {
        return ip;
    }

    public String getPort() {
        return puerto;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Server){
            Server srv = (Server)obj;
            return srv.getServer().equals(this.getServer()) && srv.getPort().equals(this.getPort());            
        }
        return false;
    }

    public String getURL() {
        return String.format(url, ip, puerto, dbName);
        /*StringBuilder sb = new StringBuilder();
        sb.append("jdbc:postgresql://")
                .append(getServer())
                .append(":")
                .append(getPort())
                .append("/")
                .append(getDbName());
        return sb.toString();*/
    }

    public String getDriverClassName() {
        return driverClass;
    }

    /**
     * @return the validationQuery
     */
    public String getValidationQuery() {
        return validationQuery;
    }


}
