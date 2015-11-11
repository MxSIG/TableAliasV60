/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmlaccess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import tablealias.xmldata.Document;

/**
 *
 * @author INEGI
 */
public class AliasDataConfigReader<T> extends AbstractXmlReader<T> {

    private List<T> data;
    private final String path;

    public AliasDataConfigReader(File xmlFile, String path) {
        super(xmlFile);
        this.path = path + "xml" + File.separatorChar;
    }

    private T getDocument(Element el) {
        String fileName = getTextValue(el, "nombre");
        Document doc = new Document(fileName);
        File f = new File(path + fileName);
        if(f.exists())
            doc.setFile(f);
        else
            System.out.println("no se encontro archivo " + fileName + " en " + path);
        return (T) doc;
    }

    @Override
    protected List<T> readDocumentStructure() {
        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getElementsByTagName("document");
        data = new ArrayList<T>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                //"buscable = " + buscable);
                Document doc = (Document) getDocument(el);
                data.add((T) doc);
            }
        }
        return data;
    }

}
