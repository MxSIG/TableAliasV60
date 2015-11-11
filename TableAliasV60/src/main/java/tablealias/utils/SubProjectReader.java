package tablealias.utils;

import javax.servlet.http.HttpServletRequest;
import tablealias.xmldata.Table;

/**
 *
 * @author INEGI
 */
public class SubProjectReader {

    public static String getSubProjectString( Table table, HttpServletRequest request ){
        String subProjectString = null;
        if( table.hasSubProjectData() ){
            subProjectString = request.getParameter( table.getSubProjectData().getVarName() );
        }
        return subProjectString;
    }
    
}
