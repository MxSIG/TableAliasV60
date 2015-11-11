/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

/**
 *
 * @author INEGI
 */
public class HTTPEncodingFormat {
    private static boolean isLinux = false;
    static public final String getJsonFormatWEncoding(){
        return "application/json;charset="+getFormat();
        //return "application/json;charset=ISO-8859-1"
    }
    static public final String getHTMLFormatWEncoding(){
        return "text/html;charset="+getFormat();
        //return "application/json;charset=ISO-8859-1"
    }
    static private final String getFormat(){
        if (isLinux){
            return "UTF-8";
        }else{
            return "ISO-8859-1";
        }
    }
}
