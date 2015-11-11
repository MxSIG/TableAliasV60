/**
 * 
 */
package tablealias.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author INEGI
 *
 */
public class GeometriaCvegeoDto {

	@NotEmpty
	private String cvegeo;

	@NotEmpty
	private String alias;

	@NotEmpty
	private String project;

	public String getCvegeo() {
		return cvegeo;
	}

	public void setCvegeo(String cvegeo) {
		this.cvegeo = cvegeo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
