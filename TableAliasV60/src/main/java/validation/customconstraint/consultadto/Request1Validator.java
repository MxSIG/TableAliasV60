package validation.customconstraint.consultadto;

import java.util.Map;

import tablealias.dto.ConsultaDto;
import validation.customconstraint.DtoValidation;

public class Request1Validator implements DtoValidation<ConsultaDto> {

	@Override
	public boolean isValidObject(ConsultaDto obj) {
		return obj.getTabla() != null && obj.getX1() != null
				&& obj.getPagina() != null && obj.getY1() != null;
		// && obj.getWidth() != null;
	}

	@Override
	public void getValidationMessages(ConsultaDto value,
			Map<String, String> msgMap) {
		if (value.getTabla() == null)
			msgMap.put("tabla", "no puede ser nulo");
		// if (value.getX1() == null)
		// msgMap.put("x1", "no puede ser nulo");
		// if (value.getPagina() == null) {
		// msgMap.put("pagina", "no puede ser nulo");
		// }
		// if (value.getY1() == null)
		// msgMap.put("y1", "no puede ser nulo");
		// if (value.getWidth() == null)
		// msgMap.put("width", "no puede ser nulo");
	}

}
