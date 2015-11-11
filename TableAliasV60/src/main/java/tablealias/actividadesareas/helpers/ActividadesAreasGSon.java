/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.actividadesareas.helpers;

import java.util.List;
import mx.inegi.dtweb.connection.DebugerLog;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasGSon {

    private int totalFields;
    private int rows;
    private int columns;
    private List<ActividadesAreasDto> data;

    public ActividadesAreasGSon(List<ActividadesAreasDto> data, ActividadesAreasGrid grid) {
        this.data = data;
        for (ActividadesAreasDto a : data){
            DebugerLog.log("Act: "  + a.getActividad().getParamValue());
            if (a.getActividad().getParamValue().equals("'30'")){
                a.getActividad().setGsonData("SECTOR ACTIVIDAD","31-33");
            }else if (a.getActividad().getParamValue().equals("'47'")){
                a.getActividad().setGsonData("SECTOR ACTIVIDAD","48-49");
            }
        }
        totalFields = data.size();
        rows = grid.getRows();
        columns = grid.getColumns();
    }




}
