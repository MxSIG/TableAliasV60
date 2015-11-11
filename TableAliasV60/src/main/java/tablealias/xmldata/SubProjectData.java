package tablealias.xmldata;

import java.util.List;
import tablealias.xmldata.dto.SubProject;

/**
 *
 * @author INEGI
 */
public class SubProjectData {
    
    private String varName;
    private List< SubProject > subProjects;

    public SubProjectData(String varName, List<SubProject> subProjects) {
        this.varName = varName;
        this.subProjects = subProjects;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public String getVarName() {
        return varName;
    }
    
}
