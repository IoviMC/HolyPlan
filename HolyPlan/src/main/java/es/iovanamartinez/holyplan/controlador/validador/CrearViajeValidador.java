package es.iovanamartinez.holyplan.controlador.validador;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.CrearViajeForm;

@Component
public class CrearViajeValidador implements Validator{
	private static final int MAX_LONG_NOMBRE = 30;
	private static final int MIN_LONG_NOMBRE = 2;
	private static final int MAX_LONG_DESCRIPCION = 250;

	@Override
	public boolean supports(Class<?> clazz) {
		return CrearViajeForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CrearViajeForm viaje = (CrearViajeForm) target;
		
		String nombreViaje = viaje.getNombreViaje();
		String descripcion = viaje.getDescripcion();
		String patron = "^[A-Za-z][A-Za-z0-9-_\\.]*$";
		if (nombreViaje == null || nombreViaje.isEmpty() || nombreViaje.length() > MAX_LONG_NOMBRE || nombreViaje.length() < MIN_LONG_NOMBRE){
			errors.rejectValue("nombreViaje", "error.viaje.nombre.longitud");
		}
		else if (!nombreViaje.matches(patron)){
			errors.rejectValue("nombreViaje", "error.viaje.nombre.alfanumerico");
		}
		
		Date fechaViaje = viaje.getFecha();
		if (fechaViaje == null) {
			errors.rejectValue("fecha", "error.viaje.fecha.null");
		}
		else {
			Date fechaActual = new Date();
			if (fechaViaje.before(fechaActual)) {
				errors.rejectValue("fecha", "error.viaje.fecha.anterior");
			}
		}
		
		if (viaje.getDuracion() != null && viaje.getDuracion() < 0) {
			errors.rejectValue("duracion", "error.viaje.duracion.negativa");
		}
		
		if (descripcion != null && !descripcion.isEmpty() && descripcion.length() > MAX_LONG_DESCRIPCION){
			errors.rejectValue("descripcion", "error.viaje.descripcion.longitud");
		}
	}
}
