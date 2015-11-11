package tablealias.dto;

import java.sql.Date;

/**
 *
 * @author INEGI
 */
public class BufferCE {
    
    private Long id;
    private Date date;
    private String geometry;
    private String cve_ent;

    public BufferCE(Long id, Date date, String geometry, String cve_ent) {
        this.id = id;
        this.date = date;
        this.geometry = geometry;//"setsrid( geometryfromtext('" + geometry + "'),4326 )";
        this.cve_ent= cve_ent;
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

    /**
     * @return the cve_edo
     */
    public String getCve_ent() {
        return cve_ent;
    }
    
}
