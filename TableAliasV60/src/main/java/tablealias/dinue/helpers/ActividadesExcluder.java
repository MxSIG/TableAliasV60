/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
public class ActividadesExcluder extends BaseExcluder{
    private final Actividades actividades;

    public ActividadesExcluder(Actividades actividades) {
        //String[] excludeData = new String[]{"11","21","22","23","31","32","33","43","46","48","49",
        //"51","52","53","54","55","56","61","62","71","72","81","93","99"};
        String[] excludeData = new String[]{"11","21","22","23","30","43","46","47",
        "51","52","53","54","55","56","61","62","71","72","81","93","99"};
        setExclusionData(excludeData);
        this.actividades = actividades;
    }


    @Override
    public boolean isExcluded() {
        int cont = 0;
        if(actividades == null)
            return true;
        if(actividades.size() != exclusionData.size())
            return false;
        for(ActividadParser ap: actividades){
            if(exclusionData.contains(ap.getSector()))
                cont++;
        }
        return cont == exclusionData.size();
    }
}
