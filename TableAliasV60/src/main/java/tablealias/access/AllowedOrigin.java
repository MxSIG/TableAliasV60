package tablealias.access;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "allow-origin")
public class AllowedOrigin implements Iterable<Origin> {

	private List<Origin> origins;

	@XmlElement(name = "origin")
	public List<Origin> getOrigins() {
		return origins;
	}

	public void setOrigins(List<Origin> origins) {
		this.origins = origins;
	}

	@Override
	public Iterator<Origin> iterator() {
		return origins.iterator();
	}

	public Origin get(int idx) {
		return origins.get(idx);
	}

	private int indexOf(String origin) {
		for (int x = 0; x < size(); x++) {
			Origin o = origins.get(x);
			if (o.getUrl().equals(origin)) {
				return x;
			}
		}
		return -1;
	}

	public Origin get(String origin) {
		int idx = indexOf(origin);
		if (idx != -1)
			return origins.get(idx);
		return null;
	}

	public int size() {
		return origins.size();
	}

	public boolean containsOrigin(String origin) {
		return indexOf(origin) != -1;
	}

}
