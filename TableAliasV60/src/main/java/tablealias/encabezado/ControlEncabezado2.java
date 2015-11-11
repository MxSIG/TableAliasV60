/*
 * ControlEncabezado.java
 *
 * Created on 20 de julio de 2009, 11:50 AM
 */
package tablealias.encabezado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author INEGI
 */
public class ControlEncabezado2 {

    private boolean encabezadoSistema = true;

    /**
     * Creates a new instance of ControlEncabezado
     */
    public ControlEncabezado2(HttpServletRequest request) throws Exception {
        HttpSession sesion = request.getSession();
        //TODO guardar el encabezado en el context para tenerlo disponible en caso de error del servidor de encabezados
        //ServletContext context=sesion.getServletContext();
        ServidoresOficiales so = new ServidoresOficiales();
        String refiere = request.getHeader("REFERER");
        String servidorReferer = so.getServidorCorrespondiente(refiere);
        sesion.setAttribute("servidorReferer", servidorReferer);
        int c = so.getConsecutivoDefault();
        llamaEncabezado(sesion, servidorReferer, c);
    }

    private void llamaEncabezado(HttpSession sesion, String rutaBase, int c) throws Exception {

        //Si el encabezado ya existe en la sesion no se vuelve a llamar
        Encabezado encabezadoSession = (Encabezado) sesion.getAttribute("es");
        if (encabezadoSession == null) {
            //System.out.println("No existe el encabezado, se invocaro desde los servidores");
            String encabezado = generaEncabezado(rutaBase, c);
            String pie = generaPie(rutaBase, c);
            String cintillo = generaCintillo(rutaBase, c);
            String franjaIzquierda = generaFranjaIzquierda(rutaBase, c);
            String tituloSubtitulo = generaTituloSubtitulo(rutaBase, c);

            Encabezado e = new Encabezado();
            e.setEncabezadoSistema(encabezado);
            e.setPieSistema(pie);
            e.setCintillo(cintillo);
            e.setFranjaIzquierda(franjaIzquierda);
            e.setTituloSubtitulo(tituloSubtitulo);

            sesion.setAttribute("es", e);

        } else {
            //System.out.println("Ya existe el encabezado, se tomaro de la sesion");
        }

    }

    private String generaEncabezado(String rutaBase, int c) throws Exception {
        InetAddress thisIp = null;
        thisIp = InetAddress.getLocalHost();
        int theHit = 1;
        String uriRequest = null;
        if (encabezadoSistema) {
            uriRequest = rutaBase + "/lib/llama_encV2.01.asp?s=geo&c=" + c + "&hit=" + theHit + "&ipcte=" + thisIp.getHostAddress();
        } else {
            uriRequest = rutaBase + "/lib/llama_encV2.01.asp?s=geo&c=" + c + "&hit=" + theHit + "&ipcte=" + thisIp.getHostAddress();
        }
        String resultado = procesaURL(uriRequest, rutaBase);
        resultado = "<!-- ruta del encabezado: " + uriRequest + " -->\n" + resultado;
        return (resultado);
    }

    private String generaPie(String rutaBase, int c) throws Exception {
        String uriRequest = rutaBase + "/lib/llama_pieV2.01.asp?s=geo&c=" + c;
        return procesaURL(uriRequest, rutaBase);
    }

    private String generaCintillo(String rutaBase, int c) throws Exception {
        String uriRequest = rutaBase + "/lib/llama_cintillo.asp?s=geo&c=" + c;
        return procesaURL(uriRequest, rutaBase);
    }

    private String generaFranjaIzquierda(String rutaBase, int c) throws Exception {
        String uriRequest = rutaBase + "/lib/llama_franja_izq.asp?s=geo&c=" + c;
        return procesaURL(uriRequest, rutaBase);
    }

    private String generaTituloSubtitulo(String rutaBase, int c) throws Exception {
        String uriRequest = rutaBase + "/lib/llama_TitSub.asp?s=geo&c=" + c;
        return procesaURL(uriRequest, rutaBase);
    }

    private String procesaURL(String uri, String rutaBase) throws Exception {
        String webResponse = getWEB(uri);
        webResponse = corrigeRutas(webResponse, rutaBase);
        webResponse = webResponse.trim();
        return webResponse;
    }

    private String corrigeRutas(String webResponse, String rutaBase) {
        if (webResponse != null) {
            //webResponse = webResponse.replaceAll("/img/", rutaBase + "/img/");
            //webResponse = webResponse.replaceAll("src=/", "src=" + rutaBase + "/");
            webResponse = webResponse.replaceAll("href=/", "href=" + rutaBase + "/");
            webResponse = webResponse.replace("'/", "'" + rutaBase + "/");
            webResponse = webResponse.replace("\"/", "\"" + rutaBase + "/");
            webResponse = webResponse.trim();
        } else {
            return "";
        }
        return webResponse;
    }

    private String getWEB(String theURL) throws Exception {

        String ln;
        String theResponse;
        String tempString = null;

        try {
            URL imsURL = new URL(theURL);
            URLConnection connection = imsURL.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(10000);
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "ISO-8859-1"));
                while ((ln = in.readLine()) != null) {
                    if (tempString == null) {
                        tempString = ln;
                    } else {
                        tempString = tempString + "\n" + ln;
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            if (tempString != null) {
                theResponse = tempString.trim();
            } else {
                theResponse = "No hubo respuesta del encabezado en la ruta: " + theURL;
            }
        } catch (IOException ioe) {
            theResponse = "Error de encabezado: " + ioe.getMessage();
        }

        return (theResponse);
    }
}
