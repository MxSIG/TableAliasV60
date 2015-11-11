package tablealias.access;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class AllowedOriginMaker {
	@Autowired
	Jaxb2Marshaller marshaller;

	private Resource resource;

	private AllowedOrigin allowedOrigin;

	@PostConstruct
	public void init() {
		StreamSource source = null;
		try {
			source = new StreamSource(resource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.allowedOrigin = (AllowedOrigin) marshaller.unmarshal(source);
	}

	public AllowedOrigin getAllowedOrigin() {
		return allowedOrigin;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
