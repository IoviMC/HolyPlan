package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.EditarUsuarioForm;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Component
public class EditarNombreUsuarioValidador implements Validator{
	private static final int MAX_LONG_NOMBRE = 30;
	private static final int MIN_LONG_NOMBRE = 3;
	
	@Autowired
	UsuarioService usuarioService;
	
	public boolean supports(Class<?> clazz) {
		return EditarUsuarioForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EditarUsuarioForm usuarioModificado = (EditarUsuarioForm) target;
		String patron;

		String nombreUsuario = usuarioModificado.getNombreUsuario();
		if (nombreUsuario != null && !nombreUsuario.isEmpty() && nombreUsuario.length() <= MAX_LONG_NOMBRE && nombreUsuario.length() >= MIN_LONG_NOMBRE) {
			patron = "^[A-Za-z][A-Za-z0-9-_\\.]*$";
			if (!nombreUsuario.matches(patron))
				errors.rejectValue("nombreUsuario", "error.usuario.nombre.alfanumerico");
			else {
				if (usuarioService.getUsuarioPorNombre(nombreUsuario) != null)
					errors.rejectValue("nombreUsuario", "error.usuario.nombre.existe");
			}
		} else
			errors.rejectValue("nombreUsuario", "error.usuario.nombre.longitud");
	}
}
