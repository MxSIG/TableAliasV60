package tablealias.dto;

import java.sql.Date;

/**
 *
 * @author INEGI
 */
public class Buffer {
    
    private Long id;
    private Date date;
    private String geometry;

    public Buffer(Long id, Date date, String geometry) {
        this.id = id;
        this.date = date;
        this.geometry = geometry;//"setsrid( geometryfromtext('" + geometry + "'),4326 )";
    }

    public Date getDate() {
        return date;
    }

    public String getGeometry() {
        return geometry;
    }

    public Long getId() {
        return id;
    }
    
}
