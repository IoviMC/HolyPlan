package es.iovanamartinez.holyplan.controlador.publico;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.iovanamartinez.holyplan.controlador.formulario.RegistroUsuarioForm;
import es.iovanamartinez.holyplan.controlador.validador.RegistroUsuarioValidador;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Controller
public class RegistroUsuarioControlador {	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RegistroUsuarioValidador registroUsuarioValidador;
	
	@RequestMapping(value = "/public/registroUsuario", method = RequestMethod.GET)
    public String mostrarForm(ModelMap model){
		RegistroUsuarioForm registroUsuarioForm = new RegistroUsuarioForm();
        model.addAttribute("registroUsuarioForm", registroUsuarioForm);
        
        return "/public/registro-usuario";
    }
	
	@RequestMapping(value = "/public/registroUsuario", method = RequestMethod.POST)
	public String procesarFormulario(@ModelAttribute @Valid RegistroUsuarioForm registroUsuarioForm,
	BindingResult result, ModelMap model, HttpServletRequest request,
	HttpServletResponse response) throws IOException {

		registroUsuarioValidador.validate(registroUsuarioForm, result);
		if (!result.hasErrors()) {
			
			UsuarioVo usuarioVo = new UsuarioVo(registroUsuarioForm.getNombreUsuario(), registroUsuarioForm.getContrasena(), registroUsuarioForm.getEmail());
			usuarioService.crearUsuario(usuarioVo);
			String mensajeRegistro = "Enhorabuena, ya casi eres miembro de HolyPlan. Se ha enviado un correo electr&oacute;nico a la direcci&oacute;n " +
					"que has indicado en el proceso de registro con las instrucciones necesarias para activar tu cuenta.";
			model.addAttribute("mensajeRegistro", mensajeRegistro);
		}
			
		return "/public/registro-usuario";
	}
	
	@RequestMapping(value = "/public/activar", method = RequestMethod.GET)
    public String activarUsuario(@RequestParam("uid") String hash){
		usuarioService.activarUsuario(hash);
        return "/public/activar";
    }
}

