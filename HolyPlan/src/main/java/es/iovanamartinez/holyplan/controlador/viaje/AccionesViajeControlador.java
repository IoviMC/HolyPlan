package es.iovanamartinez.holyplan.controlador.viaje;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.BuscarUsuarioForm;
import es.iovanamartinez.holyplan.controlador.validador.BuscarUsuarioValidador;
import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class AccionesViajeControlador {
	@Autowired
	ViajeService viajeService;
	
	@Autowired
	private BuscarUsuarioValidador buscarUsuarioValidador;
	
	private static final int ID_ROL_INVITADO = 3;
	private static final int ID_ROL_ORGANIZADOR = 2;
	private static final int ID_ROL_CREADOR = 1;
	private static final int ID_ESTADO_ACEPTADO = 1;
	private static final int ID_ESTADO_PENDIENTE = 0;
	
	@RequestMapping(value = "/viaje/mostrarViaje/{id}", method = RequestMethod.GET)
	public String mostrarViaje(@PathVariable("id") final Integer idViaje, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(usuario.getId(), idViaje);
		
		if (viajeUsuario == null) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			session.setAttribute("viajeUsuario", viajeUsuario);
			
			List<UsuarioVo> usuariosConfirmados = viajeService.obtenerUsuariosPorEstado(idViaje, ID_ESTADO_ACEPTADO);
			List<UsuarioVo> usuariosPendientes = viajeService.obtenerUsuariosPorEstado(idViaje, ID_ESTADO_PENDIENTE);
			List<TemaVo> temas = viajeService.obtenerTemas(idViaje);
			List<CheckListViajeVo> checkLists = viajeService.obtenerCheckLists(idViaje);
			List<PlaningVo> planings = viajeService.getPlanings(idViaje);
			Boolean viajeAceptado = viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0;

			model.addAttribute("viajeAceptado", viajeAceptado);
			model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
			model.addAttribute("checkLists", checkLists);
			model.addAttribute("temas", temas);
			model.addAttribute("usuariosConfirmados", usuariosConfirmados);
			model.addAttribute("usuariosPendientes", usuariosPendientes);
			model.addAttribute("planings", planings);
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("viaje", viajeUsuario.getViaje());

			return "/private/viaje/mostrar-viaje";
		}
	}
	
	@RequestMapping(value = "/viaje/editarEstadoViaje", method = RequestMethod.POST)
	public String editarEstadoViaje(HttpServletRequest request){
		String idEstadoString = request.getParameter("idEstado");
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		Integer idEstado = Integer.parseInt(idEstadoString);
		
		if(viajeUsuario.getEstado().getId().compareTo(idEstado) != 0){
			viajeService.editarEstado(viajeUsuario.getViaje().getId(), viajeUsuario.getUsuario().getId(), idEstado);
			if (idEstado.compareTo(ID_ESTADO_ACEPTADO) != 0){
				return "redirect:/private/menu";
			}
		}
		return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
	}
	
	@RequestMapping(value = "/viaje/organizarUsuarios", method = RequestMethod.GET)
	public String invitarAmigos(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0){
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			List<UsuarioVo> invitados = viajeService.obtenerUsuariosRol(viajeUsuario.getViaje().getId(), ID_ROL_INVITADO);
			List<UsuarioVo> organizadores = viajeService.obtenerUsuariosRol(viajeUsuario.getViaje().getId(), ID_ROL_ORGANIZADOR);
			List<UsuarioVo> amigosNoInvitados = viajeService.obtenerAmigosNoInvitados(viajeUsuario.getUsuario().getId(), viajeUsuario.getViaje().getId());
			BuscarUsuarioForm buscarUsuarioForm = new BuscarUsuarioForm();
			
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			model.addAttribute("invitados", invitados);
			model.addAttribute("organizadores", organizadores);
			model.addAttribute("amigosNoInvitados", amigosNoInvitados);
			model.addAttribute("buscarUsuarioForm", buscarUsuarioForm);
			
			return "/private/viaje/organizar-usuarios";
		}
	}
	
	@RequestMapping(value = "/viaje/invitarAmigos", method = RequestMethod.POST)
	public String invitarAmigos(HttpServletRequest request){
		String[] amigosIdString = request.getParameterValues("amigosId");
		HttpSession session = request.getSession();

		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		viajeService.andadirUsuarios(amigosIdString, viajeUsuario.getViaje().getId());
//		List<UsuarioVo> amigos = viajeService.obtenerAmigosNoInvitados(viajeUsuario.getUsuario().getId(), viajeUsuario.getViaje().getId());
//		model.addAttribute("amigos", amigos);
		
		return "redirect:/viaje/organizarUsuarios";
	}
	
	@RequestMapping(value = "/viaje/invitarUsuario", method = RequestMethod.POST)
	public String invitarUsuarios(@ModelAttribute @Valid BuscarUsuarioForm buscarUsuarioForm, BindingResult result, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0){
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			buscarUsuarioValidador.validate(buscarUsuarioForm, result);
			if (!result.hasErrors()) {
				ViajeUsuarioVo viajeUsuarioVo = viajeService.getViajeUsuario(buscarUsuarioForm.getNombreUsuario(), viajeUsuario.getViaje().getId());
				if (viajeUsuarioVo == null)
					viajeService.anadirUsuario(buscarUsuarioForm.getNombreUsuario(), viajeUsuario.getViaje().getId());
			}
		}
		return "redirect:/viaje/organizarUsuarios";
	}
	
	@RequestMapping(value = "/viaje/agregarOrganizador/{idOrganizador}", method = RequestMethod.GET)
	public String agregarOrganizador(@PathVariable("idOrganizador") final Integer idOrganizador, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			viajeService.anadirOrganizador(idOrganizador, viajeUsuario.getViaje().getId());
			
			return "redirect:/viaje/organizarUsuarios";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
	
	@RequestMapping(value = "/viaje/eliminarInvitado/{idInvitado}", method = RequestMethod.GET)
	public String eliminarInvitado(@PathVariable("idInvitado") final Integer idUsuario, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			viajeService.eliminarUsuario(idUsuario, viajeUsuario.getViaje().getId());			
			return "redirect:/viaje/organizarUsuarios";
		}
	}
	
	@RequestMapping(value = "/viaje/eliminarOrganizador/{idOrganizador}", method = RequestMethod.GET)
	public String eliminarOrganizador(@PathVariable("idOrganizador") final Integer idUsuario, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			viajeService.eliminarUsuario(idUsuario, viajeUsuario.getViaje().getId());			
			return "redirect:/viaje/organizarUsuarios";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
	
	@RequestMapping(value = "/viaje/cancelarViaje", method = RequestMethod.GET)
	public String cancelarViaje(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0){
			viajeService.cancelarViaje(viajeUsuario.getViaje().getId());
			return "redirect:/private/menu";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
	

//	@RequestMapping(value = "/viaje/agregarOrganizadores", method = RequestMethod.GET)
//	public String agregarOrganizadores(ModelMap model, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
//		
//		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {		
//			List<UsuarioVo> invitados = viajeService.obtenerUsuariosRol(viajeUsuario.getUsuario().getId(), viajeUsuario.getViaje().getId(), ID_ROL_INVITADO);
//	
//			model.addAttribute("invitados", invitados);	
//			return "/private/viaje/agregar-organizadores";
//		}
//		else {
//			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
//			return "/public/error";
//		}
//	}
	
	
	
	
//	@RequestMapping(value = "/viaje/agregarOrganizadores", method = RequestMethod.POST)
//	public String agregarOrganizadores(HttpServletRequest request, ModelMap model){
//		String[] invitadosIdString = request.getParameterValues("invitadosId");
////		String idViajeString = request.getParameter("idViaje");
//		
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
//		Integer idViaje = viajeUsuario.getViaje().getId();
//		
//		viajeService.andadirOrganizadores(invitadosIdString, idViaje);
////		List<UsuarioVo> amigos = viajeService.obtenerAmigosNoOrganizadores(usuario.getId(), idViaje);
////		model.addAttribute("amigos", amigos);
////		model.addAttribute("mensajeConfirmacion", "Organizadores agregados con &eacute;xito");
//		return "redirect:/viaje/mostrarViaje/" + idViaje;
//	}
	
//	@RequestMapping(value = "/viaje/editarBote", method = RequestMethod.GET)
//	public String editarBoteForm(HttpServletRequest request, ModelMap model){
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
//
//		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0){
//			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
//			return "/public/error";	
//		}
//		else {
//			EditarBoteForm editarBoteForm = new EditarBoteForm();
//			editarBoteForm.setBote(viajeUsuario.getViaje().getBote());
//			model.addAttribute("editarBoteForm", editarBoteForm);
//			model.addAttribute("idRol", viajeUsuario.getRol().getId());
//			return "private/viaje/modificar-bote";
//		}
//	}
//	
//	@RequestMapping(value = "/viaje/editarBote", method = RequestMethod.POST)
//	public String editarBote(@ModelAttribute EditarBoteForm editarBoteForm, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
//	
//		viajeService.editarBote(viajeUsuario.getViaje().getId(), editarBoteForm.getBote());		
//		return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
//	}
}
