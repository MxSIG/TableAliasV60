package validation.customconstraint.consultadto;

import java.util.Map;

import tablealias.dto.ConsultaDto;
import validation.customconstraint.DtoValidation;

public class Request2Validator implements DtoValidation<ConsultaDto> {

	@Override
	public boolean isValidObject(ConsultaDto obj) {
		return obj.getTabla() != null && obj.getWidth() != null
				&& obj.getPoligono() != null && obj.getGid() != null;
	}

	@Override
	public void getValidationMessages(ConsultaDto value,
			Map<String, String> msgMap) {
		if (value.getTabla() == null)
			msgMap.put("tabla", "no puede ser nulo");
		// if (value.getWidth() == null)
		// msgMap.put("width", "no puede ser nulo");
		// if (value.getPoligono() == null)
		// msgMap.put("poligono", "no puede ser nulo");
		if (value.getGid() == null)
			msgMap.put("gid", "no puede ser nulo");
	}

}
