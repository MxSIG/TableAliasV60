package tablealias.xmldata.dto;

/**
 *
 * @author INEGI
 */
public class SubProject {
    
    private String name;
    private String field;
    private String value;

    public SubProject(String name, String field, String value) {
        this.name = name;
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals( Object object ) {
        if( this == object ) return true;
        if( object == null ) return false;
        if( !( object instanceof SubProject ) ) return false;
        SubProject sp = ( SubProject ) object;
        return this.name.equals( sp.getName() );
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.name.hashCode();
        return result;
    }
    
}
