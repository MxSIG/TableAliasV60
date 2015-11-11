/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author INEGI
 */
public abstract class BaseExcluder {

    protected List<String> exclusionData;
    protected int counter = 0;

    public BaseExcluder() {        
    }

    public abstract boolean isExcluded();

    /**
     * @param exclusionData the exclusionData to set
     */
    public void setExclusionData(String[] exclusionData) {
        this.exclusionData = Arrays.<String>asList(exclusionData);
    }

}
