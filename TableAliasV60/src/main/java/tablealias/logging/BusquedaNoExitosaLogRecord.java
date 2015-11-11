/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.logging;

/**
 *
 * @author INEGI
 */
public class BusquedaNoExitosaLogRecord extends BusquedaLogRecord{

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ip).append(":")
                .append("\"").append(coordenadas).append("\"").append(":")
                .append(idSesion).append(":")
                .append("\"").append(criterio).append("\"").append(":")
                .append(tabla).append(":")
                .append(gid).append(":")
                .append(servlet).append(":");
        return sb.toString();
    }

    

}
