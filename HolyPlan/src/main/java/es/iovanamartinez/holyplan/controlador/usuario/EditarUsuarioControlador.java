package es.iovanamartinez.holyplan.controlador.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.iovanamartinez.holyplan.controlador.formulario.EditarContrasenaForm;
import es.iovanamartinez.holyplan.controlador.formulario.EditarUsuarioForm;
import es.iovanamartinez.holyplan.controlador.validador.EditarContrasenaValidador;
import es.iovanamartinez.holyplan.controlador.validador.EditarEmailValidador;
import es.iovanamartinez.holyplan.controlador.validador.EditarNombreUsuarioValidador;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Controller
public class EditarUsuarioControlador {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EditarEmailValidador editarEmailValidador;
	
	@Autowired
	private EditarNombreUsuarioValidador editarNombreUsuarioValidador;
	
	@Autowired
	private EditarContrasenaValidador editarContrasenaValidador;
	
	@RequestMapping(value = "/private/editarUsuario", method = RequestMethod.GET)
    public String mostrarFormEditarUsuario(ModelMap modelo, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");

        EditarUsuarioForm editarUsuarioForm = new EditarUsuarioForm();
        modelo.addAttribute("editarUsuarioForm", editarUsuarioForm);
        modelo.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		modelo.addAttribute("email", usuario.getEmail());
        
        return "/private/editar-usuario";
    }
	
	@RequestMapping(value = "/private/editarUsuario", method = RequestMethod.POST)
	public String editarUsuario(@ModelAttribute @Valid EditarUsuarioForm editarUsuarioForm, BindingResult result, 
			ModelMap modelo, HttpServletRequest request) {
		boolean emailModificado = false;
		boolean emailValido = false;
		boolean nombreUsuarioModificado = false;
		boolean nombreUsuarioValido = false;
		
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		if (editarUsuarioForm.getEmail().compareTo(usuario.getEmail()) != 0) {
			emailModificado = true;
			editarEmailValidador.validate(editarUsuarioForm, result);
			if (!result.hasErrors()){
				usuario.setEmailTemp(editarUsuarioForm.getEmail());
				usuarioService.guardarEmailTemp(usuario, editarUsuarioForm.getEmail());
				emailValido = true;
				modelo.addAttribute("mensajeCorreo", "Revisa tu correo electr&oacute;nico ("+ usuario.getEmailTemp() + 
						") para confirmar tu nueva direcci&oacute;n. Hasta que la confirmes, las notificaciones " +
						"continuar&aacute;n siendo enviadas a tu direcci&oacute;n de correo electrónico actual.");
				
			}
		}
		
		if (editarUsuarioForm.getNombreUsuario().compareTo(usuario.getNombreUsuario()) != 0) {
			nombreUsuarioModificado = true;
			editarNombreUsuarioValidador.validate(editarUsuarioForm, result);
    		if (!result.hasErrors()){
    			usuario.setNombreUsuario(editarUsuarioForm.getNombreUsuario());
    			usuarioService.modificarNombreUsuario(editarUsuarioForm.getNombreUsuario(), usuario.getId());
    			nombreUsuarioValido = true;
    		}
		}
		
		if (!((emailModificado && !emailValido) || (nombreUsuarioModificado && !nombreUsuarioValido))) {
			session.setAttribute("usuario", usuario);

			modelo.addAttribute("nombreUsuario", usuario.getNombreUsuario());
			modelo.addAttribute("email", usuario.getEmail());
			
			modelo.addAttribute("mensajeCambios", "Cambios guardados con &eacute;xito.");				
		}
		else if (!emailModificado && !nombreUsuarioModificado)
			modelo.addAttribute("mensajeCambios", "No se han efectuado cambios.");
		
		return "/private/editar-usuario";
	}
	
	@RequestMapping(value = "/private/cambiarEmail", method = RequestMethod.GET)
	public String confirmarCambioEmail(@RequestParam("uid") String hash, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		if (usuario != null && usuario.getHash().compareTo(hash)==0) {
			usuarioService.cambiarEmail(usuario.getId());
			usuario.setEmail(usuario.getEmailTemp());
			usuario.setEmailTemp(null);
			session.setAttribute("usuario", usuario);
			return "/private/cambio-email-confirmacion";
		}
		else
			//TODO manejar los errores
			return "error";
	}
	
	@RequestMapping(value = "/private/editarContrasena", method = RequestMethod.GET)
	public String mostrarFormContrasena(ModelMap modelo){
		EditarContrasenaForm editarContrasenaForm = new EditarContrasenaForm();
		modelo.addAttribute("editarContrasenaForm", editarContrasenaForm);
		return "/private/editar-contrasena";
	}
	
	@RequestMapping(value = "/private/editarContrasena", method = RequestMethod.POST)
	public String editarContrasena(@ModelAttribute @Valid EditarContrasenaForm editarContrasenaForm, BindingResult result,
			ModelMap modelo, HttpServletRequest request) {
		
		editarContrasenaValidador.validate(editarContrasenaForm, result);
		if (!result.hasErrors()) {
			HttpSession session = request.getSession();
			UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
			
			usuario.setContrasena(editarContrasenaForm.getContrasenaNueva());
			usuarioService.modificarContrasena(usuario.getId(), editarContrasenaForm.getContrasenaNueva());
			session.setAttribute("usuario", usuario);
			modelo.addAttribute("mensajeContrasena", "La contrase&ntilde;a ha sido modificada con &eacute;xito");
		}
		return "/private/editar-contrasena";
	}
	
	@RequestMapping(value = "/private/desactivarCuenta", method = RequestMethod.GET)
	public String mostrarDesacativarCuenta(ModelMap modelo, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		modelo.addAttribute("hash", usuario.getHash());
		return "/private/desactivar-cuenta";
	}
	
	@RequestMapping(value = "/private/confDesactivarCuenta", method = RequestMethod.GET)
	public String desacativarCuenta(@RequestParam("uid") String hash, ModelMap modelo, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		if (usuario.getHash().compareTo(hash)==0) {
			usuarioService.desactivarUsuario(usuario.getId());
			return "redirect:/";
		}
		else
			//TODO manejar los errores
			return "error";
	}
}

