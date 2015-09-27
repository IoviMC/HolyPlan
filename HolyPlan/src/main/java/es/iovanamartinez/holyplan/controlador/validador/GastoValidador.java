package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.GastoForm;

@Component
public class GastoValidador implements Validator {
	private static final int MAX_LONG_NOMBRE = 45;
	private static final int MIN_LONG_NOMBRE = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return GastoForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GastoForm gasto = (GastoForm) target;

		String concepto = gasto.getConcepto();
		String patron = "^[A-Za-z][A-Za-z0-9-_\\.]*$";
		if (concepto == null || concepto.isEmpty()
				|| concepto.length() > MAX_LONG_NOMBRE
				|| concepto.length() < MIN_LONG_NOMBRE) {
			errors.rejectValue("concepto", "error.gasto.concepto.longitud");
		} else if (!concepto.matches(patron)) {
			errors.rejectValue("concepto", "error.gasto.concepto.alfanumerico");
		}

		if (gasto.getImporte() == null) {
			errors.rejectValue("importe", "error.gasto.importe.vacio");
		}
	}
}
