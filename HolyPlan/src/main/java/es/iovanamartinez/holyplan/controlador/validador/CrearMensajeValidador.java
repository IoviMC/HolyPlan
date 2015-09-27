package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.CrearMensajeForm;

@Component
public class CrearMensajeValidador implements Validator{
	private final static int MAX_LONG_DESCRIPCION = 250;
	private final static int MIN_LONG_DESCRIPCION = 4;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CrearMensajeForm.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		CrearMensajeForm crearMensaje = (CrearMensajeForm) target;
		
		String descripcion = crearMensaje.getDescripcion();
	
		if (descripcion == null || descripcion.isEmpty() || descripcion.length() > MAX_LONG_DESCRIPCION || descripcion.length() < MIN_LONG_DESCRIPCION){
			errors.rejectValue("descripcion", "error.tema.descripcion.longitud");
		}
	}
}
