/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tablealias.dinue.helpers;

/**
 *
 * @author INEGI
 */
public class EstratosExcluder extends BaseExcluder{
    private final Estratos estratos;

    public EstratosExcluder( Estratos estratos) {
        setExclusionData(new String[]{"1","2","3","4","5","6","7",});
        this.estratos = estratos;
    }


    @Override
    public boolean isExcluded() {
        int cont = 0;
        if(estratos == null)
            return true;
        if(estratos.size() != exclusionData.size())
            return false;
        for(String s: estratos.getEstrats()){
            if(exclusionData.contains(s))
                cont++;
        }
        return cont == exclusionData.size();
    }

}
