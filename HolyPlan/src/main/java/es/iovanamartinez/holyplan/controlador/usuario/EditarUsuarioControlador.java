package es.iovanamartinez.holyplan.controlador.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/usuario/editarUsuario", method = RequestMethod.GET)
    public String mostrarFormEditarUsuario(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");

        EditarUsuarioForm editarUsuarioForm = new EditarUsuarioForm();
        editarUsuarioForm.setEmail(usuario.getEmail());
        editarUsuarioForm.setNombreUsuario(usuario.getNombreUsuario());
        model.addAttribute("editarUsuarioForm", editarUsuarioForm);
        
        EditarContrasenaForm editarContrasenaForm = new EditarContrasenaForm();
		model.addAttribute("editarContrasenaForm", editarContrasenaForm);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        
        return "/private/usuario/editar-usuario";
    }
	
	@RequestMapping(value = "/usuario/editarUsuario", method = RequestMethod.POST)
	public String editarUsuario(@ModelAttribute @Valid EditarUsuarioForm editarUsuarioForm, BindingResult result, 
			ModelMap model, HttpServletRequest request) {
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
				model.addAttribute("mensajeCorreo", "Revisa tu correo electr&oacute;nico ("+ usuario.getEmailTemp() + 
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

//			model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
//			model.addAttribute("email", usuario.getEmail());
			
			model.addAttribute("mensajeCambios", "Cambios guardados con &eacute;xito.");				
		}
		else if (!emailModificado && !nombreUsuarioModificado)
			model.addAttribute("mensajeCambios", "No se han efectuado cambios.");
		
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		EditarContrasenaForm editarContrasenaForm = new EditarContrasenaForm();
		model.addAttribute("editarContrasenaForm", editarContrasenaForm);
		return "/private/usuario/editar-usuario";
	}
	
	@RequestMapping(value = "/usuario/cambiarEmail/{uid}", method = RequestMethod.GET)
	public String confirmarCambioEmail(@PathVariable("uid") final String hash, HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		if (usuario != null && usuario.getHash().compareTo(hash)==0) {
			usuarioService.cambiarEmail(usuario.getId());
			usuario.setEmail(usuario.getEmailTemp());
			usuario.setEmailTemp(null);
			session.setAttribute("usuario", usuario);
			
			model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
			return "/private/usuario/cambio-email-confirmacion";
		}
		else {
			model.addAttribute("mensajeError", "Ha ocurrido un error, no se ha podido cambiar el email");
			return "/public/error";
		}
	}
	
//	@RequestMapping(value = "/usuario/editarContrasena", method = RequestMethod.GET)
//	public String mostrarFormContrasena(ModelMap model){
//		EditarContrasenaForm editarContrasenaForm = new EditarContrasenaForm();
//		model.addAttribute("editarContrasenaForm", editarContrasenaForm);
//		return "/private/usuario/editar-contrasena";
//	}
	
	@RequestMapping(value = "/usuario/editarContrasena", method = RequestMethod.POST)
	public String editarContrasena(@ModelAttribute @Valid EditarContrasenaForm editarContrasenaForm, BindingResult result,
			ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		editarContrasenaValidador.validate(editarContrasenaForm, result);
		if (!result.hasErrors()) {			
			usuario.setContrasena(editarContrasenaForm.getContrasenaNueva());
			usuarioService.modificarContrasena(usuario.getId(), editarContrasenaForm.getContrasenaNueva());
			session.setAttribute("usuario", usuario);
			model.addAttribute("mensajeContrasena", "La contrase&ntilde;a ha sido modificada con &eacute;xito");
		}
		
        EditarUsuarioForm editarUsuarioForm = new EditarUsuarioForm();
        editarUsuarioForm.setNombreUsuario(usuario.getNombreUsuario());
        editarUsuarioForm.setEmail(usuario.getEmail());
        model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        model.addAttribute("editarUsuarioForm", editarUsuarioForm);
		return "/private/usuario/editar-usuario";
	}
	
	@RequestMapping(value = "/usuario/desactivarCuenta", method = RequestMethod.GET)
	public String mostrarDesacativarCuenta(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		model.addAttribute("hash", usuario.getHash());
		return "/private/usuario/desactivar-cuenta";
	}
	
	@RequestMapping(value = "/usuario/confDesactivarCuenta", method = RequestMethod.GET)
	public String desacativarCuenta(@RequestParam("uid") String hash, ModelMap model, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		if (usuario.getHash().compareTo(hash)==0) {
			usuarioService.desactivarUsuario(usuario.getId());
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
			return "redirect:/";
		}
		else {
			model.addAttribute("mensajeError", "Ha ocurrido un error, no se ha podido desactivar la cuenta");
			return "/public/error";
		}
	}
}

