/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.List;
import mx.dtweb.geo.dao.DenueCoordsDao;
import mx.dtweb.geo.dto.DenueCoords;
import mx.dtweb.geo.exceptions.DenueCoordsDaoException;
import mx.dtweb.geo.factory.DenueCoordsDaoFactory;
import tablealias.xmldata.Field;
import tablealias.xmldata.Table;
import tablealias.xmldata.TableFields;

/**
 *
 * @author INEGI
 */
public class TableCoords {

    Object[] params;
    StringBuilder nic;
    StringBuilder nop;

    public void putCoords(List<Table> datos) throws DenueCoordsDaoException {
        getWhere(datos);
        DenueCoordsDao dao = DenueCoordsDaoFactory.create();
        DenueCoords[] cords = dao.findByDynamicWhere(nic.toString(), params);        
        for (DenueCoords d : cords) {
            for (Table t : datos) {
                TableFields tf = t.getFields();
                Field nicField = tf.getFieldByName("a.nic");
                Field nopField = tf.getFieldByName("a.nop");
                if (nicField.getValue().equals(d.getNic()) && nopField.getValue().equals(d.getNop())) {
                    Field ubicacionField = tf.getFieldByAlias("Ubicacion");
                    Field coordField = tf.getFieldByAlias("_coordenada");
                    ubicacionField.setValue(d.getUbicacion());
                    coordField.setValue(d.get_coordenadas());                    
                    break;
                }
            }
        }
        //"Encontre de " + datos.size());
    }

    private void getWhere(List<Table> datos) {
        nic = new StringBuilder();
        nop = new StringBuilder();
        nic.append(" nic in (");
        nop.append("nop in (");
        params = new String[datos.size() * 2];
        int i = 0;
        for (Table t : datos) {
            TableFields tf = t.getFields();
            Field nicField = tf.getFieldByName("a.nic");
            Field nopField = tf.getFieldByName("a.nop");
            nic.append("?, ");
            params[i] = nicField.getValue();
            nop.append("?, ");
            params[(i + datos.size())] = nopField.getValue();
            i++;
        }
        nic.delete(nic.length() - 2, nic.length());
        nop.delete(nop.length() - 2, nop.length());
        nic.append(") and ").append(nop).append(")");
    }
}
