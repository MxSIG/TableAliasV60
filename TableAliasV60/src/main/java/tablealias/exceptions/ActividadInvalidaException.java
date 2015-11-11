/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.exceptions;

import tablealias.dinue.helpers.ActividadParser;



/**
 *
 * @author INEGI
 */
public class ActividadInvalidaException extends Exception{

    public ActividadInvalidaException(ActividadParser ap) {
        super(String.format("actividad invalida:%n%s", ap.toString()));
    }

    public ActividadInvalidaException(String msg) {
        super(String.format("actividad invalida:%n%s", msg));
    }



}
