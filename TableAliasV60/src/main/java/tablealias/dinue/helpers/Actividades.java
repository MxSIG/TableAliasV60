/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tablealias.exceptions.ActividadInvalidaException;

/**
 *
 * @author INEGI
 */
public class Actividades implements DinueSqlCreator, Iterable<ActividadParser> {

    private List<ActividadParser> activs;

    public Actividades(String actividades) {
        String[] actividades1 = actividades.split(",");
        activs = new ArrayList<ActividadParser>();
        for (String ac : actividades1) {
            try {
                /*if ("30".equals(ac.trim())) {
                    ActividadParser ap = new ActividadParser("31");
                    activs.add(ap);
                    ap = new ActividadParser("32");
                    activs.add(ap);
                    ap = new ActividadParser("33");
                    activs.add(ap);
                } else if ("47".equals(ac.trim()))  {
                    ActividadParser ap = new ActividadParser("48");
                    activs.add(ap);
                    ap = new ActividadParser("49");
                    activs.add(ap);
                }else{*/
                    ActividadParser ap = new ActividadParser(ac);
                    activs.add(ap);
                //}
            } catch (ActividadInvalidaException aie) {
                aie.printStackTrace();
            }
        }
    }

    public String getSql() {
        BaseExcluder be = new ActividadesExcluder(this);
        if (be.isExcluded()) {
            return null;
        }
        return new ActividadWhereGenerator(this).getWhere();
    }

    public Iterator<ActividadParser> iterator() {
        return activs.iterator();
    }

    public int size() {
        return activs.size();
    }
}
