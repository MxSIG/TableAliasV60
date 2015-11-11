package tablealias.utils;

import tablealias.xmldata.SubProjectData;
import tablealias.xmldata.Table;
import tablealias.xmldata.dto.SubProject;

/**
 *
 * @author INEGI
 */
public class SubProjectWhereGenerator {
    
    public String getWhereSubProject( String subProject, Table table ){
        String whereSubProject = "1 = 1";
        if( subProject != null && !subProject.isEmpty() ){
            SubProjectData spd = table.getSubProjectData();
            SubProject sp1 = new SubProject( subProject, "", "");
            SubProject sp2 = spd.getSubProjects().get( spd.getSubProjects().indexOf( sp1 ) );
            if( sp2 != null ){
                whereSubProject = sp2.getField() + " = " + sp2.getValue();
            }
        }
        return whereSubProject;
    }
}
