/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author INEGI
 */
public class FieldQueryDataCleaner {

	private static StopWords stopWords;

	public FieldQueryDataCleaner(StopWords stopWordss) {
		stopWords = stopWordss;
	}

	public String[] cambia(String texto) {
		String cambio = removeConectores(texto);
		String[] datos = cambio.split(",");
		for (int x = 0; x < datos.length; x++) {
			datos[x] = removeComas(datos[x]);
			datos[x] = removeParentesis(datos[x]);
			datos[x] = creaSoundesp(datos[x]);
		}
		return datos;
	}

	private String creaSoundesp(String texto) {
		if (!texto.equals("")) {
			String valores[] = texto.split("\\s");
			StringBuilder sb = new StringBuilder();
			sb.append("(select");
			for (String s : valores) {
				if (s.trim().length() > 0) {
					sb.append(" convierte('").append(s).append("') || '_' ||");
				}
			}
			// "texto: " + texto);
			if (sb.toString().length() > 0) {
				String res = sb.toString().substring(0,
						sb.toString().length() - 10);
				return res.concat(")");
			} else {
				return "";
			}
		} else
			return "";
	}

	private static String removeParentesis(String value) {
		Pattern regex = Pattern.compile("[()]", Pattern.DOTALL);
		Matcher m = regex.matcher(value);
		return m.replaceAll("");
	}

	public static String removeAcentos(String value) {
		value = Normalizer.normalize(value, Normalizer.Form.NFD);
		Pattern regex = Pattern
				.compile("[^\\p{InBasic Latin}]", Pattern.DOTALL);
		Matcher m = regex.matcher(value);
		return m.replaceAll("");
	}

	private static String removeComas(String value) {
		Pattern regex = Pattern.compile("[,.]", Pattern.DOTALL);
		Matcher m = regex.matcher(value);
		return m.replaceAll(" ");
	}

	private static String removeBlanksBetweenWords(String value) {
		Pattern regex = Pattern.compile("(?<!\\w)\\s+(?=\\w)", Pattern.DOTALL);
		Matcher m = regex.matcher(value);
		return m.replaceAll("");
	}

	private static String removeConectores(String value) {
		// value = removeParentesis(removeComas(value));
		Pattern regex = Pattern.compile(stopWords.getStringWords(),
				Pattern.DOTALL | Pattern.MULTILINE);
		Matcher m = regex.matcher(value.toLowerCase());
		return removeBlanksBetweenWords(m.replaceAll(""));
	}

	public static String normalize(String value) {
		String s = removeAcentos(value);
		s = s.replaceAll(" ", "_");
		return s;
	}

}
