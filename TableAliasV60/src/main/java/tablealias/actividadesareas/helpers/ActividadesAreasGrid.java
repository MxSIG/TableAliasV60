/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.actividadesareas.helpers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import tablealias.dinue.helpers.ActividadParser;
import tablealias.dinue.helpers.Actividades;
import tablealias.dinue.helpers.AreaGeoParser;
import tablealias.dinue.helpers.AreasGeo;
import tablealias.dinue.helpers.Estratos;
import tablealias.dinue.helpers.DinueOptionalParams;

/**
 *
 * @author INEGI
 */
public class ActividadesAreasGrid {
    private final AreasGeo areas;
    private final Actividades acts;
    protected int columns;
    protected int rows;
    //private Map<List<ActividadRowItem>,List<AreaColumnItem>> grid;
    private Map<ActividadRowItem,List<AreaColumnItem>> grid;    

    public ActividadesAreasGrid(AreasGeo areas, Actividades acts) {
        this.areas = areas;
        this.acts = acts;
        grid = new LinkedHashMap<ActividadRowItem, List<AreaColumnItem>>();
        createGrid();        
    }   

    private void createGrid() {
        List<AreaColumnItem> cols = new ArrayList<AreaColumnItem>();
        for(AreaGeoParser agp: areas){
            AreaColumnItem aci = agp.getColumnItem();
            cols.add(aci);
        }

        setColumns(cols.size());
        for(ActividadParser ap : acts){
            ActividadRowItem ari = ap.getRowItem();
            grid.put(ari, cols);
        }
        setRows(grid.size());
    }

    /**
     * @return the grid
     */
    public Map<ActividadRowItem, List<AreaColumnItem>> getGrid() {
        return grid;
    }

    /**
     * @return the columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }



}
