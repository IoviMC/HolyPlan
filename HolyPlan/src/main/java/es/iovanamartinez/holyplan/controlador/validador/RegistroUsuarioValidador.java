package es.iovanamartinez.holyplan.controlador.validador;


import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.RegistroUsuarioForm;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Component
public class RegistroUsuarioValidador implements Validator {
	private static final int MAX_LONG_NOMBRE = 30;
	private static final int MAX_LONG_CONTRASENA = 45;
	private static final int MIN_LONG_NOMBRE = 3;
	private static final int MIN_LONG_CONTRASENA = 6;
	private static final int MAX_LONG_EMAIL = 245;

	@Autowired
	private UsuarioService usuarioService;

	public boolean supports(Class<?> clazz) {
		return RegistroUsuarioForm.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		RegistroUsuarioForm usuario = (RegistroUsuarioForm) target;
		String patron;

		String nombreUsuario = usuario.getNombreUsuario();
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

		String contrasena = usuario.getContrasena();
		if (contrasena == null || contrasena.isEmpty() || contrasena.length() > MAX_LONG_CONTRASENA || contrasena.length() < MIN_LONG_CONTRASENA)
			errors.rejectValue("contrasena", "error.usuario.contrasena.longitud");
		else {
			patron = "^[A-Za-z0-9-_\\.]*$";
			if (!contrasena.matches(patron))
				errors.rejectValue("contrasena", "error.usuario.contrasena.alfanumerico");
			else {
				String contrasenaConf = usuario.getContrasenaConf();
				if (contrasenaConf == null || contrasenaConf.isEmpty() || contrasena.compareTo(contrasenaConf) !=0)
					errors.rejectValue("contrasena", "error.usuario.contrasena.distinta");	
			}
		}
		
		String email = usuario.getEmail();
		if (email != null && !email.isEmpty() && email.length() <= MAX_LONG_EMAIL) {
			boolean emailValido = EmailValidator.getInstance().isValid(email);
			if (emailValido) {
				if (usuarioService.getUsuarioPorEmail(email) != null)
					errors.rejectValue("email", "error.usuario.email.existe");
			} else
				errors.rejectValue("email", "error.usuario.email.invalido");
		} else 
			errors.rejectValue("email", "error.usuario.email.longitud");
	}
}

