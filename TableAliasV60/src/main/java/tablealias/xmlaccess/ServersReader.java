package tablealias.xmlaccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import tablealias.xmldata.Server;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author INEGI
 */
public class ServersReader<T> extends AbstractXmlReader<T> {

    private List<T> data;

    public ServersReader(File xmlFile) {
        super(xmlFile);
    }

    private T getServer(Element el) {
        String alias = getTextValue(el, "alias");
        String ip = getTextValue(el, "ip");
        String puerto = getTextValue(el, "puerto");
        String usuario = getTextValue(el, "usuario");
        String password = getTextValue(el, "password");
        String url = getTextValue(el, "url");
        String driverClass = getTextValue(el, "driverclass");
        String validationQuery = getTextValue(el, "validationquery");
        Server srv = new Server(alias, ip, puerto, usuario, password, url, driverClass, validationQuery);
        return (T)srv;
    }

    @Override
    protected List<T> readDocumentStructure() {
        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getElementsByTagName("servidor");
        data = new ArrayList<T>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                data.add(getServer(el));
            }
        }
        return data;
    }

}
