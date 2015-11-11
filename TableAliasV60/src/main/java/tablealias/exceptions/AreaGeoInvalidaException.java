/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.exceptions;

import tablealias.dinue.helpers.AreaGeoParser;


/**
 *
 * @author INEGI
 */
public class AreaGeoInvalidaException extends Exception{

    public AreaGeoInvalidaException(AreaGeoParser agp) {
        super(String.format("areageo invalida:%n%s", agp.toString()));
    }



}
