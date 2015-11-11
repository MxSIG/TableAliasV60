package validation.customconstraint.consultadto;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import tablealias.dto.ConsultaDto;

/**
 * Interface for validating (according to JSR 303) a {@link ConsultaDto} object.
 * 
 * @author INEGI
 * 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ConsultaDtoValidator.class })
public @interface ValidConsultaDto {
	String message() default "{Invalid ConsultaDto bean.}";

	Class[] groups() default {};

	Class[] payload() default {};
}
