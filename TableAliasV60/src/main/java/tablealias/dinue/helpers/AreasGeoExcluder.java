/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
public class AreasGeoExcluder extends BaseExcluder{
    private final AreasGeo areasGeo;

    public AreasGeoExcluder(AreasGeo areasGeo) {
        String[] excludeData = new String[]{"01","02","03","04","05","06","07","08","09","10"
                ,"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26"
                ,"27","28","29","30","31","32"};
        setExclusionData(excludeData);
        this.areasGeo = areasGeo;
    }


    @Override
    public boolean isExcluded() {
        int cont = 0;
        if(areasGeo == null)
            return true;
        if(areasGeo.size() != exclusionData.size())
            return false;
        for(AreaGeoParser ag: areasGeo){
            if(!ag.hasMunicipio() && exclusionData.contains(ag.getEstado()))
                cont++;
        }
        return cont == exclusionData.size();
    }

}
