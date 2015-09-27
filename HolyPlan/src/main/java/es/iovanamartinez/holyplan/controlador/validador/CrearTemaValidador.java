package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.CrearTemaForm;

@Component
public class CrearTemaValidador implements Validator{
	private final static int MAX_LONG_TITULO = 50;
	private final static int MIN_LONG_TITULO = 4;
	private final static int MAX_LONG_DESCRIPCION = 250;
	private final static int MIN_LONG_DESCRIPCION = 4;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CrearTemaForm.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		CrearTemaForm crearTema = (CrearTemaForm) target;
		
		String titulo = crearTema.getTitulo();
		String descripcion = crearTema.getDescripcion();
	
		String patron = "^[A-Za-z][A-Za-z0-9-_\\. ]*$";
		if (titulo == null || titulo.isEmpty() || titulo.length() > MAX_LONG_TITULO || titulo.length() < MIN_LONG_TITULO){
			errors.rejectValue("titulo", "error.tema.titulo.longitud");
		}
		else if (!titulo.matches(patron)){
			errors.rejectValue("titulo", "error.tema.titulo.alfanumerico");
		}
		else if (descripcion == null || descripcion.isEmpty() || descripcion.length() > MAX_LONG_DESCRIPCION || descripcion.length() < MIN_LONG_DESCRIPCION){
			errors.rejectValue("descripcion", "error.tema.descripcion.longitud");
		}
	}
}
