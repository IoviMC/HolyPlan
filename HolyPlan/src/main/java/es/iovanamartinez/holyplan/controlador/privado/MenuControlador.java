package es.iovanamartinez.holyplan.controlador.privado;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.UsuarioService;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class MenuControlador {
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ViajeService viajeService;
	
	@RequestMapping(value = "/private/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model){
		UsuarioVo usuario = usuarioService.getUsuarioActual();
		
		HttpSession session = request.getSession(true);
		session.setAttribute("usuario", usuario);
		
		List<ViajeVo> viajes = usuarioService.obtenerProximosViajes(usuario.getId());
		
		model.addAttribute("viajes", viajes);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        return "/private/menu-usuario";
    }
	
	@RequestMapping(value = "/private/menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession(true);
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
			
		List<ViajeVo> viajes = usuarioService.obtenerProximosViajes(usuario.getId());
		
		model.addAttribute("viajes", viajes);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		
        return "/private/menu-usuario";
    }
	
	@RequestMapping(value = "/private/menu", method = RequestMethod.POST)
	public String mostrarViaje(@RequestParam("uid") Integer idViaje, ModelMap model){
		
		ViajeVo viaje = viajeService.getViaje(idViaje);
		
		model.addAttribute("viaje", viaje);
		return "/viaje/mostrar-viaje";
	}
}
