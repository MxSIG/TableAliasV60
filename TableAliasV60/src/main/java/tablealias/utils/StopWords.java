/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author INEGI
 */
public class StopWords {

    private static List<String> palabras;
    private static String palabrota;
    private String filePath;

    public StopWords(String servletPath, ConfigOptions opts) {
        this.filePath = servletPath + opts.getStopWordsFileName();
    }


    public String getStringWords() {
        if (palabrota == null || palabrota.length() < 1) {
            readFile();
            StringBuilder sb = new StringBuilder();
            for (String word : palabras) {
                sb.append("\\b").append(word).append("\\b|");
            }
            palabrota = sb.toString().substring(0, sb.toString().length() - 1);
        }
        return palabrota;
    }

    private void readFile() {
        if (palabras == null) {
            palabras = new LinkedList<String>();
            BufferedReader br = null;
            try {                
                //br = new BufferedReader(new FileReader(file));
                File file = new File(filePath);
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "8859_1"));
                String linea = null;
                while ((linea = br.readLine()) != null) {
                    palabras.add(linea);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

