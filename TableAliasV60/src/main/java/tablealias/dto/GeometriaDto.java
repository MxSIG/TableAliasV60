/**
 * 
 */
package tablealias.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author INEGI
 *
 */
public class GeometriaDto {

	@NotEmpty
	private String point;

	@NotEmpty
	private String alias;

	@NotEmpty
	private String project;

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
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
