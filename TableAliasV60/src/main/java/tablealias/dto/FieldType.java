package tablealias.dto;

/**
 *
 * @author INEGI
 */
public class FieldType {
    
    private String type;

    public FieldType() {
    }

    public FieldType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
    
}
