package es.iovanamartinez.holyplan.controlador.muro;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.CrearMensajeForm;
import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.MuroService;

@Controller
public class AccionesMuroControlador {
	@Autowired
	private MuroService muroService;
	
	private static final int ID_ROL_CREADOR = 1;
	
	@RequestMapping(value = "/muro/mostrarMensajes/{id}", method = RequestMethod.GET)
	public String mostrarMensajeForm(@PathVariable("id") final Integer idTema, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		TemaVo tema = muroService.getTema(idTema);
		if (tema.isActivo()) {
			CrearMensajeForm crearMensajeForm = new CrearMensajeForm();
			
			List<MensajeVo> mensajes = muroService.obtenerMensajes(idTema);
			
			model.addAttribute("tema", tema);
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("mensajes", mensajes);
			model.addAttribute("crearMensajeForm", crearMensajeForm);
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "/private/muro/mostrar-mensajes";
		}
		else
			return "/public/error";
	}
	
	@RequestMapping(value = "/muro/eliminarTema/{idTema}", method = RequestMethod.GET)
	public String eliminarTema(@PathVariable("idTema") final Integer idTema, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			muroService.eliminarTema(idTema);
			return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}		
	}
	
	@RequestMapping(value = "/muro/eliminarMensaje/{idMensaje}/{idTema}", method = RequestMethod.GET)
	public String eliminarMensaje(@PathVariable("idMensaje") final Integer idMensaje, @PathVariable("idTema") final Integer idTema, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			muroService.eliminarMensaje(idMensaje);
			
			return "redirect:/muro/mostrarMensajes/" + idTema;
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
}
