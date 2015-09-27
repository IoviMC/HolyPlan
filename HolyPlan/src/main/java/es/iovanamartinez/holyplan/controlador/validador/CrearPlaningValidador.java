package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.CrearPlaningForm;

@Component
public class CrearPlaningValidador implements Validator {
	private static final int MAX_LONG_NOMBRE = 45;
	private static final int MIN_LONG_NOMBRE = 2;
	private static final int MAX_LONG_DESCRIPCION = 500;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CrearPlaningForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CrearPlaningForm planing = (CrearPlaningForm) target;
		
		String nombre = planing.getNombre();
		String descripcion = planing.getDescripcion();
		String patron = "^[A-Za-z][A-Za-z0-9-_\\. ]*$";
		if (nombre == null || nombre.isEmpty() || nombre.length() > MAX_LONG_NOMBRE || nombre.length() < MIN_LONG_NOMBRE){
			errors.rejectValue("nombre", "error.viaje.nombre.longitud");
		}
		else if (!nombre.matches(patron)){
			errors.rejectValue("nombre", "error.viaje.nombre.alfanumerico");
		}
		
		if (descripcion != null && !descripcion.isEmpty() && descripcion.length() > MAX_LONG_DESCRIPCION){
			errors.rejectValue("descripcion", "error.viaje.descripcion.longitud");
		}	
	}
}
