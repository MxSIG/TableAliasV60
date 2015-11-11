/*
 * ServidoresOficiales.java
 *
 * Created on 15 de octubre de 2007, 10:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package tablealias.encabezado;

/**
 *
 * @author INEGI
 */
public class ServidoresOficiales {

    private String[] servidores;

    /** Creates a new instance of ServidoresOficiales */
    public ServidoresOficiales() {
        servidores = new String[2];
        servidores[1] = "http://www.inegi.org.mx";
        servidores[0] = "http://intranetwww.inegi.org.mx";
    }

    public boolean isServidorOficial(String refiere) {
        boolean resultado = false;
        for (int i = 0; i < servidores.length; i++) {
            int tamanio = refiere.length() > servidores[i].length() ? servidores[i].length() : refiere.length();
            String servidor = refiere.substring(0, tamanio);
            if (servidores[i].equals(servidor.trim())) {
                resultado = true;
            }
        }
        return resultado;
    }

    public String getServidorCorrespondiente(String refiere) {
        String resultado = null;
        if (refiere != null) {
            for (int i = 0; i < servidores.length; i++) {
                int tamanio = refiere.length() > servidores[i].length() ? servidores[i].length() : refiere.length();
                String servidor = refiere.substring(0, tamanio);
                if (servidores[i].equals(servidor.trim())) {
                    resultado = servidor;
                }
            }
        }
        if (resultado == null) {
            resultado = getServidorDefault();
        }
        return resultado;
    }

    public String[] getServidores() {
        return servidores;
    }

    public void setServidores(String[] servidores) {
        this.servidores = servidores;
    }

    /**
     * Getter for property consecutivoDefault.
     * @return Value of property consecutivoDefault.
     */
    public int getConsecutivoDefault() {
        return 1887; //corresponde a la RGNA, cambiar para cada aplicacion
    }

    public String getServidorDefault() {
        return servidores[0];
    }
}
