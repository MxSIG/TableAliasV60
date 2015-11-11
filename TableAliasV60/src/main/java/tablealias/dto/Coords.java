/**
 * 
 */
package tablealias.dto;

import javax.validation.constraints.NotNull;

/**
 * @author INEGI
 *
 */
public class Coords {

	@NotNull
	private double x;

	@NotNull
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
