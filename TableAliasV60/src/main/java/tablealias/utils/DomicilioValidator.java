/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author INEGI
 */
public class DomicilioValidator {
    String value2Search;
    Pattern regex = Pattern.compile("((\\b[0-9]+))");
    
    public boolean isDomicilioKind(String dom2Search){
        String [] tokens = (dom2Search.split(","))[0].split(" ");
        boolean hasNumber = false;
        boolean salida = false;
        if (tokens.length>1){
            for (String t : tokens){
                Matcher regexMatcher = regex.matcher(t);
                if (regexMatcher.find()){
                    hasNumber = true;
                }
            }
            if (hasNumber) salida=true;
        }
        return salida;
    }
    
}
