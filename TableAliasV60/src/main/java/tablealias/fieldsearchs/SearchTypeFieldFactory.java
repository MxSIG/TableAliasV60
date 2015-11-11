/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.fieldsearchs;

import tablealias.fieldsearchs.SearchType;
import tablealias.xmldata.SearchField;

/**
 *
 * @author INEGI
 */
public class SearchTypeFieldFactory {

    public static SearchType getSearchType(String type, SearchField sf) {
        SearchType st = null;
        if (type.equalsIgnoreCase("tsearch")) {
            st = new TSearchType(sf);
        }
        else if (type.equalsIgnoreCase("tsearchsoundesp")) {
            st = new TSearchSoundEspType(sf);
        }
        else if (type.equalsIgnoreCase("numerico")) {
            st = new NumericType(sf);
        }
        return st;
    }
}
