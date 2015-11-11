package tablealias.xmldata;

import tablealias.fieldsearchs.SearchTypeFieldFactory;
import tablealias.fieldsearchs.SearchType;

/**
 *
 * @author INEGI
 */
public class SearchField {

    private String name;
    private SearchType type;
    private String dicc;
    private String cfunction;

    public SearchField(String name, String type) {
        this( name, type, null, null );
    }

    public SearchField(String name, String type, String dicc, String cfunction ) {
        this.name = name;
        this.type = SearchTypeFieldFactory.getSearchType(type, this);
        if (dicc!=null && !dicc.isEmpty()){
            this.dicc = dicc;
        }else{
            this.dicc = "spanish";
        }
        if (cfunction!=null && !cfunction.isEmpty()){
            this.cfunction = cfunction;
        }else{
            this.cfunction = "convierte";
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public SearchType getType() {
        return type;
    }

    /**
     * @return the dicc
     */
    public String getDicc() {
        return dicc;
    }

    /**
     * @param dicc the dicc to set
     */
    public void setDicc(String dicc) {
        this.dicc = dicc;
    }

    public String getCfunction() {
        return cfunction;
    }

    public void setCfunction(String cfunction) {
        this.cfunction = cfunction;
    }
    
}
