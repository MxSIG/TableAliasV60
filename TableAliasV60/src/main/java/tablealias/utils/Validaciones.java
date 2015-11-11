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
public class Validaciones {

	final static Pattern floatNumberRegex = Pattern.compile("\\d+\\.\\d+");
	final static Pattern numberRegex = Pattern.compile("\\d+");

	public static boolean isNumber(String value) {
		Matcher regexMatcher = numberRegex.matcher(value);
		return regexMatcher.matches();
	}

	public static boolean isFloatNumber(String value) {
		if (value == null)
			return false;
		Matcher regexMatcher = floatNumberRegex.matcher(value);
		return regexMatcher.matches();
	}

}
