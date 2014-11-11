package es.iovanamartinez.holyplan.controlador.validador;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.iovanamartinez.holyplan.controlador.formulario.EditarUsuarioForm;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Component
public class EditarEmailValidador implements Validator{
	@Autowired
	private UsuarioService usuarioService;
	
	private static final int MAX_LONG_EMAIL = 245;
	
	public boolean supports(Class<?> clazz) {
		return EditarUsuarioForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EditarUsuarioForm usuario = (EditarUsuarioForm) target;
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