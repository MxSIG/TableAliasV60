/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.fieldsearchs.iterators;

import java.util.Iterator;
import java.util.List;
import tablealias.xmldata.SearchField;

/**
 *
 * @author INEGI
 */
public class FirstAndRestIterator implements Iterator<SearchField> {

    private List<SearchField> searchFields;
    private int initialCounter;

    public FirstAndRestIterator(List<SearchField> searchFields) {
        this.searchFields = searchFields;
        initialCounter = 0;
    }

    public boolean hasNext() {
        initialCounter = initialCounter >= searchFields.size() ? 1 : initialCounter;
        return initialCounter < searchFields.size();
    }

    public SearchField next() {        
        return searchFields.get(initialCounter++);
    }

    public void remove() {
        searchFields.iterator().remove();
    }
}
