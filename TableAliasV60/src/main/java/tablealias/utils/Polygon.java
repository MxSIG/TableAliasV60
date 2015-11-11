package tablealias.utils;

/**
 * 
 * @author INEGI
 */
public class Polygon {

	private final int buffer = 5;
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private String poligono;
	private String geomName;
	private String proy;

	public Polygon() {
		this(0.0, 0.0, 0.0, "the_geom", "900913");
	}

	public Polygon(String poligono, String geomName, String proy) {
		this.poligono = poligono;
		this.geomName = geomName;
		this.proy = proy;
	}

	// private final double res = 0.00001d;

	public Polygon(double x, double y, double res, String geomName, String proy) {
		x1 = x - (buffer * res);
		x2 = x + (buffer * res);

		y1 = y - (buffer * res);
		y2 = y + (buffer * res);
		this.geomName = geomName;
		this.proy = proy;
	}

	/**
	 * @return the x1
	 */
	public double getX1() {
		return x1;
	}

	/**
	 * @return the y1
	 */
	public double getY1() {
		return y1;
	}

	/**
	 * @return the x2
	 */
	public double getX2() {
		return x2;
	}

	/**
	 * @return the y2
	 */
	public double getY2() {
		return y2;
	}

	/**
	 * @param x1
	 *            the x1 to set
	 */
	public void setX1(double x1) {
		this.x1 = x1;
	}

	/**
	 * @param y1
	 *            the y1 to set
	 */
	public void setY1(double y1) {
		this.y1 = y1;
	}

	/**
	 * @param x2
	 *            the x2 to set
	 */
	public void setX2(double x2) {
		this.x2 = x2;
	}

	/**
	 * @param y2
	 *            the y2 to set
	 */
	public void setY2(double y2) {
		this.y2 = y2;
	}

	/**
	 * @return the poligono
	 */
	private String getPoligono() {
		return poligono;
	}

	/**
	 * @param poligono
	 *            the poligono to set
	 */
	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}

	private String getPolygonFromCoords() {
		return "setsrid(makeBox2d(" + "makepoint(" + getX1() + ", " + getY1()
				+ ")" + ",makepoint(" + getX2() + " ," + getY2() + "))," + proy
				+ ")";
	}

	private String getPolygonFromString() {
		return ("setsrid(GeometryFromText('" + poligono + "')," + proy + ")")
				.replace("''", "'");
	}

	private String getPolygonSelect() {
		return "(select " + geomName + " from control.mibuffer where gid = "
				+ poligono + ")";
	}

	public String getFPolygon() {
		return poligono;
	}

	public String getPolygon() {
		if (poligono != null) {
			if (poligono.contains("POLYGON"))
				return getPolygonFromString();
			else {
				return getPolygonSelect();
			}

		} else {
			return getPolygonFromCoords();
		}
	}
}
