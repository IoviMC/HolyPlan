package es.iovanamartinez.holyplan.controlador.validador;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.CrearParadaForm;

@Component
public class CrearParadaValidador implements Validator{
	private static final int MAX_LONG_NOMBRE = 45;
	private static final int MIN_LONG_NOMBRE = 2;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CrearParadaForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CrearParadaForm parada = (CrearParadaForm) target;
		
		String nombre = parada.getNombre();
		String lugar = parada.getLugar();
		String patron = "^[A-Za-z][A-Za-z0-9-_\\. ]*$";
		
		if (nombre == null || nombre.isEmpty() || nombre.length() > MAX_LONG_NOMBRE || nombre.length() < MIN_LONG_NOMBRE){
			errors.rejectValue("nombre", "error.viaje.nombre.longitud");
		}
		else if (!nombre.matches(patron)){
			errors.rejectValue("nombre", "error.viaje.nombre.alfanumerico");
		}
		
		if (lugar == null || lugar.isEmpty() || lugar.length() > MAX_LONG_NOMBRE || lugar.length() < MIN_LONG_NOMBRE){
			errors.rejectValue("lugar", "error.viaje.nombre.longitud");
		}
		else if (!lugar.matches(patron)){
			errors.rejectValue("lugar", "error.viaje.nombre.alfanumerico");
		}
		
		Date fecha = parada.getFecha();
		if (fecha == null) {
			errors.rejectValue("fecha", "error.viaje.fecha.null");
		}
//		else {
//			Date fechaActual = new Date();
//			if (fecha.compareTo(fechaActual) < 0) {
//				errors.rejectValue("fecha", "error.viaje.fecha.anterior");
//			}
//		}
	}
}
