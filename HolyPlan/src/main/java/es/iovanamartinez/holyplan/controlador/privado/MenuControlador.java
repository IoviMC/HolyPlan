package es.iovanamartinez.holyplan.controlador.privado;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.UsuarioService;

@Controller
public class MenuControlador {
	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(value = "/private/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession(true);
		UsuarioVo usuarioActual = usuarioService.getUsuarioActual();
		session.setAttribute("usuario", usuarioActual);
		
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
        return "/private/menu-usuario";
    }
	
	@RequestMapping(value = "/private/menu", method = RequestMethod.GET)
	public String menu(HttpServletRequest request, ModelMap model) {
//		HttpSession session = request.getSession(true);
//		UsuarioVo usuarioActual = usuarioService.getUsuarioActual();
//		if (usuarioActual != null){
//			session.setAttribute("usuario", usuarioActual);
//			
//			UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
//			model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
//		}
		HttpSession session = request.getSession(true);
		UsuarioVo usuarioActual = (UsuarioVo) session.getAttribute("usuario");
		
		model.addAttribute("nombreUsuario", usuarioActual.getNombreUsuario());
        return "/private/menu-usuario";
    }
}
