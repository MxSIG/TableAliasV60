/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author INEGI
 */
public class RequestHelper {

    private HttpServletRequest request;

    public RequestHelper(HttpServletRequest request) {
        this.request = request;
    }

    public boolean supportsGzip() {
        String encoding = request.getHeader("Accept-Encoding");
        boolean supportsGzip = false;
        if (encoding != null) {
            if (encoding.toLowerCase().indexOf("gzip") > -1) {
                supportsGzip = true;
            }
        }
        return supportsGzip;
    }
}
