package tablealias.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LineaTiempoInput {

	@NotNull(message = "no puede ser nulo")
	@Pattern(regexp = "^(?:\\w+,?)+$", message = "mal formato.")
	private String tablas;

	@NotNull(message = "no puede ser nulo")
	private String extent;

	private String proyName;

	public String[] getTablas() {
		return tablas.split(",");
	}

	public void setTablas(String tablas) {
		this.tablas = tablas;
	}

	public String getExtent() {
		return extent;
	}

	public void setExtent(String extent) {
		this.extent = extent;
	}

	public String getProyName() {
		return proyName;
	}

	public void setProyName(String proyName) {
		this.proyName = proyName;
	}

}
