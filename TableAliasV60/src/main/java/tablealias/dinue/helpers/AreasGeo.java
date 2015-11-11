/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tablealias.exceptions.AreaGeoInvalidaException;

/**
 *
 * @author INEGI
 */
public class AreasGeo implements DinueSqlCreator, Iterable<AreaGeoParser> {

    private List<AreaGeoParser> areasGeo;

    public AreasGeo(String areas) {
        if (areas != null) {
            String[] areas1 = areas.split(",");
            areasGeo = new ArrayList<AreaGeoParser>();
            for (String a : areas1) {
                try {
                    AreaGeoParser agp = new AreaGeoParser(a);
                    areasGeo.add(agp);
                } catch (AreaGeoInvalidaException age) {
                    age.printStackTrace();
                }
            }
        }
    }

    public String getSql() {

        BaseExcluder be = new AreasGeoExcluder(this);
        if (be.isExcluded()) {
            return null;
        }
        return new AreaGeoWhereGenerator(this).getWhere();
    }

    public int size() {
        return areasGeo.size();
    }

    public Iterator<AreaGeoParser> iterator() {
        return areasGeo.iterator();
    }
}
