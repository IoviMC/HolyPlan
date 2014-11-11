package es.iovanamartinez.holyplan.controlador.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.EditarContrasenaForm;
import es.iovanamartinez.holyplan.controlador.formulario.EditarUsuarioForm;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Component
public class EditarContrasenaValidador implements Validator{
	private static final int MAX_LONG_CONTRASENA = 45;
	private static final int MIN_LONG_CONTRASENA = 6;
	
	@Autowired
	UsuarioService usuarioService;

	@Override
	public boolean supports(Class<?> clazz) {
		return EditarUsuarioForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String patron;
		EditarContrasenaForm usuario = (EditarContrasenaForm) target;
		String contrasena = usuario.getContrasenaNueva();
		if (contrasena == null || contrasena.isEmpty() || contrasena.length() > MAX_LONG_CONTRASENA || contrasena.length() < MIN_LONG_CONTRASENA)
			errors.rejectValue("contrasenaNueva", "error.usuario.contrasena.longitud");
		else {
			if (usuario.getContrasenaActual().compareTo(usuarioService.getUsuarioActual().getContrasena()) == 0) {
				patron = "^[A-Za-z0-9-_\\.]*$";
				if (!contrasena.matches(patron))
					errors.rejectValue("contrasenaNueva", "error.usuario.contrasena.alfanumerico");
				else {
					String contrasenaConf = usuario.getContrasenaNuevaConf();
					if (contrasenaConf == null || contrasenaConf.isEmpty() || contrasena.compareTo(contrasenaConf) != 0)
						errors.rejectValue("contrasenaNueva", "error.usuario.contrasena.distinta");	
				}
			}
			else
				errors.rejectValue("contrasenaActual", "error.usuario.contrasenaActual.incorrecta");
		}
		
	}

}

