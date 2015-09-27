package es.iovanamartinez.holyplan.controlador.usuario;

import java.util.List;

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

import es.iovanamartinez.holyplan.controlador.formulario.BuscarUsuarioForm;
import es.iovanamartinez.holyplan.controlador.validador.BuscarUsuarioValidador;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Controller
public class AccionesUsuarioControlador {
	@Autowired
	private BuscarUsuarioValidador buscarUsuarioValidador;
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/usuario/amigos", method = RequestMethod.GET)
	public String mostrarAmigos(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		List<UsuarioVo> amigos = usuarioService.obtenerAmigos(usuario.getId());
		
		BuscarUsuarioForm buscarUsuarioForm = new BuscarUsuarioForm();
		
		model.addAttribute("amigos", amigos);
		model.addAttribute("buscarUsuarioForm", buscarUsuarioForm);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		
		return "/private/usuario/amigos";
	}
	
	@RequestMapping(value = "/usuario/anadirAmigo", method = RequestMethod.POST)
	public String buscarAmigo(@ModelAttribute @Valid BuscarUsuarioForm buscarUsuarioForm, BindingResult result, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		buscarUsuarioValidador.validate(buscarUsuarioForm, result);
		if (!result.hasErrors()) {
			UsuarioVo usuarioBuscado = usuarioService.getUsuarioPorNombre(buscarUsuarioForm.getNombreUsuario());
//			UsuarioVo usuarioAmigo = usuarioService.buscarAmigoPorNombre(usuario.getId(), buscarUsuarioForm.getNombreUsuario());			

//			if (usuarioAmigo == null) {
//				usuarioAmigo = usuarioService.anadirAmigo(usuario.getId(), buscarUsuarioForm.getNombreUsuario());
//				model.addAttribute("mensajeConfirmacion", "El usuario ha sido a&ntilde;adido a sus amigos");
//			}
//			else
//				model.addAttribute("mensajeConfirmacion", "El usuario ya forma parte de sus amigos");
			
		//	if (usuarioAmigo == null) {
			if (usuarioBuscado != null && !usuarioService.esUsuarioAmigo(usuario.getId(), usuarioBuscado.getId())) {
				usuarioService.anadirAmigo(usuario.getId(), usuarioBuscado.getId());
			}
		}
		List<UsuarioVo> amigos = usuarioService.obtenerAmigos(usuario.getId());
		
		model.addAttribute("amigos", amigos);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		return "/private/usuario/amigos";
	}
	
	@RequestMapping(value = "/usuario/eliminarAmigo", method = RequestMethod.POST)
	public String EliminarAmigo(HttpServletRequest request, ModelMap model){
		String idAmigoString = request.getParameter("idAmigo");		

		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		usuarioService.eliminarAmigo(Integer.parseInt(idAmigoString), usuario.getId());			

		return "redirect:/usuario/amigos";
	}
}
