package tablealias.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complete")
public class SetComplete {

	private String campo;

	@Value("${servlet.setcomplete.campo}")
	public void setCampo(String campo) {
		this.campo = campo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Map doComplete() {
		System.out.println("in doComleet " + this.campo);
		Map<String, String> m = new HashMap<String, String>();
		m.put("one", "1");
		return m;
	}

	@RequestMapping(value = "/dos", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, String>> doComplete2() {
		System.out.println("in doComleet2 " + this.campo);
		Map<String, String> m = new HashMap<String, String>();
		m.put("one", "1");
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Map<String, String>>(m, headers,
				HttpStatus.OK);
	}

}
