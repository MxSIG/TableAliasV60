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
public class ExceptionSearchParser {
    private String valueToSearch;    
    private String[] datosCalle1;
    private String[] datosCalle2;
    private String token;
    private String calle1;//expression to the left side of --
    private String calle2;//expression to the right side of --

    public ExceptionSearchParser(String value2Search) {
        this.valueToSearch = value2Search.trim();
    }

    public void setExceptionToken(String token){
        this.token = token;
    }

    public boolean isBusquedaNormal(){
        isValidSearchCriteria();
        return getCalle2() == null || getCalle2().equals("");

    }

    private boolean isValidSearchCriteria(){
        boolean valid = false;
        //Pattern regex = Pattern.compile("((?:[\\w ]+?,?)+)"+ token +"((?:[\\w ]+?,?)+)", Pattern.DOTALL);
        Pattern regex = Pattern.compile("((?:[\\w ],?)+)"+token+"((?:[\\w ],?)+)", Pattern.DOTALL);
        
        //"after removeAcentos = " + valueToSearch);
        Matcher regexMatcher = regex.matcher(valueToSearch);
        valid = regexMatcher.matches();
        if(valid){
            calle1 = regexMatcher.group(1).trim();
            calle2 = regexMatcher.group(2).trim();
            setDatosCalle1(getCalle1().split(","));
            setDatosCalle2(getCalle2().split(","));
            /*if(getDatosCalle1().length == 1){//solo trae un valor del lado izquierdo del token
                valid = false;
                calle2 = null;//para que sea busqueda normal
            }*/
            if(getDatosCalle2().length == 1 && datosCalle1.length > 1){//solo trae un valor del lado derecho del token
                calle2 = getCalle2().concat("," + getDatosCalle1()[1]);
            }
            else if(getDatosCalle2().length > 1){
                calle2 = getDatosCalle2()[1] + "," + getDatosCalle2()[1];
            }

        }
        return valid;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }    

     /**
     * @return the expression to the left side of token
     */
    public String[] getDatosCalle1() {
        return datosCalle1;
    }

     /**
     * @return the expression to the right side of token
     */
    public String[] getDatosCalle2() {
        return datosCalle2;
    }

    /**
     * @param datosCalle1 the datosCalle1 to set
     */
    public void setDatosCalle1(String[] datosCalle1) {
        this.datosCalle1 = datosCalle1;
    }

    /**
     * @param datosCalle2 the datosCalle2 to set
     */
    public void setDatosCalle2(String[] datosCalle2) {
        this.datosCalle2 = datosCalle2;
    }

    /**
     * @return the calle1
     */
    public String getCalle1() {
        return calle1;
    }

    /**
     * @return the calle2
     */
    public String getCalle2() {
        return calle2;
    }

    



}
