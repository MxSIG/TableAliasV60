/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.utils;

import java.util.ArrayList;
import java.util.List;
import tablealias.dto.Estados;
import tablealias.dto.LocUrbanas;

/**
 *
 * @author INEGI
 */
public class EdoCiudadData {

    private List<Estados> estados;
    private List<Estados> estadosFuera;
    private List<LocUrbanas> locUrbanas;
    private int edosTotalFields = 0;
    private int edosFueraTotalFields = 0;
    private int locUrbTotalFields = 0;
    private int totalFields = 0;

    public EdoCiudadData() {
        estados = new ArrayList<Estados>();
        locUrbanas = new ArrayList<LocUrbanas>();
        estadosFuera = new ArrayList<Estados>();
    }

    public boolean hasData(){
        return estados.size() > 0 || locUrbanas.size() > 0 || estadosFuera.size() > 0;
    }

    public void setTotalFields(){
        edosTotalFields = estados.size();
        locUrbTotalFields = locUrbanas.size();
        edosFueraTotalFields = estadosFuera.size();
        totalFields = edosTotalFields +  edosFueraTotalFields + locUrbTotalFields;
    }

    /**
     * @return the estados
     */
    public List<Estados> getEstados() {
        return estados;
    }

    /**
     * @return the locUrbanas
     */
    public List<LocUrbanas> getLocUrbanas() {
        return locUrbanas;
    }

    /**
     * @return the estadosFuera
     */
    public List<Estados> getEstadosFuera() {
        return estadosFuera;
    }

}
