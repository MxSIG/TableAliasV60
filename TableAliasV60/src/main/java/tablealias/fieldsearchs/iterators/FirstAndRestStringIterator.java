/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.fieldsearchs.iterators;

import java.util.Iterator;

/**
 *
 * @author INEGI
 */
public class FirstAndRestStringIterator implements Iterator<String>{

    private String[] searchFields;
    private int initialCounter;

    public FirstAndRestStringIterator(String[] searchFields) {
        this.searchFields = searchFields;
        initialCounter = 0;
    }

    public boolean hasNext() {
        initialCounter = initialCounter >= searchFields.length ? 1 : initialCounter;
        return initialCounter < searchFields.length;
    }

    public String next() {
        return searchFields[initialCounter++];
    }

    public void remove() {
        //searchFields.iterator().remove();
    }
    
}
