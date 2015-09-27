package es.iovanamartinez.holyplan.controlador.planing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.EditarPlaningForm;
import es.iovanamartinez.holyplan.controlador.validador.EditarPlaningValidador;
import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.PlaningService;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class AccionesPlaningControlador {
	
	@Autowired
	private EditarPlaningValidador editarPlaningValidador;
	
	@Autowired
	private PlaningService planingService;
	
	@Autowired
	private ViajeService viajeService;
	
	private static final int ID_ROL_INVITADO = 3;
	private static final int ID_ROL_CREADOR = 1;
	
//	@RequestMapping(value = "/planing/mostrarPlanings", method = RequestMethod.GET)
//	public String mostrarPlanings(HttpServletRequest request, ModelMap model) {
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
//			
//		List<PlaningVo> planings = viajeService.getPlanings(viajeUsuario.getViaje().getId());
//		List<ParadaVo> paradas = planingService.getParadas(planings);
//		
//		model.addAttribute("planings", planings);
//		model.addAttribute("paradas", paradas);
//		model.addAttribute("idRol", viajeUsuario.getRol().getId());
//		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
//        return "/private/planing/mostrar-planings";
//    }
	@RequestMapping(value = "/planing/mostrarPlaning/{idPlaning}", method = RequestMethod.GET)
	public String mostrarPlanings(@PathVariable("idPlaning") final Integer idPlaning, HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
			
		PlaningVo planing = planingService.getPlaning(idPlaning);

		List<ParadaVo> paradas = planingService.getParadas(idPlaning);
		ParadaVo proximaParada = planingService.proximaParada(paradas);
		
		model.addAttribute("planing", planing);
		model.addAttribute("paradas", paradas);
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("proximaParada", proximaParada);
        return "/private/planing/mostrar-planing";
    }
	
	@RequestMapping(value = "/planing/modificarPlaning/{idPlaning}", method = RequestMethod.GET)
	public String modificarPlaningForm(@PathVariable("idPlaning") final Integer idPlaning, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";	
		}
		else {
			PlaningVo planing = planingService.getPlaning(idPlaning);
			EditarPlaningForm editarPlaningForm = new EditarPlaningForm(planing);
			
			List<ParadaVo> paradas = planingService.getParadas(idPlaning);
			
			model.addAttribute("paradas", paradas);
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("editarPlaningForm", editarPlaningForm);
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "private/planing/modificar-planing";
		}
	}
	
	@RequestMapping(value = "/planing/modificarPlaning",  method = RequestMethod.POST)
	public String modificarPlaning(@ModelAttribute EditarPlaningForm editarPlaningForm, BindingResult result, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		editarPlaningValidador.validate(editarPlaningForm, result);
		if (!result.hasErrors()) {
			planingService.editarPlaning(editarPlaningForm.getId(), editarPlaningForm.getNombre(), editarPlaningForm.getDescripcion());
			
			List<ParadaVo> paradas = planingService.getParadas(editarPlaningForm.getId());
			
			model.addAttribute("paradas", paradas);
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("editarPlaningForm", editarPlaningForm);
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			model.addAttribute("mensajeConfirmacion", "Cambios guardados con &eacute;xito");
		}
		return "private/planing/modificar-planing";
	}
	
	@RequestMapping(value = "/planing/eliminarPlaning/{idPlaning}", method = RequestMethod.GET)
	public String eliminarPlaning(@PathVariable("idPlaning") final Integer idPlaning, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) != 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";	
		}
		else {
			planingService.eliminarPlaning(idPlaning, viajeUsuario.getViaje().getId());
	        return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
		}
    }
}
