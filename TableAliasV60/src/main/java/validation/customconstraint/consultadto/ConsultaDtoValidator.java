package validation.customconstraint.consultadto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tablealias.dto.ConsultaDto;
import validation.customconstraint.validator.CompositeValidator;

public class ConsultaDtoValidator implements
		ConstraintValidator<ValidConsultaDto, ConsultaDto> {

	@Override
	public void initialize(ValidConsultaDto constraintAnnotation) {
	}

	@Override
	public boolean isValid(ConsultaDto value, ConstraintValidatorContext context) {
		CompositeValidator<ConsultaDto> validator = new CompositeValidator<ConsultaDto>();
		validator.addValidator(new Request1Validator());
		validator.addValidator(new Request2Validator());
		return validator.validate(value, context);
	}
}
