/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.xmlaccess;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author INEGI
 */
public abstract class AbstractXmlReader<T> {

    protected File xmlFile;
    protected Document dom;    

    public AbstractXmlReader(File xmlFile) {
        this.xmlFile = xmlFile;
        try {
            parseXmlFile();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        //readDocumentStructure();
    }

    private void parseXmlFile() throws ParserConfigurationException {        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {            
            DocumentBuilder db = dbf.newDocumentBuilder();            
            dom = db.parse(xmlFile);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected String getAttributeValue(Element ele, String tagName, String attribute) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getAttribute(attribute);
        }
        return textVal;
    }

    protected String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    protected int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele, tagName));
    }
    

    protected abstract List<T> readDocumentStructure();

    public List<T> getData(){
        return readDocumentStructure();
    }

}
